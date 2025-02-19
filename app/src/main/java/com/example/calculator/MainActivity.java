package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView display;
    private String currentNumber = "";
    private String operator = "";
    private double firstOperand = 0;
    private boolean isNewOperation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize display
        display = findViewById(R.id.display);

        // Setup number buttons (0-9)
        setupNumberButton(R.id.btn0, "0");
        setupNumberButton(R.id.btn1, "1");
        setupNumberButton(R.id.btn2, "2");
        setupNumberButton(R.id.btn3, "3");
        setupNumberButton(R.id.btn4, "4");
        setupNumberButton(R.id.btn5, "5");
        setupNumberButton(R.id.btn6, "6");
        setupNumberButton(R.id.btn7, "7");
        setupNumberButton(R.id.btn8, "8");
        setupNumberButton(R.id.btn9, "9");

        // Setup operator buttons
        setupOperatorButton(R.id.btnAdd, "+");
        setupOperatorButton(R.id.btnSubtract, "-");
        setupOperatorButton(R.id.btnMultiply, "×");
        setupOperatorButton(R.id.btnDivide, "÷");

        // Setup special buttons
        Button btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(v -> clearCalculator());

        Button btnEquals = findViewById(R.id.btnEquals);
        btnEquals.setOnClickListener(v -> calculateResult());

        Button btnDot = findViewById(R.id.btnDot);
        btnDot.setOnClickListener(v -> addDecimalPoint());
    }

    // Helper method to setup number buttons
    private void setupNumberButton(int buttonId, final String number) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(v -> {
            if (isNewOperation) {
                currentNumber = number;
                isNewOperation = false;
            } else {
                currentNumber += number;
            }
            updateDisplay();
        });
    }

    // Helper method to setup operator buttons
    private void setupOperatorButton(int buttonId, final String op) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(v -> {
            if (!currentNumber.isEmpty()) {
                if (!operator.isEmpty()) {
                    calculateResult();
                }
                firstOperand = Double.parseDouble(currentNumber);
                operator = op;
                isNewOperation = true;
            }
        });
    }

    // Calculate the result
    private void calculateResult() {
        if (operator.isEmpty() || currentNumber.isEmpty()) {
            return;
        }

        double secondOperand = Double.parseDouble(currentNumber);
        double result = 0;

        switch (operator) {
            case "+":
                result = firstOperand + secondOperand;
                break;
            case "-":
                result = firstOperand - secondOperand;
                break;
            case "×":
                result = firstOperand * secondOperand;
                break;
            case "÷":
                if (secondOperand != 0) {
                    result = firstOperand / secondOperand;
                } else {
                    display.setText("Error");
                    return;
                }
                break;
        }

        // Format result to remove unnecessary decimal places
        currentNumber = formatResult(result);
        operator = "";
        isNewOperation = true;
        updateDisplay();
    }

    // Format the result to remove unnecessary decimal places
    private String formatResult(double result) {
        if (result == (long) result) {
            return String.format("%d", (long) result);
        } else {
            return String.format("%s", result);
        }
    }

    // Add decimal point
    private void addDecimalPoint() {
        if (isNewOperation) {
            currentNumber = "0.";
            isNewOperation = false;
        } else if (!currentNumber.contains(".")) {
            currentNumber += ".";
        }
        updateDisplay();
    }

    // Clear calculator
    private void clearCalculator() {
        currentNumber = "";
        operator = "";
        firstOperand = 0;
        isNewOperation = true;
        updateDisplay();
    }

    // Update display
    private void updateDisplay() {
        display.setText(currentNumber.isEmpty() ? "0" : currentNumber);
    }
}