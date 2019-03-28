package com.example.lenovo.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


public class ZixunFragment extends Fragment {

    private List<News> newsList;
    private NewsAdapter adapter;
    private Handler handler;
    private ListView lv;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view= inflater.inflate(R.layout.fragment_zixun, container, false);

//        Intent intent = new Intent(getActivity(), Main4Activity.class);
//        startActivity(intent);

        newsList = new ArrayList<>();
        lv = (ListView) view.findViewById(R.id.news_lv);
        getNews();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    adapter = new NewsAdapter(getActivity(), newsList);
                    lv.setAdapter(adapter);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            News news = newsList.get(position);
                            Intent intent = new Intent(getActivity(), NewsDisplayActvivity.class);
                            intent.putExtra("news_url", news.getNewsUrl());
                            startActivity(intent);
                        }
                    });
                }
            }
        };

        return view;
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
                        Elements imgs = doc.select("div.lmtplb");//解析来获取每条新闻的简介
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
