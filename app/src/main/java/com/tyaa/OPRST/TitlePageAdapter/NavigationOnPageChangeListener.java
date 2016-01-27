package com.tyaa.OPRST.TitlePageAdapter;

import android.app.Activity;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.tyaa.OPRST.ExtendedViews.TitleTextButton;
import com.tyaa.OPRST.R;

/**
 * Created by Pavlo on 12/02/2015.
 */
public class NavigationOnPageChangeListener extends AbstractOnPageChangeListener {
        private TitleTextButton mPrevText, mCurrText, mNextText;

        public NavigationOnPageChangeListener(Activity activity, ViewPager viewPager,
                SectionsPagerAdapter sectionsPagerAdapter) {
            super(activity, viewPager, sectionsPagerAdapter);
        }
    private void findPortraitButtons(View view){
        mPrevText = (TitleTextButton) view.findViewById(R.id.prev_button);
        mCurrText = (TitleTextButton) view.findViewById(R.id.cur_button);
        mNextText = (TitleTextButton) view.findViewById(R.id.next_button);
    }

    private int currentItem = -1;

    @Override
    public void updateViewPortrait(int position) {
        findPortraitButtons(mActivity.findViewById(R.id.title_buttons));
        currentItem = position;
        int itemCount = mSectionsPagerAdapter.getCount();

        Integer color = R.color.nav_text;String text=null;
        if (currentItem >= 1) {
            text=getPageText(currentItem - 1);
            mPrevText.setOnClickListener(new AbstractOnPageChangeListener.ButtonOnClickListener(currentItem - 1));
        } else if (currentItem == 0) {
            text=getPageText(itemCount - 1);
            mPrevText.setOnClickListener(new AbstractOnPageChangeListener.ButtonOnClickListener(itemCount-1));
        }
        mPrevText.setTitleColor(color);
        mPrevText.setText(text);

        mCurrText.setTitleColor(getCurrentTextColor(currentItem));
        mCurrText.setText(getPageText(currentItem));
        mCurrText.setOnClickListener(new AbstractOnPageChangeListener.ButtonOnClickListener(currentItem));


        color = R.color.nav_text;text=null;
        if (currentItem + 1 < itemCount) {
            text=getPageText(currentItem + 1);
            mNextText.setOnClickListener(new AbstractOnPageChangeListener.ButtonOnClickListener(currentItem+1));
        } else if (currentItem + 1 == itemCount) {
            mNextText.setOnClickListener(new AbstractOnPageChangeListener.ButtonOnClickListener(0));
            text=getPageText(0);
        }
        mNextText.setTitleColor(color);
        mNextText.setText(text);
    }
    private TitleTextButton mSuperior, mProjects, mAdvertising,mEvents,mUs;
    private void findLandscapeButtons(View view){
        mSuperior = (TitleTextButton) view.findViewById(R.id.button_nav_superior);
        mProjects = (TitleTextButton) view.findViewById(R.id.button_nav_projects);
        mAdvertising = (TitleTextButton) view.findViewById(R.id.button_nav_advertising);
        mEvents = (TitleTextButton) view.findViewById(R.id.button_nav_events);
        mUs = (TitleTextButton) view.findViewById(R.id.button_nav_us);
    }
    private void setLandscapeButtonsText(){
        mSuperior.setText(mContext.getString(R.string.button_nav_superior));
        mProjects.setText(mContext.getString(R.string.button_nav_projects));
        mAdvertising.setText(mContext.getString(R.string.button_nav_advertising));
        mEvents.setText(mContext.getString(R.string.button_nav_events));
        mUs.setText(mContext.getString(R.string.button_nav_us));
    }
    private void setLandscapeButtonsListeners(){
        mSuperior.setOnClickListener(new AbstractOnPageChangeListener.ButtonOnClickListener(0));
        mProjects.setOnClickListener(new AbstractOnPageChangeListener.ButtonOnClickListener(1));
        mAdvertising.setOnClickListener(new AbstractOnPageChangeListener.ButtonOnClickListener(2));
        mEvents.setOnClickListener(new AbstractOnPageChangeListener.ButtonOnClickListener(3));
        mUs.setOnClickListener(new AbstractOnPageChangeListener.ButtonOnClickListener(4));
    }
    private void setLandscapeButtonsBackground(){
        mSuperior.setBackgroundResource(getPageBackground(0));
        mProjects.setBackgroundResource(getPageBackground(1));
        mAdvertising.setBackgroundResource(getPageBackground(2));
        mEvents.setBackgroundResource(getPageBackground(3));
        mUs.setBackgroundResource(getPageBackground(4));
    }
    private void setLandscapeButtonsTextColor(){
        mSuperior.setTitleColor(R.color.nav_text);
        mProjects.setTitleColor(R.color.nav_text);
        mAdvertising.setTitleColor(R.color.nav_text);
        mEvents.setTitleColor(R.color.nav_text);
        mEvents.setTitleColor(R.color.nav_text);
        mUs.setTitleColor(R.color.nav_text);
    }
    @Override
    public void updateViewLandscape ( int position){
            findLandscapeButtons(mActivity.findViewById(R.id.title_buttons_landscape));
        setLandscapeButtonsText();
        setLandscapeButtonsTextColor();
        setLandscapeButtonsListeners();
        //setLandscapeButtonsBackground();
        switch (position){
            case 0:
                mSuperior.setTitleColor(getCurrentTextColor(position));
                //mSuperior.setBackgroundResource(getCurrentPageBackground(position));
                break;
            case 1:
                mProjects.setTitleColor(getCurrentTextColor(position));
                //mProjects.setBackgroundResource(getCurrentPageBackground(position));
                break;
            case 2:
                mAdvertising.setTitleColor(getCurrentTextColor(position));
                //mAdvertising.setBackgroundResource(getCurrentPageBackground(position));
                break;
            case 3:
                mEvents.setTitleColor(getCurrentTextColor(position));
                //mEvents.setBackgroundResource(getCurrentPageBackground(position));
                break;
            case 4:
                mUs.setTitleColor(getCurrentTextColor(position));
                //mUs.setBackgroundResource(getCurrentPageBackground(position));
                break;
        }

    }
}
