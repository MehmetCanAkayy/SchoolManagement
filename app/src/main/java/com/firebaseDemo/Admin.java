package com.firebaseDemo;

/**
 * Created by bulbul on 2/1/2018.
 */

public class Admin {
    String artistName;
    String phoneNumber;

    public Admin(){

    }

    public Admin(String artistName,String phoneNumber) {
        this.artistName = artistName;
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getArtistName() {
        return artistName;
    }

}
