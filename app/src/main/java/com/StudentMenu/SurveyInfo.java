package com.StudentMenu;

/**
 * Created by bulbul on 2/1/2018.
 */

public class SurveyInfo {
    String artistName;
    String artistGrade;
    String phoneNumber;
    String surveyKey;
    String startTime;
    String endTime;
    String startTime2;
    String endTime2;
    String startTime3;
    String endTime3;
    String day;
    String day2;
    String day3;
    String studentKey;

    public SurveyInfo(){

    }

    public String getSurveyKey() {
        return surveyKey;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getStartTime2() {
        return startTime2;
    }

    public String getEndTime2() {
        return endTime2;
    }

    public String getStartTime3() {
        return startTime3;
    }

    public String getEndTime3() {
        return endTime3;
    }

    public String getDay() {
        return day;
    }

    public String getDay2() {
        return day2;
    }

    public String getDay3() {
        return day3;
    }

    public SurveyInfo(String studentKey,String artistName, String artistGrade, String phoneNumber, String day, String day2, String day3, String startTime, String endTime, String startTime2, String endTime2, String startTime3, String endTime3, String surveyKey) {
        this.artistName = artistName;
        this.artistGrade = artistGrade;
        this.phoneNumber = phoneNumber;
        this.startTime = startTime;
        this.endTime=endTime;
        this.startTime2 = startTime2;
        this.endTime2=endTime2;
        this.startTime3 = startTime3;
        this.endTime3=endTime3;
        this.surveyKey = surveyKey;
        this.day = day;
        this.day2 = day2;
        this.day3 = day3;
        this.studentKey = studentKey;


    }

    public String getStudentKey() {
        return studentKey;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getArtistGrade() {
        return artistGrade;
    }

}
