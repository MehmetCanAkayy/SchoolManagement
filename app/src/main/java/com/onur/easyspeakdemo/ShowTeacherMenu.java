package com.onur.easyspeakdemo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridLayout;

import com.alam_kanak.AlamKanakView;
import com.firebaseDemo.StudentsActivity;
import com.firebaseDemo.TeacherActivity;
import com.studentsTabLayout.MainActivity;
import com.teacherTabLayout.Main3Activity;
import com.user.StudentRegister;
import com.user.TeacherRegister;

public class ShowTeacherMenu extends AppCompatActivity {

    private Button ran_al,rand_iptal,userAddButton;

    GridLayout mainGrid;
    String phoneNumber ;
    String name;
    String grade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_teacher_menu);

        mainGrid = (GridLayout) findViewById(R.id.teacherGrid);

        //Set Event
        setSingleEvent(mainGrid);

        Intent intent = getIntent();
        phoneNumber = intent.getExtras().getString("phoneNumber");
        name=intent.getExtras().getString("name");
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

                    if(finalI==1){
                        final Intent teacherList= new Intent(ShowTeacherMenu.this, AlamKanakView.class);
                        startActivity(teacherList);
                    }else if(finalI == 0 ){
                        final Intent fragmentTeacher=new Intent(ShowTeacherMenu.this, Main3Activity.class);
                        fragmentTeacher.putExtra("phoneNumber",phoneNumber);
                        fragmentTeacher.putExtra("name",name);

                        startActivityForResult(fragmentTeacher, 1);


                    }

                }
            });
        }
    }


}
