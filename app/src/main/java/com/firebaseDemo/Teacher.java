package com.firebaseDemo;

import java.util.Calendar;

public class Teacher {
    String tarih;
    String seviye;
    String icerik;
    String sinif;
    String type;
    String name;
    String phoneNumber;

    public Teacher(){}

    public Teacher(String teacherTarih, String teacherSeviye, String teacherIcerik, String teacherSinif,String teacherType){
        this.tarih=teacherTarih;
        this.seviye=teacherSeviye;
        this.icerik=teacherIcerik;
        this.sinif=teacherSinif;
        this.type=teacherType;

    }
    public Teacher(String teacherName, String teacherSeviye,String teacherPhoneNumber){
        this.name=teacherName;
        this.seviye=teacherSeviye;
        this.phoneNumber=teacherPhoneNumber;

            }


    public Teacher(String teacherName){
        this.name=teacherName;
    }

    public String getTarih() {
        return tarih;
    }

    public String getSeviye() {
        return seviye;
    }

    public String getIcerik() {
        return icerik;
    }

    public String getSinif() {
        return sinif;
    }

    public String getType() {
        return type;
    }

    public String getName(){return name;}

    public String getPhoneNumber() {
        return phoneNumber;
    }
}



