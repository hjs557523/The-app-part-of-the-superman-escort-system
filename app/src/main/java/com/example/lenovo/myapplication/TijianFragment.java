package com.example.lenovo.myapplication;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextClock;
import android.widget.Toast;

import com.skyfishjy.library.RippleBackground;

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


public class TijianFragment extends Fragment {

    Button show;
    Button write;
    Button start_show;
    Button jiuhu;
    Button yisheng;
    Button map;
    Button kaiguan;

    private Fragment1 mTab01;
    private Boolean kaiqi = true;

    User user=null;
    Upload upload;
    static Context mContext;
    public static final String CREATE_ACCOUNT_URL = "http://39.96.192.110:8080/information/servlet/NewAccount";
    public static final int MSG_CREATE_RESULT = 1;


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
            Toast.makeText(getActivity(), "没有获取到网络的响应！", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return;
        }

        if(result == 1) {
            Toast.makeText(getActivity(), "！", Toast.LENGTH_LONG).show();
            return;
        }

        if(result == 2) {
            Toast.makeText(getActivity(), "服务端出现异常！", Toast.LENGTH_LONG).show();
            return;
        }

        if(result == 0) {
            Toast.makeText(getActivity(), "上传成功！", Toast.LENGTH_LONG).show();
            return;
        }

    };

    private void createAccount(final User user) {
        progress = new ProgressDialog(getActivity());
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
        progress=progress.show(getActivity(), null, "上传中...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("clover5", "Start Network!");
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(CREATE_ACCOUNT_URL);
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                Intent intent = getActivity().getIntent();
//                params.add(new BasicNameValuePair("username", user.getUserID()));
                params.add(new BasicNameValuePair("username",intent.getStringExtra("name") ));
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





    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)

    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_tijian, container, false);

        show=(Button) view.findViewById(R.id.show);
        write = (Button)view.findViewById(R.id.write);
        start_show = (Button)view.findViewById(R.id.start_show);

        /*这里加功能*/
        jiuhu = (Button)view.findViewById(R.id.ask_forhelp);
        yisheng = (Button)view.findViewById(R.id.ask_doctor);
        map = (Button)view.findViewById(R.id.start_map);
        kaiguan = (Button)view.findViewById(R.id.kaiguan);




        final RippleBackground rippleBackground=(RippleBackground)view.findViewById(R.id.content);
        mContext = getActivity();
        upload = new Upload(mContext,user);
        start_show.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent;
                intent =new Intent(getActivity(),Monitoring.class);
                String name = getActivity().getIntent().getStringExtra("name");
                intent.putExtra("name",name);
                startActivityForResult(intent,1);
            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rippleBackground.startRippleAnimation();
                show.setText("检测中...");

                user=new User(nub(1,100),nub(36.3,37.5),nub(40.0,300.0),nub(50,100),nub(100,145),nub(55,95),nub(2.50,5.50));
                createAccount(user);
                upload.insert(user);

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Intent intent;
                        intent = new Intent(getActivity(), Main2Activity.class);
                        String name = getActivity().getIntent().getStringExtra("name");
                        intent.putExtra("name",name);
                        startActivityForResult(intent,1);
                        show.setText("一键体检");
                    }
                }, 3000);    //延时10s执行
            }
        });
        rippleBackground.stopRippleAnimation();

        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getActivity(), WriteActivity.class);
                startActivity(intent);
            }
        });

        jiuhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPhone("120");
            }
        });



        yisheng.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int id = R.id.fg_main;
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                hideFragment(transaction);

                if (mTab01 == null) {
                    mTab01 = new Fragment1();
                   transaction.replace(id,mTab01).commit();
                } else {
                    transaction.show(mTab01);
                }

            }
        });

        map.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),ShowMapActivity.class);
                startActivity(i);
            }
        });

        kaiguan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                TijianFragment.this.kaiqi = !TijianFragment.this.kaiqi;
                if (TijianFragment.this.kaiqi == true){
                    try {
                        Toast t = Toast.makeText(getActivity(),"启动需要一些时间，请稍后......",Toast.LENGTH_LONG);
                        t.setGravity(Gravity.CENTER, 0, 0);
                        t.show();

                        Thread.sleep(2500);//模拟一个启动传感器工作的初始化

                        Toast toast = Toast.makeText(getActivity(),"启动成功！",Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        TijianFragment.this.kaiguan.setText("传感器系统已开启");

                        Drawable drawable = getResources().getDrawable(R.drawable.kaiqi);
                        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                        TijianFragment.this.kaiguan.setCompoundDrawables(null,drawable,null,null);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }else {
                    try {

                        Toast t = Toast.makeText(getActivity(),"关闭需要一些时间，请稍后......",Toast.LENGTH_LONG);
                        t.setGravity(Gravity.CENTER, 0, 0);
                        t.show();

                        Thread.sleep(2500);//模拟一个关闭传感器系统的初始化

                        Toast toast2 = Toast.makeText(getActivity(),"关闭成功",Toast.LENGTH_LONG);
                        toast2.setGravity(Gravity.CENTER, 0, 0);
                        toast2.show();
                        TijianFragment.this.kaiguan.setText("传感器系统已关闭");

                        Drawable drawable = getResources().getDrawable(R.drawable.guanbi);
                        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                        TijianFragment.this.kaiguan.setCompoundDrawables(null,drawable,null,null);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return view;
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



    //直接拨打120
    public void callPhone(String phoneNum){
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (mTab01 != null) {
            transaction.hide(mTab01);
        }
    }

}
