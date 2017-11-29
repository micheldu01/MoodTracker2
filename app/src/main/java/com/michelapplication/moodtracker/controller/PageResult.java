package com.michelapplication.moodtracker.controller;

import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        //int dayCount = (int)  diff / (24 * 60 * 60 * 1000);
        int dayCount = 1;
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
            mMoodBDD.insertMood(new Mood(choice_color, size_color, size_comment, saveComment));
        }

        //add void mMooBdd if dayCount > 1
        while (dayCount > 1)
        {
            mMoodBDD.insertMood(new Mood(choice_color, 0, 0, ""));
            dayCount--;
        }

        //seven_days
        mView7.setBackgroundColor(getColor(arrayMoods.get(arrayMoods.size()-7).getColor()));
        //comment no comment
        if ((arrayMoods.get(arrayMoods.size()-7).getComment()).equals("")) {
            btn7.setVisibility(View.INVISIBLE);
        }
        if ((arrayMoods.get(arrayMoods.size()-7).getSizeColor()) == 0){
            mView7.setText(" default mood");
        }
        float sp7 = (arrayMoods.get(arrayMoods.size()-7).getSizeColor());
        float px7 = sp7 * getResources().getDisplayMetrics().density;
        mView7.getLayoutParams().width = (int) px7;
        //margin left
        float spl7 = (arrayMoods.get(arrayMoods.size()-7).getSizeCommnent());
        float pxl7 = spl7 * getResources().getDisplayMetrics().density;
        //margin top
        float spt7 = 30;
        float pxt7 = spt7 * getResources().getDisplayMetrics().density;
        //change margins
        RelativeLayout.LayoutParams lp7 = (RelativeLayout.LayoutParams) btn7.getLayoutParams();
        lp7.setMargins((int) pxl7, (int) pxt7, 0, 0);
        btn7.setLayoutParams(lp7);

        //six days
        mView6.setBackgroundColor(getColor(arrayMoods.get(arrayMoods.size()-6).getColor()));
        //comment no comment
        if ((arrayMoods.get(arrayMoods.size()-6).getComment()).equals("")) {
            btn6.setVisibility(View.INVISIBLE);
        }
        if ((arrayMoods.get(arrayMoods.size()-6).getSizeColor()) == 0){
            mView7.setText(" default mood");
        }
        float sp6 = (arrayMoods.get(arrayMoods.size()-6).getSizeColor());
        float px6 = sp6 * getResources().getDisplayMetrics().density;
        mView6.getLayoutParams().width = (int) px6;
        //margin left
        float spl6 = (arrayMoods.get(arrayMoods.size()-6).getSizeCommnent());
        float pxl6 = spl6 * getResources().getDisplayMetrics().density;
        //margin top
        float spt6 = 30;
        float pxt6 = spt6 * getResources().getDisplayMetrics().density;
        //change margins
        RelativeLayout.LayoutParams lp6 = (RelativeLayout.LayoutParams) btn6.getLayoutParams();
        lp6.setMargins((int) pxl6, (int) pxt6, 0, 0);
        btn6.setLayoutParams(lp6);

        // five days
        mView5.setBackgroundColor(getColor(arrayMoods.get(arrayMoods.size()-5).getColor()));
        //comment no comment
        if ((arrayMoods.get(arrayMoods.size()-5).getComment()).equals("")) {
            btn5.setVisibility(View.INVISIBLE);
        }
        if ((arrayMoods.get(arrayMoods.size()-5).getSizeColor()) == 0){
            mView7.setText(" default mood");
        }
        float sp5 = (arrayMoods.get(arrayMoods.size()-5).getSizeColor());
        float px5 = sp5 * getResources().getDisplayMetrics().density;
        mView5.getLayoutParams().width = (int) px5;
        //margin left
        float spl5 = (arrayMoods.get(arrayMoods.size()-5).getSizeCommnent());
        float pxl5 = spl5 * getResources().getDisplayMetrics().density;
        //margin top
        float spt5 = 30;
        float pxt5 = spt5 * getResources().getDisplayMetrics().density;
        //change margins
        RelativeLayout.LayoutParams lp5 = (RelativeLayout.LayoutParams) btn5.getLayoutParams();
        lp5.setMargins((int) pxl5, (int) pxt5, 0, 0);
        btn5.setLayoutParams(lp5);

        //four days
        mView4.setBackgroundColor(getColor(arrayMoods.get(arrayMoods.size()-4).getColor()));
        //comment no comment
        if ((arrayMoods.get(arrayMoods.size()-4).getComment()).equals("")) {
            btn4.setVisibility(View.INVISIBLE);
        }
        if ((arrayMoods.get(arrayMoods.size()-4).getSizeColor()) == 0){
            mView7.setText(" default mood");
        }
        float sp4 = (arrayMoods.get(arrayMoods.size()-4).getSizeColor());
        float px4 = sp4 * getResources().getDisplayMetrics().density;
        mView4.getLayoutParams().width = (int) px4;
        //margin left
        float spl4 = (arrayMoods.get(arrayMoods.size()-4).getSizeCommnent());
        float pxl4 = spl4 * getResources().getDisplayMetrics().density;
        //margin top
        float spt4 = 30;
        float pxt4 = spt4 * getResources().getDisplayMetrics().density;
        //change margins
        RelativeLayout.LayoutParams lp4 = (RelativeLayout.LayoutParams) btn4.getLayoutParams();
        lp4.setMargins((int) pxl4, (int) pxt4, 0, 0);
        btn4.setLayoutParams(lp4);

        //tree days
        mView3.setBackgroundColor(getColor(arrayMoods.get(arrayMoods.size()-3).getColor()));
        //comment no comment
        if ((arrayMoods.get(arrayMoods.size()-3).getComment()).equals("")) {
            btn3.setVisibility(View.INVISIBLE);
        }
        if ((arrayMoods.get(arrayMoods.size()-3).getSizeColor()) == 0){
            mView7.setText(" default mood");
        }
        float sp3 = (arrayMoods.get(arrayMoods.size()-3).getSizeColor());
        float px3 = sp3 * getResources().getDisplayMetrics().density;
        mView3.getLayoutParams().width = (int) px3;
        //margin left
        float spl3 = (arrayMoods.get(arrayMoods.size()-3).getSizeCommnent());
        float pxl3 = spl3 * getResources().getDisplayMetrics().density;
        //margin top
        float spt3 = 30;
        float pxt3 = spt3 * getResources().getDisplayMetrics().density;
        //change margins
        RelativeLayout.LayoutParams lp3 = (RelativeLayout.LayoutParams) btn3.getLayoutParams();
        lp3.setMargins((int) pxl3, (int) pxt3, 0, 0);
        btn3.setLayoutParams(lp3);

        //two days
        mView2.setBackgroundColor(getColor(arrayMoods.get(arrayMoods.size()-2).getColor()));
        //comment no comment
        if ((arrayMoods.get(arrayMoods.size()-2).getComment()).equals("")) {
            btn2.setVisibility(View.INVISIBLE);
        }
        if ((arrayMoods.get(arrayMoods.size()-2).getSizeColor()) == 0){
            mView7.setText(" default mood");
        }
        float sp2 = (arrayMoods.get(arrayMoods.size()-2).getSizeColor());
        float px2 = sp2 * getResources().getDisplayMetrics().density;
        mView2.getLayoutParams().width = (int) px2;
        //margin left
        float spl2 = (arrayMoods.get(arrayMoods.size()-2).getSizeCommnent());
        float pxl2 = spl2 * getResources().getDisplayMetrics().density;
        //margin top
        float spt2 = 30;
        float pxt2 = spt2 * getResources().getDisplayMetrics().density;
        //change margins
        RelativeLayout.LayoutParams lp2 = (RelativeLayout.LayoutParams) btn2.getLayoutParams();
        lp2.setMargins((int) pxl2, (int) pxt2, 0, 0);
        btn2.setLayoutParams(lp2);

        //one day
        mView1.setBackgroundColor(getColor(arrayMoods.get(arrayMoods.size()-1).getColor()));
        //comment no comment
        //if ((mMoods.get(mMoods.size()-1).getComment()) == "") {
        //   btn1.setVisibility(View.INVISIBLE);
        //    mView1.setText(" default mood");
        //}
        if ((arrayMoods.get(arrayMoods.size()-1).getComment().equals(""))) {
            btn1.setVisibility(View.INVISIBLE);
        }
        if ((arrayMoods.get(arrayMoods.size()-1).getSizeColor()) == 0){
            mView7.setText(" default mood");
        }
        float sp1 = (arrayMoods.get(arrayMoods.size()-1).getSizeColor());
        float px1 = sp1 * getResources().getDisplayMetrics().density;
        mView1.getLayoutParams().width = (int) px1;
        //margin left
        float spl1 = (arrayMoods.get(arrayMoods.size()-1).getSizeCommnent());
        float pxl1 = spl1 * getResources().getDisplayMetrics().density;
        //margin top
        float spt1 = 30;
        float pxt1 = spt1 * getResources().getDisplayMetrics().density;
        //change margins
        RelativeLayout.LayoutParams lp1 = (RelativeLayout.LayoutParams) btn1.getLayoutParams();
        lp1.setMargins((int) pxl1, (int) pxt1, 0, 0);
        btn1.setLayoutParams(lp1);

        //test toast
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //toast
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_layout));
                toast_mood = (TextView) layout.findViewById(R.id.toast_mood);
                toast_mood.setText(arrayMoods.get(arrayMoods.size()-7).getComment());
                Toast toast = new Toast(getApplicationContext());
                toast.setView(layout);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.show();
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //toast
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_layout));
                toast_mood = (TextView) layout.findViewById(R.id.toast_mood);
                toast_mood.setText(arrayMoods.get(arrayMoods.size()-6).getComment());
                Toast toast = new Toast(getApplicationContext());
                toast.setView(layout);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.show();
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //toast
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_layout));
                toast_mood = (TextView) layout.findViewById(R.id.toast_mood);
                toast_mood.setText(arrayMoods.get(arrayMoods.size()-5).getComment());
                Toast toast = new Toast(getApplicationContext());
                toast.setView(layout);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.show();
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //toast
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_layout));
                toast_mood = (TextView) layout.findViewById(R.id.toast_mood);
                toast_mood.setText(arrayMoods.get(arrayMoods.size()-4).getComment());
                Toast toast = new Toast(getApplicationContext());
                toast.setView(layout);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.show();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //toast
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_layout));
                toast_mood = (TextView) layout.findViewById(R.id.toast_mood);
                toast_mood.setText(arrayMoods.get(arrayMoods.size()-3).getComment());
                Toast toast = new Toast(getApplicationContext());
                toast.setView(layout);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.show();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //toast
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_layout));
                toast_mood = (TextView) layout.findViewById(R.id.toast_mood);
                toast_mood.setText(arrayMoods.get(arrayMoods.size()-2).getComment());
                Toast toast = new Toast(getApplicationContext());
                toast.setView(layout);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.show();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //toast
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_layout));
                toast_mood = (TextView) layout.findViewById(R.id.toast_mood);
                toast_mood.setText(arrayMoods.get(arrayMoods.size()-1).getComment());
                Toast toast = new Toast(getApplicationContext());
                toast.setView(layout);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.show();
            }
        });
        mMoodBDD.close();
    }
}
