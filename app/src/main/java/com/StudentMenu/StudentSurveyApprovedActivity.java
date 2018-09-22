package com.StudentMenu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.onur.easyspeakdemo.R;

public class StudentSurveyApprovedActivity extends AppCompatActivity {


    String day,baslangic,bitis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_survey_approved);

        Intent intent = getIntent();
        day = intent.getExtras().getString("day");
        baslangic = intent.getExtras().getString("baslangic");
        bitis = intent.getExtras().getString("bitis");


        TextView tv1,tv2,tv3;

        tv1 = findViewById(R.id.day);
        tv2 = findViewById(R.id.baslangicSurvey);
        tv3 = findViewById(R.id.bitisSurvey);

        tv1.setText(day);
        tv2.setText(baslangic);
        tv3.setText(bitis);




    }
}
