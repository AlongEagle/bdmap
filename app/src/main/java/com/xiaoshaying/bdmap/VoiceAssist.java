package com.xiaoshaying.bdmap;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.turing.androidsdk.HttpRequestWatcher;
import com.turing.androidsdk.InitListener;
import com.turing.androidsdk.TuringApiConfig;
import com.turing.androidsdk.TuringApiManager;
import com.turing.androidsdk.constant.Constant;
import com.turing.androidsdk.tts.TTSListener;
import com.turing.androidsdk.tts.TTSManager;
import com.turing.androidsdk.voice.VoiceRecognizeListener;
import com.turing.androidsdk.voice.VoiceRecognizeManager;

import org.json.JSONException;
import org.json.JSONObject;

/** @author changjingpei @QQ 345775093*/
public class VoiceAssist extends Activity implements VoiceRecognizeListener,
        TTSListener {

    private VoiceRecognizeManager voiceRecognizeManager;
    private TTSManager ttsManager;
    private TuringApiManager mTuringApiManager;


    private boolean ifRecoding=true;

    private TextView tv;

    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_assist);
        tv=(TextView)findViewById(R.id.tv);
        imageView= (ImageView) findViewById(R.id.imageView4);

        initMscAndTTS();

        initTulingApiManager();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("请您说出问题.....");
//                    tv.setTextColor(Color.BLUE);
                tv.setTextSize(40);
                imageView.setImageResource(R.drawable.voice2);
                voiceRecognizeManager.startRecognize(Constant.XunFei);


            }
        });

        ttsManager.startTTS("东至县居保中心欢迎您，请说出您的问题！", Constant.XunFei);
    }

    void showThinking(){
        tv.setText("亲，你说的都是啥啊？让我思考下...");
//                    tv.setTextColor(Color.BLUE);
        tv.setTextSize(38);
    }

    /**
     * 初始化网络接口管理类
     *
     * @author changjingpei
     * @date 2015年11月13日 下午4:19:30
     */
    private void initTulingApiManager() {
        TuringApiConfig turingApiConfig = new TuringApiConfig(this,
                "8f38fcd73c791550329d002a32553b34");
        turingApiConfig.init(this, new InitListener() {

            @Override
            public void onFail() {
                Toast.makeText(VoiceAssist.this, "获取userid失败...", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onComplete() {
                // 获取userid成功，此时，才支持主动请求的功能
//                Toast.makeText(VoiceAssist.this, "获取userid成功...", Toast.LENGTH_SHORT).show();


            }
        });
        mTuringApiManager = new TuringApiManager(turingApiConfig,
                new HttpRequestWatcher() {

                    @Override
                    public void onSuceess(String arg0) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv.setText("亲，你说的都是啥啊？让我思考下...");
//                    tv.setTextColor(Color.BLUE);
                                tv.setTextSize(38);
                            }
                        });

//                        showThinking();

//						Toast.makeText(MainActivity.this, "请求成功 正在转化...", Toast.LENGTH_LONG).show();


                        // api请求内容后，服务器返回数据获取位置
                        try {
                            JSONObject jsonObject = new JSONObject(arg0);
                            if (jsonObject.has("text")) {
                                handler.obtainMessage(1, jsonObject.get("text"))
                                        .sendToTarget();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(String arg0) {
                        Toast.makeText(VoiceAssist.this, "ONeRROR...", Toast.LENGTH_LONG).show();

                    }
                });
    }




    /**
     * 初始化识别和tts
     *
     * @author changjingpei
     * @date 2015年11月13日 下午4:18:46
     */
    private void initMscAndTTS() {
        // 识别管理类
        voiceRecognizeManager = new VoiceRecognizeManager(this, this);
        // tts管理类
        ttsManager = new TTSManager(this, this);
    }

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            switch (msg.what) {
                case 1:
                    ifRecoding=false;


                    voiceRecognizeManager.cancleReconize();


//                    voiceRecognizeManager.stopRecognize();
                    ttsManager.startTTS((String) msg.obj, Constant.XunFei);
                    tv.setText((String) msg.obj);
//                    tv.setTextColor(Color.BLUE);
                    tv.setTextSize(16);

//				Toast.makeText(MainActivity.this, (String)msg.obj, Toast.LENGTH_LONG).show();
                    break;

                default:
                    break;
            }
        };
    };

    @Override
    public void onSpeechCancel() {
    }

    @Override
    public void onSpeechError(int arg0) {
        Toast.makeText(this, "请重新说话", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSpeechFinish() {
//        ifRecoding=true;

        imageView.setImageResource(R.drawable.voice2);
//        Toast.makeText(this, "大白回答完毕", Toast.LENGTH_SHORT).show();
        tv.setText("请您说出问题.....");
//                    tv.setTextColor(Color.BLUE);
        tv.setTextSize(40);
        voiceRecognizeManager.startRecognize(Constant.XunFei);


    }

    @Override
    public void onSpeechPause() {
    }

    @Override
    public void onSpeechProgressChanged() {
    }

    @Override
    public void onSpeechStart() {
        imageView.setImageResource(R.drawable.voice_answer);
//        Toast.makeText(this, "大白正在回答", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRecognizeError(String arg0) {

    }

    @Override
    public void onRecognizeResult(String arg0) {
        // 识别到话语后，将其发向服务器，进行语义分析，并回答
//        Toast.makeText(this, "发送服务器了，正在处理请稍后...", Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, arg0, Toast.LENGTH_LONG).show();

        try {

            System.out.println("-----------------------------1111----------------------------------------");
            tv.setText(arg0);
//            tv.setTextColor(Color.GREEN);
            tv.setTextSize(16);
            mTuringApiManager.requestTuringAPI(arg0);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("---------------------------------------------------------------------");
            e.printStackTrace();
        }
    }

    @Override
    public void onRecordEnd() {
    }

    @Override
    public void onRecordStart() {
    }

    @Override
    public void onStartRecognize() {
        // 仅针对百度识别有效
    }

    @Override
    public void onVolumeChange(int arg0) {
        // 仅针对调用讯飞时有效
//		Toast.makeText(this, "VolumeChange", Toast.LENGTH_LONG).show();
    }





}

