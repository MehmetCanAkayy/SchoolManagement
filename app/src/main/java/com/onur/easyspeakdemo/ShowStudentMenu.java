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
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.StudentMenu.StudentSurveyActivity;
import com.StudentMenu.StudentSurveyApprovedActivity;
import com.StudentMenu.Survey;
import com.StudentMenu.SurveyInfo;
import com.firebaseDemo.Admin;
import com.firebaseDemo.StudentsActivity;
import com.firebaseDemo.TeacherActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mailSender.GuestActivity;
import com.restTime.restTimeClass;
import com.socialActivity.SocialActivitiesActivity;
import com.studentsTabLayout.MainActivity;
import com.tureng.TurengTranslate;
import com.user.StudentRegister;
import com.user.TeacherRegister;

import org.w3c.dom.Text;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ShowStudentMenu extends AppCompatActivity {

    private Button ran_al,rand_iptal,userAddButton;

    GridLayout mainGrid;
    String phoneNumber ;
    String grade;
    String name;
    String studentKey;

    Dialog myDialog;
    ImageView closeButton;
    Button delete,update;
    TextView txtName;
    boolean talepOlustur = true;




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

                    }
                    else if(finalI==5){

                        final Intent webView=new Intent(ShowStudentMenu.this, webView_activity.class);
                        startActivity(webView);

                    }else if(finalI==6){

                        final Intent activities=new Intent(ShowStudentMenu.this, SocialActivitiesActivity.class);


                        activities.putExtra("phoneNumber",phoneNumber);
                        activities.putExtra("studentKey",studentKey);



                        startActivityForResult(activities, 1);

                    }else if(finalI==7){




                        myDialog = new Dialog(ShowStudentMenu.this);


                        myDialog.setContentView(R.layout.custon_rest_time);

                        closeButton = myDialog.findViewById(R.id.close);
                        delete = myDialog.findViewById(R.id.delete);
                        update = myDialog.findViewById(R.id.update);
                        txtName = myDialog.findViewById(R.id.content);
                        delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                myDialog.dismiss();
                            }
                        });


                        closeButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                myDialog.dismiss();
                            }
                        });

                        //myDialog.show();
                        DatabaseReference databaseSurveyInfo;
                        databaseSurveyInfo = FirebaseDatabase.getInstance().getReference("restTime");
                        List<String> phoneNumbers = new ArrayList<>();
                        databaseSurveyInfo.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot surveySnapshot : dataSnapshot.getChildren()) {
                                    //Create Artist Class Object and Returning Value
                                    restTimeClass restTime = surveySnapshot.getValue(restTimeClass.class);
                                    if(restTime.getPhoneNumber().equals(phoneNumber)){
                                        talepOlustur = false;
                                        if(restTime.isReturn()==false) {
                                            txtName.setText("Talebiniz bize ulasmistir kisa zamanda donus yapilacaktir.");
                                            update.setVisibility(View.GONE);
                                            myDialog.show();

                                        }else if(restTime.isReturn()){
                                            String baslangic = restTime.getStartTime();
                                            String bitis = restTime.getEndTime();
                                            String[] parse = baslangic.split("\\W");
                                            String[] parse2 = bitis.split("\\W");

                                            System.out.println(baslangic);
                                            System.out.println(bitis);
                                            for(int i = 0 ; i < parse.length;i++){
                                                System.out.println(parse[i]);
                                            }
                                            for(int i = 0 ; i < parse2.length;i++){
                                                System.out.println(parse2[i]);
                                            }

                                            Calendar date =Calendar.getInstance();
//                                            date.set(Calendar.DATE, Integer.parseInt(parse2[0]));
//                                            date.set(Calendar.MONTH,Integer.parseInt(parse2[1]));
//                                            date.set(Calendar.YEAR,Integer.parseInt(parse2[2]));
                                            date.set(Integer.parseInt(parse2[2]),Integer.parseInt(parse2[1])-1,Integer.parseInt(parse2[0]));
                                            Calendar today = Calendar.getInstance();
                                            System.out.println(date.get(Calendar.DATE));
                                            System.out.println(date.get(Calendar.MONTH));
                                            System.out.println(date.get(Calendar.YEAR));
                                            System.out.println(today.get(Calendar.DATE));
                                            System.out.println(today.get(Calendar.MONTH));
                                            System.out.println(today.get(Calendar.YEAR));



                                            int myday = daysBetween(today.getTime(), date.getTime());

                                            int[] parseDay =find(myday);
                                            txtName.setText("Baslangic = " + baslangic + "\n" + "Bitis = " + bitis + "\n"
                                            + "Kalan sure\n" + parseDay[0] + " Day" +"\n" + parseDay[1] + " Week\n"+
                                            parseDay[2]+" Month\n" + parseDay[3] + " Year");
                                            update.setVisibility(View.GONE);
                                            myDialog.show();

                                        }
                                    }
                                    if(talepOlustur){
                                        myDialog.show();
                                    }


                                }






                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                System.out.println("Student Verisi Çekilemedi.");

                            }
                        });
                        update.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(talepOlustur){
                                    String id = databaseSurveyInfo.push().getKey();

                                    //Create An Artist Object
                                    restTimeClass artist = new restTimeClass(name,phoneNumber,false,"","");
                                    databaseSurveyInfo.child(id).setValue(artist);
                                    Toast.makeText(getApplicationContext(), "Talebiniz olsuturuldu.", Toast.LENGTH_LONG).show();

                                }
                                myDialog.dismiss();

                            }
                        });


                        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    }


                }
            });
        }
    }

    public static int daysBetween2(Calendar day1, Calendar day2){
        Calendar dayOne = (Calendar) day1.clone(),
                dayTwo = (Calendar) day2.clone();

        if (dayOne.get(Calendar.YEAR) == dayTwo.get(Calendar.YEAR)) {
            return Math.abs(dayOne.get(Calendar.DAY_OF_YEAR) - dayTwo.get(Calendar.DAY_OF_YEAR));
        } else {
            if (dayTwo.get(Calendar.YEAR) > dayOne.get(Calendar.YEAR)) {
                //swap them
                Calendar temp = dayOne;
                dayOne = dayTwo;
                dayTwo = temp;
            }
            int extraDays = 0;

            int dayOneOriginalYearDays = dayOne.get(Calendar.DAY_OF_YEAR);

            while (dayOne.get(Calendar.YEAR) > dayTwo.get(Calendar.YEAR)) {
                dayOne.add(Calendar.YEAR, -1);
                // getActualMaximum() important for leap years
                extraDays += dayOne.getActualMaximum(Calendar.DAY_OF_YEAR);
            }

            return extraDays - dayTwo.get(Calendar.DAY_OF_YEAR) + dayOneOriginalYearDays ;
        }
    }

    static final int DAYS_IN_WEEK = 7;

    // Function to find year, week, days
    public int[] find(int number_of_days)
    {
        int year, week, days,month;
        int[] parse = new int[4];
        System.out.println(number_of_days);
        // Assume that years
        // is of 365 days
        year = number_of_days / 365;
        month = (number_of_days % 365) /
                30;
        week = ((number_of_days % 365) /30)%
                DAYS_IN_WEEK;
        days = (number_of_days % 365) %
                DAYS_IN_WEEK;

        System.out.println("years = " + year);
        System.out.println("months = " + month);

        System.out.println("weeks = " + week);
        System.out.println("days = " + days);
        parse[0] = days;
        parse[1] = week;
        parse[2] = month;
        parse[3] = year;
        return parse;

    }

    public int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }






}
