package com.socialActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.alamkanak.weekview.WeekViewEvent;
import com.firebaseDemo.Admin;
import com.firebaseDemo.Artist;
import com.firebaseDemo.LessonInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onur.easyspeakdemo.EventActvity;
import com.onur.easyspeakdemo.R;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
    SingleSelectionAdapter recyclerSingleAdapter;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_activities);
        databaseActivities = FirebaseDatabase.getInstance().getReference("activities");

        Intent intent = getIntent();
        phoneNumber = intent.getExtras().getString("phoneNumber");
        studentKey = intent.getExtras().getString("studentKey");
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
        p.setAnchorId(View.NO_ID);
        fab.setLayoutParams(p);
        fab.setVisibility(View.GONE);




        databaseActivities.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;
                Boolean showRating = false;

                for (DataSnapshot lessonInfoSnapshot : dataSnapshot.getChildren()) {
                    SocialActivityClass socialActivityClass = lessonInfoSnapshot.getValue(SocialActivityClass.class);

                    if (socialActivityClass.getStudentKeys().equals("") || socialActivityClass.getStudentKeys().equals(" ")) {

                    } else {
                        Boolean found = Arrays.asList(socialActivityClass.getStudentKeys().split(" ")).contains(studentKey);
                        if (found) {
                            showRating = true;

                        } else {

                        }
                    }


                }
                if(showRating){
                    new FetchTitle().execute();
                    fab.setVisibility(View.GONE);


                }else{
                    new FetchData().execute();
                    fab.setVisibility(View.VISIBLE);

                }


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Student Verisi Çekilemedi.");

            }


        });





//        String id = databaseActivities.push().getKey();
//
//        SocialActivityClass artist = new SocialActivityClass("Masa Tenisi",id,"");
//        databaseActivities.child(id).setValue(artist);




    }

    public class FetchData extends AsyncTask<String, Void, Void> {

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
                    int i = 0;
                    for (DataSnapshot lessonInfoSnapshot : dataSnapshot.getChildren()) {
                        SocialActivityClass socialActivityClass = lessonInfoSnapshot.getValue(SocialActivityClass.class);
                        socialActivityClass.setId(i);
                        i++;
                        socialActivityClasses.add(socialActivityClass);
                    }


                    recyclerSingleAdapter = new SingleSelectionAdapter(SocialActivitiesActivity.this,socialActivityClasses);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(SocialActivitiesActivity.this));
                    mRecyclerView.setAdapter(recyclerSingleAdapter);
                    mRecyclerView.setHasFixedSize(true);
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (recyclerSingleAdapter.selectedPosition() != -1) {
                                SocialActivityClass itemModel = recyclerSingleAdapter.getSelectedItem();
                                String cityName = itemModel.getName();
                                addArtist(itemModel);
                                Toast.makeText(SocialActivitiesActivity.this,"Succesfully Stored Data",Toast.LENGTH_LONG).show();
//                                new FetchTitle().execute();
                                fab.setVisibility(View.GONE);


                            } else {
                                Toast.makeText(SocialActivitiesActivity.this,"Please select any city",Toast.LENGTH_LONG).show();

                            }

                        }
                    });



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
    private void addArtist(SocialActivityClass activity){

        databaseActivities.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot lessonInfoSnapshot : dataSnapshot.getChildren()) {
                    SocialActivityClass socialActivityClass = lessonInfoSnapshot.getValue(SocialActivityClass.class);
                    if(socialActivityClass.getName().equals(activity.getName())){

                        if (socialActivityClass.getStudentKeys().equals("") || socialActivityClass.getStudentKeys().equals(" ")) {


                            String studentKeys = socialActivityClass.getStudentKeys();

                            studentKeys = studentKey;

                            SocialActivityClass socialActivityClass1 = new SocialActivityClass(socialActivityClass.getName(), socialActivityClass.getActivityKey(), studentKeys);

                            lessonInfoSnapshot.getRef().setValue(socialActivityClass1);

                        } else {
                            Boolean found = Arrays.asList(socialActivityClass.getStudentKeys().split(" ")).contains(studentKey);
                            if (found) {

                            } else {
                                String studentKeys = socialActivityClass.getStudentKeys();

                                studentKeys += " " + studentKey;

                                SocialActivityClass socialActivityClass1 = new SocialActivityClass(socialActivityClass.getName(), socialActivityClass.getActivityKey(), studentKeys);

                                lessonInfoSnapshot.getRef().setValue(socialActivityClass1);

                            }
                        }


                    }

                }
                new FetchTitle().execute();

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Student Verisi Çekilemedi.");

            }


        });


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
