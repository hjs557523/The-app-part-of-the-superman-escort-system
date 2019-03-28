package com.example.linecharttest;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main2Activity extends AppCompatActivity {

    private UserDBhelper myDBHelper;
    private SQLiteDatabase db;
    private ListView datalist;
    private List<Map<String, Object>> generals;//要显示的数据集合
    private BaseAdapter generalAdapt;//适配器
    private final String GeneralImage = "img";
    private final String GeneralName = "name";//map中key的值
    private final String GeneralContent = "content";
    private final String GeneralData = "data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
     datalist = (ListView) findViewById(R.id.datalv);
        myDBHelper = new UserDBhelper(this, "Clover", null,2);
        db = myDBHelper.getReadableDatabase();
        init();
        initView();
       // Button history= (Button) findViewById(R.id.history);
      //  history.setOnClickListener(new View.OnClickListener() {
      ///      @Override
       //     public void onClick(View v) {
        //        Intent intent = new Intent(Main2Activity.this, Main5Activity.class);
        //        startActivity(intent);
      //      }
      //  });

    }

    private void init() {
        generals = new ArrayList<Map<String, Object>>();
        db = myDBHelper.getReadableDatabase();
        int[] img = {
                R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher
                , R.mipmap.ic_launcher
        };
        String[] names = getResources().getStringArray(R.array.name_label);
        //=getResources().getStringArray(R.array.content_label);

        StringBuilder sb= new StringBuilder();
     //  StringBuilder sb2= new StringBuilder();new String[]{"temperature,weight,heartbeat,systolicPressure,diastolicPressure,bloodFat"}
        //指定查询结果的排序方式
        Cursor cursor = db.query("user", null, null, null, null, null, null);
        if (cursor.moveToLast()) {
            do {
                String temperature = cursor.getString(cursor.getColumnIndex("temperature"));
                String weight = cursor.getString(cursor.getColumnIndex("weight"));
                String heartbeat = cursor.getString(cursor.getColumnIndex("heartbeat"));
                String systolicPressure = cursor.getString(cursor.getColumnIndex("systolicPressure"));
                String diastolicPressure = cursor.getString(cursor.getColumnIndex("diastolicPressure"));
                String bloodFat = cursor.getString(cursor.getColumnIndex("bloodFat"));
                sb.append( temperature +"/n"+ weight + "/n"+ heartbeat +"/n"+ systolicPressure + "/n"+diastolicPressure+"/n"+bloodFat+"/n");
              //  sb2.append( temperature +"/n"+ weight + "/n"+ heartbeat +"/n"+ systolicPressure + "/"+diastolicPressure+"/n"+bloodFat+"/n");
            } while (cursor.moveToNext());
        }
        cursor.close();
        String[] data = sb.toString().split("/n");
    //    String[] data2 = sb2.toString().split("/n");
        String [] contents= getResources().getStringArray(R.array.content_label);//new String[img.length];
   /*     Double[] ds=new Double[img.length];
        for(int i=0;i<img.length;i++){
            ds[i]=Double.valueOf(data[i]);
        }

        //体温
        if(ds[0]<=37.2&&ds[0]>=36.3)
        {contents[0]="体温正常";}
        if(ds[0]>37.2&&ds[0]<=38){
            contents[0]="低热";}
        if(ds[0]>38&&ds[0]<=39){
            contents[0]="中等度热";}
        if(ds[0]>39&&ds[0]<=41){
            contents[0]="高热";}
        if(ds[0]>41){
            contents[0]="超高热";}
        if(ds[0]<36.3)
        {contents[0]="体温异常";}
        //体重

          contents[1]="体重正常";
        //心跳

        if(ds[2]>=60&&ds[2]<=100)
        { contents[2]="心跳正常";}
        if(ds[2]>100)
        { contents[2]="心动过速";}
        if(ds[2]<60)
        {contents[2]="心动过缓";}
        //血压

        if(ds[3]>140&&ds[4]>90)
        {contents[3]="高血压";
        contents[4]="高血压";}
        if(ds[3]<90&&ds[4]<60)
        {   contents[3]="低血压";
        contents[4]="高血压";}
        if(ds[3]>=90&&ds[4]>=60&&ds[3]<=140&&ds[4]<=90)
            {  contents[3]="血压正常";
            contents[4]="高血压";}
        //血脂～
        if(ds[5]>=2.8&&ds[5]<=5.17)
        { contents[5]="血脂正常";}
        if(ds[5]>5.17)
        {   contents[5]="高血脂";}
        if(ds[5]<2.8)
        {  contents[5]="低血脂";}
*/
        for (int i = 0; i < img.length; i++) {
            Map<String, Object> general = new HashMap<String, Object>();
            general.put(GeneralImage, img[i]);
            general.put(GeneralName, names[i]);
            general.put(GeneralContent, contents[i]);
            general.put(GeneralData, data[i]);
            generals.add(general);
        }

    }


    private void initView() {
        datalist = (ListView) findViewById(R.id.datalv);
        generalAdapt = new SimpleAdapter(Main2Activity.this, generals,
                R.layout.listview_data,
                new String[]{GeneralImage, GeneralName, GeneralContent, GeneralData},
                new int[]{R.id.img, R.id.name, R.id.content, R.id.data});

        datalist.setAdapter(generalAdapt);
    }



}

