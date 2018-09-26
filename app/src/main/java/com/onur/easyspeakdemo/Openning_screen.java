package com.onur.easyspeakdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.GridLayout;

import com.mailSender.GuestActivity;


public class Openning_screen extends AppCompatActivity {

    GridLayout mainGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_openning_screen);

        mainGrid=findViewById(R.id.mainGrid);

        setSingleEvent(mainGrid);


    }

    private void setSingleEvent(GridLayout mainGrid) {

        for(int i=0 ; i<mainGrid.getChildCount(); i++){

            CardView cardView= (CardView) mainGrid.getChildAt(i);
            final int finalI=i;
            Intent intent = new Intent(Openning_screen.this, MainActivity.class);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(finalI==0) {

                        String name="admin";
                        intent.putExtra("admin",name);
                        startActivityForResult(intent,1);
                    }
                    else if(finalI==1)
                    {
                        String name="ogretmen";
                        intent.putExtra("admin",name);
                        startActivityForResult(intent,1);
                    }
                    else if(finalI==2){
                      String name="ogrenci";
                        intent.putExtra("admin",name);
                        startActivityForResult(intent,1);/*

                        Yapılacak Kampanya çekimi için
                      Intent intent=new Intent(Openning_screen.this,ShowCampaign.class);
                      startActivity(intent);*/
                      /*  Intent intent=new Intent(Openning_screen.this,ClubsAdd.class);
                        startActivity(intent);*/

                    }
                    else if(finalI==3){
                        String name="misafir";
                        intent.putExtra("misafir",name);

                        startActivityForResult(intent,1);


                    }
                }
            });


        }


    }
}
