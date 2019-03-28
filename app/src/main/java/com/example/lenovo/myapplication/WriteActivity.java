package com.example.lenovo.myapplication;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class WriteActivity extends Activity{

    public static final String CREATE_ACCOUNT_URL = "http://39.96.192.110:8080/information/servlet/NewAccount";
    public static final int MSG_CREATE_RESULT = 1;
    TextView display,phoneNumber;
    EditText userID,temperature,weight,heartbeat,systolicPressure,diastolicPressure,bloodFat;
    Button insert,insert2,findAll,findLast,show,start_show,pause_show,contact;
    User user=null;
    static  Context mContext;
    Upload upload;
    private SQLiteDatabase db;
    private int record_Heartbeat,record_systolicPressure,record_diastolicPressure;
    private int count_hignHeartbeat=0,count_lowHeartbeat=0,count_highPressure=0,count_lowPressure=0;
    private UserDBhelper myDBHelper;
    private Timer timer =null;
    private TimerTask timerTask =null;

    ProgressDialog progress;//提示上传数据对话框


    private Handler m2Handler = new Handler() {
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case MSG_CREATE_RESULT:
                    //progress.dismiss();
                    JSONObject json = (JSONObject) msg.obj;
                    hanleCreateAccountResult(json);
                    progress.dismiss();
                    break;
            }
        }
    };

    private void hanleCreateAccountResult(JSONObject json) {
        /*
         *   result_code:
         * 0  注册成功
         * 1  用户名已存在
         * 2 数据库操作异常
         * */
        int result;
        try {
            result = json.getInt("result_code");
        } catch (JSONException e) {
            Toast.makeText(this, "没有获取到网络的响应！", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return;
        }

        if(result == 1) {
            Toast.makeText(this, "！", Toast.LENGTH_LONG).show();
            return;
        }

        if(result == 2) {
            Toast.makeText(this, "服务端出现异常！", Toast.LENGTH_LONG).show();
            return;
        }

        if(result == 0) {
            Toast.makeText(this, "上传成功！", Toast.LENGTH_LONG).show();
            return;
        }

    };
    ContentActivity contentActivity = new ContentActivity();
    String name1 = contentActivity.name;

    private void createAccount(final User user) {
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
        progress=progress.show(this, null, "上传中...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("clover5", "Start Network!");
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(CREATE_ACCOUNT_URL);
                List<NameValuePair> params = new ArrayList<NameValuePair>();
//                params.add(new BasicNameValuePair("username", user.getUserID()));

                params.add(new BasicNameValuePair("username", name1));
                params.add(new BasicNameValuePair("temperature",  user.getTemperature()));
                params.add(new BasicNameValuePair("weight",  user.getWeight()));
                params.add(new BasicNameValuePair("heartbeat",  user.getHeartbeat()));
                params.add(new BasicNameValuePair("systolicPressure",  user.getSystolicPressure()));
                params.add(new BasicNameValuePair("diastolicPressure",  user.getDiastolicPressure()));
                params.add(new BasicNameValuePair("bloodFat",  user.getBloodFat()));
                System.out.print(user.getTemperature() + user.getWeight());
                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    if(httpResponse.getStatusLine().getStatusCode() == 200) {
                        Log.d("clover5", "Network OK!");
                        HttpEntity entity = httpResponse.getEntity();
                        String entityStr = EntityUtils.toString(entity);
                        String jsonStr = entityStr.substring(entityStr.indexOf("{"));
                        JSONObject json = new JSONObject(jsonStr);
                        sendMessage(MSG_CREATE_RESULT, json);
                    }
                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void sendMessage(int what, Object obj) {
        Message msg = Message.obtain();
        msg.what = what;
        msg.obj = obj;
        m2Handler.sendMessage(msg);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        userID = (EditText) findViewById(R.id.userID);
        temperature = (EditText) findViewById(R.id.temperature);
        weight = (EditText) findViewById(R.id.weight);
        heartbeat = (EditText) findViewById(R.id.heartbeat);
        systolicPressure=(EditText) findViewById(R.id.systolicPressure);
        diastolicPressure=(EditText) findViewById(R.id.diastolicPressure);
        bloodFat = (EditText) findViewById(R.id.bloodFat);
        insert= (Button) findViewById(R.id.insert);
        show= (Button) findViewById(R.id.show);
        mContext=WriteActivity.this;
        myDBHelper = new UserDBhelper(mContext, "Clover", null, 2);

        insert.setOnClickListener(new insertOnClick());



        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ContentActivity contentActivity = new ContentActivity();
                String name = contentActivity.name;
                Intent intent = new Intent(WriteActivity.this, Main2Activity.class);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });
        upload = new Upload(mContext,user);
    }

    class insertOnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            user=new User(userID.getText().toString(),temperature.getText().toString(),weight.getText().toString(),heartbeat.getText().toString(),systolicPressure.getText().toString(),diastolicPressure.getText().toString(),bloodFat.getText().toString());
            createAccount(user);
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

}
