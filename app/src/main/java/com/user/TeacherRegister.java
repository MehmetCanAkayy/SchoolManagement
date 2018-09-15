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

import com.firebaseDemo.Teacher;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jackandphantom.blurimage.BlurImage;
import com.onur.easyspeakdemo.R;



public class TeacherRegister extends AppCompatActivity {

    private EditText editTextName;
    private EditText phoneNo;
    private Spinner userGrade;
    DatabaseReference databaseTeachers;
    private Button add;
    private ImageView background;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        editTextName= (EditText) findViewById(R.id.userName);
        phoneNo=findViewById(R.id.phoneNo);
        databaseTeachers = FirebaseDatabase.getInstance().getReference("teachers");
        background=findViewById(R.id.background);
        add = findViewById(R.id.add);

        userGrade = (Spinner) findViewById(R.id.user_grade);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.from, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userGrade.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTeacher();
            }
        });
        BlurImage.with(getApplicationContext()).load(R.drawable.backgorund).intensity(20).Async(true).into(background);


    }

    private void addTeacher(){
        String name = editTextName.getText().toString().trim();
        String grade = userGrade.getSelectedItem().toString();
        String phoneNumber=phoneNo.getText().toString().trim();

        if(!TextUtils.isEmpty(name)){
            String id = databaseTeachers.push().getKey();

            //Create An Artist Object
            Teacher teacher = new Teacher(name,grade,phoneNumber);
            databaseTeachers.child(id).setValue(teacher);
            Toast.makeText(this,"Succesfully Stored Data",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"You should enter a name",Toast.LENGTH_LONG).show();
        }
        finish();



    }




}



