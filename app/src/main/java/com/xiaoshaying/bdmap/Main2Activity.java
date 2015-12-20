package com.xiaoshaying.bdmap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener{
    private ImageView imageView;
    private ImageView imageView2;


    private Button button1;
    private Button button2;
    private Button button3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        imageView= (ImageView) findViewById(R.id.imageView);
//        imageView2= (ImageView) findViewById(R.id.imageView2);

        button1= (Button) findViewById(R.id.button);
        button2= (Button) findViewById(R.id.button2);
        button3= (Button) findViewById(R.id.button3);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);

        imageView.setImageDrawable(getResources().getDrawable(R.drawable.sc1));
//        imageView2.setImageDrawable(getResources().getDrawable(R.drawable.sc2));


    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.button){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        if(v.getId()==R.id.button2){
            startActivity(new Intent(this, WebCheck.class));
            finish();
        }
        if(v.getId()==R.id.button3){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }
}
