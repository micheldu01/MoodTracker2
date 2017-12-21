package com.michelapplication.moodtracker.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
    //relative layout
    private RelativeLayout relativeLayout7;
    private RelativeLayout relativeLayout6;
    private RelativeLayout relativeLayout5;
    private RelativeLayout relativeLayout4;
    private RelativeLayout relativeLayout3;
    private RelativeLayout relativeLayout2;
    private RelativeLayout relativeLayout1;
    //SharedPreferences
    private String saveComment;
    private int smiley;
    private long saveDay;
    //BDD and Arrays for BDD
    private MoodBDD mMoodBDD;
    private ArrayList<Mood> arrayMoods;
    // values for BDD
    private int choice_color = 1;
    private float size_color = 1;
    //Toast
    private TextView toast_mood;
    //test array mview
    private ImageButton[] arrayBtn;
    private TextView[] arrayViews;
    private RelativeLayout[] arrayRL;
    private int[] arrayRTextView = {R.id.yesterday,R.id.day_before_yesterday,R.id.three_days_ago,R.id.four_days_ago,R.id.five_days_ago,R.id.six_days_ago,R.id.a_week_ago};
    private int[] arrayRbtn = {R.id.btn_1,R.id.btn_2,R.id.btn_3,R.id.btn_4,R.id.btn_5,R.id.btn_6,R.id.btn_7};
    private int[] arrayRelativeLayout = {R.id.rl1,R.id.rl2,R.id.rl3,R.id.rl4,R.id.rl5,R.id.rl6,R.id.rl7};
    //if first connect
    private int numberarray;



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
        dayCount = 1;

        //add BDD and Arrray for BDD
        mMoodBDD = new MoodBDD(this);
        arrayMoods = new ArrayList<>();
        mMoodBDD.open();

        //if first connect
        arrayMoods = mMoodBDD.getMood();

        if(arrayMoods == null){
            numberarray = 0;
            while (numberarray<8){
                mMoodBDD.insertMood(new Mood(R.color.white, 0.0f, ""));
                numberarray++;
            }
        }

        //add smileys possibilities for get color, size color and size btn comment
        if (smiley == 0) { size_color = 0.0f; choice_color = (R.color.banana_yellow);
        }
        if (smiley == 1) { size_color = 0.2f; choice_color = (R.color.light_sage);
        }
        if (smiley == 2) { size_color = 0.5f; choice_color = (R.color.cornflower_blue_65);
        }
        if (smiley == 3) { size_color = 1f; choice_color = (R.color.warm_grey);
        }
        if (smiley == 4) { size_color = 2f; choice_color = (R.color.faded_red);
        }

        //add value in mMooBDD if dayCount != 0
        if (dayCount != 0){
            mMoodBDD.insertMood(new Mood(choice_color, size_color, saveComment));
        }
        //add void mMooBdd if dayCount > 1
        while (dayCount > 1)
        {
            mMoodBDD.insertMood(new Mood(R.color.white, 0.0f, ""));
            dayCount--;
        }

        //set BDD into array
        arrayMoods = mMoodBDD.getMood();

        //array views and btn
        arrayViews = new TextView[] {mView1, mView2, mView3, mView4, mView5, mView6, mView7};
        arrayBtn = new ImageButton[] {btn1,btn2,btn3,btn4,btn5,btn6,btn7};
        arrayRL = new RelativeLayout[] {relativeLayout1,relativeLayout2,relativeLayout3,relativeLayout4,relativeLayout5,relativeLayout6,relativeLayout7};


        //set the seven moods
        int days = 0;
        while (days<7){
            methodDays(days);
            days++;
        }
        // close BDD
        mMoodBDD.close();
    }

        // method toast
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
        //adapt number number array and bdd
        final int number_bdd = numberDay + 1;
        arrayViews[numberDay] = (TextView) findViewById(arrayRTextView[numberDay]);
        arrayBtn[numberDay] = (ImageButton) findViewById(arrayRbtn[numberDay]);
        //set comment
        arrayBtn[numberDay].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methodToast(arrayMoods.get(arrayMoods.size()-number_bdd).getComment());
            }
        });

        arrayViews[numberDay].setBackgroundColor(getColor(arrayMoods.get(arrayMoods.size()-number_bdd).getColor()));
        //if comment or no comment btn invisible and set default mood
        if (arrayMoods.get(arrayMoods.size()-number_bdd).getComment().equals("")){
            arrayBtn[numberDay].setVisibility(View.INVISIBLE);
        }
        if (arrayMoods.get(arrayMoods.size()-number_bdd).getColor() == (R.color.white)){
            arrayViews[numberDay].setText("default mood");
        }

        arrayRL[numberDay] = (RelativeLayout) findViewById(arrayRelativeLayout[numberDay]);
        LinearLayout.LayoutParams lp1 = (LinearLayout.LayoutParams) arrayRL[numberDay].getLayoutParams();
        lp1.weight = arrayMoods.get(arrayMoods.size()-number_bdd).getSizeColor();
        arrayRL[numberDay].setLayoutParams(lp1);
    }

}