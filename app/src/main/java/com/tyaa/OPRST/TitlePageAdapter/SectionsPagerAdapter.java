package com.tyaa.OPRST.TitlePageAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.tyaa.OPRST.CollectionActivity;
import com.tyaa.OPRST.GalleryPager.GalleryPagerListener;
import com.tyaa.OPRST.R;
import com.tyaa.OPRST.TitleFragments.Advertising.AdvertisingFragment;
import com.tyaa.OPRST.TitleFragments.Events.EventFragment;
import com.tyaa.OPRST.TitleFragments.Projects.ProjectFragment;
import com.tyaa.OPRST.TitleFragments.Superior.SuperiorFragment;
import com.tyaa.OPRST.TitleFragments.Us.UsFragment;

import java.util.LinkedList;
import java.util.Objects;

/**
 * Created by Pavlo on 12/02/2015.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = SectionsPagerAdapter.class.getSimpleName() ;
    private LinkedList<LinkedList<FragmentInfo>> mFragments = null;
    private Activity mActivity;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mCurTransaction;
    private Fragment mCurrentPrimaryItem = null;
    private Gson gson;
    public LinkedList<LinkedList<FragmentInfo>> getFragments() {
        return mFragments;
    }
    public void setFragments(LinkedList<LinkedList<FragmentInfo>> fragments) {
        this.mFragments = fragments;
    }

    private FragmentInfo getLast(int position){
        return mFragments.get(position).getLast();
    }
    private ViewGroup mContainer;
    @Override
    public void startUpdate(ViewGroup container) {
        mContainer=container;
        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction();
        }
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment=null;
        // Do we already have this fragment?
        FragmentInfo last=mFragments.get(position).getLast();
        if (last.getItemId()==0) {
            fragment = getItem(position);
            String name = last.makeTag(container.getId());
            mCurTransaction.add(container.getId(), fragment, name);
        } else {
            String name = last.getTag();
            fragment = mFragmentManager.findFragmentByTag(name);
            mCurTransaction.attach(fragment);
        }
        if (fragment!=null && fragment != mCurrentPrimaryItem) {
            fragment.setMenuVisibility(false);
            fragment.setUserVisibleHint(false);
        }
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        mCurTransaction.detach((Fragment) object);
    }
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        Fragment fragment = (Fragment)object;
        if (fragment != mCurrentPrimaryItem) {
            if (mCurrentPrimaryItem != null) {
                mCurrentPrimaryItem.setMenuVisibility(false);
                mCurrentPrimaryItem.setUserVisibleHint(false);
            }
            if (fragment != null) {
                fragment.setMenuVisibility(true);
                fragment.setUserVisibleHint(true);
            }
            mCurrentPrimaryItem = fragment;
        }
    }
    @Override
    public void finishUpdate(ViewGroup container) {
        if (mCurTransaction != null) {
            mCurTransaction.commitAllowingStateLoss();
            mCurTransaction = null;
            mFragmentManager.executePendingTransactions();
        }
    }

    public SectionsPagerAdapter(Activity activity, FragmentManager fm) {
        super(fm);
        mFragmentManager=fm;
        mActivity=activity;
        gson=new Gson();
    }

    private Long removedId;
    private boolean replaceForLink(long next, Object msg){
        for(int index1=0;index1<mFragments.size();index1++)
            for(int index2=0;index2<mFragments.get(index1).size();index2++){
                FragmentInfo info=mFragments.get(index1).get(index2);
                if(next==info.getItemId()){
                    mFragments.get(index1).remove(info);
                    removedId=next;
                return true;
            }
        }
        return false;
    }
    public void goToPrev(long next, Object msg){
        if(replaceForLink(next,msg)){
            notifyDataSetChanged();
        }
    }
    private boolean addForLink(long prev, Class<?> next, Object msg){
        for(LinkedList<FragmentInfo> list: mFragments){
            for(FragmentInfo info:list){
                if(prev==info.getItemId()){
                    int level = list.indexOf(info);
                    int position=mFragments.indexOf(list);
                    FragmentInfo newInfo=new FragmentInfo();
                    newInfo.setLevel(level + 1);
                    newInfo.setName(next.getName());
                    newInfo.setPos(position);
                    newInfo.setMsg(msg);
                    list.add(newInfo);
                    removedId=prev;
                    return true;
                }}
        }
        return false;
    }
    public void goToNext(long prev, Class<?> next, Object msg){
        if(addForLink(prev, next,msg)){
            notifyDataSetChanged();
        }
    }
    public long getItemId(int position) {
        return  mFragments.get(position).getLast().getItemId();
    }
    @Override
    public int getItemPosition(Object object) {
        Fragment fragment=(Fragment)object;
        Long id=fragment.getArguments().getLong(CollectionActivity.ARG_TITLE_ID);
       if(id!=0 && id==removedId){
           removedId=0L;
           return POSITION_NONE;
       }
        return POSITION_UNCHANGED;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        FragmentInfo info = mFragments.get(position).getLast();
        Fragment fragment=createFragment(info);
        return fragment;
    }
    public Fragment createFragment(FragmentInfo info){
        Bundle args=createArgs(info);
        Fragment fragment=Fragment.instantiate(mActivity, info.getName(), args);
        return fragment;
    }
    private Bundle createArgs(FragmentInfo info){
        Bundle args = new Bundle();
        info.makeId(mFragments.size());
        args.putInt(CollectionActivity.ARG_SECTION_NUMBER, info.getPos());
        args.putInt(CollectionActivity.ARG_LEVEL_NUMBER, info.getLevel());
        args.putLong(CollectionActivity.ARG_TITLE_ID, info.getItemId());
        args.putString(CollectionActivity.ARG_MESSAGE, gson.toJson(info.getMsg()));
        return args;
    }
    @Override
    public int getCount() {
        // Show total pages.
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mActivity.getString(R.string.button_nav_superior);
            case 1:
                return  mActivity.getString(R.string.button_nav_projects);
            case 2:
                return  mActivity.getString(R.string.button_nav_advertising);
            case 3:
                return  mActivity.getString(R.string.button_nav_events);
            case 4:
                return  mActivity.getString(R.string.button_nav_us);
        }
        return null;
    }
}
