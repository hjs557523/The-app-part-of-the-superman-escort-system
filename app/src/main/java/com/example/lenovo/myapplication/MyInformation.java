
package com.example.lenovo.myapplication;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyInformation extends AppCompatActivity {
    Button back;
    EditText user,age,phonenumber,email,new_password,old_password;
    Button update,Submit;
    RadioButton new_gender_girl,new_gender_boy;
    ProgressDialog progress;
    RadioGroup Gender;
    TextView tv_user,tv_new_password,tv_old_password,tv_age,tv_gender,tv_phonenumber,tv_email;
    String name,Spassword,Sage,Sgender,Sphonenumber,Semail,requestcode;
    Boolean change,NULL;

    private List<Map<String, Object>> generals;//要显示的数据集合


    public static final String CREATE_ACCOUNT_URL = "http://39.96.192.110:8080/information/servlet/loadMessage";
    public static final int MSG_CREATE_RESULT = 1;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case MSG_CREATE_RESULT:
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
            Toast.makeText(this, "注册失败！服务端出现异常！", Toast.LENGTH_LONG).show();
            return;
        }

        if(result == 0) {
            Success(json,requestcode);
            return;
        }

    }

    private void Success(JSONObject json,String requestcode) {
        if(requestcode.equals("7")){
            try {
                tv_gender.setText(json.getString("gender"));
                tv_phonenumber.setText(json.getString("phone"));
                tv_age.setText(json.getString("age"));
                tv_email.setText(json.getString("email"));

                phonenumber.setText(json.getString("phone"));
                age.setText(json.getString("age"));
                email.setText(json.getString("email"));

                Spassword=json.getString("password");
                Sage=json.getString("age");
                Sgender=json.getString("gender");
                Sphonenumber=json.getString("phonenumber");
                Semail=json.getString("email");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }else {
            Toast.makeText(this, "修改成功", Toast.LENGTH_LONG).show();
//            findMsg(name,"7");
//            showTextView();
//            hideEditText();
//            update.setText("修改");
//            change=true;
        }
    }

    ;

    private void sendMessage(int what, Object obj) {
        Message msg = Message.obtain();
        msg.what = what;
        msg.obj = obj;
        mHandler.sendMessage(msg);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_information);
        init();
        hideEditText();

        back.setOnClickListener(new backOnClick());
        update.setOnClickListener(new updateOnClick());
        Submit.setOnClickListener(new SubmitOnClick());
    }

    void init(){
        new_password=(EditText) findViewById(R.id.new_password);
        old_password=(EditText) findViewById(R.id.old_password);
        age=(EditText) findViewById(R.id.age);
        phonenumber=(EditText) findViewById(R.id.phonenumber);
        email = (EditText)findViewById(R.id.email);
        update=(Button) findViewById(R.id.update);
        Submit=(Button) findViewById(R.id.Submit);
        back=(Button) findViewById(R.id.back);
        Gender=findViewById(R.id.new_radio_group_gender);
        new_gender_boy=findViewById(R.id.new_gender_boy);
        new_gender_girl=findViewById(R.id.new_gender_girl);

        tv_user=findViewById(R.id.tv_username);
        tv_old_password=findViewById(R.id.tv_old_password);
        tv_new_password=findViewById(R.id.tv_new_password);
        tv_age=findViewById(R.id.tv_age);
        tv_gender=findViewById(R.id.tv_gender);
        tv_phonenumber=findViewById(R.id.tv_phonenumber);
        tv_email=findViewById(R.id.tv_email);
        Intent intent=this.getIntent();
        name = intent.getStringExtra("name");
        tv_user.setText(name);
        requestcode="7";
        findMsg(name,requestcode);
        Toast.makeText(this, "查询成功", Toast.LENGTH_LONG).show();
        change=true;
    }

    void hideEditText(){
        tv_old_password.setVisibility(View.GONE);
        tv_new_password.setVisibility(View.GONE);
        old_password.setVisibility(View.GONE);
        new_password.setVisibility(View.GONE);
        age.setVisibility(View.GONE);
        phonenumber.setVisibility(View.GONE);
        email.setVisibility(View.GONE);
        Gender.setVisibility(View.GONE);
        Submit.setVisibility(View.GONE);
    }

    void showEditText(){
        tv_old_password.setVisibility(View.VISIBLE);
        tv_new_password.setVisibility(View.VISIBLE);
        old_password.setVisibility(View.VISIBLE);
        new_password.setVisibility(View.VISIBLE);
        age.setVisibility(View.VISIBLE);
        phonenumber.setVisibility(View.VISIBLE);
        email.setVisibility(View.VISIBLE);
        Gender.setVisibility(View.VISIBLE);
        new_gender_girl.setText("女");
        new_gender_boy.setText("男");
    }

    void hideTextView(){
        tv_age.setVisibility(View.GONE);
        tv_gender.setVisibility(View.GONE);
        tv_phonenumber.setVisibility(View.GONE);
        tv_email.setVisibility(View.GONE);
    }
    void showTextView(){
        tv_age.setVisibility(View.VISIBLE);
        tv_gender.setVisibility(View.VISIBLE);
        tv_phonenumber.setVisibility(View.VISIBLE);
        tv_email.setVisibility(View.VISIBLE);
    }

    class updateOnClick implements View.OnClickListener
    {
        public void onClick(View v)
        {   if(change) {
            hideTextView();
            showEditText();
            change=false;
            update.setText("返回");
            Submit.setVisibility(View.VISIBLE);
        }else{
            showTextView();
            hideEditText();
            update.setText("修改");
            requestcode="7";
            findMsg(name,requestcode);
            change=true;
            Submit.setVisibility(View.GONE);
        }
        }
    }

    class SubmitOnClick implements View.OnClickListener
    {
        public void onClick(View v)
        {
            chack();
        }
    }
    class backOnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MyInformation.this,ContentActivity.class);
            intent.putExtra("userloginflag", 1);
            intent.putExtra("name",name);
            startActivity(intent);
            finish();
        }
    }

    private void findMsg(final String username,final String requestcode) {
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
        progress=progress.show(this, null, "查询中...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("Clover5", "Start Network!");
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(CREATE_ACCOUNT_URL);
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username",username));
                if(requestcode.equals("6")){
                    params.add(new BasicNameValuePair("password",new_password.getText().toString()));
                    RadioButton selectedGender = (RadioButton)MyInformation.this.findViewById(Gender.getCheckedRadioButtonId());
                    params.add(new BasicNameValuePair("gender",selectedGender.getText().toString()));
                    params.add(new BasicNameValuePair("age",age.getText().toString()));
                    params.add(new BasicNameValuePair("phone",phonenumber.getText().toString()));
                    params.add(new BasicNameValuePair("email",email.getText().toString()));
                }
                params.add(new BasicNameValuePair("requestcode", requestcode));
                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                    HttpResponse httpResponse = httpClient.execute(httpPost);

                    if(httpResponse.getStatusLine().getStatusCode() == 200) {
                        Log.d("Clover5", "Network OK!");
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
    void chack(){
        int pwdResult = checkPassword();
        if(pwdResult == 1) {
            Toast.makeText(this, "原始密码错误！", Toast.LENGTH_LONG).show();
            return ;
        }
        if (pwdResult == 2) {
            Toast.makeText(this, "原始密码不能为空！", Toast.LENGTH_LONG).show();
            return ;
        }
        if (pwdResult == 3) {
            Toast.makeText(this, "密码不能为空！", Toast.LENGTH_LONG).show();
            return ;
        }

        int isAgeValid = checkAge();
        if(isAgeValid == -1) {
            Toast.makeText(this, "年龄不能为空！", Toast.LENGTH_LONG).show();
            return ;
        }
        if(isAgeValid == -2) {
            Toast.makeText(this, "年龄超出范围(1~100)！", Toast.LENGTH_LONG).show();
            return ;
        }
        if(isAgeValid == -3) {
            Toast.makeText(this, "年龄格式输入错误，请不要输入字母、符号等其他字符串！", Toast.LENGTH_LONG).show();
            return ;
        }

        if(TextUtils.isEmpty(phonenumber.getText().toString())) {
            Toast.makeText(this, "请输入电话号码！", Toast.LENGTH_LONG).show();
            return ;
        }

        if(TextUtils.isEmpty(email.getText().toString())) {
            Toast.makeText(this, "请输入邮箱！", Toast.LENGTH_LONG).show();
            return;
        }
        requestcode="6";
        findMsg(name,requestcode);
        Toast.makeText(MyInformation.this, "提交成功", Toast.LENGTH_SHORT).show();
    }

    private int checkPassword() {
        /*
         * return value:
         * 0 password valid
         * 1 password not equal 2 inputs
         * 2 password empty
         * */
        String pwd1 = old_password.getText().toString();

        String pwd2 = new_password.getText().toString();
        if(!pwd1.equals(Spassword)) {
            return 1;
        } else if(TextUtils.isEmpty(pwd1)) {
            return 2;
        } else if(TextUtils.isEmpty(pwd2)){
            return 3;
        } else {
            return 0;
        }
    }
    private int checkAge() {
        /*
         * return value
         * 0 输入合法
         * -1 输入为空
         * -2输入为负数
         * -3输入为非数值字符串或包括小数
         * */
        int ageNum;
        String age1 = age.getText().toString();
        if(TextUtils.isEmpty(age1)) {
            return -1;
        }
        try {
            ageNum = Integer.parseInt(age1);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return -3;
        }
        if(ageNum <= 0 || ageNum > 100) {
            return -2;
        }
        return 0;
    }
}
