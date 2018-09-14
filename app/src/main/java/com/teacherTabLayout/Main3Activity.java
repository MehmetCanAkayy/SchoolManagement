package com.teacherTabLayout;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.onur.easyspeakdemo.R;
import com.studentsTabLayout.FragmentOne;
import com.studentsTabLayout.FragmentTwo;
import com.studentsTabLayout.ViewPagerAdapter;

public class Main3Activity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    // Test iconlari
    private int[] tabIcons = {android.R.drawable.ic_menu_camera,android.R.drawable.ic_menu_agenda,android.R.drawable.ic_menu_add};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }


    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapterTeacher adapter = new ViewPagerAdapterTeacher(getSupportFragmentManager());
        adapter.addFragment(new FragmentTeacherOne(),"One");
        adapter.addFragment(new FragmentTeacherTwo(), "Two");
        viewPager.setAdapter(adapter);
    }
}
