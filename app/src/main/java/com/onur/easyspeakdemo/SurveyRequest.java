package com.onur.easyspeakdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.StudentMenu.Survey;
import com.StudentMenu.SurveyInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SurveyRequest extends AppCompatActivity {

    Spinner spinnerBitis;
    Spinner spinnerBitis2;
    Spinner spinnerBitis3;
    String phoneNumber;
    String grade;
    String name;
    String studentKey;
    String day1,
    day2 ,
    day3 ,
    start1 ,
    start2 ,
    start3 ,
    end1 ,
    end2 ,
    end3,survey ;

    Spinner spinnerDay;
    Spinner spinnerDay2;
    Spinner spinnerDay3;
    Spinner spinnerBaslangic;
    Spinner spinnerBaslangic2;
    Spinner spinnerBaslangic3;
    private DatabaseReference databaseSurveyInfo;
    private DatabaseReference databaseSurvey;

    boolean update = false;


    @Override
    protected void onStart() {
        super.onStart();
        //Retriving data From Firebase
        databaseSurvey.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot surveySnapshot : dataSnapshot.getChildren()) {
                    //Create Artist Class Object and Returning Value
                    Survey surveyInfo = surveySnapshot.getValue(Survey.class);
                    if(surveyInfo.getStudentsKey().equals(studentKey)){
                        update = true;
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Student Verisi Çekilemedi.");

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_survey);

        databaseSurveyInfo = FirebaseDatabase.getInstance().getReference("surveyInfo");
        databaseSurvey = FirebaseDatabase.getInstance().getReference("surveys");


        Intent intent = getIntent();
        phoneNumber = intent.getExtras().getString("phoneNumber");
        grade = intent.getExtras().getString("grade");
        name = intent.getExtras().getString("name");
        studentKey = intent.getExtras().getString("studentKey");
        day1 = intent.getExtras().getString("day1");
        day2 = intent.getExtras().getString("day2");
        day3 = intent.getExtras().getString("day3");
        start1 = intent.getExtras().getString("start1");
        start2 = intent.getExtras().getString("start2");
        start3 = intent.getExtras().getString("start3");
        end1 = intent.getExtras().getString("end1");
        end2 = intent.getExtras().getString("end2");
        end3 = intent.getExtras().getString("end3");
        survey = intent.getExtras().getString("survey");


        TextView tv1 = findViewById(R.id.text1);
        TextView tv2 = findViewById(R.id.text2);
        TextView tv3 = findViewById(R.id.text3);
        TextView tv4 = findViewById(R.id.text4);


        tv1.setText(day1 + " " + start1 + " " + end1);
        tv2.setText(day2 + " " + start2 + " " + end2);
        tv3.setText(day3 + " " + start3 + " " + end3);
        tv4.setText(survey);










        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Calendar date = Calendar.getInstance();


        String[] days = new String[15];
        days[0]="SECINIZ...";
        for (int i = 1; i < 15; i++) {
            date.add(Calendar.DATE, 1);
            String dayName = date.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
            days[i] = format.format(date.getTime()) + " " + dayName;

            System.out.println("date :" + days[i]);
        }

        spinnerDay = new Spinner(this);
        spinnerDay = findViewById(R.id.day1);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        days); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinnerDay.setAdapter(spinnerArrayAdapter);


        spinnerBaslangic = new Spinner(this);
        spinnerBaslangic = findViewById(R.id.baslangic1);

        ArrayAdapter<CharSequence> adapterSurveyBaslangic = ArrayAdapter.createFromResource(this,
                R.array.surveyStartTimes, android.R.layout.simple_spinner_item);
        adapterSurveyBaslangic.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerBaslangic.setAdapter(adapterSurveyBaslangic);
        spinnerBitis = new Spinner(this);
        spinnerBitis = findViewById(R.id.bitis1);



        List<String> list = Arrays.asList(getResources().getStringArray(R.array.surveyEndTimes));


        CustomAdapter dataAdapter = new CustomAdapter(this, android.R.layout.simple_spinner_item, list, -1);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerBitis.setAdapter(dataAdapter);


        Spinner finalSpinnerBaslangic = spinnerBaslangic;
        spinnerBaslangic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position == 1) {
                    spinnerBitis.setEnabled(false);
                } else {
                    int index = finalSpinnerBaslangic.getSelectedItemPosition();
                    CustomAdapter dataAdapter = new CustomAdapter(SurveyRequest.this, android.R.layout.simple_spinner_item, list, index);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerBitis.setEnabled(true);
                    spinnerBitis.setAdapter(dataAdapter);
                    spinnerBitis.setSelection(index);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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




                String key = databaseSurvey.push().getKey();


                String day = spinnerDay.getItemAtPosition(spinnerDay.getSelectedItemPosition()).toString();
                String startTime= "";
                String endTime="";
                if(day.equals("SECINIZ...")){

                    day="";
                    Toast.makeText(getApplicationContext(),"Survey eklenemedi! Gecerli gun ve tarih seciniz...",Toast.LENGTH_LONG).show();

                }else{
                    startTime = spinnerBaslangic.getItemAtPosition(spinnerBaslangic.getSelectedItemPosition()).toString();
                    endTime="";
                    if(spinnerBaslangic.getSelectedItemPosition()==1){
                        startTime = "10:30";
                        endTime = "23:30";

                    }else{
                        endTime = spinnerBitis.getItemAtPosition(spinnerBitis.getSelectedItemPosition()).toString();
                    }
                    System.out.println(day);
                    System.out.println(startTime);
                    System.out.println(endTime);
                    //final SurveyInfo surveyInfo2= new SurveyInfo(studentKey,name,grade,phoneNumber,day,day2,day3,startTime,endTime,startTime2,endTime2,startTime3,endTime3,key);
                    Survey survey2 = new Survey(studentKey,day,startTime,endTime,key);
                    String[] parse1 = startTime.split(":");
                    String[] parse2 = endTime.split(":");
                    String[] parsestart1 = start1.split(":");
                    String[] parsestart2 = start2.split(":");
                    String[] parsestart3 = start3.split(":");
                    String[] parseEnd1 = end1.split(":");
                    String[] parseEnd2 = end2.split(":");
                    String[] parseEnd3 = end3.split(":");
                    if((day.equals(day1)&&Integer.parseInt(parse1[0])>=Integer.parseInt(parsestart1[0])&&Integer.parseInt(parse2[0])<=Integer.parseInt(parseEnd1[0]))
                            ||(day.equals(day2)&&Integer.parseInt(parse1[0])>=Integer.parseInt(parsestart2[0])&&Integer.parseInt(parse2[0])<=Integer.parseInt(parseEnd2[0]))||
                            (day.equals(day3)&&Integer.parseInt(parse1[0])>=Integer.parseInt(parsestart3[0])&&Integer.parseInt(parse2[0])<=Integer.parseInt(parseEnd3[0]))){

                        if(update){
                            databaseSurvey.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    for (DataSnapshot surveySnapshot : dataSnapshot.getChildren() ){
                                        //Create Artist Class Object and Returning Value
                                        Survey surveyInfo =  surveySnapshot.getValue(Survey.class);
                                        survey2.setSurveyKey(surveyInfo.getSurveyKey());

                                        if(surveyInfo.getStudentsKey().equals(studentKey)){

                                            surveySnapshot.getRef().setValue(survey2);
                                        }
                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    System.out.println("Student Verisi Çekilemedi.");

                                }
                            });
                        }else{
                            databaseSurvey.child(key).setValue(survey2);

                        }
                        Intent intent = new Intent();
                        setResult(Activity.RESULT_OK, intent);

                        finish();

                    }else{
                        Toast.makeText(getApplicationContext(),"Survey eklenemedi! Kullanicinin istegine gore bir tarih seciniz...",Toast.LENGTH_LONG).show();

                    }



                }











                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public class CustomAdapter extends ArrayAdapter<String> {

        private int hidingItemIndex;

        public CustomAdapter(Context context, int textViewResourceId, List<String> objects, int hidingItemIndex) {
            super(context, textViewResourceId, objects);
            this.hidingItemIndex = hidingItemIndex;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View v = null;

            for(int i = 0 ; i < hidingItemIndex; i++){
                if (position < hidingItemIndex) {
                    TextView tv = new TextView(getContext());
                    tv.setVisibility(View.GONE);
                    v = tv;
                } else {
                    v = super.getDropDownView(position, null, parent);
                }
            }

            return v;
        }
    }
}

