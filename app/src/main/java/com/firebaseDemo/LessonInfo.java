package com.firebaseDemo;

import java.io.Serializable;
import java.util.Calendar;

public class LessonInfo implements Serializable {
    String baslangic;
    String bitis;
    String grade;
    String teacher;
    String icerik ;
    String ders;
    int color;
    String lessonKey;
    boolean isSelected;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public LessonInfo(){}

    public LessonInfo(String baslangic, String bitis, String grade, String teacher, String icerik,String ders,String lessonKey){
        this.baslangic=baslangic;
        this.bitis=bitis;
        this.grade=grade;
        this.teacher=teacher;
        this.icerik=icerik;
        this.ders = ders;
        this.lessonKey = lessonKey;
    }

    public String getDers() {
        return ders;
    }

    public String getLessonKey() {
        return lessonKey;
    }

    public String getBaslangic() {
        return baslangic;
    }
    public int getDay(){
        Calendar startTime=Calendar.getInstance();
        Calendar endTime=Calendar.getInstance();

        String[] calendarStartItem = baslangic.split("%");



        startTime.set(Integer.parseInt(calendarStartItem[0]),Integer.parseInt(calendarStartItem[1]),Integer.parseInt(calendarStartItem[2]),Integer.parseInt(calendarStartItem[3]),Integer.parseInt(calendarStartItem[4]));



        return startTime.get(Calendar.DAY_OF_MONTH);
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
    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
