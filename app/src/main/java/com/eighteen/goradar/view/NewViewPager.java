package com.eighteen.goradar.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by AMD on 2016/12/1.
 */
public class NewViewPager extends ViewPager {


    public NewViewPager (Context context) {
        super(context);
    }


    public NewViewPager (Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        // Not satisfied with this method of checking...
        // working on a more robust solution
        if(v.getClass().getName().equals("maps.j.b")) {
            return true;
        }
        //if(v instanceof MapView){
        //    return true;
        //}
        return super.canScroll(v, checkV, dx, x, y); }}