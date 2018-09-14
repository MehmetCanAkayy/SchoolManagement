package com.teacherTabLayout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alamkanak.weekview.WeekViewEvent;
import com.firebaseDemo.LessonInfo;
import com.firebaseDemo.TeacherActivity;
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
import com.onur.easyspeakdemo.R;


public class FragmentTeacherOne extends Fragment {


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



        recyclerView = view.findViewById(R.id.my_recycler_view_teacher);

        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        final List<LessonInfo> input = new ArrayList<LessonInfo>();

        databaseLessonInfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                input.clear();
                mNewEvents.clear();


                for (DataSnapshot lessonInfoSnapshot : dataSnapshot.getChildren() ){
                    //Create Artist Class Object and Returning Value
                    LessonInfo lessonInfo = lessonInfoSnapshot.getValue(LessonInfo.class);



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

                }

                Collections.sort(input, (p1, p2) -> p1.getDay() - p2.getDay());

                System.out.println(input.size());
                mAdapter = new MyAdapter(input);
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
