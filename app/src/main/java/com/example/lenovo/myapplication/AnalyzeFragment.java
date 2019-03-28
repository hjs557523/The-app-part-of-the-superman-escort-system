package com.example.lenovo.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
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



public class AnalyzeFragment extends Fragment {

    private int number;
    private int number4;
    private int number5;
    private int number8;
    private String[] temperatureA3;
    private String[] weightA3;
    private String[] heartbeatA3;
    private String[] systolicPressureA3;
    private String[] diastolicPressureA3 ;
    private String[] bloodFatA3;
    private String[] temperatureA2;
    private String[] weightA2;
    private String[] heartbeatA2;
    private String[] systolicPressureA2;
    private String[] diastolicPressureA2 ;
    private String[] bloodFatA2;
    private String[] temperatureA1;
    private String[] weightA1;
    private String[] heartbeatA1;
    private String[] systolicPressureA1;
    private String[] diastolicPressureA1 ;
    private String[] bloodFatA1;
    private String[] temperature1;
    private String[] weight1;
    private String[] heartbeat1;
    private String[] systolicPressure1;
    private String[] diastolicPressure1 ;
    private String[] bloodFat1;
    private String[] datetime;
    private String[] hour;
    private String[] minute;
    private String[] second;
    private String[] day1;
    private String[] month2;
    private FragmentManager mFragmentManager;
    private RadioGroup radioGroup;
    private String  week1;
    private String  month1;
    private String  year1;
    private FragmentTransaction transaction;
    public static final int MSG_LOGIN_RESULT = 0;
    public String serverUrl = "http://39.96.192.110:8080/information/servlet/loadMessage";
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOGIN_RESULT:
                    JSONObject json = (JSONObject) msg.obj;
                    handleResult(json);
                    break;
            }
        }
    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view= inflater.inflate(R.layout.fragment_analyze, container, false);
