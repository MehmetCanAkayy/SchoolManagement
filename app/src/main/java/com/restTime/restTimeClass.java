package com.restTime;

/**
 * Created by bulbul on 2/1/2018.
 */

public class restTimeClass {
    String artistName;
    String phoneNumber;
    boolean isReturn;
    String startTime;
    String endTime;

    public restTimeClass(){

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

    public restTimeClass(String artistName, String phoneNumber, boolean isReturn, String startTime, String endTime) {
        this.artistName = artistName;
        this.phoneNumber = phoneNumber;
        this.isReturn=isReturn;
        this.startTime = startTime;

        this.endTime=endTime;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isReturn() {
        return isReturn;
    }

    public void setReturn(boolean aReturn) {
        isReturn = aReturn;
    }
}
