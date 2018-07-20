package com.xywy.im.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.xywy.im.R;


/**
 * AMapV1地图demo总汇
 */
public class LocationPickerActivity extends BaseActivity{
//    private static final int MSG_LOCATION_TIMEOUT = 1000;
//    private MapView mapView;
//
//    private AMap aMap;
//    private View pin;
//    private TextView label;
//    private GeocodeSearch mGeocodeSearch;
//    private LocationManagerProxy mLocationManagerProxy;
//    private MyHandler mMyHandler;


    double longitude = 0;
    double latitude = 0;
    String address;

    private boolean isCameraChanging = false;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, LocationPickerActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_picker);

//        mapView = (MapView) findViewById(R.id.map);
//        mapView.onCreate(savedInstanceState);// 必须要写
//        aMap = mapView.getMap();
//
//        label = (TextView) findViewById(R.id.label);
//        pin = findViewById(R.id.pin);
//        aMap.setOnMapTouchListener(new AMap.OnMapTouchListener() {
//            @Override
//            public void onTouch(MotionEvent motionEvent) {
//                if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
//                    if (!isCameraChanging) {
//                        isCameraChanging = true;
//                        pinUp();
//                        setLabel(null);
//                    }
//                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//                    isCameraChanging = false;
//                    pinDown();
//                    latitude = aMap.getCameraPosition().target.latitude;
//                    longitude = aMap.getCameraPosition().target.longitude;
//                    queryLocation();
//                }
//            }
//        });
//
//        mGeocodeSearch = new GeocodeSearch(this);
//        mGeocodeSearch.setOnGeocodeSearchListener(this);
//
//        // 定位当前位置
//        getLocation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.location_picker, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_send) {
            if (longitude == 0.0 && latitude == 0.0) {
                return true;
            }

            Intent intent = new Intent();
            intent.putExtra("longitude", (float) longitude);
            intent.putExtra("latitude", (float) latitude);
            intent.putExtra("address", address);
            setResult(RESULT_OK, intent);

            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
