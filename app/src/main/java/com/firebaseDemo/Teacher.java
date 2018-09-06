package com.firebaseDemo;

import java.util.Calendar;

public class Teacher {
    String tarih;
    String seviye;
    String icerik;
    String sinif;

    public Teacher(){}

    public Teacher(String teacherTarih, String teacherSeviye, String teacherIcerik, String teacherSinif){
        this.tarih=teacherTarih;
        this.seviye=teacherSeviye;
        this.icerik=teacherIcerik;
        this.sinif=teacherSinif;

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
}
