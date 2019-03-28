package com.example.myapplication;

/**
 * Created by Lenovo on 2018/6/3.
 */

public class User {
    private String userID;//用户编号
    private String temperature;//体温
    private String weight;//体重
    private String heartbeat;//心跳
    private String bloodPressure;//血压（不使用）

    private String systolicPressure;//收缩压（高压）
    private String diastolicPressure;//舒张压（低压）

    private String bloodFat;//血脂

    public User(){
        userID=null;
        temperature=null;
        weight=null;
        heartbeat=null;
        systolicPressure=null;
        diastolicPressure=null;
        bloodFat=null;
    }

    public User(String userID,String temperature,String weight,String heartbeat,String systolicPressure,String diastolicPressure,String bloodFat){
        this.userID=userID;
        this.temperature=temperature;
        this.weight=weight;
        this.heartbeat=heartbeat;
        this.systolicPressure=systolicPressure;
        this.diastolicPressure=diastolicPressure;
        this.bloodFat=bloodFat;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeartbeat() {
        return heartbeat;
    }

    public void setHeartbeat(String heartbeat) {
        this.heartbeat = heartbeat;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public String getBloodFat() {
        return bloodFat;
    }

    public void setBloodFat(String bloodFat) {
        this.bloodFat = bloodFat;
    }


    public String getSystolicPressure() {
        return systolicPressure;
    }

    public void setSystolicPressure(String systolicPressure) {
        this.systolicPressure = systolicPressure;
    }

    public String getDiastolicPressure() {
        return diastolicPressure;
    }

    public void setDiastolicPressure(String diastolicPressure) {
        this.diastolicPressure = diastolicPressure;
    }
}