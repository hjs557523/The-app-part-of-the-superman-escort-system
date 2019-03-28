package com.example.lenovo.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class Setting extends AppCompatActivity {
    Button support,back,check,contact,back2;
    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        support=(Button) findViewById(R.id.support);
        check=(Button)  findViewById(R.id.check);
        contact=(Button) findViewById(R.id.contact);
        support.setOnClickListener(new supportOnClick());
        check.setOnClickListener(new checkOnClick());
        contact.setOnClickListener(new contactOnClick());
        back=(Button) findViewById(R.id.back);
        back.setOnClickListener(new backOnClick());

        back2 = (Button)findViewById(R.id.back2) ;
        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setting.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    class supportOnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent;
            intent =new Intent(Setting.this,Support.class);
            String name = getIntent().getStringExtra("name");
            intent.putExtra("name",name);
            startActivityForResult(intent,1);
        }
    }
    class backOnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = getIntent();
            setResult(1,intent);

            final String name = intent.getStringExtra("name");
            intent =new Intent(Setting.this,ContentActivity.class);
            intent.putExtra("name",name);
            intent.putExtra("userloginflag", 2);
            startActivityForResult(intent,1);
            finish();
        }
    }
    class checkOnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Toast.makeText(Setting.this, "当前已为最新版本", Toast.LENGTH_SHORT).show();
        }
    }
    class contactOnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            alert = null;
            builder = new AlertDialog.Builder(Setting.this);
            alert = builder.setIcon(R.mipmap.icontact1)
                    .setTitle("联系我们：")
                    .setMessage("电话: ***** \n 邮箱: ****** \n QQ: ******  \n 微信:  ******")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).create();             //创建AlertDialog对象
            alert.show();
        }
    }
}
