package com.onur.easyspeakdemo;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;



public class EventActvity extends AppCompatActivity {

    TextInputLayout baslangic =null;
    TextInputLayout bitis =null;
    EditText icerik =null;
    EditText baslangicEdit = null;
    EditText bitisEdit = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_actvity);



        Spinner spinner = (Spinner) findViewById(R.id.grades);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.grades, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);


        Spinner spinnerTeachers = (Spinner) findViewById(R.id.teachers);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.teachers, android.R.layout.simple_spinner_item);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerTeachers.setAdapter(adapter1);

        baslangic = findViewById(R.id.text_start_time_1);
        bitis = findViewById(R.id.text_end_time_1);
        icerik = findViewById(R.id.icerik);
        baslangicEdit = findViewById(R.id.text_start_time_edit_1);
        bitisEdit = findViewById(R.id.text_end_time_edit_1);

        Intent intent = getIntent();
        int hour = intent.getExtras().getInt("hour");
        int minute = intent.getExtras().getInt("minute");
        baslangicEdit.setText(hour+":30");
        bitisEdit.setText((hour+1) + ":30");

        baslangicEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(EventActvity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String label = null;
                        String label2 = null;
                        if(selectedMinute<10){
                            label = selectedHour + ":0" + selectedMinute;
                            label2 = (selectedHour+1) + ":0" + selectedMinute;

                        }else{
                            label = selectedHour + ":" + selectedMinute;
                            label2 = (selectedHour+1) + ":" + selectedMinute;


                        }
                        baslangicEdit.setText(label);
                        bitisEdit.setText(label2);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();


            }
        });
        baslangic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(EventActvity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String label = null;
                        String label2 = null;
                        if(selectedMinute<10){
                            label = selectedHour + ":0" + selectedMinute;
                            label2 = (selectedHour+1) + ":0" + selectedMinute;

                        }else{
                            label = selectedHour + ":" + selectedMinute;
                            label2 = (selectedHour+1) + ":" + selectedMinute;


                        }
                        baslangicEdit.setText(label);
                        bitisEdit.setText(label2);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();


            }
        });

        bitis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(EventActvity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String label = null;
                        if(selectedMinute<10){
                            label = selectedHour + ":0" + selectedMinute;

                        }else{
                            label = selectedHour + ":" + selectedMinute;

                        }
                        bitisEdit.setText(label);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();


            }
        });
        bitisEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(EventActvity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String label = null;
                        if(selectedMinute<10){
                            label = selectedHour + ":0" + selectedMinute;

                        }else{
                            label = selectedHour + ":" + selectedMinute;

                        }
                        bitisEdit.setText(label);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.kaydet:
                Toast.makeText(getApplicationContext(),"Kaydet Selected",Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.putExtra("baslangic", baslangicEdit.getText().toString());
                intent.putExtra("bitis", bitisEdit.getText().toString());
                intent.putExtra("icerik", icerik.getText().toString());

                setResult(Activity.RESULT_OK, intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
