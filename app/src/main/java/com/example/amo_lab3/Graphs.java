package com.example.amo_lab3;

import android.graphics.Color;
import android.os.Bundle;
import android.service.autofill.Dataset;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Graphs extends AppCompatActivity {

    private List<Entry> lineEntries1;
    private List<Entry> lineEntries2;
    private List<Entry> lineEntries1_lines, lineEntries2_lines, errrors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_graphs);

        double[] funcLinesX = new double[80];
        double[] funcLinesY = new double[80];
        funcLinesX[0] = 3.0;
        funcLinesX[funcLinesX.length - 1] = 6.0;
        funcLinesY[0] = -0.973;
        double gap = 3.0 / (funcLinesX.length - 1);
        for (int i = 1; i < funcLinesX.length; i++) {
            funcLinesX[i] = funcLinesX[i - 1] + gap;
            funcLinesY[i] = Math.cos(funcLinesX[i]+Math.exp(Math.cos(funcLinesX[i])));
        }
        lineEntries1_lines = new ArrayList<Entry>();
        lineEntries1 = new ArrayList<Entry>();
        lineEntries2 = new ArrayList<Entry>();
        lineEntries2_lines = new ArrayList<Entry>();
        errrors = new ArrayList<>();
        int length = MainActivity.x.length;
        int length_lines = funcLinesX.length;
        int length1 = MainActivity.xN.length;
        int length1_lines = MainActivity.xInter.length;
        int [] xx = new int[5];
        xx[0] = MainActivity.errorsX1.length;
        xx[1] = MainActivity.errorsX2.length;
        xx[2] = MainActivity.errorsX3.length;
        xx[3] = MainActivity.errorsX4.length;
        xx[4] = MainActivity.errorsX5.length;
        for (int i = 0; i < 5; i++) {
            errrors.add(new Entry((float) xx[i], (float) MainActivity.errors[i]));
        }

        for (int i = 0; i < length_lines; i++) {
            lineEntries1_lines.add(new Entry((float) funcLinesX[i], (float) funcLinesY[i]));
        }

        for (int i = 0; i < length; i++) {
            lineEntries1.add(new Entry((float) MainActivity.x[i], (float) MainActivity.y[i]));
        }

        for (int i = 0; i < length1; i++) {
            lineEntries2.add(new Entry((float) MainActivity.xN[i], (float) MainActivity.yN[i]));
        }
        for (int i = 0; i < length1_lines; i++) {
            lineEntries2_lines.add(new Entry((float) MainActivity.xInter[i], (float) MainActivity.yInter[i]));
        }
        drawLineChart1();
        drawLineChart2();
        drawLineChart3();
        drawLineChart4();
    }

    private void drawLineChart1() {
        LineChart lineChart = findViewById(R.id.lineChart1);
        List<Entry> lineEntries = lineEntries1;
        List<Entry> lineEntries_lines = lineEntries1_lines;
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        LineDataSet lineDataSet1 = new LineDataSet(lineEntries, "ФУНКЦІЯ");
        LineDataSet lineDataSet2 = new LineDataSet(lineEntries_lines,"");
        dataSets.add(lineDataSet1);
        dataSets.add(lineDataSet2);

        lineDataSet1.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet1.setHighlightEnabled(true);
        lineDataSet1.setLineWidth(2);
        lineDataSet1.setColor(Color.TRANSPARENT);
        lineDataSet1.setCircleColor(Color.parseColor("#FF2E0096"));
        lineDataSet1.setCircleRadius(6);
        lineDataSet1.setCircleHoleRadius(3);
        lineDataSet1.setDrawHighlightIndicators(true);
        lineDataSet1.setHighLightColor(Color.parseColor("#FF2E0096"));
        lineDataSet1.setDrawValues(true);
        lineDataSet1.setValueTextSize(12);
        lineDataSet1.setValueTextColor(Color.DKGRAY);


        lineDataSet2.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet2.setHighlightEnabled(true);
        lineDataSet2.setLineWidth(2);
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setColor(Color.parseColor("#FF2E0096"));
        lineDataSet2.disableDashedLine();
        lineDataSet2.setHighLightColor(Color.parseColor("#FF2E0096"));
        lineDataSet2.setValueTextSize(12);
        lineDataSet2.setDrawValues(false);
        lineDataSet2.setValueTextColor(Color.DKGRAY);

        LineData lineData = new LineData(dataSets);
        lineChart.getDescription().setText(getString(R.string.X));
        lineChart.getDescription().setTextSize(12);
        lineChart.setDrawMarkers(true);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.animateY(1000);
        lineChart.getXAxis().setGranularityEnabled(true);
        lineChart.getXAxis().setGranularity(0.1f);
        lineChart.getXAxis().setLabelCount(lineDataSet1.getEntryCount());
        lineChart.getXAxis().setLabelCount(lineDataSet2.getEntryCount());
        lineChart.setData(lineData);
    }

    private void drawLineChart2() {
        LineChart lineChart = findViewById(R.id.lineChart2);
        List<Entry> lineEntries = lineEntries2;
        List<Entry> lineEntries_lines = lineEntries2_lines;
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        LineDataSet lineDataSet1 = new LineDataSet(lineEntries, "ІНТЕРПОЛЯЦІЯ");
        LineDataSet lineDataSet2 = new LineDataSet(lineEntries_lines,"");
        dataSets.add(lineDataSet1);
        dataSets.add(lineDataSet2);

        lineDataSet1.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet1.setHighlightEnabled(true);
        lineDataSet1.setLineWidth(2);
        lineDataSet1.setColor(Color.TRANSPARENT);
        lineDataSet1.setCircleColor(Color.GREEN);
        lineDataSet1.setCircleRadius(6);
        lineDataSet1.setCircleHoleRadius(3);
        lineDataSet1.setDrawHighlightIndicators(true);
        lineDataSet1.setHighLightColor(Color.RED);
        lineDataSet1.setValueTextSize(12);
        lineDataSet1.setValueTextColor(Color.DKGRAY);


        lineDataSet2.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet2.setHighlightEnabled(true);
        lineDataSet2.setLineWidth(2);
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setColor(Color.GREEN);
        lineDataSet2.disableDashedLine();
        lineDataSet2.setHighLightColor(Color.GREEN);
        lineDataSet2.setValueTextSize(12);
        lineDataSet2.setDrawValues(false);
        lineDataSet2.setValueTextColor(Color.DKGRAY);

        LineData lineData = new LineData(dataSets);
        lineChart.getDescription().setText(getString(R.string.X));
        lineChart.getDescription().setTextSize(12);
        lineChart.setDrawMarkers(true);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.animateY(1000);
        lineChart.getXAxis().setGranularityEnabled(true);
        lineChart.getXAxis().setGranularity(0.1f);
        lineChart.getXAxis().setLabelCount(lineDataSet1.getEntryCount());
        lineChart.getXAxis().setLabelCount(lineDataSet2.getEntryCount());
        lineChart.setData(lineData);
    }

    private void drawLineChart3() {
        LineChart lineChart = findViewById(R.id.lineChart3);
        List<Entry> lineEntries11 = lineEntries1;
        List<Entry> lineEntries11_lines = lineEntries1_lines;
        List<Entry> lineEntries22 = lineEntries2;
        List<Entry> lineEntries22_lines = lineEntries2_lines;
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        LineDataSet lineDataSet1 = new LineDataSet(lineEntries11, "ФУНКЦІЯ");
        LineDataSet lineDataSet2 = new LineDataSet(lineEntries11_lines,"");
        LineDataSet lineDataSet3 = new LineDataSet(lineEntries22, "ІНТЕРПОЛЯЦІЯ");
        LineDataSet lineDataSet4 = new LineDataSet(lineEntries22_lines,"");
        dataSets.add(lineDataSet1);
        dataSets.add(lineDataSet2);
        dataSets.add(lineDataSet3);
        dataSets.add(lineDataSet4);

        lineDataSet1.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet1.setHighlightEnabled(true);
        lineDataSet1.setLineWidth(2);
        lineDataSet1.setColor(Color.TRANSPARENT);
        lineDataSet1.setCircleColor(Color.parseColor("#FF2E0096"));
        lineDataSet1.setCircleRadius(6);
        lineDataSet1.setCircleHoleRadius(3);
        lineDataSet1.setDrawHighlightIndicators(true);
        lineDataSet1.setHighLightColor(Color.parseColor("#FF2E0096"));
        lineDataSet1.setValueTextSize(12);
        lineDataSet1.setValueTextColor(Color.DKGRAY);

        lineDataSet2.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet2.setHighlightEnabled(true);
        lineDataSet2.setLineWidth(2);
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setColor(Color.parseColor("#FF2E0096"));
        lineDataSet2.disableDashedLine();
        lineDataSet2.setHighLightColor(Color.parseColor("#FF2E0096"));
        lineDataSet2.setValueTextSize(12);
        lineDataSet2.setDrawValues(false);
        lineDataSet2.setValueTextColor(Color.DKGRAY);

        lineDataSet3.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet3.setHighlightEnabled(true);
        lineDataSet3.setLineWidth(2);
        lineDataSet3.setColor(Color.TRANSPARENT);
        lineDataSet3.setCircleColor(Color.GREEN);
        lineDataSet3.setCircleRadius(6);
        lineDataSet3.setCircleHoleRadius(3);
        lineDataSet3.setDrawHighlightIndicators(true);
        lineDataSet3.setHighLightColor(Color.RED);
        lineDataSet3.setValueTextSize(12);
        lineDataSet3.setValueTextColor(Color.DKGRAY);

        lineDataSet4.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet4.setHighlightEnabled(true);
        lineDataSet4.setLineWidth(2);
        lineDataSet4.setDrawCircles(false);
        lineDataSet4.setColor(Color.GREEN);
        lineDataSet4.disableDashedLine();
        lineDataSet4.setHighLightColor(Color.GREEN);
        lineDataSet4.setValueTextSize(12);
        lineDataSet4.setDrawValues(false);
        lineDataSet4.setValueTextColor(Color.DKGRAY);

        LineData lineData = new LineData(dataSets);
        lineChart.getDescription().setText(getString(R.string.X));
        lineChart.getDescription().setTextSize(12);
        lineChart.setDrawMarkers(true);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.animateY(1000);
        lineChart.getXAxis().setGranularityEnabled(true);
        lineChart.getXAxis().setGranularity(0.1f);
        lineChart.getXAxis().setLabelCount(lineDataSet1.getEntryCount());
        lineChart.getXAxis().setLabelCount(lineDataSet2.getEntryCount());
        lineChart.setData(lineData);
    }

    private void drawLineChart4() {
        LineChart lineChart = findViewById(R.id.lineChart4);
        List<Entry> lineEntries = errrors;
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        LineDataSet lineDataSet1 = new LineDataSet(lineEntries, "ПОХИБКИ");
        dataSets.add(lineDataSet1);

        lineDataSet1.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet1.setHighlightEnabled(true);
        lineDataSet1.setLineWidth(2);
        lineDataSet1.setColor(Color.RED);
        lineDataSet1.setCircleColor(Color.RED);
        lineDataSet1.setCircleRadius(6);
        lineDataSet1.setCircleHoleRadius(3);
        lineDataSet1.setDrawHighlightIndicators(true);
        lineDataSet1.setHighLightColor(Color.RED);
        lineDataSet1.setValueTextSize(12);
        lineDataSet1.setValueTextColor(Color.DKGRAY);

        LineData lineData = new LineData(dataSets);
        lineChart.getDescription().setText("КІЛЬКІСТЬ ТОЧОК");
        lineChart.getDescription().setTextSize(12);
        lineChart.setDrawMarkers(true);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.animateY(1000);
        lineChart.getXAxis().setGranularityEnabled(true);
        lineChart.getXAxis().setGranularity(0.1f);
        lineChart.getXAxis().setLabelCount(lineDataSet1.getEntryCount());
        lineChart.setData(lineData);
    }


}