package com.michelapplication.moodtracker.model;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by michel on 06/10/2017.
 */

public class VerticalViewPager extends ViewPager {
    
    public VerticalViewPager(Context context) {
        super(context);
        init();
    }

    public VerticalViewPager(Context context, AttributeSet attrs){
        super(context, attrs);
        init();
    }

    private void init() {

        setPageTransformer(true, new VerticalPageTransformer());

        setOverScrollMode(OVER_SCROLL_NEVER);

    }

    private class VerticalPageTransformer implements PageTransformer{

        @Override
        public void transformPage(View view, float position) {

            if (position < -1 ) {

                view.setAlpha(0);
            }

            else if (position <=1){
                view.setAlpha(1);

                view.setTranslationX(view.getWidth() * -position);

                float yPosition = position * view.getHeight();
                view.setTranslationY(yPosition);

            }

            else {
                view.setAlpha(0);
            }
        }
    }

    private MotionEvent swapXY(MotionEvent ev){
        float width = getWidth();
        float height = getHeight();

        float newX = (ev.getY()/height) * width;
        float newY = (ev.getX()/width) * height;

        ev.setLocation(newX, newY);
        return ev;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = super.onInterceptTouchEvent(swapXY(ev));
        swapXY(ev);
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        return super.onTouchEvent(swapXY(ev));
    }
}
