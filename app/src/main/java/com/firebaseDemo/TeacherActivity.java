package com.firebaseDemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alamkanak.weekview.WeekViewEvent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onur.easyspeakdemo.R;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class TeacherActivity extends AppCompatActivity {

    DatabaseReference databaseTeachers;
    List<Teacher> teacherList;
    private ArrayList<WeekViewEvent> mNewEvents;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacherecycler);


        databaseTeachers = FirebaseDatabase.getInstance().getReference("teachers");
        teacherList=new ArrayList<>();


    }

    @Override
    protected void onStart() {
        super.onStart();
        //Retriving data From Firebase
        databaseTeachers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                teacherList.clear();

                for (DataSnapshot teacherSnapshot : dataSnapshot.getChildren() ){
                    //Create Artist Class Object and Returning Value
                    Teacher teachers = teacherSnapshot.getValue(Teacher.class);
                    teacherList.add(teachers);


                }
                recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_teacher);
                recyclerView.setHasFixedSize(true);
                // use a linear layout manager
                layoutManager = new LinearLayoutManager(TeacherActivity.this);
                recyclerView.setLayoutManager(layoutManager);


                mAdapter=new TeacherAdapter(teacherList);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Student Verisi Çekilemedi.");

            }
        });
    }

}
