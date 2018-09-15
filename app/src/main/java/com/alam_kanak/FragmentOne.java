package com.alam_kanak;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.firebaseDemo.Artist;
import com.firebaseDemo.LessonInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onur.easyspeakdemo.EventActvity;
import com.onur.easyspeakdemo.R;
import com.studentsTabLayout.MyAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class FragmentOne extends Fragment implements WeekView.EventClickListener, MonthLoader.MonthChangeListener,WeekView.EmptyViewClickListener, WeekView.EventLongPressListener, WeekView.EmptyViewLongPressListener {



    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_THREE_DAY_VIEW = 2;
    private static final int TYPE_WEEK_VIEW = 3;
    private int mWeekViewType = TYPE_THREE_DAY_VIEW;
    private WeekView mWeekView;
    private ArrayList<WeekViewEvent> mNewEvents;
    private Calendar selectedDate;
    private DatabaseReference databaseLessonInfo;
    private DatabaseReference databaseUpdate;


    String baslangic;
    int day;

    String bitis;
    String grade;
    String ders;
    String teacher;
    String icerik ;
    Calendar calendarStartTime;
    Calendar calendarEndTime;
    String endHour;
    String startHour;
    int a1,a1Plus,a2,b1,b2,c1,advanced;
    Dialog myDialog;
    TextView titleTv,messageTv;
    ImageView closeButton;
    CardView card;
    Button delete,update;
    public static FragmentOne newInstance( int mWeekViewType) {
        Bundle bundle = new Bundle();
        bundle.putInt("mWeekViewType", mWeekViewType);

        FragmentOne fragment = new FragmentOne();
        fragment.setArguments(bundle);

        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            mWeekViewType = bundle.getInt("mWeekViewType");
            if (mWeekViewType == 3) {
                mWeekViewType = TYPE_THREE_DAY_VIEW;
                mWeekView.setNumberOfVisibleDays(3);

                // Lets change some dimensions to best fit the view.
                mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
            }
            else{
                mWeekViewType = TYPE_WEEK_VIEW;
                mWeekView.setNumberOfVisibleDays(7);

                // Lets change some dimensions to best fit the view.
                mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
                mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sample, container, false);
            a1=view.getResources().getColor(R.color.a1LessonColor);
            a1Plus=view.getResources().getColor(R.color.a1PlusLessonColor);
        a2=view.getResources().getColor(R.color.a2LessonColor);
        b1=view.getResources().getColor(R.color.b1LessonColor);
        b2=view.getResources().getColor(R.color.b2LessonColor);
        c1=view.getResources().getColor(R.color.c1LessonColor);
        advanced=view.getResources().getColor(R.color.advencedLessonColor);

        myDialog = new Dialog(view.getContext());

        databaseLessonInfo = FirebaseDatabase.getInstance().getReference("lessonInfo");
        databaseUpdate = FirebaseDatabase.getInstance().getReference("lessonInfo");

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action

                selectedDate = Calendar.getInstance();
                selectedDate.setFirstDayOfWeek(Calendar.MONDAY);

                calendarStartTime=Calendar.getInstance();
                calendarEndTime=Calendar.getInstance();
                calendarStartTime.setFirstDayOfWeek(Calendar.MONDAY);
                calendarEndTime.setFirstDayOfWeek(Calendar.MONDAY);

                while (selectedDate.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                    selectedDate.add(Calendar.DATE, -1);
                    calendarStartTime.add(Calendar.DATE, -1);
                    calendarEndTime.add(Calendar.DATE, -1);

                }

                Intent eventActivity = new Intent(getActivity(), EventActvity.class);
                eventActivity.putExtra("hour", 10);
                eventActivity.putExtra("minute", 30);
                eventActivity.putExtra("isFloatingButtonClicked", true);
                eventActivity.putExtra("isUpdate",false);
                eventActivity.putExtra("Day",0);
                eventActivity.putExtra("Grade",0);
                eventActivity.putExtra("Teacher",0);




                startActivityForResult(eventActivity, 1);

            }
        });



        // Get a reference for the week view in the layout.
        mWeekView = (WeekView) view.findViewById(R.id.weekView);

        // Show a toast message about the touched event.
        mWeekView.setOnEventClickListener(this);

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(this);

        // Set long press listener for events.
        mWeekView.setEventLongPressListener(this);

        // Set long press listener for empty view
        mWeekView.setEmptyViewLongPressListener(this);
        mWeekView.setEmptyViewClickListener(this);

        mNewEvents = new ArrayList<WeekViewEvent>();


        // Set up a date time interpreter to interpret how the date and time will be formatted in
        // the week view. This is optional.
        setupDateTimeInterpreter(false);
        readBundle(getArguments());


        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        //Retriving data From Firebase

        databaseLessonInfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
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


                    WeekViewEvent myEvent = new WeekViewEvent(lessonInfo.getGrade(), lessonInfo.getTeacher(), lessonInfo.getIcerik(), startTime, endTime,lessonInfo.getDers());
                    if(lessonInfo.getGrade().equals("A1")){
                        myEvent.setColor(a1);
                    }else if(lessonInfo.getGrade().equals("A1+")){
                        myEvent.setColor(a1Plus);
                    }else if(lessonInfo.getGrade().equals("A2")){
                        myEvent.setColor(a2);
                    }else if(lessonInfo.getGrade().equals("B1")){
                        myEvent.setColor(b1);
                    }else if(lessonInfo.getGrade().equals("B2")){
                        myEvent.setColor(b2);
                    }else if(lessonInfo.getGrade().equals("C1")){
                        myEvent.setColor(c1);
                    }else if(lessonInfo.getGrade().equals("Advanced")){
                        myEvent.setColor(advanced);
                    }


                    mNewEvents.add(myEvent);

                    // Refresh the week view. onMonthChange will be called again.

                }
                mWeekView.notifyDatasetChanged();
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Student Verisi Çekilemedi.");

            }


        });
    }
    @Override
    public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        // Populate the week view with the events that was added by tapping on empty view.
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        ArrayList<WeekViewEvent> newEvents = getNewEvents(newYear, newMonth);
        events.addAll(newEvents);
        return events;
    }

    /**
     * Get events that were added by tapping on empty view.
     *
     * @param year  The year currently visible on the week view.
     * @param month The month currently visible on the week view.
     * @return The events of the given year and month.
     */
    private ArrayList<WeekViewEvent> getNewEvents(int year, int month) {

        // Get the starting point and ending point of the given month. We need this to find the
        // events of the given month.
        Calendar startOfMonth = Calendar.getInstance();
        startOfMonth.set(Calendar.YEAR, year);
        startOfMonth.set(Calendar.MONTH, month - 1);
        startOfMonth.set(Calendar.DAY_OF_MONTH, 1);
        startOfMonth.set(Calendar.HOUR_OF_DAY, 0);
        startOfMonth.set(Calendar.MINUTE, 0);
        startOfMonth.set(Calendar.SECOND, 0);
        startOfMonth.set(Calendar.MILLISECOND, 0);
        Calendar endOfMonth = (Calendar) startOfMonth.clone();
        endOfMonth.set(Calendar.DAY_OF_MONTH, endOfMonth.getMaximum(Calendar.DAY_OF_MONTH));
        endOfMonth.set(Calendar.HOUR_OF_DAY, 23);
        endOfMonth.set(Calendar.MINUTE, 59);
        endOfMonth.set(Calendar.SECOND, 59);

        // Find the events that were added by tapping on empty view and that occurs in the given
        // time frame.
        ArrayList<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        for (WeekViewEvent event : mNewEvents) {

            events.add(event);

        }
        return events;
    }



    private void setupDateTimeInterpreter(final boolean shortDate) {
        mWeekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
                String weekday = weekdayNameFormat.format(date.getTime());
                SimpleDateFormat format = new SimpleDateFormat(" d", Locale.getDefault());

                // All android api level do not have a standard way of getting the first letter of
                // the week day name. Hence we get the first char programmatically.
                // Details: http://stackoverflow.com/questions/16959502/get-one-letter-abbreviation-of-week-day-of-a-date-in-java#answer-16959657
                if (shortDate)
                    weekday = String.valueOf(weekday.charAt(0));
                return weekday.toUpperCase() + format.format(date.getTime());
            }

            @Override
            public String interpretTime(int hour) {
                if (hour > 12) {
                    return (hour - 12) + " PM";
                } else {
                    return (hour) + " AM";
                }
            }
        });
    }

    protected String getEventTitle(Calendar time) {
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH) + 1, time.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onEventClick(final WeekViewEvent event, final RectF eventRect) {

        Toast.makeText(getActivity(), "Clicked " + event.getContent(), Toast.LENGTH_SHORT).show();

        //        AlertDialog.Builder builder = new AlertDialog.Builder(AlamKanakActivity.this);
//        builder.setTitle("Lesson Content");
//        builder.setMessage(event.getGrade() + "\n" + event.getTeacher() + "\n" + event.getStartEnd() + "\n" + event.mGetContent());


        myDialog.setContentView(R.layout.custom_dialog_box);
        messageTv = myDialog.findViewById(R.id.content);
        card = myDialog.findViewById(R.id.mycard);

        card.setBackgroundColor(event.getColor());

        closeButton = myDialog.findViewById(R.id.close);
        delete = myDialog.findViewById(R.id.delete);
        update = myDialog.findViewById(R.id.update);

        messageTv.setText(event.getDers()+"\n"+event.getGrade() + "\n" + event.getTeacher() + "\n" + event.getStartEnd() + "\n" + event.mGetContent());


        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedDate = Calendar.getInstance();
                selectedDate.setFirstDayOfWeek(Calendar.MONDAY);

                calendarStartTime=Calendar.getInstance();
                calendarEndTime=Calendar.getInstance();
                calendarStartTime.setFirstDayOfWeek(Calendar.MONDAY);
                calendarEndTime.setFirstDayOfWeek(Calendar.MONDAY);

                while (selectedDate.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                    selectedDate.add(Calendar.DATE, -1);
                    calendarStartTime.add(Calendar.DATE, -1);
                    calendarEndTime.add(Calendar.DATE, -1);

                }

                //final Intent eventActivity = new Intent(AlamKanakActivity.this, EventActvity.class);

                databaseUpdate.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
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


                            WeekViewEvent myEvent = new WeekViewEvent(lessonInfo.getGrade(), lessonInfo.getTeacher(), lessonInfo.getIcerik(), startTime, endTime,lessonInfo.getDers());

                            if(event.getGrade()==myEvent.getGrade()){
                                final Intent eventActivity = new Intent(getContext(), EventActvity.class);
                                eventActivity.putExtra("hour", startTime.get(Calendar.HOUR_OF_DAY));
                                eventActivity.putExtra("minute", startTime.get(Calendar.MINUTE));
                                eventActivity.putExtra("isFloatingButtonClicked", true);
                                eventActivity.putExtra("isUpdate",true);
                                eventActivity.putExtra("Key",lessonInfoSnapshot.getKey());


                                eventActivity.putExtra("Content",myEvent.mGetContent());

                                String[] some_array = getResources().getStringArray(R.array.teachers);

                                for (int i = 0 ; i <some_array.length;i++){
                                    if(some_array[i].equals(myEvent.getTeacher())){
                                        eventActivity.putExtra("Teacher",i);
                                        break;
                                    }
                                }
                                String[] some_array1 = getResources().getStringArray(R.array.lesson);

                                for (int i = 0 ; i <some_array1.length;i++){
                                    if(some_array1[i].equals(myEvent.getDers())){
                                        eventActivity.putExtra("Ders",i);
                                        break;
                                    }
                                }



                                if(startTime.get((Calendar.DAY_OF_WEEK))==Calendar.MONDAY){
                                    eventActivity.putExtra("Day",0);
                                }else if(startTime.get((Calendar.DAY_OF_WEEK))==Calendar.TUESDAY){
                                    eventActivity.putExtra("Day",1);
                                }else if(startTime.get((Calendar.DAY_OF_WEEK))==Calendar.WEDNESDAY){
                                    eventActivity.putExtra("Day",2);
                                }else if(startTime.get((Calendar.DAY_OF_WEEK))==Calendar.THURSDAY){
                                    eventActivity.putExtra("Day",3);
                                }else if(startTime.get((Calendar.DAY_OF_WEEK))==Calendar.FRIDAY){
                                    eventActivity.putExtra("Day",4);
                                }else if(startTime.get((Calendar.DAY_OF_WEEK))==Calendar.SATURDAY){
                                    eventActivity.putExtra("Day",5);
                                }else if(startTime.get((Calendar.DAY_OF_WEEK))==Calendar.SUNDAY){
                                    eventActivity.putExtra("Day",6);
                                }
                                if(myEvent.getGrade().equals("A1")){
                                    eventActivity.putExtra("Grade",0);
                                }else if(myEvent.getGrade().equals("A1+")){
                                    eventActivity.putExtra("Grade",1);
                                }else if(myEvent.getGrade().equals("A2")){
                                    eventActivity.putExtra("Grade",2);
                                }else if(myEvent.getGrade().equals("B1")){
                                    eventActivity.putExtra("Grade",3);
                                }else if(myEvent.getGrade().equals("B2")){
                                    eventActivity.putExtra("Grade",4);
                                }else if(myEvent.getGrade().equals("C1")){
                                    eventActivity.putExtra("Grade",5);
                                }else if(myEvent.getGrade().equals("Advanced")){
                                    eventActivity.putExtra("Grade",6);
                                }
                                startActivityForResult(eventActivity, 1);


                                //lessonInfoSnapshot.getRef().removeValue();
                            }

                            myDialog.dismiss();

                            // Refresh the week view. onMonthChange will be called again.

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("Student Verisi Çekilemedi.");

                    }


                });




                //startActivityForResult(eventActivity, 1);

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseLessonInfo.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
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


                            WeekViewEvent myEvent = new WeekViewEvent(lessonInfo.getGrade(), lessonInfo.getTeacher(), lessonInfo.getIcerik(), startTime, endTime,lessonInfo.getDers());

                            if(event.getGrade()==myEvent.getGrade()){
                                lessonInfoSnapshot.getRef().removeValue();
                            }

                            myDialog.dismiss();

                            // Refresh the week view. onMonthChange will be called again.

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("Student Verisi Çekilemedi.");

                    }


                });


            }
        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();




    }

    //    @Override
