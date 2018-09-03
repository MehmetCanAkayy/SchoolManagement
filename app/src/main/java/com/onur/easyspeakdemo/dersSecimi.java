package com.onur.easyspeakdemo;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebaseDemo.StudentsActivity;

public class dersSecimi extends AppCompatActivity {

    private Button ran_al,rand_iptal;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ders_secimi);

        final Intent sayfagecis=new Intent(this, randevuAl.class);
        final Intent sayfagecis2=new Intent(this, ListWeek.class);
        final Intent sayfagecis3=new Intent(this, AlamKanakActivity.class);
        final Intent studentsActivity=new Intent(this, StudentsActivity.class);


        ran_al= (Button) findViewById(R.id.rand_al);
        ran_al.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(dersSecimi.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_login, null);
                Button activity = (Button) mView.findViewById(R.id.activity);

                Button social = (Button) mView.findViewById(R.id.social);

                Button chat = (Button) mView.findViewById(R.id.chat);
                Button speaking = (Button) mView.findViewById(R.id.speaking);




//                mBuilder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                });
//
//                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                });
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                activity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(sayfagecis2);
                    }
                });
                social.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(sayfagecis3);
                    }
                });
                chat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(studentsActivity);
                    }
                });
                speaking.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(sayfagecis);
                    }
                });
//                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if (!mEmail.getText().toString().isEmpty() && !mPassword.getText().toString().isEmpty()) {
//                            Toast.makeText(MainActivity.this,
//                                    R.string.success_login_msg,
//                                    Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(MainActivity.this, Main2Activity.class));
//                            dialog.dismiss();
//                        } else {
//                            Toast.makeText(MainActivity.this,
//                                    R.string.error_login_msg,
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });



            }
        });




    }


}
