package com.example.lenovo.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Feedback extends AppCompatActivity {
    private Button submit;
    private EditText opinion;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        submit=(Button) findViewById(R.id.submit);
        opinion=(EditText) findViewById(R.id.opinion);
        submit.setOnClickListener(new submitOnClick());
        back=(Button) findViewById(R.id.back);
        back.setOnClickListener(new backOnClick());
    }
    class submitOnClick implements View.OnClickListener{
        public void onClick(View v)
        {
            opinion.setText("");
            Toast.makeText(Feedback.this, "感谢您的反馈,我们开发小组会尽快处理您的意见。", Toast.LENGTH_SHORT).show();
        }
    }
    class backOnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = getIntent();
            setResult(1,intent);
            final String name = intent.getStringExtra("name");
            intent =new Intent(Feedback.this,ContentActivity.class);
            intent.putExtra("name",name);
            intent.putExtra("userloginflag", 2);
            startActivityForResult(intent,1);
            finish();
        }
    }
}