//
        mFragmentManager = getActivity().getSupportFragmentManager();


        FragmentTransaction beginTransaction=mFragmentManager.beginTransaction();
        DayFragment fragment=new DayFragment();
        beginTransaction.replace(R.id.fl_main,fragment);
        beginTransaction.commit();
        String name = getActivity().getIntent().getStringExtra("name");
        radioGroup = (RadioGroup)view.findViewById(R.id.rg);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                transaction = mFragmentManager.beginTransaction();
                Fragment fragment = getInstanceByIndex(checkedId);
                transaction.replace(R.id.fl_main, fragment);
                transaction.commit();
            }
        });
        query(name, "2");
        Calendar cal = Calendar.getInstance();//这一句必须要设置，否则美国认为第一天是周日，而我国认为是周一，对计算当期日期是第几周会有错误
        cal.setFirstDayOfWeek(Calendar.SUNDAY); // 设置每周的第一天为星期一
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);// 每周从周一开始
        cal.setMinimalDaysInFirstWeek(7); // 设置每周最少为7天
        cal.setTime(new Date());
        int weeks=cal.get(Calendar.WEEK_OF_YEAR);
        week1=weeks+"";
        query1(name, "3",week1);

        int month = Integer.valueOf(Calendar.getInstance().get(Calendar.MONTH)+1);
        month1=month+"";
        int year = Integer.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        year1=year+"";
        query2(name, "4",month1);
        query3(name, "5",year1);
        return view;
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
            case 3:
                onSuccess1(json);
                break;
            case 4:
                onSuccess2(json);
                break;
            case 5:
                onSuccess3(json);
                break;
            case 2:
                Toast.makeText(getActivity(), "数据库异常！", Toast.LENGTH_LONG).show();
                break;
            case -1:
            default:
                Toast.makeText(getActivity(), "未知错误！", Toast.LENGTH_LONG).show();
                break;
        }
    }
    private void query(final String username, final String requestcode) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                Log.d("clover", "start network!");
                HttpClient client = (HttpClient) new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(serverUrl);
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("requestcode", requestcode));


                org.apache.http.HttpResponse httpResponse = null;
                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                    httpResponse = client.execute((HttpUriRequest) httpPost);
                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        Log.d("clover5", "network OK!");
                        org.apache.http.HttpEntity entity = httpResponse.getEntity();
                        String entityString = EntityUtils.toString((HttpEntity) entity);
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
    private void query1(final String username, final String requestcode,final String week1) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                Log.d("clover", "start network!");
                HttpClient client = (HttpClient) new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(serverUrl);
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("requestcode", requestcode));
                params.add(new BasicNameValuePair("week1", week1));

                org.apache.http.HttpResponse httpResponse = null;
                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                    httpResponse = client.execute((HttpUriRequest) httpPost);
                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        Log.d("clover5", "network OK!");
                        org.apache.http.HttpEntity entity = httpResponse.getEntity();
                        String entityString = EntityUtils.toString((HttpEntity) entity);
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
    private void query2(final String username, final String requestcode,final String month1) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                Log.d("clover", "start network!");
                HttpClient client = (HttpClient) new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(serverUrl);
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("requestcode", requestcode));
                params.add(new BasicNameValuePair("month1", month1));

                org.apache.http.HttpResponse httpResponse = null;
                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                    httpResponse = client.execute((HttpUriRequest) httpPost);
                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        Log.d("clover5", "network OK!");
                        org.apache.http.HttpEntity entity = httpResponse.getEntity();
                        String entityString = EntityUtils.toString((HttpEntity) entity);
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
    private void query3(final String username, final String requestcode,final String year1) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                Log.d("clover", "start network!");
                HttpClient client = (HttpClient) new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(serverUrl);
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("requestcode", requestcode));
                params.add(new BasicNameValuePair("year1", year1));

                org.apache.http.HttpResponse httpResponse = null;
                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                    httpResponse = client.execute((HttpUriRequest) httpPost);
                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        Log.d("clover5", "network OK!");
                        org.apache.http.HttpEntity entity = httpResponse.getEntity();
                        String entityString = EntityUtils.toString((HttpEntity) entity);
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

        try {
            HashMap<String, JSONObject> map = new HashMap<>();
            // String number=json.getString("number");
            int number1 = Integer.parseInt(json.getString("number"));
            temperature1 = new String[number1];
            weight1 = new String[number1];
            heartbeat1 = new String[number1];
            systolicPressure1 = new String[number1];
            diastolicPressure1 = new String[number1];
            bloodFat1 = new String[number1];
            number=number1;
            hour=new String[number1];
            minute=new String[number1];
            second=new String[number1];
            for (int i = 0; i < number1; i++) {

                map.put("" + i, json.getJSONObject("" + i));
                temperature1[i] = map.get(i + "").get("temperature") + "";
                weight1[i] = map.get(i + "").get("weight") + "";
                heartbeat1[i] = map.get(i + "").get("heartbeat") + "";
                systolicPressure1[i] = map.get(i + "").get("systolicPressure") + "";
                diastolicPressure1[i] = map.get(i + "").get("diastolicPressure") + "";
                bloodFat1[i] = map.get(i + "").get("bloodFat") + "";
                hour[i] = map.get(i + "").get("hour(datetime)")+"" ;//+ "时";
                minute[i] = map.get(i + "").get("minute(datetime)")+"";// + "分";
                second[i] = map.get(i + "").get("second(datetime)")+""; //+ "秒";
            }


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    private void onSuccess1(JSONObject json) {

        try {
            HashMap<String, JSONObject> map = new HashMap<>();
            int number3 = Integer.parseInt(json.getString("number"));
            temperatureA1= new String[number3];
            weightA1 = new String[number3];
            heartbeatA1 = new String[number3];
            systolicPressureA1 = new String[number3];
            diastolicPressureA1 = new String[number3];
            bloodFatA1 = new String[number3];
            number4=number3;

            for (int i = 0; i < number3; i++) {
                map.put("" + i, json.getJSONObject("" + i));
                temperatureA1[i] = map.get(i + "").get("AVG(temperature)") + "";
                weightA1[i] = map.get(i + "").get("AVG(weight)") + "";
                heartbeatA1[i] = map.get(i + "").get("AVG(heartbeat)") + "";
                systolicPressureA1[i] = map.get(i + "").get("AVG(systolicPressure)") + "";
                diastolicPressureA1[i] = map.get(i + "").get("AVG(diastolicPressure)") + "";
                bloodFatA1[i] = map.get(i + "").get("AVG(bloodFat)") + "";

            }


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    private void onSuccess2(JSONObject json) {

        try {
            HashMap<String, JSONObject> map = new HashMap<>();
            int number6= Integer.parseInt(json.getString("number"));
            temperatureA2= new String[number6];
            weightA2 = new String[number6];
            heartbeatA2 = new String[number6];
            systolicPressureA2 = new String[number6];
            diastolicPressureA2 = new String[number6];
            bloodFatA2 = new String[number6];
            day1=new String[number6];
            number5=number6;

            for (int i = 0; i < number6; i++) {
                map.put("" + i, json.getJSONObject("" + i));
                temperatureA2[i] = map.get(i + "").get("AVG(temperature)") + "";
                weightA2[i] = map.get(i + "").get("AVG(weight)") + "";
                heartbeatA2[i] = map.get(i + "").get("AVG(heartbeat)") + "";
                systolicPressureA2[i] = map.get(i + "").get("AVG(systolicPressure)") + "";
                diastolicPressureA2[i] = map.get(i + "").get("AVG(diastolicPressure)") + "";
                bloodFatA2[i] = map.get(i + "").get("AVG(bloodFat)") + "";
                day1[i] = map.get(i + "").get("DAY(datetime)") + "";
            }


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    private void onSuccess3(JSONObject json) {

        try {
            HashMap<String, JSONObject> map = new HashMap<>();
            int number7= Integer.parseInt(json.getString("number"));
            temperatureA3= new String[number7];
            weightA3 = new String[number7];
            heartbeatA3 = new String[number7];
            systolicPressureA3 = new String[number7];
            diastolicPressureA3 = new String[number7];
            bloodFatA3 = new String[number7];
            month2 = new String[number7];
            number8=number7;

            for (int i = 0; i < number7; i++) {
                map.put("" + i, json.getJSONObject("" + i));
                temperatureA3[i] = map.get(i + "").get("AVG(temperature)") + "";
                weightA3[i] = map.get(i + "").get("AVG(weight)") + "";
                heartbeatA3[i] = map.get(i + "").get("AVG(heartbeat)") + "";
                systolicPressureA3[i] = map.get(i + "").get("AVG(systolicPressure)") + "";
                diastolicPressureA3[i] = map.get(i + "").get("AVG(diastolicPressure)") + "";
                bloodFatA3[i] = map.get(i + "").get("AVG(bloodFat)") + "";
                month2[i] = map.get(i + "").get("MONTH(datetime)") + "";
            }


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    private void sendMessage(int what, Object obj) {
        Message msg = Message.obtain();
        msg.what = what;
        msg.obj = obj;
        mHandler.sendMessage(msg);
    }

    public Fragment getInstanceByIndex(int index) {
        Fragment fragment = null;
        switch (index) {
            case R.id.btn_day:
                fragment = new DayFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("n",number);
                bundle.putStringArray("temperature1",temperature1);
                bundle.putStringArray("weight1",weight1);
                bundle.putStringArray("heartbeat1",heartbeat1);
                bundle.putStringArray("systolicPressure1",systolicPressure1);
                bundle.putStringArray("diastolicPressure1",diastolicPressure1);
                bundle.putStringArray("bloodFat1",bloodFat1);
                bundle.putStringArray("hour",hour);
                bundle.putStringArray("minute",minute);
                bundle.putStringArray("second",second);
                fragment.setArguments(bundle);
                break;
            case R.id.btn_week:
                fragment = new WeekFragment();
                Bundle bundle2 = new Bundle();
                bundle2.putInt("n",number4);
                bundle2.putStringArray("temperatureA1",temperatureA1);
                bundle2.putStringArray("weightA1",weightA1);
                bundle2.putStringArray("heartbeatA1",heartbeatA1);
                bundle2.putStringArray("systolicPressureA1",systolicPressureA1);
                bundle2.putStringArray("diastolicPressureA1",diastolicPressureA1);
                bundle2.putStringArray("bloodFatA1",bloodFatA1);
                fragment.setArguments(bundle2);
                break;
            case R.id.btn_month:
                fragment = new MonthFragment();
                Bundle bundle3 = new Bundle();
                bundle3.putInt("n",number5);
                bundle3.putStringArray("temperatureA2",temperatureA2);
                bundle3.putStringArray("weightA2",weightA2);
                bundle3.putStringArray("heartbeatA2",heartbeatA2);
                bundle3.putStringArray("systolicPressureA2",systolicPressureA2);
                bundle3.putStringArray("diastolicPressureA2",diastolicPressureA2);
                bundle3.putStringArray("bloodFatA2",bloodFatA2);
                bundle3.putStringArray("day",day1);
                bundle3.putString("month",month1);
                fragment.setArguments(bundle3);
                break;
            case R.id.btn_year:
                fragment = new YearFragment();
                Bundle bundle4 = new Bundle();
                bundle4.putInt("n",number8);
                bundle4.putStringArray("temperatureA3",temperatureA3);
                bundle4.putStringArray("weightA3",weightA3);
                bundle4.putStringArray("heartbeatA3",heartbeatA3);
                bundle4.putStringArray("systolicPressureA3",systolicPressureA3);
                bundle4.putStringArray("diastolicPressureA3",diastolicPressureA3);
                bundle4.putStringArray("bloodFatA3",bloodFatA3);
                bundle4.putStringArray("month2",month2);
                fragment.setArguments(bundle4);
                break;

        }
        return fragment;
    }

}
