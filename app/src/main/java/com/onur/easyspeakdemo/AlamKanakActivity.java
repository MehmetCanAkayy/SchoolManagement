package com.onur.easyspeakdemo;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.firebaseDemo.MyAdapter;
import com.firebaseDemo.StudentsActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * This is a base activity which contains week view and all the codes necessary to initialize the
 * week view.
 * Created by Raquib-ul-Alam Kanak on 1/3/2014.
 * Website: http://alamkanak.github.io
 */
public class AlamKanakActivity extends AppCompatActivity implements WeekView.EventClickListener, MonthLoader.MonthChangeListener,WeekView.EmptyViewClickListener, WeekView.EventLongPressListener, WeekView.EmptyViewLongPressListener {
    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_THREE_DAY_VIEW = 2;
    private static final int TYPE_WEEK_VIEW = 3;
    private int mWeekViewType = TYPE_THREE_DAY_VIEW;
    private WeekView mWeekView;
    private ArrayList<WeekViewEvent> mNewEvents;
    private Calendar selectedDate;
    private DatabaseReference databaseLessonInfo;

    String baslangic;
    int day;

    String bitis;
    String grade;
    String teacher;
    String icerik ;
    Calendar calendarStartTime;
    Calendar calendarEndTime;
    String endHour;
    String startHour;


    Dialog myDialog;
    TextView titleTv,messageTv;
    ImageView closeButton;
    CardView card;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        myDialog = new Dialog(this);

        databaseLessonInfo = FirebaseDatabase.getInstance().getReference("lessonInfo");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
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

                Intent eventActivity = new Intent(AlamKanakActivity.this, EventActvity.class);
                eventActivity.putExtra("hour", 10);
                eventActivity.putExtra("minute", 30);
                eventActivity.putExtra("isFloatingButtonClicked", true);

                startActivityForResult(eventActivity, 1);

            }
        });



        // Get a reference for the week view in the layout.
        mWeekView = (WeekView) findViewById(R.id.weekView);

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
    }

    @Override
    protected void onStart() {
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


                    WeekViewEvent myEvent = new WeekViewEvent(lessonInfo.getGrade(), lessonInfo.getTeacher(), lessonInfo.getIcerik(), startTime, endTime);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        setupDateTimeInterpreter(id == R.id.action_week_view);
        switch (id) {
            case R.id.action_day_view:
                if (mWeekViewType != TYPE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(1);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                }
                return true;
            case R.id.action_three_day_view:
                if (mWeekViewType != TYPE_THREE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_THREE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(3);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                }
                return true;
            case R.id.action_week_view:
                if (mWeekViewType != TYPE_WEEK_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_WEEK_VIEW;
                    mWeekView.setNumberOfVisibleDays(7);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Set up a date time interpreter which will show short date values when in week view and long
     * date values otherwise.
     *
     * @param shortDate True if the date values should be short.
     */
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
    public void onEventClick(WeekViewEvent event, RectF eventRect) {

        Toast.makeText(this, "Clicked " + event.getContent(), Toast.LENGTH_SHORT).show();

//        AlertDialog.Builder builder = new AlertDialog.Builder(AlamKanakActivity.this);
//        builder.setTitle("Lesson Content");
//        builder.setMessage(event.getGrade() + "\n" + event.getTeacher() + "\n" + event.getStartEnd() + "\n" + event.mGetContent());


        myDialog.setContentView(R.layout.custom_dialog_box);
        messageTv = myDialog.findViewById(R.id.content);
        card = myDialog.findViewById(R.id.mycard);

        card.setBackgroundColor(event.getColor());

        closeButton = myDialog.findViewById(R.id.close);

        messageTv.setText(event.getGrade() + "\n" + event.getTeacher() + "\n" + event.getStartEnd() + "\n" + event.mGetContent());


        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();


        //builder.show();
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
        final Intent eventActivity = new Intent(this, EventActvity.class);
        eventActivity.putExtra("hour", time.get(Calendar.HOUR_OF_DAY));
        eventActivity.putExtra("minute", time.get(Calendar.MINUTE));
        eventActivity.putExtra("isFloatingButtonClicked", false);

        calendarStartTime=time;
        calendarEndTime=time;


        startActivityForResult(eventActivity, 1);
        Toast.makeText(this, "Empty view clicked: " + getEventTitle(time), Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                baslangic = data.getStringExtra("baslangic");
                bitis = data.getStringExtra("bitis");
                grade = data.getStringExtra("grade");
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




            } else if (resultCode == 0) {
                System.out.println("RESULT CANCELLED");
            }
        }
    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
        Toast.makeText(this, "Long pressed event: " + event.getContent(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmptyViewLongPress(Calendar time) {
        Toast.makeText(this, "Empty view long pressed: " + getEventTitle(time), Toast.LENGTH_SHORT).show();
    }

    public WeekView getWeekView() {
        return mWeekView;
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
            LessonInfo lessonInfo= new LessonInfo(startTime,endTime,grade,teacher,icerik);

            databaseLessonInfo.child(id).setValue(lessonInfo);
            Toast.makeText(this,"Succesfully Stored Data",Toast.LENGTH_LONG).show();
    }





}