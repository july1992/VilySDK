package com.vily.chart;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

/**
 *  * description :   15分钟一条数据  总共 96条数据
 *  * Author : Vily
 *  * Date : 2019/7/11
 *  
 **/
public class DownHoroBarUi extends LinearLayout {

    private static final String TAG = "DownHoroBarUi";
    private LayoutInflater mInflater;
    private Context mContext;
    private View mView;
    private BarChart mChart;


    public DownHoroBarUi(Context context) {
        this(context, null);
    }

    public DownHoroBarUi(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DownHoroBarUi(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    private void initView() {
        mContext = BaseApplication.getContext();
        mInflater = LayoutInflater.from(mContext);
        if (mView == null) {
            mView = mInflater.inflate(R.layout.layout_downbar, this, true);
            mChart = mView.findViewById(R.id.bar);

        }
    }


    public void initChartData() {

        XAxis xAxis = mChart.getXAxis();
        xAxis.setEnabled(true);
//        xAxis.setGranularity(1f);
//        xAxis.setCenterAxisLabels(false);
        xAxis.setLabelCount(5);

        xAxis.setAxisLineColor(Color.parseColor("#00FFFFFF"));
        xAxis.setTextSize(10f);
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setAvoidFirstLastClipping(false);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) value);
            }
        });

        YAxis yAxis = mChart.getAxisLeft();
        yAxis.setEnabled(false);
        // 一定要设置 不然X轴和Y轴的0点不相交
        yAxis.setAxisMaximum(0);

        mChart.getAxisRight().setEnabled(false);
        mChart.setExtraTopOffset(10f);
        mChart.getLegend().setEnabled(false);
        mChart.getDescription().setEnabled(false);
        mChart.setDrawGridBackground(false);

    }


    public void setData() {


//        float groupSpace = 0.08f;
//        float barSpace = 0.03f; // x4 DataSet
//        float barWidth = 0.2f; // x4 DataSet
//        // (0.2 + 0.03) * 4 + 0.08 = 1.00 -> interval per "group"

        float groupSpace = 0f;
        float barSpace = 0f; // x4 DataSet
        float barWidth = 0.25f; // x4 DataSet

        int startYear = 0;
        int endYear = 0 + 5;


        ArrayList<BarEntry> values1 = new ArrayList<>();
        ArrayList<BarEntry> values2 = new ArrayList<>();
        ArrayList<BarEntry> values3 = new ArrayList<>();
        ArrayList<BarEntry> values4 = new ArrayList<>();


        for (int i = startYear; i < endYear; i++) {
            values1.add(new BarEntry(i, -(float) (Math.random() * 10)));
            values2.add(new BarEntry(i, -(float) (Math.random() * 10)));
            values3.add(new BarEntry(i, -(float) (Math.random() * 10)));
            values4.add(new BarEntry(i, -(float) (Math.random() * 10)));
        }

        BarDataSet set1, set2, set3, set4;

        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {

            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set2 = (BarDataSet) mChart.getData().getDataSetByIndex(1);
            set3 = (BarDataSet) mChart.getData().getDataSetByIndex(2);
            set4 = (BarDataSet) mChart.getData().getDataSetByIndex(3);
            set1.setValues(values1);
            set2.setValues(values2);
            set3.setValues(values3);
            set4.setValues(values4);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();

        } else {
            // create 4 DataSets
            set1 = new BarDataSet(values1, "Company A");
            set1.setColor(Color.rgb(104, 241, 175));
            set1.setDrawValues(false);
            set2 = new BarDataSet(values2, "Company B");
            set2.setColor(Color.rgb(164, 228, 251));
            set2.setDrawValues(false);
            set3 = new BarDataSet(values3, "Company C");
            set3.setColor(Color.rgb(242, 247, 158));
            set3.setDrawValues(false);
            set4 = new BarDataSet(values4, "Company D");
            set4.setColor(Color.rgb(255, 102, 0));
            set4.setDrawValues(false);

            BarData data = new BarData(set1, set2, set3, set4);

            mChart.setData(data);
        }

        // specify the width each bar should have
        mChart.getBarData().setBarWidth(barWidth);

        // restrict the x-axis range
        mChart.getXAxis().setAxisMinimum(startYear);

        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
        mChart.getXAxis().setAxisMaximum(startYear + mChart.getBarData().getGroupWidth(groupSpace, barSpace) * 5);
        mChart.groupBars(startYear, groupSpace, barSpace);
        mChart.invalidate();
    }
}
