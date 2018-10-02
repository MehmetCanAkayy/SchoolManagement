package com.StudentMenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.onur.easyspeakdemo.R;
import com.wajahatkarim3.easyflipview.EasyFlipView;

public class AboutExams extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_exams);

        final EasyFlipView easyFlipView1 = (EasyFlipView) findViewById(R.id.easyFlipView1);
        easyFlipView1.setFlipDuration(1000);
        easyFlipView1.setFlipEnabled(true);
        final EasyFlipView easyFlipView2 = (EasyFlipView) findViewById(R.id.easyFlipView2);
        easyFlipView2.setFlipDuration(1000);
        easyFlipView2.setFlipEnabled(true);
        final EasyFlipView easyFlipView3 = (EasyFlipView) findViewById(R.id.easyFlipView3);
        easyFlipView3.setFlipDuration(1000);
        easyFlipView3.setFlipEnabled(true);
        final EasyFlipView easyFlipView4 = (EasyFlipView) findViewById(R.id.easyFlipView4);
        easyFlipView4.setFlipDuration(1000);
        easyFlipView4.setFlipEnabled(true);
        findViewById(R.id.front1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easyFlipView1.flipTheView();
            }
        });
        findViewById(R.id.back1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easyFlipView1.flipTheView();
            }
        });
        findViewById(R.id.front1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easyFlipView1.flipTheView();
            }
        });
        findViewById(R.id.back2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easyFlipView2.flipTheView();
            }
        });
        findViewById(R.id.front2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easyFlipView2.flipTheView();
            }
        });
        findViewById(R.id.back3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easyFlipView3.flipTheView();
            }
        });
        findViewById(R.id.front3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easyFlipView3.flipTheView();
            }
        });
        findViewById(R.id.back4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easyFlipView4.flipTheView();
            }
        });
        findViewById(R.id.front4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easyFlipView4.flipTheView();
            }
        });



        ScrollView scrollview1 = findViewById(R.id.scrollView);
        final TextView tv = findViewById(R.id.txtWord_cardback1);
        final TextView tv2 = findViewById(R.id.txtWord_cardback2);
        final TextView tv3 = findViewById(R.id.txtWord_cardback3);
        final TextView tv4 = findViewById(R.id.txtWord_cardback4);

        tv.setMovementMethod(new ScrollingMovementMethod());
        tv2.setMovementMethod(new ScrollingMovementMethod());
        tv3.setMovementMethod(new ScrollingMovementMethod());
        tv4.setMovementMethod(new ScrollingMovementMethod());

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easyFlipView1.flipTheView();
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easyFlipView2.flipTheView();
            }
        });
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easyFlipView3.flipTheView();
            }
        });
        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easyFlipView4.flipTheView();
            }
        });


        scrollview1.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                tv.getParent().requestDisallowInterceptTouchEvent(false);
                tv2.getParent().requestDisallowInterceptTouchEvent(false);
                tv3.getParent().requestDisallowInterceptTouchEvent(false);
                tv4.getParent().requestDisallowInterceptTouchEvent(false);

                return false;
            }
        });

        tv.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                tv.getParent().requestDisallowInterceptTouchEvent(true);

                return false;
            }
        });
        tv2.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                tv2.getParent().requestDisallowInterceptTouchEvent(true);

                return false;
            }
        });
        tv3.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                tv3.getParent().requestDisallowInterceptTouchEvent(true);

                return false;
            }
        });
        tv4.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                tv4.getParent().requestDisallowInterceptTouchEvent(true);

                return false;
            }
        });

    }
}
