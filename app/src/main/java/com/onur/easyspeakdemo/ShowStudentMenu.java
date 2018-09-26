package com.onur.easyspeakdemo;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.Clubs.DenemeActivity;
import com.StudentMenu.StudentSurveyActivity;
import com.StudentMenu.StudentSurveyApprovedActivity;
import com.StudentMenu.Survey;
import com.StudentMenu.SurveyInfo;
import com.firebaseDemo.StudentsActivity;
import com.firebaseDemo.TeacherActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studentsTabLayout.MainActivity;
import com.tureng.TurengTranslate;
import com.user.StudentRegister;
import com.user.TeacherRegister;

import java.util.ArrayList;
import java.util.List;

public class ShowStudentMenu extends AppCompatActivity {

    private Button ran_al,rand_iptal,userAddButton;

    GridLayout mainGrid;
    String phoneNumber ;
    String grade;
    String name;
    String studentKey;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_student_menu);


        mainGrid = (GridLayout) findViewById(R.id.mainGrid);

        //Set Event
        setSingleEvent(mainGrid);

        Intent intent = getIntent();
        phoneNumber = intent.getExtras().getString("phoneNumber");
        grade = intent.getExtras().getString("grade");
        name = intent.getExtras().getString("name");
        studentKey = intent.getExtras().getString("studentKey");


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
                        final Intent studentSurvey2=new Intent(ShowStudentMenu.this, StudentSurveyApprovedActivity.class);


                        DatabaseReference databaseSurveyInfo;
                        databaseSurveyInfo = FirebaseDatabase.getInstance().getReference("surveyInfo");
                        List<String> phoneNumbers = new ArrayList<>();

                        databaseSurveyInfo.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot surveySnapshot : dataSnapshot.getChildren()) {
                                    //Create Artist Class Object and Returning Value
                                    SurveyInfo surveyInfo = surveySnapshot.getValue(SurveyInfo.class);
                                    if(surveyInfo.isApproved()==false&&surveyInfo.getPhoneNumber().equals(phoneNumber)){
                                        phoneNumbers.add(surveyInfo.getPhoneNumber());

                                        studentSurvey.putExtra("phoneNumber",phoneNumber);
                                        studentSurvey.putExtra("grade",grade);
                                        studentSurvey.putExtra("name",name);
                                        studentSurvey.putExtra("studentKey",studentKey);




                                        startActivityForResult(studentSurvey, 1);
                                    }
                                    else if(surveyInfo.isApproved()==true&&surveyInfo.getPhoneNumber().equals(phoneNumber)){
                                        phoneNumbers.add(surveyInfo.getPhoneNumber());

                                        DatabaseReference databaseSurvey;
                                        databaseSurvey = FirebaseDatabase.getInstance().getReference("surveys");

                                        databaseSurvey.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {

                                                for (DataSnapshot surveySnapshot : dataSnapshot.getChildren()) {
                                                    //Create Artist Class Object and Returning Value
                                                    Survey surveyInfo = surveySnapshot.getValue(Survey.class);
                                                    if(surveyInfo.getStudentsKey().equals(studentKey)){

                                                        studentSurvey2.putExtra("day",surveyInfo.getDay());
                                                        studentSurvey2.putExtra("baslangic",surveyInfo.getStartTime());
                                                        studentSurvey2.putExtra("bitis",surveyInfo.getEndTime());




                                                        startActivityForResult(studentSurvey2, 1);

                                                    }
                                                }

                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                                System.out.println("Student Verisi Çekilemedi.");

                                            }
                                        });


                                    }

                                }
                                if(phoneNumbers.size()==0){
                                    studentSurvey.putExtra("phoneNumber",phoneNumber);
                                    studentSurvey.putExtra("grade",grade);
                                    studentSurvey.putExtra("name",name);
                                    studentSurvey.putExtra("studentKey",studentKey);




                                    startActivityForResult(studentSurvey, 1);
                                }
                                for(int i = 0 ; i < phoneNumbers.size();i++){
                                    if(!phoneNumber.equals(phoneNumbers.get(i))){
                                        studentSurvey.putExtra("phoneNumber",phoneNumber);
                                        studentSurvey.putExtra("grade",grade);
                                        studentSurvey.putExtra("name",name);
                                        studentSurvey.putExtra("studentKey",studentKey);




                                        startActivityForResult(studentSurvey, 1);
                                    }
                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                System.out.println("Student Verisi Çekilemedi.");

                            }
                        });







                    }
                    else if(finalI==2){

                        final Intent tureng=new Intent(ShowStudentMenu.this, TurengTranslate.class);
                        startActivity(tureng);
                    }
                    else if(finalI==3){
                        Uri uri = Uri.parse("https://sso.rumba.pearsoncmg.com/sso/login?service=https%3A%2F%2Fmyenglishlab.pearson-intl.com%2Frumba%2Flogin&profile=iokingmel&allowLangChange=true");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                    else if(finalI==4){
                        final Intent intent=new Intent(ShowStudentMenu.this,ShowCampaign.class);
                        startActivity(intent);

                    }
                    else if(finalI==5){

                        final Intent webView=new Intent(ShowStudentMenu.this, webView_activity.class);
                        startActivity(webView);

                    }
                    else if(finalI==6)
                    {
                        final Intent intent=new Intent(ShowStudentMenu.this,DenemeActivity.class);
                        startActivity(intent);
                    }


                }
            });
        }
    }



}
