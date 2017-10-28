package com.michelapplication.moodtracker;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {



    private ViewPager mPager;
    private int[] layouts = {R.layout.first_screen,R.layout.second_screen,
            R.layout.third_screen,R.layout.fourth_screen,R.layout.five_screen};
    private MpagerAdapter mpagerAdapter;
    private Button btn_history;
    //rajout carré blanc, edit text, et btn annule et valide
    protected Button btn_annuler_commentaire;
    protected Button btn_valider_commentaire;
    protected EditText edit_text_commentaire;
    protected TextView carre_blanc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_history = (Button)findViewById(R.id.button_history_black);
        mPager = (ViewPager)findViewById(R.id.viewPager);
        mpagerAdapter = new MpagerAdapter(layouts,this);
        mPager.setAdapter(mpagerAdapter);
        mPager.setCurrentItem(1);
        //referencement carré blanc avec btn et edit text (en mode invisible)
        btn_annuler_commentaire = (Button) findViewById(R.id.btn_annuler_commentaire);
        btn_annuler_commentaire.setVisibility(View.INVISIBLE);
        btn_valider_commentaire = (Button) findViewById(R.id.btn_valider_commentaire);
        btn_valider_commentaire.setVisibility(View.INVISIBLE);
        edit_text_commentaire = (EditText) findViewById(R.id.edit_text);
        edit_text_commentaire.setVisibility(View.INVISIBLE);
        carre_blanc = (TextView) findViewById(R.id.carre_blanc);
        carre_blanc.setVisibility(View.INVISIBLE);



        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PageResult.class);
                startActivity(intent);

            }
        });

        btn_annuler_commentaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //affichage de la fenetre edit text en mode visible
                btn_annuler_commentaire = (Button) findViewById(R.id.btn_annuler_commentaire);
                btn_annuler_commentaire.setVisibility(View.VISIBLE);
                btn_valider_commentaire = (Button) findViewById(R.id.btn_valider_commentaire);
                btn_valider_commentaire.setVisibility(View.VISIBLE);
                edit_text_commentaire = (EditText) findViewById(R.id.edit_text);
                edit_text_commentaire.setVisibility(View.VISIBLE);
                carre_blanc = (TextView) findViewById(R.id.carre_blanc);
                carre_blanc.setVisibility(View.VISIBLE);
            }
        });

        btn_valider_commentaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //recupération edit text et envoi sur la page resultat
                Intent intent = new Intent(getApplicationContext(),PageResult.class);
                intent.putExtra("name",edit_text_commentaire.getText().toString());
                startActivity(intent);

                // fenetre edit text invisible
                btn_annuler_commentaire = (Button) findViewById(R.id.btn_annuler_commentaire);
                btn_annuler_commentaire.setVisibility(View.INVISIBLE);
                btn_valider_commentaire = (Button) findViewById(R.id.btn_valider_commentaire);
                btn_valider_commentaire.setVisibility(View.INVISIBLE);
                edit_text_commentaire = (EditText) findViewById(R.id.edit_text);
                edit_text_commentaire.setVisibility(View.INVISIBLE);
                carre_blanc = (TextView) findViewById(R.id.carre_blanc);
                carre_blanc.setVisibility(View.INVISIBLE);
            }
        });

        btn_annuler_commentaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_annuler_commentaire = (Button) findViewById(R.id.btn_annuler_commentaire);
                btn_annuler_commentaire.setVisibility(View.INVISIBLE);
                btn_valider_commentaire = (Button) findViewById(R.id.btn_valider_commentaire);
                btn_valider_commentaire.setVisibility(View.INVISIBLE);
                edit_text_commentaire = (EditText) findViewById(R.id.edit_text);
                edit_text_commentaire.setVisibility(View.INVISIBLE);
                carre_blanc = (TextView) findViewById(R.id.carre_blanc);
                carre_blanc.setVisibility(View.INVISIBLE);
            }
        });

    }
}
