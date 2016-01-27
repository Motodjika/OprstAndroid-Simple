package com.tyaa.OPRST.GalleryPager;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tyaa.OPRST.R;

/**
 * Created by Pavlo on 12/05/2015.
 */
public class GalleryPagerListener implements ViewPager.OnPageChangeListener{
    public static final String TAG = "GalleryPagerListener";
    public static final String CURRENT_PAGE_NUMBER=TAG+"current.page.number";
    protected View mView;
    protected ViewPager mViewPager;

    public GalleryPagerListener(View view,ViewPager viewPager){
        mView=view;
        mViewPager=viewPager;
        onPageSelected(0);
    }
    public void setSize(int size) {
        if (end != null) {
            end.setText(String.valueOf(size));
        }
    }
    ImageButton prev,next;
    Button begin,end;
    TextView current;
    private void findNavButtonsLand(View view){
        findNavButtons(view);
        begin = (Button) view.findViewById(R.id.begin_pager_button);
        end = (Button) view.findViewById(R.id.end_pager_button);
    }
    private void findNavButtons(View view){
        prev = (ImageButton) view.findViewById(R.id.prev_pager_button);
        current = (TextView) view.findViewById(R.id.navigation_pager_count);
        next = (ImageButton) view.findViewById(R.id.next_pager_button);
    }
    private void updateViewLand(final int position){
        findNavButtonsLand(mView.findViewById(R.id.pager_panel));
        begin.setText(String.valueOf(position+1));
        updateViewPortrait(position);
    }
    private void updateViewPortrait(final int position){
        findNavButtons(mView.findViewById(R.id.pager_panel));
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(position - 1);
            }
        });
        current.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String text = current.getText().toString();
                if (actionId == KeyEvent.KEYCODE_ENDCALL) {
                    int pos = -1;
                    try {
                        pos = Integer.valueOf(text);
                    } catch (NumberFormatException ex) {
                        Log.e(TAG, "Failed to convert int", ex);
                    }
                    if (pos > 0) {
                        mViewPager.setCurrentItem(pos - 1);
                    }
                } else if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    int pos = -1;
                    try {
                        pos = Integer.valueOf(text);
                    } catch (NumberFormatException ex) {
                        Log.e(TAG, "Failed to convert int", ex);
                    }
                    if (pos > 0) {
                        mViewPager.setCurrentItem(pos - 1);
                    }
                    InputMethodManager imm = (InputMethodManager) mView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mView.getWindowToken(), 0);
                    v.setText("");
                    return true;
                }
                return false;
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(position +1);
            }
        });
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(final int position) {
        int orientation=mViewPager.getResources().getConfiguration().orientation;
        int screen=mViewPager.getResources().getConfiguration().screenLayout;
        switch (orientation){
            case Configuration.ORIENTATION_LANDSCAPE:
                updateViewLand(position);
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                switch (screen & Configuration.SCREENLAYOUT_SIZE_MASK){
                    case Configuration.SCREENLAYOUT_SIZE_LARGE:
                        updateViewLand(position);
                        break;
                    default:
                        updateViewPortrait(position);
                        break;
                }
                break;
        }
        current.setText(String.valueOf(position + 1));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
