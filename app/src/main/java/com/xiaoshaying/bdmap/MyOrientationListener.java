package com.xiaoshaying.bdmap;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by Administrator on 2015-12-16.
 */
public class MyOrientationListener implements SensorEventListener {


    private SensorManager mSensorManager;
    private Sensor mSensor;
    private Context mContext;


    private float xLast;

    public void start(){
        mSensorManager= (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);

        //获得方向传感器
        if(mSensorManager!=null){
            mSensor=mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        }


        if(mSensor!=null){
            mSensorManager.registerListener(this,mSensor,SensorManager.SENSOR_DELAY_UI);
        }
    }

    public void stop(){
            mSensorManager.unregisterListener(this);

    }

    public MyOrientationListener(Context context){
        this.mContext=context;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()==Sensor.TYPE_ORIENTATION){

            float x=event.values[SensorManager.DATA_X];
            if(Math.abs(x-xLast)>1.0){
                if(mOnorientationListener!=null){
                    mOnorientationListener.onOrientationChanged(x);

                }

            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public OnOrientationListener getmOnorientationListener() {
        return mOnorientationListener;
    }

    public void setmOnorientationListener(OnOrientationListener mOnorientationListener) {
        this.mOnorientationListener = mOnorientationListener;
    }

    private OnOrientationListener mOnorientationListener;

    public void setmSensorManager(SensorManager mSensorManager) {
        this.mSensorManager = mSensorManager;
    }

    public interface  OnOrientationListener{
        void onOrientationChanged(float x);
    }
}
