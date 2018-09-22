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

import com.firebaseDemo.StudentsActivity;
import com.firebaseDemo.TeacherActivity;
import com.studentsTabLayout.MainActivity;
import com.user.StudentRegister;
import com.user.TeacherRegister;

public class dersSecimi extends AppCompatActivity {

    private Button ran_al,rand_iptal,userAddButton;

    GridLayout mainGrid;
    String phoneNumber ;
    String grade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ders_secimi2);

        mainGrid = (GridLayout) findViewById(R.id.mainGrid);

        //Set Event
            setSingleEvent(mainGrid);

        Intent intent = getIntent();
        phoneNumber = intent.getExtras().getString("phoneNumber");
        grade = intent.getExtras().getString("grade");

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
                        final Intent teacherList= new Intent(dersSecimi.this, TeacherActivity.class);

                        final Intent studentActivity = new Intent(dersSecimi.this, StudentsActivity.class);

                        //final Intent sayfagecis3=new Intent(dersSecimi.this, AlamKanakActivity.class);

                        final Intent studentListWeek=new Intent(dersSecimi.this, MainActivity.class);
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(dersSecimi.this);
                        View mView = getLayoutInflater().inflate(R.layout.dialog_login, null);
                        Button activity = (Button) mView.findViewById(R.id.activity);

                        Button social = (Button) mView.findViewById(R.id.social);

                        Button chat = (Button) mView.findViewById(R.id.chat);
                        Button speaking = (Button) mView.findViewById(R.id.speaking);
                        Button teacherListe=(Button) mView.findViewById(R.id.teacherList);




//                mBuilder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                });
//
//                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                });
                        mBuilder.setView(mView);
                        final AlertDialog dialog = mBuilder.create();
                        dialog.show();
                        activity.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                studentListWeek.putExtra("phoneNumber",phoneNumber);
                                studentListWeek.putExtra("grade",grade);


                                startActivityForResult(studentListWeek, 1);
                            }
                        });
                        social.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //startActivity(sayfagecis3);
                            }
                        });
                        chat.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                startActivity(studentActivity);
                            }
                        });
                        speaking.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //startActivity(sayfagecis);
                            }
                        });

                        teacherListe.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(teacherList);
                            }
                        });
                    }else if(finalI == 1 ){
                        final Intent studentAdd=new Intent(dersSecimi.this, StudentRegister.class);
                        final Intent teacherAdd=new Intent(dersSecimi.this, TeacherRegister.class);

                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(dersSecimi.this);
                        View mView = getLayoutInflater().inflate(R.layout.dialog_register, null);
                        Button student = (Button) mView.findViewById(R.id.student);

                        Button teacher = (Button) mView.findViewById(R.id.teacher);

                        mBuilder.setView(mView);
                        final AlertDialog dialog = mBuilder.create();
                        dialog.show();
                        student.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(studentAdd);
                            }
                        });
                        teacher.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(teacherAdd);
                            }
                        });
                    }

                }
            });
        }
    }


}
