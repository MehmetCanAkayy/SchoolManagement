package com.onur.easyspeakdemo;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.StudentMenu.StudentSurveyActivity;
import com.firebaseDemo.StudentsActivity;
import com.firebaseDemo.TeacherActivity;
import com.studentsTabLayout.MainActivity;
import com.user.StudentRegister;
import com.user.TeacherRegister;

public class ShowStudentMenu extends AppCompatActivity {

    private Button ran_al,rand_iptal,userAddButton;

    GridLayout mainGrid;
    String phoneNumber ;
    String grade;
    String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_student_menu);

        mainGrid = (GridLayout) findViewById(R.id.studentGrid);

        //Set Event
        setSingleEvent(mainGrid);

        Intent intent = getIntent();
        phoneNumber = intent.getExtras().getString("phoneNumber");
        grade = intent.getExtras().getString("grade");
        name = intent.getExtras().getString("name");

    }


    private void setSingleEvent(GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(finalI==0){

                        final Intent studentListWeek=new Intent(ShowStudentMenu.this, MainActivity.class);


                                studentListWeek.putExtra("phoneNumber",phoneNumber);
                                studentListWeek.putExtra("grade",grade);


                                startActivityForResult(studentListWeek, 1);

                    }else if(finalI == 1 ){
                        final Intent studentSurvey=new Intent(ShowStudentMenu.this, StudentSurveyActivity.class);

                        studentSurvey.putExtra("phoneNumber",phoneNumber);
                        studentSurvey.putExtra("grade",grade);
                        studentSurvey.putExtra("name",name);



                        startActivityForResult(studentSurvey, 1);

                    }

                }
            });
        }
    }


}
