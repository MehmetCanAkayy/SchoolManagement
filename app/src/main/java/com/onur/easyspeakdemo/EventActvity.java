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

import com.firebaseDemo.FirebaseHelper;
import com.firebaseDemo.Teacher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;



public class EventActvity extends AppCompatActivity {

    TextInputLayout baslangic =null;
    TextInputLayout bitis =null;
    EditText icerik =null;
    EditText baslangicEdit = null;
    EditText bitisEdit = null;
    Spinner grades ;
    Spinner days ;
    int grade=0,teacher=0;
    DatabaseReference databaseTeacher;
    private List<Teacher> teacherList;
    List<String> teacherlist1 = new ArrayList<>();

    Spinner spinnerTeachers;
    Spinner spinnerLesson;

    boolean isUpdate;
    String key;
    FirebaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_actvity);


        databaseTeacher = FirebaseDatabase.getInstance().getReference("teachers");
        teacherList = new ArrayList<>();
        teacherlist1=new ArrayList<>();
        days = (Spinner) findViewById(R.id.day);
        spinnerTeachers= findViewById(R.id.teachers);


        ArrayAdapter<CharSequence> adapterDays = ArrayAdapter.createFromResource(this,
                R.array.days, android.R.layout.simple_spinner_item);
        adapterDays.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        days.setAdapter(adapterDays);

        grades = (Spinner) findViewById(R.id.grades);





        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.grades, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        grades.setAdapter(adapter);

        spinnerLesson = (Spinner) findViewById(R.id.lesson);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.lesson, android.R.layout.simple_spinner_item);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLesson.setAdapter(adapter2);


       databaseTeacher.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot teacherSnapshot : dataSnapshot.getChildren() ){
                    //Create Artist Class Object and Returning Value
                    Teacher teachers = teacherSnapshot.getValue(Teacher.class);
                    teacherList.add(teachers);
                    teacherlist1.add(teachers.getName());


                }

                ArrayAdapter<String> adapter1 = new ArrayAdapter<String> (EventActvity.this, android.R.layout.simple_spinner_item,teacherlist1);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_item);

                spinnerTeachers.setAdapter(adapter1);
                spinnerTeachers.setSelection(0);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


       /* teacherlist1.add("deneme1");
        teacherlist1.add("deneme2");*/



        //adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);*/



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
        boolean control = intent.getExtras().getBoolean("isFloatingButtonClicked");
        isUpdate = intent.getExtras().getBoolean("isUpdate");
        int day = intent.getExtras().getInt("Day");
        grade = intent.getExtras().getInt("Grade");
        teacher = intent.getExtras().getInt("Teacher");


        if(control){
            days.setVisibility(View.VISIBLE);
            if(isUpdate){
                spinnerLesson.setSelection(intent.getExtras().getInt("Ders"));
                days.setSelection(day);
                grades.setSelection(grade);
                spinnerTeachers.setSelection(teacher);
                icerik.setText(intent.getExtras().getString("Content"));
                key = intent.getExtras().getString("Key");

            }
        }
        else{
            days.setVisibility(View.GONE);
        }
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
                intent.putExtra("day", (days.getSelectedItemPosition()));
                intent.putExtra("teacher", spinnerTeachers.getSelectedItem().toString());
                intent.putExtra("grade", grades.getSelectedItem().toString());
                intent.putExtra("baslangic", baslangicEdit.getText().toString());

                intent.putExtra("bitis", bitisEdit.getText().toString());
                intent.putExtra("icerik", icerik.getText().toString());
                intent.putExtra("ders", spinnerLesson.getSelectedItem().toString());
                if(isUpdate){


                    intent.putExtra("key", key);

                    setResult(2, intent);
                }else{

                    setResult(Activity.RESULT_OK, intent);
                }
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
