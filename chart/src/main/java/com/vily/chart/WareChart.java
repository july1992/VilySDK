package com.vily.chart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import java.util.ArrayList;

import androidx.annotation.Nullable;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019/7/11
 *  
 **/
public class WareChart extends LinearLayout {

    private static final String TAG = "WareChart";
    private LayoutInflater mInflater;
    private Context mContext;
    private View mView;

    public LineChart mChart;



    public WareChart(Context context) {
        this(context,null);
    }

    public WareChart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WareChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    private void initView() {
        mContext=BaseApplication.getContext();
        mInflater=LayoutInflater.from(mContext);
        if(mView==null) {
            mView = mInflater.inflate(R.layout.layout_chart, this,true);

            mChart = mView.findViewById(R.id.mLineChar);
        }

        initChart();
        setChartData();
    }


    public void initChart() {


        mContext=BaseApplication.getContext();

        Description description = new Description();
        description.setText("");
        mChart.setDescription(description);
        mChart.setTouchEnabled(true);//设置是否可触摸
        mChart.setNoDataText("当前数据为空");//设置当 chart 为空时显示的描述文字
        mChart.setExtraBottomOffset(10f);

        //图表注解
        mChart.getLegend().setEnabled(false);



        //X轴 建议隐藏，然后自定义一组展示出的数据,如：分钟0 5 10 15 20 30等
        XAxis xAxis = mChart.getXAxis();
        xAxis.setEnabled(true);//设置轴启用或禁用
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(10);
        xAxis.setTextColor(mContext.getResources().getColor(R.color.chart_text));

        xAxis.setAxisMinimum(1);
        xAxis.setAxisMaximum(12);

        xAxis.setValueFormatter(new MyXFormatter());

        // 设置true  就能全部显示出来12个
        xAxis.setLabelCount(12,true);
        xAxis.setAxisLineWidth(1f);
        xAxis.setDrawGridLines(false);//是否展示网格线


        YAxis yAxis = mChart.getAxisLeft();
        yAxis.setEnabled(false);
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxis.setTextSize(10);
        yAxis.setTextColor(mContext.getResources().getColor(R.color.chart_text));
        yAxis.setAxisMinimum(0);
        yAxis.setLabelCount(4, false);
        yAxis.setAxisLineWidth(1f);
        yAxis.setDrawGridLines(false);//是否展示网格线

        mChart.getAxisRight().setEnabled(false);



    }

    private void setChartData() {
        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<Entry> entries2 = new ArrayList<>();

        for (float x = 1; x < 10; x++) {
            entries.add(new Entry(x, (float) (x+Math.random())*20));
            entries2.add(new Entry(x, (float) (x+Math.random())*50));
        }

        mChart.invalidate();
        mChart.notifyDataSetChanged();
        LineDataSet set1;
        LineDataSet set2;

        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {

            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(entries);
            set1.setDrawValues(false);
            set1.setDrawCircles(false);
            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);

            set2 = (LineDataSet) mChart.getData().getDataSetByIndex(1);
            set2.setValues(entries2);
            set2.setDrawValues(false);
            set2.setDrawCircles(false);
            set2.setMode(LineDataSet.Mode.CUBIC_BEZIER);

            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {

            set1 = new LineDataSet(entries, "111");
            set1.setColor(this.mContext.getResources().getColor(R.color.colorAccent));
            set1.setLineWidth(1f);
            //是否绘制阴影
            set1.setDrawFilled(true);
            Drawable drawable = ContextCompat.getDrawable(mContext,R.color.colorAccent);
            set1.setFillDrawable(drawable);
            set1.setValues(entries);
            set1.setDrawValues(false);
            set1.setValueFormatter(new MyValueFormatter(entries.size(),"set1"));
            set1.setDrawCircles(false);
            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);


            set2 = new LineDataSet(entries, "222");
            set2.setColor(this.mContext.getResources().getColor(R.color.colorPrimary));
            set2.setLineWidth(1f);
            //是否绘制阴影
            set2.setDrawFilled(true);
            Drawable drawable2 = ContextCompat.getDrawable(mContext,R.color.colorPrimary);
            set2.setFillDrawable(drawable2);
            set2.setValues(entries2);
            set2.setDrawValues(false);
            set2.setValueFormatter(new MyValueFormatter(entries2.size(),"set2"));
            set2.setDrawCircles(false);
            set2.setMode(LineDataSet.Mode.CUBIC_BEZIER);

            LineData data = new LineData(set2,set1);
            data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(9f);

            mChart.setData(data);
        }


    }

}
