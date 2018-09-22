package com.studentsTabLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class FragmentShowLessonsOne extends Fragment {


    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference databaseLessonInfo;
    private ArrayList<WeekViewEvent> mNewEvents;
    private DatabaseReference databaseUpdate;
    String[] studentLessons;
    final List<com.firebaseDemo.LessonInfo> input = new ArrayList<com.firebaseDemo.LessonInfo>();
    private RecyclerView mRecyclerView;
    private Context mContext;
    String phoneNumber;
    String grade;
    RecyclerDataAdapter recyclerDataAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_dynamic_recycler, container, false);

            Intent intent = getActivity().getIntent();
            phoneNumber = intent.getExtras().getString("phoneNumber");
            grade = intent.getExtras().getString("grade");


        mContext = view.getContext();
        mRecyclerView = view.findViewById(R.id.recyclerView);


        new FetchTitle().execute(); // açıklama çekmek için


        FloatingActionButton fab=(FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                new FetchTitle().execute();

            }
        });


                return view;
            }
       // });

        @Override
        public void setUserVisibleHint ( boolean isVisibleToUser){
            super.setUserVisibleHint(isVisibleToUser);
            if (isVisibleToUser) {
                System.out.println("Fragment 1 refresh");
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(FragmentShowLessonsOne.this).attach(FragmentShowLessonsOne.this).commit();
            }

        }



    public class FetchTitle extends AsyncTask<String, Void, Void> {

        String title;
        ArrayList<DummyParentDataItem> dummyDataItems = new ArrayList<>();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();



        }

        @Override
        protected Void doInBackground(String... params) {

            databaseLessonInfo = FirebaseDatabase.getInstance().getReference("lessonInfo");
            mNewEvents = new ArrayList<WeekViewEvent>();
            databaseUpdate = FirebaseDatabase.getInstance().getReference("students");




            final List<String> studentLessons = new ArrayList<String>();


            databaseUpdate.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot studentInfoSnapshot : dataSnapshot.getChildren()) {


                        Artist studentInfo = studentInfoSnapshot.getValue(Artist.class);

                        if (studentInfo.getPhoneNumber().equals(phoneNumber)) {


                            String[] out = studentInfo.getLessonKey().split(" ");

                            for (int i = 0; i < out.length; i++) {
                                studentLessons.add(out[i]);
                                System.out.println(studentLessons.get(studentLessons.size() - 1));
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


                    for (DataSnapshot lessonInfoSnapshot : dataSnapshot.getChildren()) {
                        boolean control = true;
                        //Create Artist Class Object and Returning Value
                        LessonInfo lessonInfo = lessonInfoSnapshot.getValue(LessonInfo.class);
                        for (int i = 0; i < studentLessons.size(); i++) {
                            if (studentLessons.get(i).equals(lessonInfo.getLessonKey())) {
                                control = false;
                            }
                        }
                        if (lessonInfo.getGrade().equals(grade) && control) {

                            Calendar startTime = Calendar.getInstance();
                            Calendar endTime = Calendar.getInstance();

                            String[] calendarStartItem = lessonInfo.getBaslangic().split("%");
                            String[] calendarEndItem = lessonInfo.getBitis().split("%");

                            for (String t : calendarStartItem)
                                System.out.println(t);


                            startTime.set(Integer.parseInt(calendarStartItem[0]), Integer.parseInt(calendarStartItem[1]), Integer.parseInt(calendarStartItem[2]), Integer.parseInt(calendarStartItem[3]), Integer.parseInt(calendarStartItem[4]));
                            endTime.set(Integer.parseInt(calendarEndItem[0]), Integer.parseInt(calendarEndItem[1]), Integer.parseInt(calendarEndItem[2]), Integer.parseInt(calendarEndItem[3]), Integer.parseInt(calendarEndItem[4]));


                            WeekViewEvent myEvent = new WeekViewEvent(lessonInfo.getGrade(), lessonInfo.getTeacher(), lessonInfo.getIcerik(), startTime, endTime, lessonInfo.getDers());
                            if (lessonInfo.getGrade().equals("A1")) {
                                myEvent.setColor(getResources().getColor(R.color.a1LessonColor));
                            } else if (lessonInfo.getGrade().equals("A1+")) {
                                myEvent.setColor(getResources().getColor(R.color.a1PlusLessonColor));
                            } else if (lessonInfo.getGrade().equals("A2")) {
                                myEvent.setColor(getResources().getColor(R.color.a2LessonColor));
                            } else if (lessonInfo.getGrade().equals("B1")) {
                                myEvent.setColor(getResources().getColor(R.color.b1LessonColor));
                            } else if (lessonInfo.getGrade().equals("B2")) {
                                myEvent.setColor(getResources().getColor(R.color.b2LessonColor));
                            } else if (lessonInfo.getGrade().equals("C1")) {
                                myEvent.setColor(getResources().getColor(R.color.c1LessonColor));
                            } else if (lessonInfo.getGrade().equals("Advanced")) {
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

                    ArrayList<LessonInfo> lessonInfos;
                    DummyParentDataItem dummyParentDataItem;
                    LessonInfo lessonInfo;


                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

                    Calendar date = Calendar.getInstance();
                    System.out.println("firstDayOfWeek" +date.getFirstDayOfWeek());

                    if(date.get(Calendar.DAY_OF_WEEK)==1){
                        date.add(Calendar.DATE,-1);
                    }
                    while (date.get(Calendar.DAY_OF_WEEK) > date.getFirstDayOfWeek()) {
                        date.add(Calendar.DATE, -1); // Substract 1 day until first day of week.
                    }



                    date.add(Calendar.DATE, -1);

                    String[] days = new String[14];
                    for (int i = 0; i < 7; i++) {
                        date.add(Calendar.DATE, 1);
                        String dayName = date.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
                        days[i] = format.format(date.getTime()) + " " + dayName;

                        System.out.println("date :" + days[i]);
                        String[] parse = days[i].split("/");

                        dummyParentDataItem = new DummyParentDataItem();
                        dummyParentDataItem.setParentName(days[i]);
                        lessonInfos = new ArrayList<>();
                        //
                        lessonInfo = new LessonInfo();
                        lessonInfos.add(lessonInfo);
                        for (int j = 0; j < input.size(); j++) {
                            System.out.println("sdfknsdlgnslngslkn " + input.get(j).getDay());
                            if (input.get(j).getDay() == Integer.parseInt(parse[0])) {
                                lessonInfos.add(input.get(j));
                            }
                        }

                        //
                        dummyParentDataItem.setChildDataItems(lessonInfos);
                        dummyDataItems.add(dummyParentDataItem);
                    }
                    recyclerDataAdapter = new RecyclerDataAdapter(dummyDataItems,phoneNumber);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                    mRecyclerView.setAdapter(recyclerDataAdapter);
                    mRecyclerView.setHasFixedSize(true);


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
}