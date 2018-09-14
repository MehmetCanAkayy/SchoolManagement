package com.user;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebaseDemo.Admin;
import com.firebaseDemo.Artist;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jackandphantom.blurimage.BlurImage;
import com.onur.easyspeakdemo.R;



public class AdminRegister extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextPhoneNumber;

    DatabaseReference databaseArtists;
    private Button add;
    private ImageView background;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);

        editTextName= (EditText) findViewById(R.id.adminName);
        editTextPhoneNumber= findViewById(R.id.adminPhoneNo);

        databaseArtists = FirebaseDatabase.getInstance().getReference("admin");

        background=findViewById(R.id.background);
        add = findViewById(R.id.add);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addArtist();
            }
        });
        BlurImage.with(getApplicationContext()).load(R.drawable.backgorund).intensity(20).Async(true).into(background);


    }

    private void addArtist(){
        String name = editTextName.getText().toString().trim();
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();


        if(!TextUtils.isEmpty(name)){
            String id = databaseArtists.push().getKey();

            //Create An Artist Object
            Admin artist = new Admin(name,phoneNumber);
            databaseArtists.child(id).setValue(artist);
            Toast.makeText(this,"Succesfully Stored Data",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"You should enter a name",Toast.LENGTH_LONG).show();
        }
        finish();

    }




}
