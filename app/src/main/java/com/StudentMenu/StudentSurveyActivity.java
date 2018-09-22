package com.StudentMenu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebaseDemo.Artist;
import com.firebaseDemo.LessonInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onur.easyspeakdemo.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class StudentSurveyActivity extends AppCompatActivity {

    Spinner spinnerBitis;
    Spinner spinnerBitis2;
    Spinner spinnerBitis3;
    String phoneNumber;
    String grade;
    String name;
    String studentKey;

    Spinner spinnerDay;
    Spinner spinnerDay2;
    Spinner spinnerDay3;
    Spinner spinnerBaslangic;
    Spinner spinnerBaslangic2;
    Spinner spinnerBaslangic3;
    private DatabaseReference databaseSurveyInfo;
    List<SurveyInfo> surveyList;
    boolean update = false;



    @Override
    protected void onStart() {
        super.onStart();
        //Retriving data From Firebase
        databaseSurveyInfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                surveyList.clear();

                for (DataSnapshot surveySnapshot : dataSnapshot.getChildren()) {
                    //Create Artist Class Object and Returning Value
                    SurveyInfo surveyInfo = surveySnapshot.getValue(SurveyInfo.class);
                    if(surveyInfo.getStudentKey().equals(studentKey)){
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
        setContentView(R.layout.activity_student_survey);

        databaseSurveyInfo = FirebaseDatabase.getInstance().getReference("surveyInfo");

        surveyList = new ArrayList<>();

        Intent intent = getIntent();
        phoneNumber = intent.getExtras().getString("phoneNumber");
        grade = intent.getExtras().getString("grade");
        name = intent.getExtras().getString("name");
        studentKey = intent.getExtras().getString("studentKey");



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


        CustomAdapter dataAdapter = new CustomAdapter(this, android.R.layout.simple_spinner_item, list, 0);

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
                    CustomAdapter dataAdapter = new CustomAdapter(StudentSurveyActivity.this, android.R.layout.simple_spinner_item, list, index);
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

        spinnerDay2 = new Spinner(this);
        spinnerDay2 = findViewById(R.id.day2);

        ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        days); //selected item will look like a spinner set from XML
        spinnerArrayAdapter2.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinnerDay2.setAdapter(spinnerArrayAdapter2);


        spinnerBaslangic2 = new Spinner(this);
        spinnerBaslangic2 = findViewById(R.id.baslangic2);

        ArrayAdapter<CharSequence> adapterSurveyBaslangic2 = ArrayAdapter.createFromResource(this,
                R.array.surveyStartTimes, android.R.layout.simple_spinner_item);
        adapterSurveyBaslangic2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerBaslangic2.setAdapter(adapterSurveyBaslangic2);
        spinnerBitis2 = new Spinner(this);
        spinnerBitis2 = findViewById(R.id.bitis2);





        CustomAdapter dataAdapter2 = new CustomAdapter(this, android.R.layout.simple_spinner_item, list, 0);

        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerBitis2.setAdapter(dataAdapter2);


        Spinner finalSpinnerBaslangic2 = spinnerBaslangic2;
        spinnerBaslangic2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position == 1) {
                    spinnerBitis2.setEnabled(false);
                } else {
                    int index = finalSpinnerBaslangic2.getSelectedItemPosition();
                    CustomAdapter dataAdapter2 = new CustomAdapter(StudentSurveyActivity.this, android.R.layout.simple_spinner_item, list, index);
                    dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerBitis2.setEnabled(true);
                    spinnerBitis2.setAdapter(dataAdapter2);
                    spinnerBitis2.setSelection(index);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerDay3 = new Spinner(this);
        spinnerDay3 = findViewById(R.id.day3);

        ArrayAdapter<String> spinnerArrayAdapter3 = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        days); //selected item will look like a spinner set from XML
        spinnerArrayAdapter3.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinnerDay3.setAdapter(spinnerArrayAdapter3);


        spinnerBaslangic3 = new Spinner(this);
        spinnerBaslangic3 = findViewById(R.id.baslangic3);

        ArrayAdapter<CharSequence> adapterSurveyBaslangic3 = ArrayAdapter.createFromResource(this,
                R.array.surveyStartTimes, android.R.layout.simple_spinner_item);
        adapterSurveyBaslangic3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerBaslangic3.setAdapter(adapterSurveyBaslangic3);
        spinnerBitis3 = new Spinner(this);
        spinnerBitis3 = findViewById(R.id.bitis3);





        CustomAdapter dataAdapter3 = new CustomAdapter(this, android.R.layout.simple_spinner_item, list, 0);

        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerBitis3.setAdapter(dataAdapter3);


        Spinner finalSpinnerBaslangic3 = spinnerBaslangic3;
        spinnerBaslangic3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position == 1) {
                    spinnerBitis3.setEnabled(false);
                } else {
                    int index = finalSpinnerBaslangic3.getSelectedItemPosition();
                    CustomAdapter dataAdapter3 = new CustomAdapter(StudentSurveyActivity.this, android.R.layout.simple_spinner_item, list, index);
                    dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerBitis3.setEnabled(true);
                    spinnerBitis3.setAdapter(dataAdapter3);
                    spinnerBitis3.setSelection(index);
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
                Toast.makeText(getApplicationContext(),"Kaydet Selected",Toast.LENGTH_LONG).show();




                String key = databaseSurveyInfo.push().getKey();


                String day = spinnerDay.getItemAtPosition(spinnerDay.getSelectedItemPosition()).toString();
                String startTime= "";
                String endTime="";
                if(day.equals("SECINIZ...")){

                    day="";
                }else{
                    startTime = spinnerBaslangic.getItemAtPosition(spinnerBaslangic.getSelectedItemPosition()).toString();
                    endTime="";
                    if(spinnerBaslangic.getSelectedItemPosition()==1){
                        startTime = "10:30";
                        endTime = "23:30";

                    }else{
                        endTime = spinnerBitis.getItemAtPosition(spinnerBitis.getSelectedItemPosition()).toString();
                    }
                }




                String day2 = spinnerDay2.getItemAtPosition(spinnerDay2.getSelectedItemPosition()).toString();
                String startTime2= "";
                String endTime2="";
                if(day2.equals("SECINIZ...")){

                    day2="";
                }else{
                    startTime2 = spinnerBaslangic2.getItemAtPosition(spinnerBaslangic2.getSelectedItemPosition()).toString();
                    endTime2="";
                    if(spinnerBaslangic2.getSelectedItemPosition()==1){
                        startTime2 = "10:30";
                        endTime2 = "23:30";

                    }else{
                        endTime2 = spinnerBitis2.getItemAtPosition(spinnerBitis2.getSelectedItemPosition()).toString();
                    }
                }


                String day3 = spinnerDay3.getItemAtPosition(spinnerDay3.getSelectedItemPosition()).toString();

                String startTime3= "";
                String endTime3="";
                if(day3.equals("SECINIZ...")){

                    day3="";
                }else{
                    startTime3 = spinnerBaslangic3.getItemAtPosition(spinnerBaslangic3.getSelectedItemPosition()).toString();
                    endTime3="";
                    if(spinnerBaslangic3.getSelectedItemPosition()==1){
                        startTime3 = "10:30";
                        endTime3 = "23:30";

                    }else{
                        endTime3 = spinnerBitis3.getItemAtPosition(spinnerBitis3.getSelectedItemPosition()).toString();
                    }
                }

                System.out.println(day);
                System.out.println(startTime);
                System.out.println(endTime);
                final SurveyInfo surveyInfo2= new SurveyInfo(false,studentKey,name,grade,phoneNumber,day,day2,day3,startTime,endTime,startTime2,endTime2,startTime3,endTime3,key);

                if(update){
                    databaseSurveyInfo.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot surveySnapshot : dataSnapshot.getChildren() ){
                                //Create Artist Class Object and Returning Value
                                SurveyInfo surveyInfo =  surveySnapshot.getValue(SurveyInfo.class);
                                surveyInfo2.surveyKey=surveyInfo.getSurveyKey();

                                if(surveyInfo.getStudentKey().equals(studentKey)){

                                    surveySnapshot.getRef().setValue(surveyInfo2);
                                }
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            System.out.println("Student Verisi Çekilemedi.");

                        }
                    });
                }else{
                    databaseSurveyInfo.child(key).setValue(surveyInfo2);

                }


                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);

                finish();
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

