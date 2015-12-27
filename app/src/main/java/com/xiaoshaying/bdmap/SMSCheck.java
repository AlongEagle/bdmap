package com.xiaoshaying.bdmap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.Random;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class SMSCheck extends AppCompatActivity {

    private Button button;
    private Button button1;

    private Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smscheck);



        button= (Button) findViewById(R.id.button);
        button1= (Button) findViewById(R.id.button1);
        button1= (Button) findViewById(R.id.button1);



        SMSSDK.initSDK(this, "de94f4670fa2", "9661679b681ebb7e63cf7dd5f6436a04");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chushihua();


            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SMSCheck.this,LoginActivity.class));
            }
        });


        //加载联系人
//        ContactsPage contactsPage = new ContactsPage();
//        contactsPage.show(context);


    }

    private void chushihua() {
        //打开注册页面
        RegisterPage registerPage = new RegisterPage();
        registerPage.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
// 解析注册结果
                if (result == SMSSDK.RESULT_COMPLETE) {
                    @SuppressWarnings("unchecked")
                    HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country");
                    String phone = (String) phoneMap.get("phone");

// 提交用户信息

                    registerUser(country, phone);
                }
            }
        });


        registerPage.show(context);
    }

    private void registerUser(String country, String phone) {
        Random random=new Random();

        String uid;
        String nickname;
        uid= Math.abs(random.nextInt())+"";
        nickname="xiaoshaying";
        SMSSDK.submitUserInfo(uid, nickname, null, country, phone);
    }

}

