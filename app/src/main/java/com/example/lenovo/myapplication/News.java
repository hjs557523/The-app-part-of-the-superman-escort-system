package com.example.lenovo.myapplication;

import android.media.Image;

/**
 * Created by Lenovo on 2018/7/23.
 */

public class News {
    private String newsTitle;   //新闻标题
    private String newsUrl;     //新闻链接地址
    private String imags;        //新闻图片
    private String newsTime;    //新闻时间与来源

    public News(String newsTitle, String newsUrl,String imags,String newsTime) {
            this.newsTitle = newsTitle;
        this.newsUrl = newsUrl;
        this.imags = imags;
        this.newsTime = newsTime;
    }



    public String getNewsTime() {
        return newsTime;
    }

    public void setNewsTime(String newsTime) {
        this.newsTime = newsTime;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getImags() {
        return imags;
    }

    public void setImags(String imags) {
        this.imags = imags;
    }
    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }
}