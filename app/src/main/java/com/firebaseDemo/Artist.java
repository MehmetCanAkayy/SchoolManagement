package com.firebaseDemo;

/**
 * Created by bulbul on 2/1/2018.
 */

public class Artist {
    String artistName;
    String artistGrade;
    String phoneNumber;

    public Artist(){

    }

    public Artist(String artistName, String artistGrade,String phoneNumber) {
        this.artistName = artistName;
        this.artistGrade = artistGrade;
        this.phoneNumber = phoneNumber;
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
