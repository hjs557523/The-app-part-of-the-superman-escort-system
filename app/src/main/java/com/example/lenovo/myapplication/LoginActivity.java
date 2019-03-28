package com.example.lenovo.myapplication;

import android.graphics.Typeface;
import android.os.Bundle;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
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
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



public class LoginActivity extends Activity implements OnClickListener {
    private EditText loginUsername;
    private EditText loginPassword;
    private Button loginButton;
    private Button createButton;
    private ImageView imageview1;
    private LinearLayout linearLayout_bg;
    private TextView dlbiaoti;



    //Boolean gone;

    private ProgressDialog loginProgress;

    public static final int MSG_LOGIN_RESULT = 0;

    public String serverUrl = "http://39.96.192.110:8080/test/servlet/loadMessage";

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case MSG_LOGIN_RESULT:
                    loginProgress.dismiss();
                    JSONObject json = (JSONObject) msg.obj;
                    handleLoginResult(json);
                    break;
            }
        };
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dlbiaoti = (TextView)findViewById(R.id.dlbiaoti);
        dlbiaoti.setTypeface(Typeface.createFromAsset(LoginActivity.this.getAssets(),"font/nuansequyuanti.ttf"));
        loginUsername = findViewById(R.id.login_username);
        loginPassword = findViewById(R.id.login_password);
        loginButton   = findViewById(R.id.login);
        createButton  = findViewById(R.id.create_count);
        imageview1 = findViewById(R.id.imageview1);
        linearLayout_bg=findViewById(R.id.linearLayout_bg);
        //linearLayout_bg.setBackgroundResource(R.mipmap.bg);
        initViews();

    }
    private void initViews() {
        imageview1.setAlpha((float)1);

        //loginUsername.setVisibility(View.GONE);
        //loginPassword.setVisibility(View.GONE);
        //gone=false;

        loginButton.setOnClickListener(this);
        createButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.login:
                //if(gone==true)
                    handleLogin();
                imageview1.setAlpha((float) 0.5);
                //loginUsername.setVisibility(View.VISIBLE);
                //loginPassword.setVisibility(View.VISIBLE);

                //gone=true;
                break;
            case R.id.create_count:
                handleCreateCount();
                break;
            default:
                break;
        }

    }

    private void handleLogin() {
        String username = loginUsername.getText().toString();
        String password = loginPassword.getText().toString();
        login(username, password);
    }
    private void login(final String username, final String password) {
        loginProgress = new ProgressDialog(this);
        loginProgress.setCancelable(false);
        loginProgress.setCanceledOnTouchOutside(false);
        loginProgress=loginProgress.show(this, null, "正在登录中，请稍后...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("yanghongbing", "start network!");
                HttpClient client = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(serverUrl);
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("password", password));

                HttpResponse httpResponse = null;
                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                    httpResponse = client.execute(httpPost);
                    if(httpResponse.getStatusLine().getStatusCode() == 200) {
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
    private void handleCreateCount() {
        Intent intent = new Intent(this, CreateUserActivity.class);
        startActivity(intent);
        finish();
    }

    private void handleLoginResult(JSONObject json){
        /*
         * login_result:
         * -1：登陆失败，未知错误！
         * 0: 登陆成功！
         * 1：登陆失败，用户名或密码错误！
         * 2：登陆失败，用户名不存在！
         * */
        int resultCode = -1;
        try {
            resultCode = json.getInt("result_code");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        switch(resultCode) {
            case 0:
                onLoginSuccess(json);
                break;
            case 1:
                Toast.makeText(this, "用户名或密码错误！", Toast.LENGTH_LONG).show();
                break;
            case 2:
                Toast.makeText(this, "当前用户不存在！", Toast.LENGTH_LONG).show();
                break;
            case -1:
            default:
                Toast.makeText(this, "登陆失败！未知错误！", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void onLoginSuccess(JSONObject json) {

        System.out.println("这里被执行到了成功登录方法");
        Intent intent = new Intent(this, ContentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("name",loginUsername.getText().toString());
        intent.putExtras(bundle);

        try {

            intent.putExtra("username", json.getString("username"));
            intent.putExtra("gender", json.getString("gender"));
            intent.putExtra("age", json.getInt("age"));
            intent.putExtra("phone", json.getString("phone"));
            intent.putExtra("email", json.getString("email"));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        startActivityForResult(intent,1);
        finish();
    }
    private void sendMessage(int what, Object obj) {
        Message msg = Message.obtain();
        msg.what = what;
        msg.obj = obj;
        mHandler.sendMessage(msg);
    }
}

