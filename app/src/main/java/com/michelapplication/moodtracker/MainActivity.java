package com.michelapplication.moodtracker;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {



    private ViewPager mPager;
    private int[] layouts = {R.layout.first_screen,R.layout.second_screen,
            R.layout.third_screen,R.layout.fourth_screen,R.layout.five_screen};
    private MpagerAdapter mpagerAdapter;
    private Button btn_history;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_history = (Button)findViewById(R.id.button_history_black);
        mPager = (ViewPager)findViewById(R.id.viewPager);
        mpagerAdapter = new MpagerAdapter(layouts,this);
        mPager.setAdapter(mpagerAdapter);
        mPager.setCurrentItem(1);

        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PageResult.class);
                startActivity(intent);

            }
        });

    }
}
