package com.michelapplication.moodtracker.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.michelapplication.moodtracker.BDD.Mood;
import com.michelapplication.moodtracker.BDD.MoodBDD;
import com.michelapplication.moodtracker.R;

import java.util.ArrayList;

import static com.michelapplication.moodtracker.controller.MainActivity.COMMENT;
import static com.michelapplication.moodtracker.controller.MainActivity.DATE;
import static com.michelapplication.moodtracker.controller.MainActivity.MOOD_TEMPORARY;
import static com.michelapplication.moodtracker.controller.MainActivity.MYMOOD;

public class PageResult extends MainActivity {

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
    // values for BDD
    private int choice_color = 1;
    private int size_color = 1;
    private int size_comment = 1;
    //Toast
    private TextView toast_mood;


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
            mMoodBDD.insertMood(new Mood(choice_color, size_color, size_comment, saveComment));
        }
        //add void mMooBdd if dayCount > 1
        while (dayCount > 1)
        {
            mMoodBDD.insertMood(new Mood(R.color.white, 300, 0, ""));
            dayCount--;
        }
        arrayMoods = mMoodBDD.getMood();

        //seven_days
        methodDay(arrayMoods.get(arrayMoods.size()-7).getColor(),
                arrayMoods.get(arrayMoods.size()-7).getComment(),
                arrayMoods.get(arrayMoods.size()-7).getSizeColor(),
                arrayMoods.get(arrayMoods.size()-7).getSizeCommnent(),
                btn7,
                mView7);

        //six days
        methodDay(arrayMoods.get(arrayMoods.size()-6).getColor(),
                arrayMoods.get(arrayMoods.size()-6).getComment(),
                arrayMoods.get(arrayMoods.size()-6).getSizeColor(),
                arrayMoods.get(arrayMoods.size()-6).getSizeCommnent(),
                btn6,
                mView6);

        // five days
        methodDay(arrayMoods.get(arrayMoods.size()-5).getColor(),
                arrayMoods.get(arrayMoods.size()-5).getComment(),
                arrayMoods.get(arrayMoods.size()-5).getSizeColor(),
                arrayMoods.get(arrayMoods.size()-5).getSizeCommnent(),
                btn5,
                mView5);

        //four days
        methodDay(arrayMoods.get(arrayMoods.size()-4).getColor(),
                arrayMoods.get(arrayMoods.size()-4).getComment(),
                arrayMoods.get(arrayMoods.size()-4).getSizeColor(),
                arrayMoods.get(arrayMoods.size()-4).getSizeCommnent(),
                btn4,
                mView4);

        //tree days
        methodDay(arrayMoods.get(arrayMoods.size()-3).getColor(),
                arrayMoods.get(arrayMoods.size()-3).getComment(),
                arrayMoods.get(arrayMoods.size()-3).getSizeColor(),
                arrayMoods.get(arrayMoods.size()-3).getSizeCommnent(),
                btn3,
                mView3);

        //two days
        methodDay(arrayMoods.get(arrayMoods.size()-2).getColor(),
                arrayMoods.get(arrayMoods.size()-2).getComment(),
                arrayMoods.get(arrayMoods.size()-2).getSizeColor(),
                arrayMoods.get(arrayMoods.size()-2).getSizeCommnent(),
                btn2,
                mView2);

        //one day
        methodDay(arrayMoods.get(arrayMoods.size()-1).getColor(),
                arrayMoods.get(arrayMoods.size()-1).getComment(),
                arrayMoods.get(arrayMoods.size()-1).getSizeColor(),
                arrayMoods.get(arrayMoods.size()-1).getSizeCommnent(),
                btn1,
                mView1);

        //test toast
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methodToast(arrayMoods.get(arrayMoods.size()-7).getComment());
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methodToast(arrayMoods.get(arrayMoods.size()-6).getComment());
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methodToast(arrayMoods.get(arrayMoods.size()-5).getComment());
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methodToast(arrayMoods.get(arrayMoods.size()-4).getComment());
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methodToast(arrayMoods.get(arrayMoods.size()-3).getComment());
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methodToast(arrayMoods.get(arrayMoods.size()-2).getComment());
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methodToast(arrayMoods.get(arrayMoods.size()-1).getComment());
            }
        });
        mMoodBDD.close();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void methodDay(int myColor, String myComment, int mySizeColor, int mySizeComment, ImageButton myButton, TextView myTextView){
        myTextView.setBackgroundColor(getColor(myColor));
        //comment no comment
        if (myComment.equals("")){
            myButton.setVisibility(View.INVISIBLE);
        }
        if (myColor == (R.color.white)){
            myTextView.setText("default mood");
        }
        float sp7 = (mySizeColor);
        float px7 = sp7 * getResources().getDisplayMetrics().density;
        myTextView.getLayoutParams().width = (int) px7;
        //margin left
        float spl7 = (mySizeComment);
        float pxl7 = spl7 * getResources().getDisplayMetrics().density;
        //margin top
        float spt7 = 30;
        float pxt7 = spt7 * getResources().getDisplayMetrics().density;
        //change margins
        RelativeLayout.LayoutParams lp7 = (RelativeLayout.LayoutParams) myButton.getLayoutParams();
        lp7.setMargins((int) pxl7, (int) pxt7, 0, 0);
        myButton.setLayoutParams(lp7);
    }

    public void methodToast(String mString){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_layout));
        toast_mood = (TextView) layout.findViewById(R.id.toast_mood);
        toast_mood.setText(mString);
        Toast toast = new Toast(getApplicationContext());
        toast.setView(layout);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }
}
