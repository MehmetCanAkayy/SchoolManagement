package com.firebaseDemo;

import android.os.Parcelable;

/**
 * Created by bulbul on 2/1/2018.
 */

public class Artist  {
    String artistName;
    String artistGrade;
    String phoneNumber;
    String lessonKey;
    String controlLesson;
    String artistKey;

    public Artist(){

    }

    public Artist(String artistKey,String artistName, String artistGrade,String phoneNumber,String lessonKey,String controlLesson) {
        this.artistName = artistName;
        this.artistGrade = artistGrade;
        this.phoneNumber = phoneNumber;
        this.lessonKey = lessonKey;
        this.controlLesson = controlLesson;
        this.artistKey=artistKey;
    }

    public String getArtistKey() {
        return artistKey;
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
