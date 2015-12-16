package com.xiaoshaying.bdmap;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MapView mMapView;
    private BaiduMap baiduMap;


    private Context context;


    private BDLocation bdLocation;
    private LocationClient locationClient;

    private MyLocationListener myLocationListener;

    private boolean isFirstLocation = true;


    //自定义定位图标

    private BitmapDescriptor iconLocation;

    private MyOrientationListener myOrientationListener;


    //方向
    private float currentX;


    private double jingdu;
    private double weidu;

    private Button button1;
    private Button button2;
    private Button button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //取出titilebar
//        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());

        setContentView(R.layout.activity_main);
        mMapView = (MapView) findViewById(R.id.bmapView);

        this.context = this;

        initLocation();

        //改变地图比例
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        baiduMap = mMapView.getMap();

        //设置指定的地图比例
        baiduMap.setMapStatus(msu);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button4 = (Button) findViewById(R.id.button4);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button4.setOnClickListener(this);
        baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);


    }

    private void initLocation() {


        locationClient = new LocationClient(this);
        myLocationListener = new MyLocationListener();

        //注册监听器
        locationClient.registerLocationListener(myLocationListener);

        //设置locationclient
        LocationClientOption locationClientOption = new LocationClientOption();
        locationClientOption.setCoorType("bd09ll");
        locationClientOption.setIsNeedAddress(true);
        locationClientOption.setOpenGps(true);
        locationClientOption.setScanSpan(1000);
        locationClientOption.setIsNeedLocationDescribe(true);
        locationClientOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);

        locationClient.setLocOption(locationClientOption);

//        初始化图标
        iconLocation = BitmapDescriptorFactory.fromResource(R.mipmap.navi_map_gps_locked);

        myOrientationListener =new MyOrientationListener(this);

        myOrientationListener.setmOnorientationListener(new MyOrientationListener.OnOrientationListener() {
            @Override
            public void onOrientationChanged(float x) {
                currentX=x;
            }
        });


//        locationClient.start();


    }


    class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            MyLocationData myLocationData = new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(currentX).latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude()).build();
            baiduMap.setMyLocationData(myLocationData);


            //设置自定义图标
            MyLocationConfiguration myLocationConfiguration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.COMPASS, true, iconLocation);
            baiduMap.setMyLocationConfigeration(myLocationConfiguration);


            jingdu = bdLocation.getLatitude();
            weidu = bdLocation.getLongitude();
//            Toast.makeText(context, bdLocation.getLocType(), Toast.LENGTH_LONG).show();

//            Toast.makeText(MainActivity.this, String.valueOf(jingdu) + "&&&&" + String.valueOf(weidu), Toast.LENGTH_SHORT).show();
            weidu = bdLocation.getLongitude();


            if (isFirstLocation) {

                dingwei(bdLocation.getLatitude(), bdLocation.getLongitude());

                isFirstLocation = false;

//                Toast.makeText(MainActivity.this,bdLocation.getAddrStr(),Toast.LENGTH_LONG).show();
            }


        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        myOrientationListener.start();
        baiduMap.setMyLocationEnabled(true);
        if (!locationClient.isStarted()) {

            locationClient.start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        baiduMap.setMyLocationEnabled(false);
        locationClient.stop();
        myOrientationListener.stop();


    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                baiduMap.setMapType(baiduMap.MAP_TYPE_SATELLITE);
                break;

            case R.id.button2:
                baiduMap.setMapType(baiduMap.MAP_TYPE_NORMAL);

                break;
            case R.id.button4:
                Toast.makeText(this, "jinlaile", Toast.LENGTH_LONG).show();

                dingwei(jingdu, weidu);

        }
    }

    private void dingwei(double jingdu, double weidu) {
        LatLng latLng = new LatLng(jingdu, weidu);

        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(latLng);

        baiduMap.animateMapStatus(mapStatusUpdate);
    }
}