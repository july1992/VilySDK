package com.vily.map;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MyLocationStyle;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019/7/10
 *  
 **/
public class MapActivity extends AppCompatActivity implements AMap.OnMyLocationChangeListener, AMap.OnMarkerClickListener {


    private static final String TAG = "MapActivity";
    private MapView mMapView;
    private MapUtils mMapUtils;
    private AMap mMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map);

        mMapView = findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);


        initData();

        initListener();
        drawMarker();


    }


    private void initData() {
        mMapUtils = MapUtils.getInstance();

        mMapUtils.initMap(mMapView);
        mMap=mMapUtils.getAMap();
        mMapUtils.initLocation();




    }
    private void initListener() {
        mMap.setOnMyLocationChangeListener(this);
        mMap.setOnMarkerClickListener(this);
    }


    private void drawMarker() {

        String title="sss";
        double lat=34.341568;
        double lon=108.940174;

//        mMapUtils.addMarker(title,lat,lon);
        mMapUtils.drawLine();
        mMapUtils.drawCircle();
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMyLocationChange(Location location) {
        // 获取经纬度信息


        Log.i(TAG, "onMyLocationChange: ---"+location.getLatitude()+"---:"+location.getLongitude());

        mMapUtils.moveCamera(location);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
//        if(mMapUtils!=null){
//            mMapUtils.onDestroy();
//            mMapUtils=null;
//        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }


}
