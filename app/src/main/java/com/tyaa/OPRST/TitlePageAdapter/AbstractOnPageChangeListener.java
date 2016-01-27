package com.tyaa.OPRST.TitlePageAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.tyaa.OPRST.R;

import java.io.Serializable;

/**
 * Created by Pavlo on 12/01/2015.
 */
public abstract class AbstractOnPageChangeListener implements ViewPager.OnPageChangeListener {
    protected Context mContext;
    protected Activity mActivity;
    protected ViewPager mViewPager;
    protected SectionsPagerAdapter mSectionsPagerAdapter;
    public AbstractOnPageChangeListener(Activity activity,ViewPager viewPager,
                                        SectionsPagerAdapter sectionsPagerAdapter){
        mActivity=activity;
        mContext=mActivity.getApplicationContext();
        mViewPager=viewPager;
        mSectionsPagerAdapter=sectionsPagerAdapter;
        onPageSelected(0);
    }

    protected class ButtonOnClickListener implements View.OnClickListener {
        private int pos;
        public ButtonOnClickListener(int position) {
            pos=position;
        }
        @Override
        public void onClick(View v) {
            mViewPager.setCurrentItem(pos);
        }
    }

    public int getPageBackground(int position) {
        switch (position) {
            case 0:
                return R.mipmap.superior2;
            case 1:
                return R.mipmap.projects2;
            case 2:
                return R.mipmap.advertising2;
            case 3:
                return R.mipmap.events2;
            case 4:
                return R.mipmap.us2;
        }
        return 0;
    }

    public int getCurrentPageBackground(int position) {
        switch (position) {
            case 0:
                return R.mipmap.superior1;
            case 1:
                return R.mipmap.projects1;
            case 2:
                return R.mipmap.advertising1;
            case 3:
                return R.mipmap.events1;
            case 4:
                return R.mipmap.us1;
        }
        return 0;
    }
    public String getPageText(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.button_nav_superior);
            case 1:
                return mContext.getString(R.string.button_nav_projects);
            case 2:
                return mContext.getString(R.string.button_nav_advertising);
            case 3:
                return mContext.getString(R.string.button_nav_events);
            case 4:
                return mContext.getString(R.string.button_nav_us);
        }
        return null;
    }
    public int getCurrentTextColor(int position) {
        switch (position) {
            case 0:
                return R.color.nav_text_sel;
            case 1:
                return R.color.nav_text_sel;
            case 2:
                return R.color.nav_text_sel;
            case 3:
                return R.color.nav_text_sel;
            case 4:
                return R.color.nav_text_sel;
        }
        return 0;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // This space for rent
    }

    @Override
    public void onPageSelected(int position) {
        // This space for rent
        int orientation=mContext.getResources().getConfiguration().orientation;
        int screen=mContext.getResources().getConfiguration().screenLayout;
        switch (orientation){
            case Configuration.ORIENTATION_LANDSCAPE:
                updateViewLandscape(position);
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                switch (screen & Configuration.SCREENLAYOUT_SIZE_MASK){
                    case Configuration.SCREENLAYOUT_SIZE_LARGE:
                        updateViewLandscape(position);
                        break;
                    default:
                        updateViewPortrait(position);
                        break;
                }
                break;
        }
    }
    public abstract void updateViewPortrait(int position);
    public abstract void updateViewLandscape(int position);

    private int previousState;
    private int currentState;

    @Override
    public void onPageScrollStateChanged(int state) {
        // This space for rent
        int currentPage = mViewPager.getCurrentItem();
        if (currentPage == mSectionsPagerAdapter.getCount() - 1 || currentPage == 0) {
            previousState = currentState;
            currentState = state;
            if (previousState == 1 && currentState == 0) {
                mViewPager.setCurrentItem(currentPage == 0 ?
                        mSectionsPagerAdapter.getCount() - 1 : 0);
            }
        }
    }
}
