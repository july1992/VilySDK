package com.vily.chart.line;

import android.content.Context;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.highlight.BarHighlighter;
import com.github.mikephil.charting.highlight.HorizontalBarHighlighter;


/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019/7/18
 *  
 **/
public class MyHoroBarChart extends HorizontalBarChart {


    public MyHoroBarChart(Context context) {
        super(context);
    }

    public MyHoroBarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyHoroBarChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init() {
        super.init();

        mRenderer = new MyHoroBarChartRender(this, mAnimator, mViewPortHandler);

        setHighlighter(new HorizontalBarHighlighter(this));
        getXAxis().setSpaceMin(0.5f);
        getXAxis().setSpaceMax(0.5f);
    }
}
