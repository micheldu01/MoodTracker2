package com.michelapplication.moodtracker.model;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.michelapplication.moodtracker.R;

/**
 * Created by michel on 26/10/2017.
 */

public class MpagerAdapter extends PagerAdapter {

    // variables
    private int[] layouts;
    private LayoutInflater layoutInflater;
    private Context context;



    // constructor
    public MpagerAdapter(int[] layouts, Context context)
    {
        this.layouts = layouts;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return layouts.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int  position) {
        View view = layoutInflater.inflate(layouts[position],container,false);
        container.addView(view);
        //add music
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.music_applic);
        mediaPlayer.start();
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View)object;
        container.removeView(view);
    }
}