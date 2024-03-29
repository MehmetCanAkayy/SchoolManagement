package com.studentsTabLayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alamkanak.weekview.WeekViewEvent;
import com.firebaseDemo.Artist;
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

public class FragmentTwo extends Fragment {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference databaseLessonInfo;
    private ArrayList<WeekViewEvent> mNewEvents;
    private DatabaseReference databaseUpdate;
    String[] studentLessons;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_list_week, container, false);

        databaseLessonInfo = FirebaseDatabase.getInstance().getReference("lessonInfo");
        mNewEvents = new ArrayList<WeekViewEvent>();
        databaseUpdate = FirebaseDatabase.getInstance().getReference("students");
        System.out.println("Fragment 2 refresh");


        Intent intent = getActivity().getIntent();
        final String phoneNumber = intent.getExtras().getString("phoneNumber");
        final String grade = intent.getExtras().getString("grade");



        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        final List<LessonInfo> input = new ArrayList<LessonInfo>();
        final List<String> studentLessons = new ArrayList<String>();


        databaseUpdate.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot studentInfoSnapshot : dataSnapshot.getChildren() ){


                    Artist studentInfo = studentInfoSnapshot.getValue(Artist.class);

                    if(studentInfo.getPhoneNumber().equals(phoneNumber)){


                        String[] out = studentInfo.getLessonKey().split(" ");

                        for(int i = 0 ; i < out.length;i++){
                            studentLessons.add(out[i]);
                            System.out.println(studentLessons.get(studentLessons.size()-1));
                        }



                    }







                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Student Verisi Çekilemedi.");

            }


        });




        databaseLessonInfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                input.clear();
                mNewEvents.clear();


                for (DataSnapshot lessonInfoSnapshot : dataSnapshot.getChildren() ){
                    boolean control = false;
                    //Create Artist Class Object and Returning Value
                    LessonInfo lessonInfo = lessonInfoSnapshot.getValue(LessonInfo.class);
                    for(int i = 0 ; i < studentLessons.size();i++){
                        if(studentLessons.get(i).equals(lessonInfo.getLessonKey())){
                            control=true;
                        }
                    }
                    if(lessonInfo.getGrade().equals(grade)&&control){

                        Calendar startTime=Calendar.getInstance();
                        Calendar endTime=Calendar.getInstance();

                        String[] calendarStartItem = lessonInfo.getBaslangic().split("%");
                        String[] calendarEndItem = lessonInfo.getBitis().split("%");

                        for (String t : calendarStartItem)
                            System.out.println(t);


                        startTime.set(Integer.parseInt(calendarStartItem[0]),Integer.parseInt(calendarStartItem[1]),Integer.parseInt(calendarStartItem[2]),Integer.parseInt(calendarStartItem[3]),Integer.parseInt(calendarStartItem[4]));
                        endTime.set(Integer.parseInt(calendarEndItem[0]),Integer.parseInt(calendarEndItem[1]),Integer.parseInt(calendarEndItem[2]),Integer.parseInt(calendarEndItem[3]),Integer.parseInt(calendarEndItem[4]));


                        WeekViewEvent myEvent = new WeekViewEvent(lessonInfo.getGrade(), lessonInfo.getTeacher(), lessonInfo.getIcerik(), startTime, endTime,lessonInfo.getDers());
                        if(lessonInfo.getGrade().equals("A1")){
                            myEvent.setColor(getResources().getColor(R.color.a1LessonColor));
                        }else if(lessonInfo.getGrade().equals("A1+")){
                            myEvent.setColor(getResources().getColor(R.color.a1PlusLessonColor));
                        }else if(lessonInfo.getGrade().equals("A2")){
                            myEvent.setColor(getResources().getColor(R.color.a2LessonColor));
                        }else if(lessonInfo.getGrade().equals("B1")){
                            myEvent.setColor(getResources().getColor(R.color.b1LessonColor));
                        }else if(lessonInfo.getGrade().equals("B2")){
                            myEvent.setColor(getResources().getColor(R.color.b2LessonColor));
                        }else if(lessonInfo.getGrade().equals("C1")){
                            myEvent.setColor(getResources().getColor(R.color.c1LessonColor));
                        }else if(lessonInfo.getGrade().equals("Advanced")){
                            myEvent.setColor(getResources().getColor(R.color.advencedLessonColor));
                        }


                        lessonInfo.setColor(myEvent.getColor());
                        input.add(lessonInfo);
                        mNewEvents.add(myEvent);

                    }



                    // Refresh the week view. onMonthChange will be called again.

                }

                Collections.sort(input, (p1, p2) -> p1.getDay() - p2.getDay());

                System.out.println(input.size());
                mAdapter = new MyAdapter2(input,phoneNumber);
                recyclerView.setAdapter(mAdapter);
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Student Verisi Çekilemedi.");

            }


        });

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(FragmentTwo.this).attach(FragmentTwo.this).commit();
        }

    }

}