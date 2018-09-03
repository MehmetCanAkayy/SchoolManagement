package com.firebaseDemo;

/**
 * Created by bulbul on 2/1/2018.
 */

public class Artist {
    String artistId;
    String artistName;
    String artistGenre;
    String addSession;

    public Artist(){

    }

    public Artist(String artistId, String artistName, String artistGenre, String addSession) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.artistGenre = artistGenre;
        this.addSession=addSession;
    }

    public String getArtistId() {
        return artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getArtistGenre() {
        return artistGenre;
    }
    public String getAddSession(){
        return addSession;
    }
}
