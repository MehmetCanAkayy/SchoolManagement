package com.intro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.GridLayout;

import com.mailSender.GuestActivity;
import com.onur.easyspeakdemo.MainActivity;
import com.onur.easyspeakdemo.Openning_screen;
import com.onur.easyspeakdemo.R;

public class IntroMenu extends AppCompatActivity {
    GridLayout mainGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_menu);

        mainGrid=findViewById(R.id.mainGrid);

        setSingleEvent(mainGrid);


    }

    private void setSingleEvent(GridLayout mainGrid) {

        for(int i=0 ; i<mainGrid.getChildCount(); i++){

            CardView cardView= (CardView) mainGrid.getChildAt(i);
            final int finalI=i;

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(finalI==0) {
                        Intent intent = new Intent(IntroMenu.this, IntroActivity.class);

                        startActivityForResult(intent,1);


                    }else if(finalI==1) {
                        Intent intent = new Intent(IntroMenu.this, IntroActivity2.class);

                        startActivityForResult(intent,1);


                    }
//                    else if(finalI==1)
//                    {
//                        String name="ogretmen";
//                        intent.putExtra("admin",name);
//                        startActivityForResult(intent,1);
//                    }
//                    else if(finalI==2){
//                        String name="ogrenci";
//                        intent.putExtra("admin",name);
//                        startActivityForResult(intent,1);
//
//                    }

                }
            });


        }


    }
}
