package com.example.amo_lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
                new ColorDrawable(Color.parseColor("#FFCF1306")));
        setContentView(R.layout.activity_main);
        numberOfDots = findViewById(R.id.editTextNumber2);
        double RX = 0.4;
        xNLines = new double[25];
        yNLines = new double[25];
        xNLines[0] = 0;
        xNLines[xNLines.length - 1] = 1.0;
        yNLines[0] = 1.0;
        double gap = 1.0 / (xNLines.length - 1);
        for (int i = 1; i < xNLines.length; i++) {
            xNLines[i] = xNLines[i - 1] + gap;
            yNLines[i] = Math.pow(Math.cos(xNLines[i]),2);
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

         double aitkenInter1 = aitkenInterpolation(errorsX1, errorsY1,RX);
         double aitkenInter2 = aitkenInterpolation(errorsX2, errorsY2,RX);
         double aitkenInter3 = aitkenInterpolation(errorsX3, errorsY3,RX);
         double aitkenInter4 = aitkenInterpolation(errorsX4, errorsY4,RX);
         double aitkenInter5 = aitkenInterpolation(errorsX5, errorsY5,RX);

        errors[0] = Math.abs(aitkenInter1 - (Math.pow(Math.cos(RX),2)));
        errors[1] = Math.abs(aitkenInter2 - (Math.pow(Math.cos(RX),2)));
        errors[2] = Math.abs(aitkenInter3 - (Math.pow(Math.cos(RX),2)));
        errors[3] = Math.abs(aitkenInter4 - (Math.pow(Math.cos(RX),2)));
        errors[4] = Math.abs(aitkenInter5 - (Math.pow(Math.cos(RX),2)));


    }

    public static double aitkenInterpolation(double[] x, double[] y, double xInt) {
        double[][] f = new double[x.length][x.length];

        // заповнення першого стовпця таблиці
        for (int i = 0; i < x.length; i++) {
            f[i][0] = y[i];
        }

        // побудова таблиці
        for (int j = 1; j < x.length; j++) {
            for (int i = 0; i < x.length - j; i++) {
                f[i][j] = ((xInt - x[i + j]) * f[i][j - 1] - (xInt - x[i]) * f[i + 1][j - 1]) / (x[i] - x[i + j]);
            }
        }

        // пошук стовпця з найдешевшою різницею
        int col = -1;
        double minDiff = Double.POSITIVE_INFINITY;
        for (int j = 1; j < x.length; j++) {
            double diff = Math.abs(f[j][j] - f[j - 1][j - 1]);
            if (diff < minDiff) {
                col = j;
                minDiff = diff;
            }
        }

        // результат - значення функції в точці xInt
        return f[0][col];
    }


    public void makeGraphs(View v) {
        x = new double[Integer.parseInt(String.valueOf(numberOfDots.getText()))];
        y = new double[x.length];
        x[0] = 0;
        x[x.length - 1] = 1.0;
        y[0] = 1.0;
        double gap = 1.0 / (x.length - 1);
        for (int i = 1; i < x.length; i++) {
            x[i] = x[i - 1] + gap;
            y[i] = Math.pow(Math.cos(x[i]),2);
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
            yN[i] = aitkenInterpolation(x,y,x[i]);
        }

    }

    public void showInterLines(View v) {
        xInter = new double[25];
        yInter = new double[25];
        for (int i = 0; i < xNLines.length; i++) {
            xInter[i] = xNLines[i];
            yInter[i] = aitkenInterpolation(x,y,xNLines[i]);
        }
    }

    public void fullArr(double[] ex, double[] ey) {
        double gap = 1.0 / (ex.length - 1);
        for (int i = 1; i < ex.length; i++) {
            ex[i] = ex[i - 1] + gap;
            ey[i] = Math.pow(Math.cos(ex[i]),2);
        }
    }


}
