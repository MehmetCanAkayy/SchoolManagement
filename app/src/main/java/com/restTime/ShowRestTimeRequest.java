package com.restTime;

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

public class ShowRestTimeRequest extends AppCompatActivity {
    DatabaseReference databaseRestTimeInfo;
    List<restTimeClass> restTimeList;
    private RecyclerView recyclerView;
    private RestTimeAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_rest_time_request);

        databaseRestTimeInfo = FirebaseDatabase.getInstance().getReference("restTime");
        restTimeList = new ArrayList<>();




    }


    @Override
    protected void onStart() {
        super.onStart();
        //Retriving data From Firebase
        databaseRestTimeInfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                restTimeList.clear();

                for (DataSnapshot surveyInfoSnapshot : dataSnapshot.getChildren() ){
                    //Create Artist Class Object and Returning Value
                    restTimeClass surveyInfo = surveyInfoSnapshot.getValue(restTimeClass.class);
                    if(surveyInfo.isReturn()==false){
                        restTimeList.add(surveyInfo);

                    }

                }
                recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
                // use this setting to
                // improve performance if you know that changes
                // in content do not change the layout size
                // of the RecyclerVieww
                recyclerView.setHasFixedSize(true);
                // use a linear layout manager
                layoutManager = new LinearLayoutManager(ShowRestTimeRequest.this);
                recyclerView.setLayoutManager(layoutManager);

                mAdapter = new RestTimeAdapter(restTimeList);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Student Verisi Çekilemedi.");

            }
        });
    }
}
