package com.example.amo_lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#EC920D")));
        setContentView(R.layout.activity_main);
        numberOfDots = findViewById(R.id.editTextNumber2);
        double RX = 1.3;
        xNLines = new double[34];
        yNLines = new double[34];
        xNLines[0] = 3.0;
        xNLines[xNLines.length - 1] = 6.0;
        yNLines[0] = -0.973;
        double gap = 3.0 / (xNLines.length - 1);
        for (int i = 1; i < xNLines.length; i++) {
            xNLines[i] = xNLines[i - 1] + gap;
            yNLines[i] = Math.cos(xNLines[i]+Math.exp(Math.cos(xNLines[i])));
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

         double newton1 = newtonInterpolation(errorsX1, errorsY1,RX);
         double newton2 = newtonInterpolation(errorsX2, errorsY2,RX);
         double newton3 = newtonInterpolation(errorsX3, errorsY3,RX);
         double newton4 = newtonInterpolation(errorsX4, errorsY4,RX);
         double newton5 = newtonInterpolation(errorsX5, errorsY5,RX);

        errors[0] = Math.abs(newton1 - (Math.cos(RX+Math.exp(Math.cos(RX)))));
        errors[1] = Math.abs(newton2 - (Math.cos(RX+Math.exp(Math.cos(RX)))));
        errors[2] = Math.abs(newton3 - (Math.cos(RX+Math.exp(Math.cos(RX)))));
        errors[3] = Math.abs(newton4 - (Math.cos(RX+Math.exp(Math.cos(RX)))));
        errors[4] = Math.abs(newton5 - (Math.cos(RX+Math.exp(Math.cos(RX)))));


    }
public static double newtonInterpolation(double[] xValues, double[] yValues, double x) {
    int n = xValues.length;
    double[][] f = new double[n][n];

    for (int i = 0; i < n; i++) {
        f[i][0] = yValues[i];
    }

    for (int j = 1; j < n; j++) {
        for (int i = 0; i < n - j; i++) {
            f[i][j] = (f[i + 1][j - 1] - f[i][j - 1]) / (xValues[i + j] - xValues[i]);
        }
    }

    double y = 0.0;
    for (int i = 0; i < n; i++) {
        double prod = f[0][i];
        for (int j = 0; j < i; j++) {
            prod *= (x - xValues[j]);
        }
        y += prod;
    }

    return y;
}



    public void makeGraphs(View v) {
        x = new double[Integer.parseInt(String.valueOf(numberOfDots.getText()))];
        y = new double[x.length];
        x[0] = 3.0;
        x[x.length - 1] = 6.0;
        y[0] = -0.973;
        double gap = 3.0 / (x.length - 1);
        for (int i = 1; i < x.length; i++) {
            x[i] = x[i - 1] + gap;
            y[i] = Math.cos(x[i]+Math.exp(Math.cos(x[i])));
        }
        showInter(v);
        showInterLines(v);
        if(x.length<2) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Кількість точок має бути більше за 1", Toast.LENGTH_SHORT);
            toast.show();
        }else {
            Intent intent = new Intent(this, Graphs.class);
            startActivity(intent);
        }
    }

    public void showInter(View v) {
        xN = new double[x.length];
        yN = new double[y.length];
        for (int i = 0; i < x.length; i++) {
            xN[i] = x[i];
            yN[i] = newtonInterpolation(x,y,x[i]);
        }

    }

    public void showInterLines(View v) {
        xInter = new double[34];
        yInter = new double[34];
        for (int i = 0; i < xNLines.length; i++) {
            xInter[i] = xNLines[i];
            yInter[i] = newtonInterpolation(x,y,xNLines[i]);
        }
    }

    public void fullArr(double[] ex, double[] ey) {
        double gap = 3.0 / (ex.length - 1);
        for (int i = 1; i < ex.length; i++) {
            ex[i] = ex[i - 1] + gap;
            ey[i] = Math.cos(ex[i]+Math.exp(Math.cos(ex[i])));
        }
    }


}
