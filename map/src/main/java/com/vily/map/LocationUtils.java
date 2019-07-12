package com.vily.map;

import com.amap.api.maps.model.MyLocationStyle;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019/7/10
 *  
 **/
public class LocationUtils {

    public static MyLocationStyle myLocationStyle;
    public static LocationUtils sLocationUtils;

    public static LocationUtils getInstance(){
        synchronized (LocationUtils.class){
            if (sLocationUtils == null) {
                sLocationUtils = new LocationUtils();
            }
        }

        return sLocationUtils;
    }

    public MyLocationStyle getMyLocationStyle(){
        synchronized (LocationUtils.class){
            if (myLocationStyle == null) {
                myLocationStyle = new MyLocationStyle();
            }
        }
        return myLocationStyle;
    }

    public void initLocation() {

        myLocationStyle.interval(2000);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);//只定位一次。
        myLocationStyle.showMyLocation(true);
   }
}
