package com.mailSender;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;


import com.onur.easyspeakdemo.R;
import com.onur.easyspeakdemo.ShowCampaign;
import com.tureng.TurengTranslate;


public class GuestActivity extends AppCompatActivity {

    private Button ran_al,rand_iptal,userAddButton;

    GridLayout mainGrid;

    Dialog myDialog;
    ImageView closeButton;
    Button delete,update;
    EditText name;
    EditText phoneNumber;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);

        mainGrid = (GridLayout) findViewById(R.id.mainGrid);

        //Set Event
        setSingleEvent(mainGrid);
        myDialog = new Dialog(this);
        final SendEmailTask sendEmailTask = new SendEmailTask();


        myDialog.setContentView(R.layout.custom_guest_dialog);

        closeButton = myDialog.findViewById(R.id.close);
        delete = myDialog.findViewById(R.id.delete);
        update = myDialog.findViewById(R.id.update);
        name = myDialog.findViewById(R.id.name);
        phoneNumber = myDialog.findViewById(R.id.phoneNumber);


        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                sendEmailTask.execute();

                myDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Talebiniz bize ulasmistir en kisa surede size donuz yapilacaktir.", Toast.LENGTH_LONG).show();





            }
        });


        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


    }


    private void setSingleEvent(GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(finalI==0){


                        myDialog.show();

                    }else if(finalI == 1 ){
                        //final Intent adminRegister=new Intent(ShowAdminMenu.this, AdminRegister.class);


                        //startActivityForResult(adminRegister, 1);

                    }
                    else if(finalI==2)
                    {
                        Intent intent=new Intent(GuestActivity.this, ShowCampaign.class);
                        startActivityForResult(intent,1);
                    }
                    else if(finalI==3)
                    {
                        Intent intent=new Intent(GuestActivity.this, TurengTranslate.class);
                        startActivityForResult(intent,1);
                    }

                }
            });
        }
    }

    class SendEmailTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("Email sending", "sending start");
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                GmailSender sender = new GmailSender("EasySpeakDemo@gmail.com", "52548226");
                //subject, body, sender, to
                sender.sendMail("Bilgi Almak Istiyorum",
                        "Name: "+name.getText().toString() + "\n" +"Phone Number: "+ phoneNumber.getText().toString(),
                        "EasySpeakDemo@gmail.com",
                        "19.onurcelebi.95@gmail.com");

                Log.i("Email sending", "send");
            } catch (Exception e) {
                Log.i("Email sending", "cannot send");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }



}
