package com.firebaseDemo;

/**
 * Created by bulbul on 2/1/2018.
 */

public class Artist {
    String artistName;
    String artistGrade;

    public Artist(){

    }

    public Artist(String artistName, String artistGrade) {
        this.artistName = artistName;
        this.artistGrade = artistGrade;
    }


    public String getArtistName() {
        return artistName;
    }

    public String getArtistGrade() {
        return artistGrade;
    }
}
