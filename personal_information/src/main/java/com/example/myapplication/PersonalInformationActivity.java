package com.example.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PersonalInformationActivity extends AppCompatActivity {

    TextView name,explain;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);
        name = (TextView)findViewById(R.id.name);
        explain = (TextView)findViewById(R.id.explain);
        name.setTypeface(Typeface.createFromAsset(PersonalInformationActivity.this.getAssets(),"font/nuansequyuanti.ttf"));
        explain.setTypeface(Typeface.createFromAsset(PersonalInformationActivity.this.getAssets(),"font/youyuan.TTF"));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(PersonalInformationActivity.this, ContentActivity.class);
                startActivity(intent);
                //如果不加finish跳转过去之后我们再次点击返回键他会返回到这个页面
                finish();
            }
        },2500);

    }
}
