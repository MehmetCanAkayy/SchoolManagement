package com.onur.easyspeakdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import com.Clubs.ClubsAdd;
import com.alam_kanak.AlamKanakView;
import com.firebaseDemo.StudentsActivity;
import com.firebaseDemo.TeacherActivity;

import com.restTime.ShowRestTimeRequest;
import com.studentsTabLayout.MainActivity;

import com.user.AdminRegister;
import com.user.StudentRegister;
import com.user.TeacherRegister;

public class ShowAdminMenu extends AppCompatActivity {

    private Button ran_al,rand_iptal,userAddButton;

    GridLayout mainGrid;
    String phoneNumber ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_admin_menu);

        mainGrid = (GridLayout) findViewById(R.id.studentGrid);

        //Set Event
        setSingleEvent(mainGrid);

        Intent intent = getIntent();
        phoneNumber = intent.getExtras().getString("phoneNumber");
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

                        final Intent studentListWeek=new Intent(ShowAdminMenu.this, AlamKanakView.class);

                         startActivityForResult(studentListWeek, 1);

                    }else if(finalI == 1 ){
                        final Intent adminRegister=new Intent(ShowAdminMenu.this, AdminRegister.class);


                        startActivityForResult(adminRegister, 1);


                    }
                    else if(finalI == 2 ){
                        final Intent teacherRegister=new Intent(ShowAdminMenu.this, TeacherRegister.class);


                        startActivityForResult(teacherRegister, 1);


                    }else if(finalI == 3 ){
                        final Intent studentRegister=new Intent(ShowAdminMenu.this, StudentRegister.class);


                        startActivityForResult(studentRegister, 1);


                    }else if(finalI == 4 ){

                        final Intent showTeacher=new Intent(ShowAdminMenu.this, TeacherActivity.class);


                        startActivityForResult(showTeacher, 1);



                    }else if(finalI == 5 ){
                        final Intent showStudents=new Intent(ShowAdminMenu.this, StudentsActivity.class);


                        startActivityForResult(showStudents, 1);


                    }
                      else if(finalI== 6){
                        final Intent intent=new Intent(ShowAdminMenu.this,Kampanyalar.class);
                        startActivity(intent);
                    }
                    else if(finalI==7) {
                        final Intent intent = new Intent(ShowAdminMenu.this, ClubsAdd.class);
                        startActivity(intent);
                    }
                    else if(finalI == 8 ){
                        final Intent showRestTimeRequest=new Intent(ShowAdminMenu.this, ShowRestTimeRequest.class);
                        startActivityForResult(showRestTimeRequest, 1);


                    }

                }
            });
        }
    }


}
