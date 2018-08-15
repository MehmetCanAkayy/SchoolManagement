package com.onur.easyspeakdemo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.nfc.Tag;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import net.steamcrafted.lineartimepicker.dialog.LinearTimePickerDialog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class randevuAl extends AppCompatActivity {

    CustomTimePickerDialog timePickerDialog = null;
    CalendarView calendarView;
    String date[] = new String[2];

//    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randevu_al);

        List<EventDay> events = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        events.add(new EventDay(calendar, R.drawable.day_item_background));
        final String fileName = "data.txt";
        final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/instinctcoder/readwrite/" ;



        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setEvents(events);
        timePickerDialog = new CustomTimePickerDialog(this, timeSetListener,
                Calendar.getInstance().get(Calendar.HOUR),
                CustomTimePickerDialog.getRoundedMinute(Calendar.getInstance().get(Calendar.MINUTE) + CustomTimePickerDialog.TIME_PICKER_INTERVAL),
                false
        );

        timePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Okey",
                new DialogInterface.OnClickListener() {
 String dizi[]=new String[4];
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        bundle.putInt("Selected Hour",timePickerDialog.getHour() );
//                        bundle.putInt("Selected Minute",timePickerDialog.getMinute() );
//                        bundle.putString("Selected Month",date[0] );
//                        bundle.putString("Selected Date",date[1] );
//                        randevuAl.this.onSaveInstanceState(bundle);
             dizi[0]= String.valueOf(timePickerDialog.getHour());
             dizi[1] = String.valueOf(timePickerDialog.getMinute());
             dizi[2] = String.valueOf(date[0]);
             dizi[3] = String.valueOf(date[1]);

             DosyayaEkle(dizi);
                        System.out.println(timePickerDialog.getHour() + " " + timePickerDialog.getMinute());
                    }
                });
        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();
String key;
                timePickerDialog.show();
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd");
                System.out.println(dateFormat.format(clickedDayCalendar.getTime()));
                date = dateFormat.format(clickedDayCalendar.getTime()).split(" ");

            }
        });









  }

//    @Override
//    public void onSaveInstanceState(Bundle savedInstanceState) {
//        super.onSaveInstanceState(savedInstanceState);
//        // Save UI state changes to the savedInstanceState.
//        // This bundle will be passed to onCreate if the process is
//        // killed and restarted.
//        savedInstanceState.putInt("Selected Hour",timePickerDialog.getHour() );
//        savedInstanceState.putInt("Selected Minute",timePickerDialog.getMinute() );
//        savedInstanceState.putString("Selected Month",date[0] );
//        savedInstanceState.putString("Selected Date",date[1] );
//
//        // etc.
//    }

    private CustomTimePickerDialog.OnTimeSetListener timeSetListener = new CustomTimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        }
    };// using CustomTimePickerDialog

    private static void DosyayaEkle(String[] metin){
        try{
            File dosya = new File("/storage/emulated/0/yeni.txt");
            FileWriter yazici = new FileWriter(dosya,true);
            BufferedWriter yaz = new BufferedWriter(yazici);
            for (int i = 0 ; i < metin.length ; i ++){
                yaz.write(metin[i]);
            }
            yaz.close();
            System.out.println("Ekleme İşlemi Başarılı"+dosya.getAbsolutePath());
        }
        catch (Exception hata){
            hata.printStackTrace();
        }
    }



}


