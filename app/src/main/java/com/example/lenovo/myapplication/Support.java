package com.example.lenovo.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class Support extends AppCompatActivity {
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        back=(Button) findViewById(R.id.back);
        back.setOnClickListener(new backOnClick());
    }
    class backOnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = getIntent();
            setResult(1,intent);

            final String name = intent.getStringExtra("name");
            intent =new Intent(Support.this,Setting.class);
            intent.putExtra("name",name);
            startActivityForResult(intent,1);
            finish();
        }
    }
}
