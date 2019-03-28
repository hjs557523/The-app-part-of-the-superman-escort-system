package com.example.lenovo.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;

import static android.view.View.*;


public class ContentActivity extends FragmentActivity {

    RadioGroup radioGroup;
    FragmentManager fragManager;
    RadioButton result;
    public static String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        result = (RadioButton) findViewById(R.id.rb_ms);


        Intent intent = this.getIntent();
        name = intent.getStringExtra("name");
        setResult(1, intent);

        fragManager = getSupportFragmentManager();
        //获取radioGroup控件

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        FragmentTransaction beginTransaction = fragManager.beginTransaction();
        TijianFragment fragment = new TijianFragment();
        beginTransaction.replace(R.id.fg_main, fragment);
        beginTransaction.commit();


        //监听点击按钮事件,实现不同Fragment之间的切换
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==result.getId()){
                    callPhone("120");
                }else {
                    FragmentTransaction transaction = fragManager.beginTransaction();
                    Fragment fragment = ChangeFragment.getInstanceByIndex(checkedId);
                    transaction.replace(R.id.fg_main, fragment);
                    transaction.commit();
                }
            }
        });

        requestContact();
       //requestStorage();
        requestPower();

    }

    protected void onResume() {
        int id = getIntent().getIntExtra("userloginflag", 0);

        if (id == 2) {
            FragmentTransaction beginTransaction = fragManager.beginTransaction();
            MyFragment fragment = new MyFragment();
            beginTransaction.replace(R.id.fg_main, fragment);
            beginTransaction.commit();
        }
        super.onResume();
    }

    //获取联系人权限
    private void requestContact() {
        if (PermissionsUtil.hasPermission(this, Manifest.permission.READ_CONTACTS)) {
        } else {
            PermissionsUtil.requestPermission(getApplication(), new PermissionListener() {
                public void permissionGranted(@NonNull String[] permissions) {
                    Toast.makeText(ContentActivity.this, "允许访问通讯录", Toast.LENGTH_SHORT).show();
                }

                public void permissionDenied(@NonNull String[] permissions) {
                    Toast.makeText(ContentActivity.this, "用户拒绝了访问通讯录", Toast.LENGTH_SHORT).show();
                }
            }, Manifest.permission.READ_CONTACTS);
        }
    }
  /*  private void requestStorage() {
       if (PermissionsUtil.hasPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
       } else {
           PermissionsUtil.requestPermission(this.getApplication(), new PermissionListener() {
               public void permissionGranted(@NonNull String[] permissions) {
                   Toast.makeText(ContentActivity.this, "允许访问访问相册", Toast.LENGTH_SHORT).show();
               }

               public void permissionDenied(@NonNull String[] permissions) {
               Toast.makeText(ContentActivity.this, "用户拒绝了访问相册", Toast.LENGTH_SHORT).show();
              }
           }, Manifest.permission.READ_EXTERNAL_STORAGE);
       }
    }*/


    //Android官方申请动态权限写法（打电话）（同意定位）
    public void requestPower() {
        //判断是否已经赋予权限
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。即第一次拒绝，就不再请求
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {//这里可以写个对话框之类的项向用户解释为什么要申请权限，并在对话框的确认键后续再次申请权限.它在用户选择"不再询问"的情况下返回false
            } else {
                //申请权限，字符串数组内是一个或多个要申请的权限，1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
        }
    }


    //直接拨打120
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        startActivity(intent);
    }


}
