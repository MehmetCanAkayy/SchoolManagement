package com.teacherTabLayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alamkanak.weekview.WeekViewEvent;
import com.firebaseDemo.FirebaseHelper;
import com.firebaseDemo.LessonInfo;
import com.firebaseDemo.Teacher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onur.easyspeakdemo.MyAdapter;
import com.onur.easyspeakdemo.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class TeacherLessonList extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference databaseLessonInfo;
    private DatabaseReference databaseTeacherInfo;
    private ArrayList<WeekViewEvent> mNewEvents;


    Teacher teacher=new Teacher();
    String teacherName ;
    public String TeacherName(){
        if(teacher.getPhoneNumber().equals(getIntent().getExtras().getString("phoneNumber"))){
            teacherName=teacher.getName();
        }
        return teacherName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_lesson_list);



        databaseLessonInfo = FirebaseDatabase.getInstance().getReference("lessonInfo");
        mNewEvents = new ArrayList<WeekViewEvent>();

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        final List<LessonInfo> input = new ArrayList<LessonInfo>();


        databaseLessonInfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                input.clear();
                mNewEvents.clear();

               for(DataSnapshot lessonInfoSnapshot : dataSnapshot.getChildren()){

                   LessonInfo lessonInfo = lessonInfoSnapshot.getValue(LessonInfo.class);

                   if(lessonInfo.getTeacher().equals(teacherName)){

                       Calendar startTime=Calendar.getInstance();
                       Calendar endTime=Calendar.getInstance();

                       String[] calendarStartItem = lessonInfo.getBaslangic().split("%");
                       String[] calendarEndItem = lessonInfo.getBitis().split("%");

                       startTime.set(Integer.parseInt(calendarStartItem[0]),Integer.parseInt(calendarStartItem[1]),Integer.parseInt(calendarStartItem[2]),Integer.parseInt(calendarStartItem[3]),Integer.parseInt(calendarStartItem[4]));
                       endTime.set(Integer.parseInt(calendarEndItem[0]),Integer.parseInt(calendarEndItem[1]),Integer.parseInt(calendarEndItem[2]),Integer.parseInt(calendarEndItem[3]),Integer.parseInt(calendarEndItem[4]));
                       WeekViewEvent myEvent = new WeekViewEvent(lessonInfo.getGrade(), lessonInfo.getTeacher(), lessonInfo.getIcerik(), startTime, endTime,lessonInfo.getDers());

                       input.add(lessonInfo);
                       mNewEvents.add(myEvent);
                   }


               }
                Collections.sort(input, (p1, p2) -> p1.getDay() - p2.getDay());

                System.out.println(input.size());
                mAdapter = new MyAdapter(input);
                recyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
