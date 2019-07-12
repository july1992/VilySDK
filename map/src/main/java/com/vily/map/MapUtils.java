package com.vily.map;

import android.graphics.BitmapFactory;
import android.location.Location;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019/7/10
 *  
 **/
public  class MapUtils {

    private static AMap aMap;
    private static MapUtils sMapUtils;



    public static MapUtils getInstance(){

        synchronized (MapUtils.class){

            if (sMapUtils == null) {
                sMapUtils = new MapUtils();
            }
        }

        return sMapUtils;
    }

    public AMap getAMap(MapView view){

        synchronized (MapUtils.class){

            if (aMap == null) {
                aMap = view.getMap();
            }

        }

        return aMap;
    }
    public void initMap() {
        UiSettings mUiSettings = aMap.getUiSettings();//实例化UiSettings类对象
        mUiSettings.setLogoBottomMargin(-50);

        mUiSettings.setMyLocationButtonEnabled(true); //显示默认的定位按钮

        aMap.setMyLocationEnabled(true);// 可触发定位并显示当前位置

    }
    public void setLocation(MyLocationStyle myLocationStyle) {

        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style

    }

    private boolean isFirstLoc=true;
    public void moveCamera(Location location ) {

        if (isFirstLoc) {

            //设置缩放级别
            aMap.moveCamera(CameraUpdateFactory.zoomTo(10));
            //将地图移动到定位点
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(location.getLatitude(), location.getLongitude())));

            isFirstLoc = false;

        }

    }


    public void addMarker(String title, double lat, double lon) {
        LatLng latLng=new LatLng(lat,lon);


        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
//        markerOption.title(title).snippet("西安市：34.341568, 108.940174");
//
//        markerOption.draggable(true);//设置Marker可拖动


        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(BaseApplication.getContext().getResources(),R.mipmap.ic_launcher)));
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        markerOption.setFlat(true);//设置marker平贴地图效果

        aMap.addMarker(markerOption);
    }

    public void closeMarker() {

    }


    public interface InfoWindowAdapter {
        View getInfoWindow(Marker marker);
        View getInfoContents(Marker marker);
    }
}
