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
                new ColorDrawable(Color.parseColor("#E10DC5")));
        setContentView(R.layout.activity_main);
        numberOfDots = findViewById(R.id.editTextNumber2);
        double RX = 1.3;
        xNLines = new double[25];
        yNLines = new double[25];
        xNLines[0] = 0;
        xNLines[xNLines.length - 1] = 2.0;
        yNLines[0] = 0;
        double gap = 2.0 / (xNLines.length - 1);
        for (int i = 1; i < xNLines.length; i++) {
            xNLines[i] = xNLines[i - 1] + gap;
            yNLines[i] = Math.sin(Math.pow(xNLines[i],2));
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

         double lp1 = lagrangePolynomial(errorsX1, errorsY1,RX);
         double lp2 = lagrangePolynomial(errorsX2, errorsY2,RX);
         double lp3 = lagrangePolynomial(errorsX3, errorsY3,RX);
         double lp4 = lagrangePolynomial(errorsX4, errorsY4,RX);
         double lp5 = lagrangePolynomial(errorsX5, errorsY5,RX);

        errors[0] = Math.abs(lp1 - (Math.sin(Math.pow(RX,2))));
        errors[1] = Math.abs(lp2 - (Math.sin(Math.pow(RX,2))));
        errors[2] = Math.abs(lp3 - (Math.sin(Math.pow(RX,2))));
        errors[3] = Math.abs(lp4 - (Math.sin(Math.pow(RX,2))));
        errors[4] = Math.abs(lp5 - (Math.sin(Math.pow(RX,2))));


    }

    public double lagrangePolynomial(double[] x, double[] y, double t) {
        int n = x.length;
        double p = 0;

        for (int j = 0; j < n; j++) {
            double L = 1;
            for (int i = 0; i < n; i++) {
                if (i != j) {
                    L *= (t - x[i]) / (x[j] - x[i]);
                }
            }
            p += y[j] * L;
        }

        return p;
    }


    public void makeGraphs(View v) {
        x = new double[Integer.parseInt(String.valueOf(numberOfDots.getText()))];
        y = new double[x.length];
        x[0] = 0;
        x[x.length - 1] = 2.0;
        y[0] = 0;
        double gap = 2.0 / (x.length - 1);
        for (int i = 1; i < x.length; i++) {
            x[i] = x[i - 1] + gap;
            y[i] = Math.sin(Math.pow(x[i],2));
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
            yN[i] = lagrangePolynomial(x,y,x[i]);
        }

    }

    public void showInterLines(View v) {
        xInter = new double[25];
        yInter = new double[25];
        for (int i = 0; i < xNLines.length; i++) {
            xInter[i] = xNLines[i];
            yInter[i] = lagrangePolynomial(x,y,xNLines[i]);
        }
    }

    public void fullArr(double[] ex, double[] ey) {
        double gap = 2.0 / (ex.length - 1);
        for (int i = 1; i < ex.length; i++) {
            ex[i] = ex[i - 1] + gap;
            ey[i] = Math.sin(Math.pow(ex[i],2));
        }
    }


}
