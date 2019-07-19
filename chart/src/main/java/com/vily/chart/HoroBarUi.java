package com.vily.chart;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.vily.chart.line.MyHoroBarChart;

import java.util.ArrayList;
import java.util.List;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019/7/18
 *  
 **/
public class HoroBarUi extends RelativeLayout {

    private static final String TAG = "HoroBarView";
    private MyHoroBarChart mChart;


    public HoroBarUi(Context context) {
        this(context,null);
    }

    public HoroBarUi(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HoroBarUi(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();

    }

    public void initView() {

        Log.i(TAG, "initView: -------------初始化");
        View view = View.inflate(getContext(), R.layout.item_horo_bar, this);
        mChart = view.findViewById(R.id.chart);


    }

    public void initChartData( ) {
        Log.i(TAG, "initView: -------------初始化数据");
        //初始化图表
        mChart.setDrawBarShadow(false);

        mChart.setDrawValueAboveBar(true);


        mChart.getDescription().setEnabled(false);
        mChart.setMaxVisibleValueCount(40);
        mChart.setPinchZoom(false);


        mChart.setDrawGridBackground(false);


        XAxis xl = mChart.getXAxis();   // X 对应Y轴
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(false);
        xl.setDrawGridLines(false);
        xl.setGranularity(1f);
        xl.setLabelCount(40);
//        xl.setCenterAxisLabels(true);
        xl.setAxisMinimum(0f);
        xl.setValueFormatter(new MyXOnLineFormatter());
        xl.setTextColor(Color.BLACK);

        xl.setTextSize(12f);

        mChart.getAxisLeft().setEnabled(false);



        YAxis yr = mChart.getAxisRight();

        yr.setEnabled(false);


//        mChart.setFitBars(true);
        mChart.animateY(1000);

        mChart.setExtraBottomOffset(10f);
        Legend l = mChart.getLegend();
        l.setEnabled(false);


    }

    public void setData( ) {

        List<Integer> pnums =new ArrayList<>();
        pnums.add(10);
        pnums.add(32);
        pnums.add(54);
        pnums.add(78);
        pnums.add(29);
        pnums.add(62);

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for(int i=0 ;i<pnums.size();i++){
            yVals1.add(new BarEntry(i+1, pnums.get(i)));
        }

        BarDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "");

            set1.setDrawIcons(false);
            set1.setDrawValues(true);
            set1.setValueFormatter(new MyValueFormatter( ));
            set1.setColor(Color.RED);


            BarData data = new BarData(set1);
            data.setValueTextSize(10f);
            data.setValueTextColor(Color.BLACK);
//            data.setBarWidth(0.5f);
            mChart.setData(data);
            mChart.invalidate();
        }

    }



}
