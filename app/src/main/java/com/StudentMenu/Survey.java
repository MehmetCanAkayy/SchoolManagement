package com.StudentMenu;

/**
 * Created by bulbul on 2/1/2018.
 */

public class Survey {
    String surveyKey;
    String startTime;
    String endTime;
    String day;
    String studentsKey;

    public Survey(){

    }



    public Survey(String studentKey, String day, String startTime, String endTime, String surveyKey) {
        this.startTime = startTime;
        this.endTime=endTime;
        this.surveyKey = surveyKey;
        this.day = day;
        this.studentsKey = studentKey;
    }


    public String getSurveyKey() {
        return surveyKey;
    }

    public void setSurveyKey(String surveyKey) {
        this.surveyKey = surveyKey;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStudentsKey() {
        return studentsKey;
    }

    public void setStudentsKey(String studentsKey) {
        this.studentsKey = studentsKey;
    }
}
