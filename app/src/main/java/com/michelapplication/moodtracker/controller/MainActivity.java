package com.michelapplication.moodtracker.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.ParcelUuid;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.michelapplication.moodtracker.BDD.Mood;
import com.michelapplication.moodtracker.BDD.MoodBDD;
import com.michelapplication.moodtracker.model.MpagerAdapter;
import com.michelapplication.moodtracker.R;
import com.michelapplication.moodtracker.model.VerticalViewPager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    //layouts smiley
    protected VerticalViewPager mPager;
    private int[] layouts = {R.drawable.smiley_super_happy,R.drawable.smiley_happy,
            R.drawable.smiley_normal,R.drawable.smiley_disappointed,R.drawable.smiley_sad};
    private MpagerAdapter mpagerAdapter;
    private Button btn_history;
    //add white edit text and btn cancel and accept
    protected Button btn_comment;
    protected Button btn_cancel_comment;
    protected Button btn_accept_comment;
    protected EditText edit_text_comment;
    protected TextView white_square;
    //SharedPreferences
    protected SharedPreferences mSharedPreferences;
    public static final String MYMOOD = "MyMood";
    public static final String COMMENT = "Comment";
    public static final String MOOD_TEMPORARY = "";
    public static final String DATE  = "yyyy-MM-dd";
    public static final String FIRST_CONNECT = "TRUE";
    // music
    private MediaPlayer mMediaPlayer;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn_history = (Button)findViewById(R.id.button_history_black);
        mPager = (VerticalViewPager) findViewById(R.id.viewPager);
        mpagerAdapter = new MpagerAdapter(layouts,this);
        mPager.setAdapter(mpagerAdapter);
        //set the current item (smiley))
        //implement white square and btn and edit text (in mode invisible)
        btn_cancel_comment = (Button) findViewById(R.id.btn_cancel_comment);
        btn_cancel_comment.setVisibility(View.INVISIBLE);
        btn_accept_comment = (Button) findViewById(R.id.btn_accept_comment);
        btn_accept_comment.setVisibility(View.INVISIBLE);
        btn_comment = (Button) findViewById(R.id.button_comment);
        edit_text_comment = (EditText) findViewById(R.id.edit_text);
        edit_text_comment.setVisibility(View.INVISIBLE);
        white_square = (TextView) findViewById(R.id.carre_blanc);
        white_square.setVisibility(View.INVISIBLE);
        //SharedPreferences
        mSharedPreferences = getSharedPreferences(MYMOOD, Context.MODE_PRIVATE);
        //music
        mMediaPlayer = MediaPlayer.create(this,R.raw.music_appli);


        //If first connection
        if (FIRST_CONNECT.equals("TRUE")){
            // set mood happy
            mPager.setCurrentItem(1);
            //save FALSE for the first connect
            mSharedPreferences.edit().putString(FIRST_CONNECT, "FALSE");
        }

            // insert save date and save mood position
        mPager.addOnPageChangeListener(new VerticalViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // save smiley selected
                mSharedPreferences.edit().putInt(MOOD_TEMPORARY,(position)).commit();
                // save date
                Calendar thatDay = Calendar.getInstance();
                thatDay.get(Calendar.DAY_OF_MONTH);
                thatDay.get(Calendar.MONTH);
                thatDay.get(Calendar.YEAR);
                long saveDay = thatDay.getTimeInMillis();
                mSharedPreferences.edit().putLong(DATE, saveDay).commit();
            }

            @Override
            public void onPageSelected(int position) {
                // start music
                mMediaPlayer.start();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //btn of the MainActivity
        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PageResult.class);
                startActivity(intent);

            }
        });

        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HideShow(true);
            }
        });

        btn_accept_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HideShow(false);
                //SharedPreferences comment
                String h = edit_text_comment.getText().toString();
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putString(COMMENT,h);
                editor.commit();
            }
        });

        btn_cancel_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               HideShow(false);
            }
        });
    }

    //method for Hide and Show the white square edit text
    private void HideShow(boolean isVisible){
        int codeVisible = 0;
        if(isVisible == true){
            codeVisible = View.VISIBLE;
        }else{
            codeVisible = View.INVISIBLE;
        }
        btn_cancel_comment = (Button) findViewById(R.id.btn_cancel_comment);
        btn_cancel_comment.setVisibility(codeVisible);
        btn_accept_comment = (Button) findViewById(R.id.btn_accept_comment);
        btn_accept_comment.setVisibility(codeVisible);
        edit_text_comment = (EditText) findViewById(R.id.edit_text);
        edit_text_comment.setVisibility(codeVisible);
        white_square = (TextView) findViewById(R.id.carre_blanc);
        white_square.setVisibility(codeVisible);
    }
}
