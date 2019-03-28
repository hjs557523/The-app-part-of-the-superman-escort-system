package com.example.myapplication;

import android.content.Intent;
import android.renderscript.Int4;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.txusballesteros.SnakeView;

public class MainActivity extends AppCompatActivity {


    int u;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SnakeView snakeView = (SnakeView) findViewById(R.id.snake);

        snakeView.setMinValue(0);
        snakeView.setMaxValue(100);

        snakeView.addValue(50);
        snakeView.addValue(40);
        snakeView.addValue(60);
        snakeView.addValue(30);
        snakeView.addValue(70);
        snakeView.addValue(20);
        snakeView.addValue(80);
        snakeView.addValue(10);
        snakeView.addValue(90);

    }

}
