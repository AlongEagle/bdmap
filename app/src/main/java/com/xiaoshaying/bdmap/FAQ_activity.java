package com.xiaoshaying.bdmap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

public class FAQ_activity extends AppCompatActivity {
    private AutoCompleteTextView autotext;
    private ArrayAdapter<String> arrayAdapter;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_activity);
        textView= (TextView) findViewById(R.id.textView2);
        textView.setText("请输入关键字查询\n如：\n参保条件，待遇领取 等");

        autotext =(AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        autotext.setDropDownBackgroundResource(R.color.blue);
        String [] arr={"尧渡镇",	"东流镇",	"大渡口镇",	"胜利镇",	"张溪镇",	"洋湖镇",	"葛公镇",	"香隅镇",	"官港镇",
                "昭潭镇",	"龙泉镇",	"泥溪镇",	"花园里乡",	"木塔乡",	"青山乡",
                "aa","aab","aac","abc","aaccaacbbcc","aaccbcc","中国","心中的梦想","人中的信心中","我的"};
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arr);
        autotext.setAdapter(arrayAdapter);
        autotext.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(FAQ_activity.this,position,Toast.LENGTH_LONG).show();
                textView.setText(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(FAQ_activity.this,"dsfdsfdsds",Toast.LENGTH_LONG).show();
            }
        });


    }

}
