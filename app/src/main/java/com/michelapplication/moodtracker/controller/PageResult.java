package com.michelapplication.moodtracker.controller;

import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.michelapplication.moodtracker.BDD.Mood;
import com.michelapplication.moodtracker.BDD.MoodBDD;
import com.michelapplication.moodtracker.R;

import java.util.ArrayList;

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
    private String saveComment;
    private int smiley;
    private long saveDay;
    //BDD and Arrays for BDD
    private MoodBDD mMoodBDD;
    private ArrayList<Mood> arrayMoods;
    private ArrayList<Integer> arrayColor;
    private ArrayList<Integer> arraySizeColor;
    private ArrayList<Integer> arraySizeComment;
    private ArrayList<String> arrayComment;
    // values for BDD
    private int choice_color = 1;
    private int size_color = 1;
    private int size_comment = 1;
    private String comment = "Default mood";



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
        saveComment = prefs.getString(COMMENT, "");
        smiley = prefs.getInt(MOOD_TEMPORARY, 0);
        saveDay = prefs.getLong(DATE, 0);

        //Days between today and saveDay
        Calendar today  = Calendar.getInstance();
        today.get(Calendar.DAY_OF_MONTH);
        today.get(Calendar.MONTH);
        today.get(Calendar.YEAR);
        long diff = today.getTimeInMillis() - saveDay;
        int dayCount = (int)  diff / (24 * 60 * 60 * 1000);

        //add BDD and Arrray for BDD
        mMoodBDD = new MoodBDD(this);
        arrayMoods = new ArrayList<>();
        mMoodBDD.open();
        arrayMoods = mMoodBDD.getMood();

        //add smileys possibilities for get color, size color and size btn comment
        if (smiley == 0) { size_color = 360; size_comment = 315; choice_color = (R.color.banana_yellow);
        }
        if (smiley == 1) { size_color = 288; size_comment = 243; choice_color = (R.color.light_sage);
        }
        if (smiley == 2) { size_color = 216; size_comment = 171; choice_color = (R.color.cornflower_blue_65);
        }
        if (smiley == 3) { size_color = 144; size_comment = 99; choice_color = (R.color.warm_grey);
        }
        if (smiley == 4) { size_color = 72; size_comment = 27; choice_color = (R.color.faded_red);
        }

        //add value in mMooBDD if dayCount != 0
        if (dayCount != 0){
            mMoodBDD.insertMood(new Mood(choice_color, size_color, size_comment, comment));
        }
        
        //add void mMooBdd if dayCount > 1
        while (dayCount > 1)
        {
            mMoodBDD.insertMood(new Mood(choice_color, 0, 0, ""));
            dayCount--;
        }

        mView7.setText(String.valueOf(size_comment));



        mMoodBDD.close();
    }
}
