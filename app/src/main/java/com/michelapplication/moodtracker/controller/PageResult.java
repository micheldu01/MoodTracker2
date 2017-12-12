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
    //test array mview
    private ImageButton[] arrayBtn;
    private TextView[] arrayViews;
    private int[] arrayRTextView = {R.id.yesterday,R.id.day_before_yesterday,R.id.three_days_ago,R.id.four_days_ago,R.id.five_days_ago,R.id.six_days_ago,R.id.a_week_ago};
    private int[] arrayRbtn = {R.id.btn_1,R.id.btn_2,R.id.btn_3,R.id.btn_4,R.id.btn_5,R.id.btn_6,R.id.btn_7};

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_result);

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
        //TEXT DATE
        dayCount = 1;

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

        //TEST MVIEW
        arrayViews = new TextView[] {mView1, mView2, mView3, mView4, mView5, mView6, mView7};
        arrayBtn = new ImageButton[] {btn1,btn2,btn3,btn4,btn5,btn6,btn7};

        int jours = 0;
        while (jours<7){
            methodDays(jours);
            jours++;
        }

        mMoodBDD.close();
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
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void methodDays(int numberDay){
        final int number_bdd = numberDay + 1;
        arrayViews[numberDay] = (TextView) findViewById(arrayRTextView[numberDay]);
        arrayBtn[numberDay] = (ImageButton) findViewById(arrayRbtn[numberDay]);
        arrayBtn[numberDay].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methodToast(arrayMoods.get(arrayMoods.size()-number_bdd).getComment());
            }
        });

        arrayViews[numberDay].setBackgroundColor(getColor(arrayMoods.get(arrayMoods.size()-number_bdd).getColor()));
        //comment no comment
        if (arrayMoods.get(arrayMoods.size()-number_bdd).getComment().equals("")){
            arrayBtn[numberDay].setVisibility(View.INVISIBLE);
        }
        if (arrayMoods.get(arrayMoods.size()-number_bdd).getColor() == (R.color.white)){
            arrayViews[numberDay].setText("default mood");
        }
        float sp = (arrayMoods.get(arrayMoods.size()-number_bdd).getSizeColor());
        float px = sp * getResources().getDisplayMetrics().density;
        arrayViews[numberDay].getLayoutParams().width = (int) px;
        //margin left
        float spl = (arrayMoods.get(arrayMoods.size()-number_bdd).getSizeCommnent());
        float pxl = spl * getResources().getDisplayMetrics().density;
        //margin top
        float spt = 30;
        float pxt = spt * getResources().getDisplayMetrics().density;
        //change margins
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) arrayBtn[numberDay].getLayoutParams();
        lp.setMargins((int) pxl, (int) pxt, 0, 0);
        arrayBtn[numberDay].setLayoutParams(lp);
    }
}
