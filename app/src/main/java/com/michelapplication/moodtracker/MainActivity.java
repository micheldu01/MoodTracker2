package com.michelapplication.moodtracker;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {



    private ViewPager mPager;
    private int[] layouts = {R.layout.first_screen,R.layout.second_screen,
            R.layout.third_screen,R.layout.fourth_screen,R.layout.five_screen};
    private MpagerAdapter mpagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mPager = (ViewPager)findViewById(R.id.viewPager);
        mpagerAdapter = new MpagerAdapter(layouts,this);
        mPager.setAdapter(mpagerAdapter);

    }
}
