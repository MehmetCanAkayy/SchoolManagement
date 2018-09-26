package com.socialActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alamkanak.weekview.WeekViewEvent;
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
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;



public class SocialActivitiesActivity extends AppCompatActivity {

    DatabaseReference databaseActivities;

    String phoneNumber,studentKey;

    private RecyclerView mRecyclerView;
    SocialActivityAdapter recyclerDataAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_activities);
        databaseActivities = FirebaseDatabase.getInstance().getReference("activities");

        Intent intent = getIntent();
        phoneNumber = intent.getExtras().getString("phoneNumber");
        studentKey = intent.getExtras().getString("studentKey");
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        new FetchTitle().execute();


//        String id = databaseActivities.push().getKey();
//
//        SocialActivityClass artist = new SocialActivityClass("Masa Tenisi",id,"");
//        databaseActivities.child(id).setValue(artist);




    }


    public class FetchTitle extends AsyncTask<String, Void, Void> {

        String title;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();



        }

        @Override
        protected Void doInBackground(String... params) {


            List<SocialActivityClass> socialActivityClasses = new ArrayList<>();
            databaseActivities.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    float toplamRating = 0;
                    for (DataSnapshot lessonInfoSnapshot : dataSnapshot.getChildren()) {
                        //Create Artist Class Object and Returning Value
                        SocialActivityClass socialActivityClass = lessonInfoSnapshot.getValue(SocialActivityClass.class);
//                        for (int i = 0; i < studentLessons.size(); i++) {
//                            if (studentLessons.get(i).equals(socialActivityClass.get())) {
//                                control = false;
//                            }
//                        }
                        if(socialActivityClass.getStudentKeys().equals("")||socialActivityClass.getStudentKeys().equals(" ")){

                        }else {
                            String[] parse = socialActivityClass.getStudentKeys().split(" ");
                            toplamRating += parse.length;
                        }

                    }

                    for (DataSnapshot lessonInfoSnapshot : dataSnapshot.getChildren()) {
                        //Create Artist Class Object and Returning Value
                        SocialActivityClass socialActivityClass = lessonInfoSnapshot.getValue(SocialActivityClass.class);
//                        for (int i = 0; i < studentLessons.size(); i++) {
//                            if (studentLessons.get(i).equals(socialActivityClass.get())) {
//                                control = false;
//                            }
//                        }
                        if(socialActivityClass.getStudentKeys().equals("")||socialActivityClass.getStudentKeys().equals(" ")){

                        }else {
                            String[] parse = socialActivityClass.getStudentKeys().split(" ");
                            float rating = parse.length;
                            socialActivityClass.setRating((rating/toplamRating)*5);
                            System.out.println(socialActivityClass.getName()+ " = "+rating);
                        }



                        socialActivityClasses.add(socialActivityClass);
                    }


                    Collections.sort(socialActivityClasses,new EmployeeAgeComparator());


                    //Collections.sort(socialActivityClasses, (p1, p2) -> (int) (p1.getRating() - p2.getRating()));


                    recyclerDataAdapter = new SocialActivityAdapter(socialActivityClasses,studentKey);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(SocialActivitiesActivity.this));
                    mRecyclerView.setAdapter(recyclerDataAdapter);
                    mRecyclerView.setHasFixedSize(true);
                    System.out.println("Toplam rating = " + toplamRating);



                }


                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("Student Verisi Çekilemedi.");

                }


            });


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);






        }
    }
    public class EmployeeAgeComparator implements Comparator<SocialActivityClass> {

        @Override
        public int compare(SocialActivityClass emp1, SocialActivityClass emp2) {
            return (int) (emp2.getRating() - emp1.getRating());
        }
    }


}
