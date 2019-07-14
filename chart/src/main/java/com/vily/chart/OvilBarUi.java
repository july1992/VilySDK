package com.vily.chart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.vily.chart.mychart.MyBarChart;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

/**
 *  * description :   椭圆的圆柱
 *  * Author : Vily
 *  * Date : 2019/7/11
 *  
 **/
public class OvilBarUi extends LinearLayout {

    private static final String TAG = "OvilBarUi";
    private LayoutInflater mInflater;
    private Context mContext;
    private View mView;
    private MyBarChart mChart;


    public OvilBarUi(Context context) {
        this(context, null);
    }

    public OvilBarUi(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OvilBarUi(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    private void initView() {
        mContext = BaseApplication.getContext();
        mInflater = LayoutInflater.from(mContext);
        if (mView == null) {
            mView = mInflater.inflate(R.layout.layout_ovil_bar, this, true);
            mChart = mView.findViewById(R.id.bar);

        }
    }


    public void initChartData() {

        XAxis xAxis = mChart.getXAxis();
        xAxis.setEnabled(true);
        xAxis.setLabelCount(15);

        xAxis.setTextSize(10f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
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
        yAxis.setAxisMinimum(0);

        mChart.getAxisRight().setEnabled(false);
        mChart.setExtraTopOffset(10f);
        mChart.getLegend().setEnabled(false);
        mChart.getDescription().setEnabled(false);
        mChart.setDrawGridBackground(false);
    }


    public void setData() {


        ArrayList<BarEntry> values1 = new ArrayList<>();

        for (int i = 0; i < 31; i++) {
            values1.add(new BarEntry(i, (float) (Math.random() * 100)));

        }

        BarDataSet set1;

        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {

            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(values1);

            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();

        } else {
            // create 4 DataSets
            set1 = new BarDataSet(values1, "Company A");
            set1.setDrawValues(false);
//            set1.setColor(Color.parseColor("#8B0000"));

            set1.setColors(new int[]{Color.parseColor("#36648B"),Color.parseColor("#79CDCD"),Color.parseColor("#FFFAFA")},2000);
            BarData data = new BarData(set1);

            mChart.setData(data);
        }


        mChart.invalidate();
    }
}
