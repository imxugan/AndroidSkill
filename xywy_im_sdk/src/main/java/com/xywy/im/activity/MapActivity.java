package com.xywy.im.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.xywy.im.R;

/**
 * AMapV1地图demo总汇
 */
public class MapActivity extends BaseActivity{
    private static final int MSG_LOCATION_TIMEOUT = 1000;
//    private MapView mapView;
//
//    private AMap aMap;
//    private GeocodeSearch mGeocodeSearch;
//    private LocationManagerProxy mLocationManagerProxy;
//    private MyHandler mMyHandler;


    double longitude;
    double latitude;

    public static Intent newIntent(Context context, float longitude, float latitude) {
        Intent intent = new Intent();
        intent.setClass(context, MapActivity.class);
        intent.putExtra("longitude", longitude);
        intent.putExtra("latitude", latitude);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_location);


        Intent intent = getIntent();
        longitude = intent.getFloatExtra("longitude", 0);
        latitude = intent.getFloatExtra("latitude", 0);


    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
//        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
//        mapView.onPause();
        super.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
//        mapView.onDestroy();
        super.onDestroy();
    }


}
