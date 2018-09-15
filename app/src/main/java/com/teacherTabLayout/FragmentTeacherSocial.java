package com.teacherTabLayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alamkanak.weekview.WeekViewEvent;
import com.firebaseDemo.LessonInfo;
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


public class FragmentTeacherSocial extends Fragment {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference databaseLessonInfo;
    private ArrayList<WeekViewEvent> mNewEvents;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_teacherecycler, container, false);

        databaseLessonInfo = FirebaseDatabase.getInstance().getReference("lessonInfo");



        Intent intent = getActivity().getIntent();
        final String phoneNumber = intent.getExtras().getString("phoneNumber");
        final String name= intent.getExtras().getString("name");


        recyclerView = view.findViewById(R.id.my_recycler_view_teacher);
        mNewEvents=new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        final List<LessonInfo> input = new ArrayList<LessonInfo>();

        databaseLessonInfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                input.clear();
                mNewEvents.clear();


                for (DataSnapshot lessonInfoSnapshot : dataSnapshot.getChildren() ){
                    //Create Artist Class Object and Returning Value
                    LessonInfo lessonInfo = lessonInfoSnapshot.getValue(LessonInfo.class);

                       if(lessonInfo.getTeacher().equals(name)&& lessonInfo.getDers().equals("Social"))
                       {
                        Calendar startTime=Calendar.getInstance();
                        Calendar endTime=Calendar.getInstance();

                        String[] calendarStartItem = lessonInfo.getBaslangic().split("%");
                        String[] calendarEndItem = lessonInfo.getBitis().split("%");

                        for (String t : calendarStartItem)
                            System.out.println(t);


                        startTime.set(Integer.parseInt(calendarStartItem[0]),Integer.parseInt(calendarStartItem[1]),Integer.parseInt(calendarStartItem[2]),Integer.parseInt(calendarStartItem[3]),Integer.parseInt(calendarStartItem[4]));
                        endTime.set(Integer.parseInt(calendarEndItem[0]),Integer.parseInt(calendarEndItem[1]),Integer.parseInt(calendarEndItem[2]),Integer.parseInt(calendarEndItem[3]),Integer.parseInt(calendarEndItem[4]));


                        input.add(lessonInfo);






                    // Refresh the week view. onMonthChange will be called again.

                }}

                Collections.sort(input, (p1, p2) -> p1.getDay() - p2.getDay());

                System.out.println(input.size());
                mAdapter = new MyAdapterTeacher(input);
                recyclerView.setAdapter(mAdapter);
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Student Verisi Çekilemedi.");

            }


        });

        return view;
    }




}
