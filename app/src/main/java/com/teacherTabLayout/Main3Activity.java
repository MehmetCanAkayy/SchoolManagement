package com.teacherTabLayout;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.onur.easyspeakdemo.R;

public class Main3Activity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    // Test iconlari

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


    }


    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapterTeacher adapter = new ViewPagerAdapterTeacher(getSupportFragmentManager());
        adapter.addFragment(new FragmentTeacherActivity(),"Activity");
        adapter.addFragment(new FragmentTeacherChat(), "Chat");
        adapter.addFragment(new FragmentTeacherSpeaking(), "Speaking");
        adapter.addFragment(new FragmentTeacherSocial(), "Social");
        viewPager.setAdapter(adapter);
    }
}
