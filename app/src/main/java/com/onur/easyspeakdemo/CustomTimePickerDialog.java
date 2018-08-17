package com.onur.easyspeakdemo;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;

public class CustomTimePickerDialog extends TimePickerDialog {

    public static final int TIME_PICKER_INTERVAL=30;
    private boolean mIgnoreEvent=false;
    int hour=0;
    int minute=0;

    public CustomTimePickerDialog(Context context, OnTimeSetListener callBack, int hourOfDay, int minute,
                                  boolean is24HourView) {
        super(context, AlertDialog.THEME_HOLO_DARK, callBack, hourOfDay, minute, is24HourView);
    }
    /*
     * (non-Javadoc)
     * @see android.app.TimePickerDialog#onTimeChanged(android.widget.TimePicker, int, int)
     * Implements Time Change Interval
     */
    @Override
    public void onTimeChanged(TimePicker timePicker, int hourOfDay, int minute) {
        super.onTimeChanged(timePicker, hourOfDay, minute);
        if (!mIgnoreEvent){
            minute = getRoundedMinute(minute);
            hourOfDay = getRoundenHours(hourOfDay);
            mIgnoreEvent=true;
            timePicker.setCurrentMinute(minute);
            mIgnoreEvent=false;
            timePicker.setCurrentHour(hourOfDay);
            this.hour = hourOfDay;
            this.minute=minute;
        }
    }

    public void onTimeSet(TimePicker view,int hourOfDay,int minute){

        System.out.println(hour + " " + minute);
    }



    public int getHour() {
        return hour;
    }


    public int getMinute() {
        return minute;
    }


    public static int getRoundedMinute(int minute){
        if(minute % TIME_PICKER_INTERVAL != 0){
            int minuteFloor = minute - (minute % TIME_PICKER_INTERVAL);
            minute = minuteFloor + (minute == minuteFloor + 1 ? TIME_PICKER_INTERVAL : 0);
            if (minute == 60)  minute=0;
        }

        return minute;
    }
    public static int getRoundenHours(int hour){
        if (0<=hour && hour<=5){

            return hour;
        }
        else return 0;

    }
}