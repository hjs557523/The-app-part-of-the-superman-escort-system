package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class Upload {

    private User user;
    private SQLiteDatabase db;
    private UserDBhelper myDBHelper;
    private StringBuilder sb=null;
    Context mContext;
    //初始化，为Upload提供Context和user;
    public Upload(Context mContext,User user){
        this.mContext=mContext;
        this.myDBHelper = new UserDBhelper(mContext, "Clover", null, 2);
        this.user=user;
    }
    //辅助方法，设置和得到Upload类中的user
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    //上传方法，调用此方法将直接上传传入的参数user内容；
    public void insert(User user){
        db =myDBHelper.getWritableDatabase();
        db.execSQL("INSERT INTO user(userID,temperature,weight,heartbeat,systolicPressure,diastolicPressure,bloodFat) values(?,?,?,?,?,?,?)",
                new String[]{user.getUserID(),user.getTemperature(),user.getWeight(),user.getHeartbeat(),user.getSystolicPressure(),user.getDiastolicPressure(),user.getBloodFat()});
    }
    //上传方法2，调用此方法将上传类中参数user的内容；
    public void insert(){
        db =myDBHelper.getWritableDatabase();
        db.execSQL("INSERT INTO user(userID,temperature,weight,heartbeat,systolicPressure,diastolicPressure,bloodFat) values(?,?,?,?,?,?,?)",
                new String[]{user.getUserID(), user.getTemperature(), user.getWeight(), user.getHeartbeat(),user.getSystolicPressure(),user.getDiastolicPressure(), user.getBloodFat()});
    }
    //辅助方法
    //查询数据库中所有的数据，并以StringBuilder的形式返回；
//    public StringBuilder findAll(){
//        sb = new StringBuilder();
//        db=myDBHelper.getReadableDatabase();
//        //指定查询结果的排序方式
//        Cursor cursor = db.query("user", null, null, null, null, null, null);
//        if (cursor.moveToFirst()) {
//            do {
//                String userID = cursor.getString(cursor.getColumnIndex("userID"));
//                String temperature = cursor.getString(cursor.getColumnIndex("temperature"));
//                String weight = cursor.getString(cursor.getColumnIndex("weight"));
//                String heartbeat = cursor.getString(cursor.getColumnIndex("heartbeat"));
//                String bloodPressure = cursor.getString(cursor.getColumnIndex("bloodPressure"));
//                String bloodFat = cursor.getString(cursor.getColumnIndex("bloodFat"));
//                String dateTime = cursor.getString(cursor.getColumnIndex("dateTime"));
//                sb.append("userID：" + userID +
//                        "temperature：" + temperature +
//                        "weight：" + weight +
//                        "heartbeat：" + heartbeat +
//                        "bloodPressure：" + bloodPressure +
//                        "bloodFat：" + bloodFat +
//                        "dateTime：" + dateTime +
//                        "\n");
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        Toast.makeText(mContext, sb.toString(), Toast.LENGTH_SHORT).show();
//        return sb;
//    }
    //查询数据库中最后一个user的插入结果，返回一个User类型；
//    public User findLast(){
//        db=myDBHelper.getReadableDatabase();
//        Cursor cursor = db.query("user", null, null, null, null, null, null);
//        if (cursor.moveToFirst()) {
//            do {
//                user.setUserID(cursor.getString(cursor.getColumnIndex("userID")));
//                user.setTemperature(cursor.getString(cursor.getColumnIndex("temperature")));
//                user.setWeight(cursor.getString(cursor.getColumnIndex("weight")));
//                user.setHeartbeat(cursor.getString(cursor.getColumnIndex("heartbeat")));
//                user.setBloodPressure(cursor.getString(cursor.getColumnIndex("bloodPressure")));
//                user.setBloodFat(cursor.getString(cursor.getColumnIndex("bloodFat")));
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        return user;
//    }

}