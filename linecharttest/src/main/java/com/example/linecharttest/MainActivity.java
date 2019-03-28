package com.example.linecharttest;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    EditText userID,temperature,weight,heartbeat,systolicPressure,diastolicPressure,bloodFat;
    Button insert,insert2,findAll,findLast,show;
    User user=null;
    Context mContext;
    Upload upload;
    private SQLiteDatabase db;
    private UserDBhelper myDBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userID = (EditText) findViewById(R.id.userID);
        temperature = (EditText) findViewById(R.id.temperature);
        weight = (EditText) findViewById(R.id.weight);
        heartbeat = (EditText) findViewById(R.id.heartbeat);
        systolicPressure=(EditText) findViewById(R.id.systolicPressure);
        diastolicPressure=(EditText) findViewById(R.id.diastolicPressure);

        bloodFat = (EditText) findViewById(R.id.bloodFat);
        insert= (Button) findViewById(R.id.insert);
        insert2= (Button) findViewById(R.id.insert2);
        show= (Button) findViewById(R.id.show);
        findAll= (Button) findViewById(R.id.findAll);
        findLast= (Button) findViewById(R.id.findLast);
        mContext=MainActivity.this;
        myDBHelper = new UserDBhelper(mContext, "Clover", null, 2);

        insert.setOnClickListener(new insertOnClick());
        insert2.setOnClickListener(new insert2OnClick());
        findAll.setOnClickListener(new findAllOnClick());
        findLast.setOnClickListener(new findLastOnClick());
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });
        upload = new Upload(mContext,user);
    }

    class insertOnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            user=new User(userID.getText().toString(),temperature.getText().toString(),weight.getText().toString(),heartbeat.getText().toString(),systolicPressure.getText().toString(),diastolicPressure.getText().toString(),bloodFat.getText().toString());
            upload.insert(user);
        }
    }

    class insert2OnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            user=new User(nub(1,100),nub(36.3,45.0),nub(40.0,300.0),nub(50,100),nub(80,145),nub(55,95),nub(2.50,5.50));
            upload.insert(user);
        }
    }

    public String nub(double min,double max){//输出1位小数点随机数
        BigDecimal db = new BigDecimal(Math.random() * (max - min) + min);
        return db.setScale(1, BigDecimal.ROUND_HALF_UP).toString();
    }
    public String nub(int min,int max){//输出整数随机数
        BigDecimal db = new BigDecimal(Math.random() * (max - min) + min);
        return db.setScale(0, BigDecimal.ROUND_HALF_UP).toString();
    }
    public String nub(float min,float max){//输出2位小数点随机数
        BigDecimal db = new BigDecimal(Math.random() * (max - min) + min);
        return db.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }



    private class findAllOnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            StringBuilder sb= new StringBuilder();
            db=myDBHelper.getReadableDatabase();
            //指定查询结果的排序方式
            Cursor cursor = db.query("user", null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    String userID = cursor.getString(cursor.getColumnIndex("userID"));
                    String temperature = cursor.getString(cursor.getColumnIndex("temperature"));
                    String weight = cursor.getString(cursor.getColumnIndex("weight"));
                    String heartbeat = cursor.getString(cursor.getColumnIndex("heartbeat"));
                    String systolicPressure = cursor.getString(cursor.getColumnIndex("systolicPressure"));
                    String diastolicPressure = cursor.getString(cursor.getColumnIndex("diastolicPressure"));
                    String bloodFat = cursor.getString(cursor.getColumnIndex("bloodFat"));
                    String dateTime = cursor.getString(cursor.getColumnIndex("dateTime"));
                    sb.append("用户编号：" + userID +
                            " 体温：" + temperature +
                            " 体重：" + weight +
                            " 心跳：" + heartbeat +
                            " 收缩压（高压）：" + systolicPressure +
                            " 舒张压（低压）：" + diastolicPressure +
                            " 血脂：" + bloodFat +
                            " 记录时间：" + dateTime +
                            "\n\n");
                } while (cursor.moveToNext());
            }
            cursor.close();
            Toast.makeText(mContext, sb.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private class findLastOnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String dateTime = null;
            user=new User();
            db=myDBHelper.getReadableDatabase();
            Cursor cursor = db.query("user", null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    user.setUserID(cursor.getString(cursor.getColumnIndex("userID")));
                    user.setTemperature(cursor.getString(cursor.getColumnIndex("temperature")));
                    user.setWeight(cursor.getString(cursor.getColumnIndex("weight")));
                    user.setHeartbeat(cursor.getString(cursor.getColumnIndex("heartbeat")));
                    user.setSystolicPressure(cursor.getString(cursor.getColumnIndex("systolicPressure")));
                    user.setDiastolicPressure(cursor.getString(cursor.getColumnIndex("diastolicPressure")));
                    user.setBloodFat(cursor.getString(cursor.getColumnIndex("bloodFat")));
                    dateTime = cursor.getString(cursor.getColumnIndex("dateTime"));
                } while (cursor.moveToNext());
            }
            cursor.close();
            Toast.makeText(mContext,"用户编号：" + user.getUserID() +
                    " 体温：" + user.getTemperature() +
                    " 体重：" + user.getWeight() +
                    " 心跳：" + user.getHeartbeat() +
                    " 收缩压（高压）：" + user.getSystolicPressure() +
                    " 舒张压（低压）：" + user.getDiastolicPressure() +
                    " 血脂：" + user.getBloodFat() +
                    " 记录时间：" + dateTime, Toast.LENGTH_SHORT).show();
        }
    }
}
