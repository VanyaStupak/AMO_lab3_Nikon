package com.example.amo_lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;
import java.util.function.Function;

public class MainActivity extends AppCompatActivity {
    public static double[] x, xInter;
    public static double[] y, yInter;
    public static double[] xN, xNLines, errorsX1, errorsY1, errorsX2, errorsY2, errorsX3, errorsY3, errorsX4, errorsY4, errorsX5, errorsY5, errors;
    public static double[] yN, yNLines;

    EditText numberOfDots;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numberOfDots = findViewById(R.id.editTextNumber2);
        double RX = 3.1;
        xNLines = new double[30];
        yNLines = new double[30];
        xNLines[0] = 1.0;
        xNLines[xNLines.length - 1] = 5;
        yNLines[0] = 3.46;
        double gap = 4.0 / (xNLines.length - 1);
        for (int i = 1; i < xNLines.length; i++) {
            xNLines[i] = xNLines[i - 1] + gap;
            yNLines[i] = 10 * Math.log(2 * xNLines[i]) / (1 + xNLines[i]);
        }
        errors = new double[5];
        errorsX1 = new double[2];
        errorsX2 = new double[3];
        errorsX3 = new double[5];
        errorsX4 = new double[10];
        errorsX5 = new double[20];
        errorsY1 = new double[2];
        errorsY2 = new double[3];
        errorsY3 = new double[5];
        errorsY4 = new double[10];
        errorsY5 = new double[20];
        fullArr(errorsX1, errorsY1);
        fullArr(errorsX2, errorsY2);
        fullArr(errorsX3, errorsY3);
        fullArr(errorsX4, errorsY4);
        fullArr(errorsX5, errorsY5);

        Function<Double, Double> newtonPolynomial1 = createNewtonPolynomial(errorsX1, errorsY1);
        Function<Double, Double> newtonPolynomial2 = createNewtonPolynomial(errorsX2, errorsY2);
        Function<Double, Double> newtonPolynomial3 = createNewtonPolynomial(errorsX3, errorsY3);
        Function<Double, Double> newtonPolynomial4 = createNewtonPolynomial(errorsX4, errorsY4);
        Function<Double, Double> newtonPolynomial5 = createNewtonPolynomial(errorsX5, errorsY5);

        errors[0] = Math.abs(newtonPolynomial1.apply(RX) - (10 * Math.log(2 * RX) / (1 + RX)));
        errors[1] = Math.abs(newtonPolynomial2.apply(RX) - (10 * Math.log(2 * RX) / (1 + RX)));
        errors[2] = Math.abs(newtonPolynomial3.apply(RX) - (10 * Math.log(2 * RX) / (1 + RX)));
        errors[3] = Math.abs(newtonPolynomial4.apply(RX) - (10 * Math.log(2 * RX) / (1 + RX)));
        errors[4] = Math.abs(newtonPolynomial5.apply(RX) - (10 * Math.log(2 * RX) / (1 + RX)));


    }

    public static double calculateDividedDifferences(double[] x, double[] y, int k) {
        double result = 0;
        for (int j = 0; j <= k; j++) {
            double mul = 1;
            for (int i = 0; i <= k; i++) {
                if (j != i) {
                    mul *= (x[j] - x[i]);
                }
            }
            result += y[j] / mul;
        }
        return result;
    }

    public static Function<Double, Double> createNewtonPolynomial(double[] x, double[] y) {
        double[] divDiff = new double[x.length - 1];
        for (int i = 1; i < x.length; i++) {
            divDiff[i - 1] = calculateDividedDifferences(x, y, i);
        }
        Function<Double, Double> newtonPolynomial = (xVal) -> {
            double result = y[0];
            for (int k = 1; k < x.length; k++) {
                double mul = 1;
                for (int j = 0; j < k; j++) {
                    mul *= (xVal - x[j]);
                }
                result += divDiff[k - 1] * mul;
            }
            return result;
        };
        return newtonPolynomial;
    }


    public void makeGraphs(View v) {
        x = new double[Integer.parseInt(String.valueOf(numberOfDots.getText()))];
        y = new double[x.length];
        x[0] = 1.0;
        x[x.length - 1] = 5;
        y[0] = 3.46;
        double gap = 4.0 / (x.length - 1);
        for (int i = 1; i < x.length; i++) {
            x[i] = x[i - 1] + gap;
            y[i] = (10 * Math.log(2 * x[i])) / (1 + x[i]);
        }
        showInter(v);
        showInterLines(v);
        Intent intent = new Intent(this, Graphs.class);
        startActivity(intent);
    }

    public void showInter(View v) {
        Function<Double, Double> newtonPolynomial = createNewtonPolynomial(x, y);
        xN = new double[x.length];
        yN = new double[y.length];
        for (int i = 0; i < x.length; i++) {
            xN[i] = x[i];
            yN[i] = newtonPolynomial.apply(x[i]);
        }

    }

    public void showInterLines(View v) {
        Function<Double, Double> newtonPolynomial = createNewtonPolynomial(xNLines, yNLines);
        xInter = new double[30];
        yInter = new double[30];
        for (int i = 0; i < xNLines.length; i++) {
            xInter[i] = xNLines[i];
            yInter[i] = newtonPolynomial.apply(xNLines[i]);
        }
    }

    public void fullArr(double[] ex, double[] ey) {
        double gap = 4.0 / (ex.length - 1);
        for (int i = 1; i < ex.length; i++) {
            ex[i] = ex[i - 1] + gap;
            ey[i] = (10 * Math.log(2 * ex[i])) / (1 + ex[i]);
        }
    }


}
