package com.tyaa.OPRST.GalleryPager;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.google.gson.Gson;
import com.tyaa.OPRST.TitlePageAdapter.ActivityCallbacks;
import com.tyaa.OPRST.CollectionActivity;
import com.tyaa.OPRST.DataService.DataExecutor;
import com.tyaa.OPRST.R;
import com.tyaa.OPRST.TitlePageAdapter.FragmentCallbacks;

import java.util.ArrayList;

/**
 * Created by Pavlo on 10/20/2015.
 */
public abstract class BaseContainerFragment extends Fragment implements FragmentCallbacks {
    public static final String TAG = "BaseContainerFragment";
    protected ActivityCallbacks mCallbacks;
    protected int page;
    protected abstract void getItems();
    protected ArrayList<?> mItems;
    protected GalleryPagerAdapter mGalleryPagerAdapter;
    protected ViewPager mViewPager;
    protected GalleryPagerListener listener;
    protected Gson gson;
    protected int orientation;
    protected int count;

    public void setItems(ArrayList<?> items) {
        mItems = items;
    }

    public ActivityCallbacks getCallbacks() {
        return mCallbacks;
    }

    public void setCallbacks(ActivityCallbacks callbacks) {
        mCallbacks = callbacks;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try {
            mCallbacks = (ActivityCallbacks) context;
        }catch (ClassCastException ex){}
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks=null;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int pos = getArguments().getInt(CollectionActivity.ARG_SECTION_NUMBER);
        if (savedInstanceState != null) {
            page=savedInstanceState.getInt(GalleryPagerListener.CURRENT_PAGE_NUMBER);
            //currentControlPanelOption = savedInstanceState.getInt("currentControlPanelOption", currentControlPanelOption);
            //currentControlPanelTab = savedInstanceState.getInt("currentControlPanelTab", currentControlPanelTab);
        }
        getItems();
        //setControlPanelTabs();
        //setIconPager();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mViewPager!=null) {
            int page=mViewPager.getCurrentItem();
            final int ratio = getResources().getInteger(R.integer.gallery_items_land)/
                    getResources().getInteger(R.integer.gallery_items);
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                page=page/ratio;
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE){
                page=page*ratio+ratio/2;
            }
            outState.putInt(GalleryPagerListener.CURRENT_PAGE_NUMBER, page);
        }
        //outState.putInt("currentControlPanelOption", pager.getCurrentItem());
        //outState.putInt("currentControlPanelTab", mTabHost.getCurrentTab());
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson=new Gson();
        orientation=getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            count = getResources().getInteger(R.integer.gallery_items);
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            count = getResources().getInteger(R.integer.gallery_items_land);
        }
        //setRetainInstance(true);
    }
}
