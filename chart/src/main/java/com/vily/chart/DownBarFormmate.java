package com.vily.chart;

import android.util.Log;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.List;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019/7/11
 *  
 **/
public class DownBarFormmate extends ValueFormatter {

    private static final String TAG = "DownBarFormmate";

    public DownBarFormmate(List<String> lendata) {
    }

    public DownBarFormmate() {
    }

    @Override
    public String getFormattedValue(float value) {
        Log.i(TAG, "getFormattedValue: ----:"+value);
        return super.getFormattedValue(value);
    }

    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        Log.i(TAG, "getAxisLabel: -----:"+value);
        return super.getAxisLabel(value, axis);
    }

}
