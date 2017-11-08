package com.michelapplication.moodtracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {



    private VerticalViewPager mPager;
    private int[] layouts = {R.layout.first_screen,R.layout.second_screen,
            R.layout.third_screen,R.layout.fourth_screen,R.layout.five_screen};
    private MpagerAdapter mpagerAdapter;
    private Button btn_history;
    //rajout carré blanc, edit text, et btn annule et valide
    protected Button btn_commentaire;
    protected Button btn_annuler_commentaire;
    protected Button btn_valider_commentaire;
    protected EditText edit_text_commentaire;
    protected TextView carre_blanc;
    //SharedPreferences
    private SharedPreferences mSharedPreferences;
    protected static final String MONHUMEUR = "MonHumeur";
    protected static final String HUM = "Hum";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_history = (Button)findViewById(R.id.button_history_black);
        mPager = (VerticalViewPager) findViewById(R.id.viewPager);
        mpagerAdapter = new MpagerAdapter(layouts,this);
        mPager.setAdapter(mpagerAdapter);
        mPager.setCurrentItem(1);
        //referencement carré blanc avec btn et edit text (en mode invisible)
        btn_annuler_commentaire = (Button) findViewById(R.id.btn_annuler_commentaire);
        btn_annuler_commentaire.setVisibility(View.INVISIBLE);
        btn_valider_commentaire = (Button) findViewById(R.id.btn_valider_commentaire);
        btn_valider_commentaire.setVisibility(View.INVISIBLE);
        btn_commentaire = (Button) findViewById(R.id.button_commenter);
        edit_text_commentaire = (EditText) findViewById(R.id.edit_text);
        edit_text_commentaire.setVisibility(View.INVISIBLE);
        carre_blanc = (TextView) findViewById(R.id.carre_blanc);
        carre_blanc.setVisibility(View.INVISIBLE);
        //SharedPreferences
        mSharedPreferences = getSharedPreferences(MONHUMEUR, Context.MODE_PRIVATE);


        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PageResult.class);
                startActivity(intent);

            }
        });

        btn_commentaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HideShow(true);
            }
        });

        btn_valider_commentaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HideShow(false);
                //SharedPreferences
                String h = edit_text_commentaire.getText().toString();
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putString(HUM,h);
                editor.commit();

            }
        });

        btn_annuler_commentaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               HideShow(false);
            }
        });
    }

    private void HideShow(boolean isVisible){
        int codeVisible = 0;
        if(isVisible == true){
            codeVisible = View.VISIBLE;
        }else{
            codeVisible = View.INVISIBLE;
        }
        btn_annuler_commentaire = (Button) findViewById(R.id.btn_annuler_commentaire);
        btn_annuler_commentaire.setVisibility(codeVisible);
        btn_valider_commentaire = (Button) findViewById(R.id.btn_valider_commentaire);
        btn_valider_commentaire.setVisibility(codeVisible);
        edit_text_commentaire = (EditText) findViewById(R.id.edit_text);
        edit_text_commentaire.setVisibility(codeVisible);
        carre_blanc = (TextView) findViewById(R.id.carre_blanc);
        carre_blanc.setVisibility(codeVisible);
    }
}
