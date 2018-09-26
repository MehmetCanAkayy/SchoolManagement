package com.onur.easyspeakdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebaseDemo.Camp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowCampaign extends AppCompatActivity {

    TextView txtContent;
    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_campaign);
        txtContent= findViewById(R.id.textviewCamp);
        image=findViewById(R.id.image42);

        ArrayList<Camp> kampanya=new ArrayList<>();

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Kampanya");



        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                kampanya.clear();

                for(DataSnapshot campSnapshot : dataSnapshot.getChildren()){

                    Camp camp= campSnapshot.getValue(Camp.class);



                    if(camp.getTur().toString().equals("Kur")){
                        txtContent.setText(camp.getkAlana().toString()+" Kur Alana "+camp.getkAlanaHediye().toString()+" Kur Hediye \n"+camp.getMesaj().toString());
                        String url="https://firebasestorage.googleapis.com/v0/b/easyspeakdemo2.appspot.com/o/uploads%2Fuploads%2F1537918940037.png?alt=media&token=2e23c443-06f8-4088-bd6f-119c988cfe06";

                        Glide.with(getApplicationContext()).load(url).into(image);
                        break;
                    }
                    else if(camp.getTur().equals("Yuzde ve Kur")){
                        txtContent.setText(camp.getkAlana().toString()+" Kur Alana " +camp.getkAlanaHediye().toString()+" Kur Hediye ve %"+camp.getYuzde().toString()+" indirim \n"+camp.getMesaj().toString());break;
                    }
                    else if(camp.getTur().equals("Kur ve Hediye")){
                        txtContent.setText(camp.getkAlana().toString()+" Kur Alana "+camp.getkAlanaHediye().toString()+" Kur Hediye ve Yanında" + camp.getHediye().toString() +" \n "+camp.getMesaj().toString());break;
                    }
                    else if(camp.getTur().equals("Yuzde, Kur ve Hediye")){
                        txtContent.setText(camp.getkAlana().toString()+" Kur Alana "+camp.getkAlanaHediye().toString()+" Kur Hediye ve Yanında" + camp.getHediye().toString() +"ve %"+camp.getYuzde().toString()+" indirim \n"+camp.getMesaj().toString());break;
                    }
                    else if(camp.getTur().equals("Hediye")){
                        txtContent.setText("Hediye"+camp.getHediye().toString() +" \n"+camp.getMesaj().toString());
                        break;
                    }
                    }
                }





            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
