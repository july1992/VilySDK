package com.vily.chart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {
    private WareChart mWare_chart;
    private DownHoroBarUi mDown_bar;
    private OvilBarUi ovil_bar;
    private HoroBarUi mHoro_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWare_chart = findViewById(R.id.ware_chart);

        mDown_bar = findViewById(R.id.down_bar);

        ovil_bar = findViewById(R.id.ovil_bar);

        mHoro_bar = findViewById(R.id.horo_bar);

        mDown_bar.initChartData();
        mDown_bar.setData();


        ovil_bar.initChartData();
        ovil_bar.setData();

        mHoro_bar.initChartData();
        mHoro_bar.setData();
    }
}
