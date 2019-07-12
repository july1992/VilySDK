package com.vily.chart;

import android.util.Log;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;

/**
 *  * description :  年  下标

 *  
 **/
public class MyXFormatter extends ValueFormatter {

    private static final String TAG = "MyXFormatter";
    private final DecimalFormat mFormat;
    private String suffix;

    public MyXFormatter(String suffix) {
        mFormat = new DecimalFormat("###,###,###,##0.0");
        this.suffix = suffix;
    }

    public MyXFormatter() {
        mFormat = new DecimalFormat("###,###,###,##0.0");
    }

    @Override
    public String getFormattedValue(float value) {
        return mFormat.format(value) + suffix;
    }

    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        Log.i(TAG, "getAxisLabel: ---------value:"+value);

        return (int)value+"";

    }

}
