package com.alam_kanak;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.onur.easyspeakdemo.R;

public class AlamKanakView extends AppCompatActivity {


    private ViewPager viewPager;
    private TabLayout tabLayout;

    // Test iconlari
    private int[] tabIcons = {R.drawable.add_circle_white,R.drawable.done_white};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }


    private void setupViewPager(ViewPager viewPager) {

        FragmentOne fragment1 = FragmentOne.newInstance( 3);

        FragmentOne fragment2 = FragmentOne.newInstance(7);



        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(fragment1,"3 DAY");
        adapter.addFragment(fragment2, "7 DAY");
        viewPager.setAdapter(adapter);
    }
}