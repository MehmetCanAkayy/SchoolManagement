package com.onur.easyspeakdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.StudentMenu.SurveyInfo;
import com.firebaseDemo.Artist;
import com.firebaseDemo.MyAdapter;
import com.firebaseDemo.StudentsActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowSurveyRequest extends AppCompatActivity {


    DatabaseReference databaseSurveyInfo;
    List<SurveyInfo> surveyList;
    private RecyclerView recyclerView;
    private SurveyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_survey_request);

        databaseSurveyInfo = FirebaseDatabase.getInstance().getReference("surveyInfo");
        surveyList = new ArrayList<>();




    }


    @Override
    protected void onStart() {
        super.onStart();
        //Retriving data From Firebase
        databaseSurveyInfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                surveyList.clear();

                for (DataSnapshot surveyInfoSnapshot : dataSnapshot.getChildren() ){
                    //Create Artist Class Object and Returning Value
                    SurveyInfo surveyInfo = surveyInfoSnapshot.getValue(SurveyInfo.class);
                    surveyList.add(surveyInfo);

                }
                recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
                // use this setting to
                // improve performance if you know that changes
                // in content do not change the layout size
                // of the RecyclerVieww
                recyclerView.setHasFixedSize(true);
                // use a linear layout manager
                layoutManager = new LinearLayoutManager(ShowSurveyRequest.this);
                recyclerView.setLayoutManager(layoutManager);

                mAdapter = new SurveyAdapter(surveyList);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Student Verisi Çekilemedi.");

            }
        });
    }


}
