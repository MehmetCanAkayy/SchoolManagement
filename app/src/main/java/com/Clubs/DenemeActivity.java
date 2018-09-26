package com.Clubs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onur.easyspeakdemo.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DenemeActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    ArrayList<Upload> mUploads;
    ImageView image;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deneme);

        image= findViewById(R.id.image1);


        databaseReference = FirebaseDatabase.getInstance().getReference("uploads");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);

                    String url="https://firebasestorage.googleapis.com/v0/b/easyspeakdemo2.appspot.com/o/uploads%2Fuploads%2F1537918940037.png?alt=media&token=2e23c443-06f8-4088-bd6f-119c988cfe06";


                    Glide.with(getApplicationContext()).load(url).into(image);

                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}
