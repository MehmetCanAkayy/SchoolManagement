package com.firebaseDemo;

import java.util.Calendar;

public class LessonInfo {
    String baslangic;
    String bitis;
    String grade;
    String teacher;
    String icerik ;


    public LessonInfo(){}

    public LessonInfo(String baslangic, String bitis, String grade, String teacher, String icerik){
        this.baslangic=baslangic;
        this.bitis=bitis;
        this.grade=grade;
        this.teacher=teacher;
        this.icerik=icerik;
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
