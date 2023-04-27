package com.example.amo_lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#FFA424BA")));

        numberOfDots = findViewById(R.id.editTextNumber2);
        double RX = 0.4;
        xNLines = new double[9];
        yNLines = new double[9];
        xNLines[0] = 0;
        xNLines[xNLines.length - 1] = 1;
        yNLines[0] = 2;
        double gap = 1.0 / (xNLines.length - 1);
        for (int i = 1; i < xNLines.length; i++) {
            xNLines[i] = xNLines[i - 1] + gap;
            yNLines[i] = Math.pow(xNLines[i], 2) + 2 * Math.exp(xNLines[i]);
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

        double lagrangePolynomial1 = lagrangeInterpolation(errorsX1, errorsY1, RX);
        double lagrangePolynomial2 = lagrangeInterpolation(errorsX2, errorsY2,RX);
        double lagrangePolynomial3 = lagrangeInterpolation(errorsX3, errorsY3,RX);
        double lagrangePolynomial4 = lagrangeInterpolation(errorsX4, errorsY4,RX);
        double lagrangePolynomial5 = lagrangeInterpolation(errorsX5, errorsY5,RX);

        errors[0] = Math.abs(lagrangePolynomial1 - (Math.pow(RX, 2) + 2 * Math.exp(RX)));
        errors[1] = Math.abs(lagrangePolynomial2  - (Math.pow(RX, 2) + 2 * Math.exp(RX)));
        errors[2] = Math.abs(lagrangePolynomial3 - (Math.pow(RX, 2) + 2 * Math.exp(RX)));
        errors[3] = Math.abs(lagrangePolynomial4 - (Math.pow(RX, 2) + 2 * Math.exp(RX)));
        errors[4] = Math.abs(lagrangePolynomial5 - (Math.pow(RX, 2) + 2 * Math.exp(RX)));


    }

    public static double lagrangeInterpolation(double[] x, double[] y, double x_int) {
        double result = 0;
        for (int i = 0; i < x.length; i++) {
            double term = y[i];
            for (int j = 0; j < x.length; j++) {
                if (i != j) {
                    term *= (x_int - x[j]) / (x[i] - x[j]);
                }
            }
            result += term;
        }
        return result;
    }


    public void makeGraphs(View v) {
        x = new double[Integer.parseInt(String.valueOf(numberOfDots.getText()))];
        y = new double[x.length];
        x[0] = 0;
        x[x.length - 1] = 1;
        y[0] = 2;
        double gap = 1.0 / (x.length - 1);
        for (int i = 1; i < x.length; i++) {
            x[i] = x[i - 1] + gap;
            y[i] = (Math.pow(x[i], 2) + 2 * Math.exp(x[i]));
        }
        showInter(v);
        showInterLines(v);
        Intent intent = new Intent(this, Graphs.class);
        startActivity(intent);
    }

    public void showInter(View v) {
        xN = new double[x.length];
        yN = new double[y.length];
        for (int i = 0; i < x.length; i++) {
            xN[i] = x[i];
            yN[i] = lagrangeInterpolation(x,y,x[i]);
        }

    }

    public void showInterLines(View v) {
        xInter = new double[9];
        yInter = new double[9];
        for (int i = 0; i < xNLines.length; i++) {
            xInter[i] = xNLines[i];
            yInter[i] = lagrangeInterpolation(xNLines,yNLines,xNLines[i]);
        }
    }

    public void fullArr(double[] ex, double[] ey) {
        double gap = 1.0 / (ex.length - 1);
        for (int i = 1; i < ex.length; i++) {
            ex[i] = ex[i - 1] + gap;
            ey[i] = (Math.pow(ex[i], 2) + 2 * Math.exp(ex[i]));
        }
    }


}
