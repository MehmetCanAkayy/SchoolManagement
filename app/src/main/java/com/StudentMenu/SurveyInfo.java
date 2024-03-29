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
    String today;
    boolean approved;

    public SurveyInfo(){

    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistGrade() {
        return artistGrade;
    }

    public void setArtistGrade(String artistGrade) {
        this.artistGrade = artistGrade;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getStartTime2() {
        return startTime2;
    }

    public void setStartTime2(String startTime2) {
        this.startTime2 = startTime2;
    }

    public String getEndTime2() {
        return endTime2;
    }

    public void setEndTime2(String endTime2) {
        this.endTime2 = endTime2;
    }

    public String getStartTime3() {
        return startTime3;
    }

    public void setStartTime3(String startTime3) {
        this.startTime3 = startTime3;
    }

    public String getEndTime3() {
        return endTime3;
    }

    public void setEndTime3(String endTime3) {
        this.endTime3 = endTime3;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDay2() {
        return day2;
    }

    public void setDay2(String day2) {
        this.day2 = day2;
    }

    public String getDay3() {
        return day3;
    }

    public void setDay3(String day3) {
        this.day3 = day3;
    }

    public String getStudentKey() {
        return studentKey;
    }

    public void setStudentKey(String studentKey) {
        this.studentKey = studentKey;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

    public SurveyInfo(String today, boolean approved, String studentKey, String artistName, String artistGrade, String phoneNumber, String day, String day2, String day3, String startTime, String endTime, String startTime2, String endTime2, String startTime3, String endTime3, String surveyKey) {
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

        this.approved = approved;
        this.today = today;


    }


}
