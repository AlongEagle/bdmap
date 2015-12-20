package com.xiaoshaying.bdmap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class WebCheck extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_check);

        WebView webView = (WebView) findViewById(R.id.webView);
        String url = "http://61.190.31.163:8080/";
        webView.loadUrl(url);
    }
}
