package com.firebaseDemo;

import java.util.Calendar;

public class LessonInfo {
    Calendar baslangic;
    Calendar bitis;
    String grade;
    String teacher;
    String icerik ;


    public LessonInfo(){}

    public LessonInfo(Calendar baslangic, Calendar bitis, String grade, String teacher, String icerik){
        this.baslangic=baslangic;
        this.bitis=bitis;
        this.grade=grade;
        this.teacher=teacher;
        this.icerik=icerik;
    }

    public Calendar getBaslangic() {
        return baslangic;
    }

    public Calendar getBitis() {
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
