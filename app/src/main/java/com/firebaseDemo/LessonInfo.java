package com.firebaseDemo;

import java.util.Calendar;

public class LessonInfo {
    String baslangic;
    String bitis;
    String grade;
    String teacher;
    String icerik ;
    String ders;


    public LessonInfo(){}

    public LessonInfo(String baslangic, String bitis, String grade, String teacher, String icerik,String ders){
        this.baslangic=baslangic;
        this.bitis=bitis;
        this.grade=grade;
        this.teacher=teacher;
        this.icerik=icerik;
        this.ders = ders;
    }

    public String getDers() {
        return ders;
    }

    public String getBaslangic() {
        return baslangic;
    }

    public String getBitis() {
        return bitis;
    }

    public String getGrade() {
        return grade;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getIcerik() {
        return icerik;
    }
}
