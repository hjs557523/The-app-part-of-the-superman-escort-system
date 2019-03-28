package com.example.lenovo.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import java.util.ArrayList;
import java.util.List;

public class Main4Activity extends AppCompatActivity {
    private List<News> newsList;
    private NewsAdapter adapter;
    private Handler handler;
    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_news);
        newsList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.news_lv);
        getNews();



      handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    adapter = new NewsAdapter(Main4Activity.this, newsList);
                    lv.setAdapter(adapter);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            News news = newsList.get(position);
                            Intent intent = new Intent(Main4Activity.this, NewsDisplayActvivity.class);
                            intent.putExtra("news_url", news.getNewsUrl());
                            startActivity(intent);
                        }
                    });
                }
            }
        };
    }




    private void getNews(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{

                    for(int i = 2;i<=10;i++) {

                        Document doc = Jsoup.connect("http://health.hsw.cn/jkzx08/"+Integer.toString(i)+".shtml" ).get();

                        Elements titleLinks = doc.select("div.btzayao");//解析来获取每条新闻的标题与链接地址
                        Elements urlLinks = doc.select("div.btzayao");
                        Elements imgs = doc.select("div.ztp");//解析来获取每条新闻的简介
                        Elements timeLinks = doc.select("span.time");   //解析来获取每条新闻的时间与来源

                        Log.e("title",Integer.toString(titleLinks.size()));
                        for(int j = 0;j < titleLinks.size();j++){
                            String title = titleLinks.get(j).select("a").text();
                            String uri = urlLinks.get(j).select("a").attr("href");
                            String imgStr = imgs.get(j).select("img").attr("data-original");
                            String time = timeLinks.get(j).select("span.time").select("span").text();

                            News news = new News(title,uri,imgStr,time);
                            newsList.add(news);
                        }

                      }

                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

}