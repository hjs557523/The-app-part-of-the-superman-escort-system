package com.example.lenovo.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;



/**
 * Created by Lenovo on 2018/7/23.
 */

public class NewsAdapter extends BaseAdapter {

    private List<News> newsList;
    private View view;
    private Context mContext;
    private ViewHolder viewHolder;


    public NewsAdapter(Context mContext, List<News> newsList) {
        this.newsList = newsList;
        this.mContext= mContext;
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.news_item,
                    null);
            viewHolder = new ViewHolder();
            viewHolder.newsTitle = (TextView) view
                    .findViewById(R.id.news_title);
            viewHolder.newsTime = (TextView)view.findViewById(R.id.news_time);
            viewHolder.img=(ImageView)view.findViewById(R.id.news_img);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.newsTitle.setText(newsList.get(position).getNewsTitle());
        viewHolder.newsTime.setText("时间: "+newsList.get(position).getNewsTime());
        Glide.with(mContext).load(newsList.get(position).getImags()).into(viewHolder.img);
        return view;
    }

    class ViewHolder{
        TextView newsTitle;
        TextView newsTime;
        ImageView img;
    }

}

