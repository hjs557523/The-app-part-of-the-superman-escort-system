package com.example.lenovo.myapplication;

import android.content.Intent;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
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
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Main2Activity extends AppCompatActivity {
    private ListView datalist;
    private List<Map<String, Object>> generals;//要显示的数据集合
    private BaseAdapter generalAdapt;//适配器
    private final String GeneralImage = "img";
    private final String GeneralName = "name";//map中key的值
    private final String GeneralContent = "content";
    private final String GeneralData = "data";
    private TextView tx1;
    public String username;
    private Calendar calendar;
    public static final int MSG_LOGIN_RESULT = 0;
    String tem,wei,hb,sys,dia,bf;
    Button back;
    public String serverUrl = "http://39.96.192.110:8080/information/servlet/loadMessage";

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case MSG_LOGIN_RESULT:
                    JSONObject json = (JSONObject) msg.obj;
                    handleResult(json);
                    break;
            }
        };
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        back = (Button)findViewById(R.id.back) ;
        SpeechUtility.createUtility(Main2Activity.this, SpeechConstant.APPID +"=5b1cbb92");

        Intent intent = this.getIntent();
        setResult(1,intent);

        final String name = intent.getStringExtra("name");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer(Main2Activity.this,null);
                Intent intent = new Intent(Main2Activity.this,ContentActivity.class);
                intent.putExtra("name",name);
                startActivityForResult(intent,1);
                mTts.stopSpeaking();
                finish();
            }
        });

        query(name,"1");

    }

    private void handleResult(JSONObject json) {

        int resultCode = -1;
        try {
            resultCode = json.getInt("result_code");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        switch (resultCode) {
            case 0:
                onSuccess(json);
                break;
            case 2:
                Toast.makeText(this, "数据库异常！", Toast.LENGTH_LONG).show();
                break;
            case -1:
            default:
                Toast.makeText(this, "体检失败！未知错误！", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void query(final String username,final String requestcode) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("clover", "start network!");
                HttpClient client = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(serverUrl);
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("requestcode", requestcode));

                HttpResponse httpResponse = null;
                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                    httpResponse = client.execute(httpPost);
                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        Log.d("clover5", "network OK!");
                        HttpEntity entity = httpResponse.getEntity();
                        String entityString = EntityUtils.toString(entity);
                        String jsonString = entityString.substring(entityString.indexOf("{"));
                        Log.d("clover5", "entity = " + jsonString);
                        JSONObject json = new JSONObject(jsonString);
                        sendMessage(MSG_LOGIN_RESULT, json);
                        Log.d("clover5", "json = " + json);
                    }
                } catch (UnsupportedEncodingException e) {
                    Log.d("clover5", "UnsupportedEncodingException");
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    Log.d("clover5", "ClientProtocolException");
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.d("clover5", "IOException");
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (JSONException e) {
                    Log.d("clover5", "IOException");
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void onSuccess(JSONObject json) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();

        try {

            String temperature1 = json.getString("temperature");
            String weight1 = json.getString("weight");
            String heartbeat1 = json.getString("heartbeat");
            String systolicPressure1 = json.getString("systolicPressure");
            String diastolicPressure1 = json.getString("diastolicPressure");
            String bloodFat1 = json.getString("bloodFat");


            tem = temperature1;
            wei = weight1;
            hb = heartbeat1;
            sys = systolicPressure1;
            dia = diastolicPressure1;
            bf = bloodFat1;

            sb.append(temperature1 + "/n" + weight1 + "/n" + heartbeat1 + "/n" + systolicPressure1 + "/n" + diastolicPressure1 + "/n" + bloodFat1 + "/n");
            sb2.append(temperature1 + "/n" + weight1 + "/n" + heartbeat1 + "/n" + systolicPressure1 + "/" + diastolicPressure1 + "/n" + bloodFat1 + "/n");
            generals = new ArrayList<Map<String, Object>>();
            int[] img = {
                    R.mipmap.tiwen, R.mipmap.tizhong, R.mipmap.xintiao, R.mipmap.xueya, R.mipmap.xuezhi

            };
            String[] names = getResources().getStringArray(R.array.name_label);

            String[] data = sb.toString().split("/n");
            String[] data2 = sb2.toString().split("/n");
            String[] contents = new String[img.length];
            Double[] ds = new Double[data.length];
            for (int i = 0; i < data.length; i++) {
                ds[i] = Double.valueOf(data[i]);
            }

            //体温
            if (ds[0] <= 37.2 && ds[0] >= 36.3) {
                contents[0] = "体温正常";
            }
            if (ds[0] > 37.2 && ds[0] <= 38) {
                contents[0] = "低热                                                                                        "
                        +"您的体温测量值异常，请立即停止驾驶并服用退烧药或前往医院就医！";
            }
            if (ds[0] > 38 && ds[0] <= 39) {
                contents[0] = "中等热度                                                                                        "
                        +"您的体温测量值异常，请立即停止驾驶并服用退烧药或前往医院就医！";
            }
            if (ds[0] > 39 && ds[0] <= 41) {
                contents[0] = "高热                                                                                        "
                        +"您的体温测量值异常，请立即停止驾驶并服用退烧药或前往医院就医！";
            }
            if (ds[0] > 41) {
                contents[0] = "超高热                                                                                        "
                        +"您的体温测量值异常，请立即停止驾驶并前往医院就医！";
            }
            if (ds[0] < 36.3) {
                contents[0] = "体温异常";
            }
            //体重
            if (ds[1] < 200)
                contents[1] = "体重正常";
            //心跳
            if (ds[1] >= 200)
                contents[1] = "体重超重";
            if (ds[2] >= 60 && ds[2] <= 100) {
                contents[2] = "心跳正常";
            }
            if (ds[2] > 100) {
                contents[2] = "心动过速                                                                                        "+
                        "您的心率测量值异常，请停止驾驶！";
            }
            if (ds[2] < 60) {
                contents[2] = "心动过缓                                                                                        "+
                        "您的心率测量值异常，请停止驾驶！";
            }
            //血压

            if (ds[3] > 140 && ds[4] > 90) {
                contents[3] = "高血压                                                                                        ";
            }
            if (ds[3] < 90 && ds[4] < 60) {
                contents[3] = "低血压                                                                                        ";
            }
            if (ds[3] >= 90 && ds[3] <= 140) {
                contents[3] = "血压正常                                                                                        " +
                        "您的血压测量值正常，请继续保持当前的生活方式，并定期测量血压。"
                ;
            }
            if (ds[4] >= 60 && ds[4] <= 89) {
                contents[3] = "血压正常                                                                                        "+
                        "您的血压测量值正常，请继续保持当前的生活方式，并定期测量血压。"
                ;
            }
            //血脂～
            if (ds[5] >= 2.8 && ds[5] <= 5.17) {
                contents[4] = "血脂正常";
            }
            if (ds[5] > 5.17) {
                contents[4] = "高血脂                                                                                        "+
                        "您的血脂测量值异常，请进行健康的生活方式并进行药物治疗和饮食治疗，并定期测量血脂。";
            }
            if (ds[5] < 2.8) {
                contents[4] = "低血脂";
            }

            for (int i = 0; i < img.length; i++) {
                Map<String, Object> general = new HashMap<String, Object>();
                general.put(GeneralImage, img[i]);
                general.put(GeneralName, names[i]);
                general.put(GeneralContent, contents[i]);
                general.put(GeneralData, data2[i]);
                generals.add(general);
            }

            SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer(Main2Activity.this,null);
            mTts.setParameter(SpeechConstant.VOICE_NAME,"xiaoyan");
            mTts.setParameter(SpeechConstant.PITCH,"20");
            mTts.setParameter(SpeechConstant.VOLUME,"100");
            mTts.setParameter(SpeechConstant.ENGINE_TYPE,SpeechConstant.TYPE_CLOUD);
            mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH,"./sdcard/iflytek.pcm");
            mTts.startSpeaking("您的体温是"+tem+"度"+"。"+contents[0]+"。"+"体重是"+wei+"斤"+"。"+contents[1]+"。"+
                    "心跳是"+hb+"次每分"+"。"+contents[2]+"。"+"高压为"+sys+"。"+
                    "低压为"+dia+"。"+contents[3]+"。"+"血脂为"+bf+"。"+contents[4],null);



        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        initView();
    }

    private void sendMessage(int what, Object obj) {
        Message msg = Message.obtain();
        msg.what = what;
        msg.obj = obj;
        mHandler.sendMessage(msg);
    }
    public void stopSpeaking(){
        SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer(Main2Activity.this,null);
        mTts.stopSpeaking();
    }


    private void initView() {
        datalist = (ListView) findViewById(R.id.datalv);
        generalAdapt = new SimpleAdapter(Main2Activity.this, generals,
                R.layout.listview_data,
                new String[]{GeneralImage, GeneralName, GeneralContent, GeneralData},
                new int[]{R.id.img, R.id.name, R.id.content, R.id.data});

        datalist.setAdapter(generalAdapt);
    }
    public void onBackPressed(){
        Intent intent = this.getIntent();
        setResult(1,intent);

        final String name = intent.getStringExtra("name");
        SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer(Main2Activity.this,null);
        intent = new Intent(Main2Activity.this,ContentActivity.class);
        intent.putExtra("name",name);
        startActivityForResult(intent,1);
        mTts.stopSpeaking();
        finish();
    }



}


