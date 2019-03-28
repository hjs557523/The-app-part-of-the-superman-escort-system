package com.example.myapplication;

import android.app.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.support.v4.app.FragmentTransaction;


public class ContentActivity extends FragmentActivity{

    RadioGroup radioGroup;
    FragmentManager fragManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        fragManager = getSupportFragmentManager();

        //获取radioGroup控件
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        //监听点击按钮事件,实现不同Fragment之间的切换
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction = fragManager.beginTransaction();
                Fragment fragment = ChangeFragment.getInstanceByIndex(checkedId);
                transaction.replace(R.id.fg_main, fragment);
                transaction.commit();
            }
        });

    }
}
