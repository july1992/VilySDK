package com.vily.map;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019/7/10
 *  
 **/
public class MapUtils {


    private static MapUtils sMapUtils;
    private AMap aMap;
    private MyLocationStyle myLocationStyle;

    private boolean isFirstLoc = true;

    public MapUtils() {

        myLocationStyle = new MyLocationStyle();
    }

    public static MapUtils getInstance() {

        synchronized (MapUtils.class) {

            if (sMapUtils == null) {
                sMapUtils = new MapUtils();
            }
        }

        return sMapUtils;
    }

    public AMap getAMap() {

        return aMap;
    }

    public void initMap(MapView view) {
        aMap = view.getMap();
        UiSettings mUiSettings = aMap.getUiSettings();//实例化UiSettings类对象
        mUiSettings.setLogoBottomMargin(-50);

        mUiSettings.setMyLocationButtonEnabled(true); //显示默认的定位按钮



    }

    public void initLocation() {

        myLocationStyle.interval(2000);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);//只定位一次。
        myLocationStyle.showMyLocation(true);
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.setMyLocationEnabled(true);// 可触发定位并显示当前位置
    }



    public void moveCamera(Location location) {

        if (isFirstLoc) {

            //设置缩放级别
            aMap.moveCamera(CameraUpdateFactory.zoomTo(10));
            //将地图移动到定位点
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(location.getLatitude(), location.getLongitude())));

            isFirstLoc = false;

        }

    }
    public void moveCamera(LatLng latLng) {

        //设置缩放级别
        aMap.moveCamera(CameraUpdateFactory.zoomTo(10));
        //将地图移动到定位点
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));

    }


    public void addMarker(String title, double lat, double lon) {
        LatLng latLng = new LatLng(lat, lon);


        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
        markerOption.title(title).snippet("西安市：34.341568, 108.940174");
//
//        markerOption.draggable(true);//设置Marker可拖动


        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(BaseApplication.getContext().getResources(), R.mipmap.ic_launcher)));
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        markerOption.setFlat(true);//设置marker平贴地图效果

        aMap.addMarker(markerOption);
    }

    public void closeMarker() {

    }


    public void drawLine(){

        List<LatLng> latLngs = new ArrayList<LatLng>();
        latLngs.add(new LatLng(34.341568, 108.940174));
        latLngs.add(new LatLng(39.898323,116.057694));
        latLngs.add(new LatLng(39.900430,116.265061));
        latLngs.add(new LatLng(39.955192,116.140092));
        Polyline polyline = aMap.addPolyline(new PolylineOptions().
                addAll(latLngs).width(10).color(Color.argb(255, 1, 1, 1)));

        moveCamera(new LatLng(39.999391,116.135972));
    }

    public void drawCircle(){

        LatLng latLng = new LatLng(39.984059,116.307771);
        aMap.addCircle(new CircleOptions().
                center(latLng).
                radius(1000).
                fillColor(Color.argb(255, 1, 1, 1)).
                strokeColor(Color.argb(255, 1, 1, 1)).
                strokeWidth(15));

        moveCamera(new LatLng(39.984059,116.307771));
    }

    public void onDestroy() {
        if(myLocationStyle!=null){
            myLocationStyle=null;
        }
        isFirstLoc=true;
        if(aMap!=null){
            aMap.clear();
            aMap=null;
        }
    }


    public interface InfoWindowAdapter {
        View getInfoWindow(Marker marker);

        View getInfoContents(Marker marker);
    }
}
