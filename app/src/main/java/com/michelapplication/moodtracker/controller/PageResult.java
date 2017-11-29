package com.michelapplication.moodtracker.controller;

import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.michelapplication.moodtracker.R;

import static com.michelapplication.moodtracker.controller.MainActivity.COMMENT;
import static com.michelapplication.moodtracker.controller.MainActivity.DATE;
import static com.michelapplication.moodtracker.controller.MainActivity.MOOD_TEMPORARY;
import static com.michelapplication.moodtracker.controller.MainActivity.MYMOOD;

public class PageResult extends AppCompatActivity {

    //text view
    private TextView mView7;
    private TextView mView6;
    private TextView mView5;
    private TextView mView4;
    private TextView mView3;
    private TextView mView2;
    private TextView mView1;
    //image btn
    private ImageButton btn7;
    private ImageButton btn6;
    private ImageButton btn5;
    private ImageButton btn4;
    private ImageButton btn3;
    private ImageButton btn2;
    private ImageButton btn1;
    //SharedPreferences
    private String comment;
    private int smiley;
    private long saveDay;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_result);

        // text view and image btn
        mView7 = (TextView) findViewById(R.id.a_week_ago);
        mView6 = (TextView) findViewById(R.id.six_days_ago);
        mView5 = (TextView) findViewById(R.id.five_days_ago);
        mView4 = (TextView) findViewById(R.id.four_days_ago);
        mView3 = (TextView) findViewById(R.id.three_days_ago);
        mView2 = (TextView) findViewById(R.id.day_before_yesterday);
        mView1 = (TextView) findViewById(R.id.yesterday);
        btn7 = (ImageButton) findViewById(R.id.btn_7);
        btn6 = (ImageButton) findViewById(R.id.btn_6);
        btn5 = (ImageButton) findViewById(R.id.btn_5);
        btn4 = (ImageButton) findViewById(R.id.btn_4);
        btn3 = (ImageButton) findViewById(R.id.btn_3);
        btn2 = (ImageButton) findViewById(R.id.btn_2);
        btn1 = (ImageButton) findViewById(R.id.btn_1);

        //SharedPreferences date comment and smiley
        SharedPreferences prefs = getSharedPreferences(MYMOOD, MODE_PRIVATE);
        comment = prefs.getString(COMMENT, "");
        smiley = prefs.getInt(MOOD_TEMPORARY, 0);
        saveDay = prefs.getLong(DATE, 0);

        //Days between today and saveDay
        Calendar today  = Calendar.getInstance();
        today.get(Calendar.DAY_OF_MONTH);
        today.get(Calendar.MONTH);
        today.get(Calendar.YEAR);
        long diff = today.getTimeInMillis() - saveDay;
        int dayCount = (int)  diff / (24 * 60 * 60 * 1000);

        


        mView7.setText(String.valueOf(dayCount));



    }
}
