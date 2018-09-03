package com.firebaseDemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onur.easyspeakdemo.R;

import java.util.ArrayList;
import java.util.List;

public class StudentsActivity extends AppCompatActivity {



    DatabaseReference databaseArtists;
    List<Artist> artistList;
    ListView listViewArtists;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        databaseArtists = FirebaseDatabase.getInstance().getReference("students");
        artistList = new ArrayList<>();




    }


    @Override
    protected void onStart() {
        super.onStart();
        //Retriving data From Firebase
        databaseArtists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                artistList.clear();
                
                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren() ){
                    //Create Artist Class Object and Returning Value
                    Artist artist = artistSnapshot.getValue(Artist.class);
                    artistList.add(artist);

                }
                recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
                // use this setting to
                // improve performance if you know that changes
                // in content do not change the layout size
                // of the RecyclerVieww
                recyclerView.setHasFixedSize(true);
                // use a linear layout manager
                layoutManager = new LinearLayoutManager(StudentsActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                List<String> input = new ArrayList<>();

                mAdapter = new MyAdapter(artistList);
                recyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
