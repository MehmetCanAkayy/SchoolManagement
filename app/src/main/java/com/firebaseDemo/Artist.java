package com.firebaseDemo;

/**
 * Created by bulbul on 2/1/2018.
 */

public class Artist {
    String artistName;
    String artistGrade;
    String phoneNumber;
    String lessonKey;
    String controlLesson;

    public Artist(){

    }

    public Artist(String artistName, String artistGrade,String phoneNumber,String lessonKey,String controlLesson) {
        this.artistName = artistName;
        this.artistGrade = artistGrade;
        this.phoneNumber = phoneNumber;
        this.lessonKey = lessonKey;
        this.controlLesson = controlLesson;
    }

    public String getControlLesson() {
        return controlLesson;
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

    public String getLessonKey() {
        return lessonKey;
    }
}
