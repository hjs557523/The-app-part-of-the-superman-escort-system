package com.example.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

public class Main5Activity extends FragmentActivity {
    private FragmentManager mFragmentManager;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        //获取FragmentManager
        mFragmentManager = getSupportFragmentManager();

        //获取radioGroup控件
        radioGroup = (RadioGroup) findViewById(R.id.rg);

        //监听点击按钮事件,实现不同Fragment之间的切换
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                Fragment fragment = FragmentFactory.getInstanceByIndex(checkedId);
                transaction.replace(R.id.fl_main, fragment);
                transaction.commit();
            }
        });


    }

}
