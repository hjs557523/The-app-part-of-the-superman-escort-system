package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends Activity {

    private EditText userName;
    private EditText passWord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName = (EditText)findViewById(R.id.userName);
        passWord = (EditText)findViewById(R.id.passWord);
    }
    public void login(View v){
        String name = userName.getText().toString();
        String pass = passWord.getText().toString();
        boolean result = UserService.check(name,pass);
        if(result){
            Toast.makeText(getApplicationContext(),"成功", LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(),"失败",LENGTH_SHORT).show();
        }
    }
}
