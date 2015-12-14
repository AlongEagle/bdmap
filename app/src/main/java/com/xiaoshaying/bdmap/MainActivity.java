package com.xiaoshaying.bdmap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private MapView mMapView ;
    private BaiduMap baiduMap;

    private Button button1;
    private Button button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        //取出titilebar
//        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());

        setContentView(R.layout.activity_main);
        mMapView= (MapView) findViewById(R.id.bmapView);

        //改变地图比例
        MapStatusUpdate msu=MapStatusUpdateFactory.zoomTo(15.0f);
        baiduMap=mMapView.getMap();

        //设置指定的地图比例
        baiduMap.setMapStatus(msu);

        button1= (Button) findViewById(R.id.button1);
        button2= (Button) findViewById(R.id.button2);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
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
        switch (v.getId()){
            case R.id.button1:
                baiduMap.setMapType(baiduMap.MAP_TYPE_SATELLITE);
                break;

            case R.id.button2:
                baiduMap.setMapType(baiduMap.MAP_TYPE_NORMAL);

                break;
        }
    }
}