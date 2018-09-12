package com.firebaseDemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onur.easyspeakdemo.R;


import java.util.ArrayList;
import java.util.List;

public class TeacherActivity extends AppCompatActivity {

    DatabaseReference databaseTeachers;
    List<LessonInfo> teacherList;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacherecycler);


        databaseTeachers = FirebaseDatabase.getInstance().getReference("lessonInfo");
        teacherList = new ArrayList<>();

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
                    LessonInfo teachers = teacherSnapshot.getValue(LessonInfo.class);
                    teacherList.add(teachers);
                }
                recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_teacher);
                // use this setting to
                // improve performance if you know that changes
                // in content do not change the layout size
                // of the RecyclerVieww
                recyclerView.setHasFixedSize(true);
                // use a linear layout manager
                layoutManager = new LinearLayoutManager(TeacherActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                List<String> input = new ArrayList<>();

                mAdapter = new TeacherAdapter(teacherList);
                recyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Student Verisi Çekilemedi.");

            }
        });
    }

}
