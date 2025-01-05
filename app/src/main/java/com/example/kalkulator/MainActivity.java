package com.example.kalkulator;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import org.apache.commons.jexl3.*;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private TextView calculation;
    private final StringBuilder input = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);
        calculation = findViewById(R.id.calculation);

        Button button1 = findViewById(R.id.button_1);
        Button button2 = findViewById(R.id.button_2);
        Button button3 = findViewById(R.id.button_3);
        Button button4 = findViewById(R.id.button_4);
        Button button5 = findViewById(R.id.button_5);
        Button button6 = findViewById(R.id.button_6);
        Button button7 = findViewById(R.id.button_7);
        Button button8 = findViewById(R.id.button_8);
        Button button9 = findViewById(R.id.button_9);
        Button button0 = findViewById(R.id.button_0);
        Button buttonPlus = findViewById(R.id.button_plus);
        Button buttonMinus = findViewById(R.id.button_minus);
        Button buttonMultiply = findViewById(R.id.button_multiply);
        Button buttonDivide = findViewById(R.id.button_divide);
        Button buttonClear = findViewById(R.id.button_clear);
        Button buttonEquals = findViewById(R.id.button_equals);
        Button buttonPercent = findViewById(R.id.button_percent);
        Button buttonPlusMinus = findViewById(R.id.button_plus_minus);
        Button buttonParentheses = findViewById(R.id.button_parentheses);
        Button buttonDot = findViewById(R.id.button_dot);
        Button buttonStraighten = findViewById(R.id.button_straighten);

        button1.setOnClickListener(v -> updateCalculation("1"));
        button2.setOnClickListener(v -> updateCalculation("2"));
        button3.setOnClickListener(v -> updateCalculation("3"));
        button4.setOnClickListener(v -> updateCalculation("4"));
        button5.setOnClickListener(v -> updateCalculation("5"));
        button6.setOnClickListener(v -> updateCalculation("6"));
        button7.setOnClickListener(v -> updateCalculation("7"));
        button8.setOnClickListener(v -> updateCalculation("8"));
        button9.setOnClickListener(v -> updateCalculation("9"));
        button0.setOnClickListener(v -> updateCalculation("0"));

        buttonPlus.setOnClickListener(v -> updateCalculation("+"));
        buttonMinus.setOnClickListener(v -> updateCalculation("-"));
        buttonMultiply.setOnClickListener(v -> updateCalculation("*"));
        buttonDivide.setOnClickListener(v -> updateCalculation("/"));

        buttonClear.setOnClickListener(v -> clearCalculation());
        buttonEquals.setOnClickListener(v -> calculateResult());

        buttonPercent.setOnClickListener(v -> updateCalculation("%"));
        buttonPlusMinus.setOnClickListener(v -> togglePlusMinus());
        buttonParentheses.setOnClickListener(v -> updateParentheses());
        buttonDot.setOnClickListener(v -> updateCalculation("."));
        buttonStraighten.setOnClickListener(v -> backspace());
    }

    private void updateCalculation(String value) {
        input.append(value);
        calculation.setText(input.toString());
    }

    private void clearCalculation() {
        input.setLength(0);
        calculation.setText("0");
        display.setText("0");
    }

    private void calculateResult() {
        String expression = input.toString();
        try {
            double result = evaluateExpression(expression);
            display.setText(String.valueOf(result));
            input.setLength(0);
        } catch (Exception e) {
            display.setText("Error");
            input.setLength(0);
        }
    }

    private double evaluateExpression(String expression) {
        JexlEngine jexl = new JexlBuilder().create();
        JexlExpression jexlExpression = jexl.createExpression(expression);
        Object result = jexlExpression.evaluate(null);

        if (result instanceof Number) {
            return ((Number) result).doubleValue();
        } else {
            throw new IllegalArgumentException("Invalid expression");
        }
    }

    private void togglePlusMinus() {
        String currentInput = input.toString();
        if (currentInput.isEmpty()) {
            return;
        }
        if (currentInput.charAt(0) == '-') {
            input.deleteCharAt(0);
        } else {
            input.insert(0, '-');
        }
        calculation.setText(input.toString());
    }

    private void updateParentheses() {
        String currentInput = input.toString();
        if (currentInput.isEmpty() || currentInput.contains("(") && !currentInput.contains(")")) {
            input.append("(");
        } else if (currentInput.contains(")") && !currentInput.contains("(")) {
            input.insert(0, "(");
        } else {
            input.append(")");
        }
        calculation.setText(input.toString());
    }

    private void backspace() {
        if (input.length() > 0) {
            input.deleteCharAt(input.length() - 1);
            calculation.setText(input.toString());
        } else {
            calculation.setText("0");
        }
    }
}