//    public void onEmptyViewClicked(Calendar time) {
//        // Set the new event with duration one hour.
//        Calendar endTime = (Calendar) time.clone();
//        endTime.add(Calendar.HOUR, 1);
//
//        // Create a new event.
//        WeekViewEvent event = new WeekViewEvent(20, "New event", time, endTime);
//        mNewEvents.add(event);
//
//        // Refresh the week view. onMonthChange will be called again.
//        mWeekView.notifyDatasetChanged();
//    }
    @Override
    public void onEmptyViewClicked(Calendar time) {

        System.out.println(time.getTime());


        selectedDate = time;
        final Intent eventActivity = new Intent(getContext(), EventActvity.class);
        eventActivity.putExtra("hour", time.get(Calendar.HOUR_OF_DAY));
        eventActivity.putExtra("minute", time.get(Calendar.MINUTE));
        eventActivity.putExtra("isFloatingButtonClicked", false);
        eventActivity.putExtra("isUpdate",false);
        eventActivity.putExtra("Day",0);
        eventActivity.putExtra("Grade",0);
        eventActivity.putExtra("Teacher",0);



        calendarStartTime=time;
        calendarEndTime=time;


        startActivityForResult(eventActivity, 1);
        Toast.makeText(getActivity(), "Empty view clicked: " + getEventTitle(time), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                baslangic = data.getStringExtra("baslangic");
                bitis = data.getStringExtra("bitis");
                grade = data.getStringExtra("grade");
                ders = data.getStringExtra("ders");
                teacher = data.getStringExtra("teacher");
                icerik = data.getStringExtra("icerik");
                day = data.getIntExtra("day",2);
                System.out.println("dayyyyyyyyyyyyyyyyyyyyyyyyyyy " + day);

                String[] parse;
                String[] parse2;

                parse = baslangic.split(":");
                parse2 = bitis.split(":");

                System.out.println(baslangic);
                System.out.println(bitis);
                System.out.println(grade);
                System.out.println(teacher);
                System.out.println(icerik);

                selectedDate  .add(Calendar.DATE,day);

                calendarStartTime.add(Calendar.DATE,day);
                calendarEndTime.add(Calendar.DATE,day);



                Calendar endTime = (Calendar) selectedDate.clone();
                endTime.add(Calendar.HOUR, 1);

                System.out.println("Month = "+selectedDate.get(Calendar.MONTH));
                // Create a new event.
                //WeekViewEvent event = new WeekViewEvent(20, "New event", selectedDate, endTime);
//                WeekViewEvent myEvent = new WeekViewEvent(grade, teacher, icerik, selectedDate, endTime);
//                myEvent.setColor(R.color.event_color_02);
//                mNewEvents.add(myEvent);

                endHour = parse2[0];
                startHour = parse[0];
                calendarStartTime.set(Calendar.HOUR_OF_DAY,Integer.parseInt(parse[0]));
                System.out.println(calendarStartTime.get(Calendar.HOUR_OF_DAY));
                calendarEndTime.set(Calendar.HOUR_OF_DAY,Integer.parseInt(parse2[0]));
                System.out.println(calendarEndTime.get(Calendar.HOUR_OF_DAY));

                // Refresh the week view. onMonthChange will be called again.
                //mWeekView.notifyDatasetChanged();
                addLessonInfo();




            }else if(resultCode==2){
                baslangic = data.getStringExtra("baslangic");
                bitis = data.getStringExtra("bitis");
                grade = data.getStringExtra("grade");
                ders = data.getStringExtra("ders");

                teacher = data.getStringExtra("teacher");
                icerik = data.getStringExtra("icerik");
                day = data.getIntExtra("day",2);
                System.out.println("dayyyyyyyyyyyyyyyyyyyyyyyyyyy " + day);

                String[] parse;
                String[] parse2;

                parse = baslangic.split(":");
                parse2 = bitis.split(":");

                System.out.println(baslangic);
                System.out.println(bitis);
                System.out.println(grade);
                System.out.println(teacher);
                System.out.println(icerik);

                selectedDate  .add(Calendar.DATE,day);

                calendarStartTime.add(Calendar.DATE,day);
                calendarEndTime.add(Calendar.DATE,day);



                Calendar endTime = (Calendar) selectedDate.clone();
                endTime.add(Calendar.HOUR, 1);

                System.out.println("Month = "+selectedDate.get(Calendar.MONTH));
                // Create a new event.
                //WeekViewEvent event = new WeekViewEvent(20, "New event", selectedDate, endTime);
//                WeekViewEvent myEvent = new WeekViewEvent(grade, teacher, icerik, selectedDate, endTime);
//                myEvent.setColor(R.color.event_color_02);
//                mNewEvents.add(myEvent);

                endHour = parse2[0];
                startHour = parse[0];
                calendarStartTime.set(Calendar.HOUR_OF_DAY,Integer.parseInt(parse[0]));
                System.out.println(calendarStartTime.get(Calendar.HOUR_OF_DAY));
                calendarEndTime.set(Calendar.HOUR_OF_DAY,Integer.parseInt(parse2[0]));
                System.out.println(calendarEndTime.get(Calendar.HOUR_OF_DAY));

                // Refresh the week view. onMonthChange will be called again.
                //mWeekView.notifyDatasetChanged();
                updateLessonInfo(data.getStringExtra("key"));
            }

            else if (resultCode == 0) {
                System.out.println("RESULT CANCELLED");
            }
        }
    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
        Toast.makeText(getActivity(), "Long pressed event: " + event.getContent(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmptyViewLongPress(Calendar time) {
        Toast.makeText(getActivity(), "Empty view long pressed: " + getEventTitle(time), Toast.LENGTH_SHORT).show();
    }

    public WeekView getWeekView() {
        return mWeekView;
    }


    private void updateLessonInfo(final String key){

        databaseUpdate.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot lessonInfoSnapshot : dataSnapshot.getChildren() ){
                    //Create Artist Class Object and Returning Value
//Create An Artist Object

                    int startYear= calendarStartTime.get(Calendar.YEAR);
                    int startMonth = calendarStartTime.get(Calendar.MONTH);
                    int startDay = calendarStartTime.get(Calendar.DAY_OF_MONTH);
                    //int startHour = calendarStartTime.get(Calendar.HOUR_OF_DAY);
                    int startMinute = 30;
                    //calendarEndTime.set(Calendar.HOUR_OF_DAY,(calendarStartTime.get(Calendar.HOUR_OF_DAY)+1));

                    //int endHour = calendarEndTime.get(Calendar.HOUR_OF_DAY);
                    int endMinute = 30;

                    String startTime = startYear+"%" + startMonth +"%" + startDay + "%" + Integer.parseInt(startHour) + "%" + startMinute;
                    String endTime = startYear+"%" + startMonth+"%" + startDay + "%" + Integer.parseInt(endHour) + "%" + endMinute;
                    System.out.println("start time = " + startTime);
                    System.out.println("end time = " + endTime);
                    LessonInfo lessonInfo= new LessonInfo(startTime,endTime,grade,teacher,icerik,ders,key);
                    if(lessonInfoSnapshot.getKey().equals(key)){
                        lessonInfoSnapshot.getRef().setValue(lessonInfo);
                    }


                    // Refresh the week view. onMonthChange will be called again.

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Student Verisi Çekilemedi.");

            }


        });

    }

    private void addLessonInfo(){

        String id = databaseLessonInfo.push().getKey();
        //Create An Artist Object

        int startYear= calendarStartTime.get(Calendar.YEAR);
        int startMonth = calendarStartTime.get(Calendar.MONTH);
        int startDay = calendarStartTime.get(Calendar.DAY_OF_MONTH);
        //int startHour = calendarStartTime.get(Calendar.HOUR_OF_DAY);
        int startMinute = 30;
        //calendarEndTime.set(Calendar.HOUR_OF_DAY,(calendarStartTime.get(Calendar.HOUR_OF_DAY)+1));

        //int endHour = calendarEndTime.get(Calendar.HOUR_OF_DAY);
        int endMinute = 30;

        String startTime = startYear+"%" + startMonth +"%" + startDay + "%" + Integer.parseInt(startHour) + "%" + startMinute;
        String endTime = startYear+"%" + startMonth+"%" + startDay + "%" + Integer.parseInt(endHour) + "%" + endMinute;
        System.out.println("start time = " + startTime);
        System.out.println("end time = " + endTime);
        LessonInfo lessonInfo= new LessonInfo(startTime,endTime,grade,teacher,icerik,ders,id);

        databaseLessonInfo.child(id).setValue(lessonInfo);
        Toast.makeText(getActivity(),"Succesfully Stored Data",Toast.LENGTH_LONG).show();
    }

}