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

import com.firebaseDemo.Artist;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jackandphantom.blurimage.BlurImage;
import com.onur.easyspeakdemo.R;



public class StudentRegister extends AppCompatActivity {

    private EditText editTextName;
    private Spinner userGrade;
    DatabaseReference databaseArtists;
    private Button add;
    private ImageView background;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        
        editTextName= (EditText) findViewById(R.id.userName);
        databaseArtists = FirebaseDatabase.getInstance().getReference("students");

        background=findViewById(R.id.background);
        add = findViewById(R.id.add);

        userGrade = (Spinner) findViewById(R.id.user_grade);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.grades, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        userGrade.setAdapter(adapter);

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
        String grade = userGrade.getSelectedItem().toString();


        if(!TextUtils.isEmpty(name)){
            String id = databaseArtists.push().getKey();

            //Create An Artist Object
            Artist artist = new Artist(name,grade);
            databaseArtists.child(id).setValue(artist);
            Toast.makeText(this,"Succesfully Stored Data",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"You should enter a name",Toast.LENGTH_LONG).show();
        }
        finish();



    }




}
