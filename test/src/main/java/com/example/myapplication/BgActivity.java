package com.example.myapplication;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.sackcentury.shinebuttonlib.ShineButton;


public class BgActivity extends Activity {

    ShineButton shineButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bg);
        shineButton = (ShineButton) findViewById(R.id.po_image2);
        shineButton.init(BgActivity.this);
    }
}
