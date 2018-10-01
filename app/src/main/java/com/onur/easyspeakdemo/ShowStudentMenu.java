package com.onur.easyspeakdemo;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Clubs.DenemeActivity;
import com.StudentMenu.StudentSurveyActivity;
import com.StudentMenu.SurveyInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.restTime.restTimeClass;
import com.socialActivity.SocialActivitiesActivity;
import com.studentsTabLayout.MainActivity;
import com.tureng.TurengTranslate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    TextView txtName,txtTitle;
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
                        Calendar calendar = Calendar.getInstance();
                        calendar.add(Calendar.DATE, -10);

                        DatabaseReference databaseSurveyInfo;
                        databaseSurveyInfo = FirebaseDatabase.getInstance().getReference("surveyInfo");
                        List<String> phoneNumbers = new ArrayList<>();

                        myDialog = new Dialog(ShowStudentMenu.this);


                        myDialog.setContentView(R.layout.custon_rest_time);

                        closeButton = myDialog.findViewById(R.id.close);
                        delete = myDialog.findViewById(R.id.delete);
                        update = myDialog.findViewById(R.id.update);
                        txtName = myDialog.findViewById(R.id.content);
                        txtTitle= myDialog.findViewById(R.id.title);
                        txtTitle.setText("UYARI!");
                        txtTitle.setTextColor(getResources().getColor(R.color.red));
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
                        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                        databaseSurveyInfo.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot surveySnapshot : dataSnapshot.getChildren()) {
                                    //Create Artist Class Object and Returning Value
                                    SurveyInfo surveyInfo = surveySnapshot.getValue(SurveyInfo.class);
                                    String[] parse = surveyInfo.getToday().split(" ");

                                    if(surveyInfo.getPhoneNumber().equals(phoneNumber)){
                                        phoneNumbers.add(surveyInfo.getPhoneNumber());
                                        Calendar today = Calendar.getInstance();
                                        today.set(Calendar.DATE, Integer.parseInt(parse[0]));
                                        today.set(Calendar.MONTH, Integer.parseInt(parse[1]));
                                        today.set(Calendar.YEAR, Integer.parseInt(parse[2]));
                                        today.add(Calendar.DATE, 10);



                                        if(calendar.get(Calendar.YEAR)>=Integer.parseInt(parse[2])){
                                            if(calendar.get(Calendar.MONTH)>Integer.parseInt(parse[1])){
                                                    surveySnapshot.getRef().removeValue();
                                                    studentSurvey.putExtra("phoneNumber",phoneNumber);
                                                    studentSurvey.putExtra("grade",grade);
                                                    studentSurvey.putExtra("name",name);
                                                    studentSurvey.putExtra("studentKey",studentKey);
                                                    startActivityForResult(studentSurvey, 1);
                                                    myDialog.dismiss();
                                            }else if(calendar.get(Calendar.MONTH)==Integer.parseInt(parse[1])){
                                                if(calendar.get(Calendar.DATE)>=Integer.parseInt(parse[0])){
                                                    surveySnapshot.getRef().removeValue();
                                                    studentSurvey.putExtra("phoneNumber",phoneNumber);
                                                    studentSurvey.putExtra("grade",grade);
                                                    studentSurvey.putExtra("name",name);
                                                    studentSurvey.putExtra("studentKey",studentKey);
                                                    startActivityForResult(studentSurvey, 1);
                                                    myDialog.dismiss();
                                                }else{
                                                    txtName.setText(today.get(Calendar.DATE)+"/" + (today.get(Calendar.MONTH)+1) + "/" + today.get(Calendar.YEAR)  + " tarihinde talepte bulunabilirsiniz.");
                                                    update.setVisibility(View.GONE);
                                                    myDialog.show();

                                                }
                                            }else{
                                                txtName.setText(today.get(Calendar.DATE)+"/" + (today.get(Calendar.MONTH)+1) + "/" + today.get(Calendar.YEAR)  + " tarihinde talepte bulunabilirsiniz.");
                                                update.setVisibility(View.GONE);
                                                myDialog.show();

                                            }
                                        }else{
                                            txtName.setText(today.get(Calendar.DATE)+"/" + (today.get(Calendar.MONTH)+1) + "/" + today.get(Calendar.YEAR)  + " tarihinde talepte bulunabilirsiniz.");
                                            update.setVisibility(View.GONE);
                                            myDialog.show();

                                        }


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

                        Uri uri = Uri.parse("https://www.youtube.com/watch?v=E01-jA1-kpQ");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                    else if(finalI==3){

                        final Intent tureng=new Intent(ShowStudentMenu.this, TurengTranslate.class);
                        startActivity(tureng);
                    }
                    else if(finalI==4){
                        Uri uri = Uri.parse("https://sso.rumba.pearsoncmg.com/sso/login?service=https%3A%2F%2Fmyenglishlab.pearson-intl.com%2Frumba%2Flogin&profile=iokingmel&allowLangChange=true");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                    else if(finalI==5){
                        final Intent intent=new Intent(ShowStudentMenu.this,ShowCampaign.class);
                        startActivity(intent);

                    }
                    else if(finalI==6){

                        final Intent webView=new Intent(ShowStudentMenu.this, webView_activity.class);
                        startActivity(webView);

                    }else if(finalI==7) {
                        Intent intent=new Intent(ShowStudentMenu.this,DenemeActivity.class);
                        startActivityForResult(intent,1);


                    }
                     else if(finalI==8){

                        final Intent activities = new Intent(ShowStudentMenu.this, SocialActivitiesActivity.class);


                        activities.putExtra("phoneNumber", phoneNumber);
                        activities.putExtra("studentKey", studentKey);


                        startActivityForResult(activities, 1);


                        }


                    else if(finalI==9){




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
                        myDialog.show();


                        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    }


                }
            });
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
