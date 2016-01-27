package com.tyaa.OPRST.GalleryPager;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.tyaa.OPRST.CollectionActivity;
import com.tyaa.OPRST.R;
import com.tyaa.OPRST.TitlePageAdapter.FragmentInfo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pavlo on 12/05/2015.
 */
public class GalleryPagerAdapter extends FragmentPagerAdapter {
    public static final String TAG = "GalleryPagerAdapter";
    public static final String PAGE_NUMBER = TAG+"page_number";

    private LinkedList<PageFragmentInfo> mFragments;
    private Activity mActivity;
    private Gson gson;
    private Fragment mContainer;
    private Long containerId;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mCurTransaction;
    private Fragment mCurrentPrimaryItem = null;
    public GalleryPagerAdapter(Fragment container, Class<?> clazz,ArrayList<?> items,int count){
        super(container.getChildFragmentManager());
        mFragmentManager=container.getChildFragmentManager();
        mActivity=container.getActivity();
        mContainer=container;
        containerId=mContainer.getArguments().getLong(CollectionActivity.ARG_TITLE_ID);
        gson=new Gson();
        mFragments=new LinkedList<>();
        int start=0, end=0,numberPage=0;
        for(int i=0;i<=items.size()-count;){
            start=end;
            end=i+=count;
            String title=String.valueOf(start/count + 1)+
                    mActivity.getResources().getString(R.string.page_separator2)+
                    String.valueOf(items.size()/count);
            PageFragmentInfo info=new PageFragmentInfo();
            info.setName(clazz.getName());
            info.setMsg(items.subList(start, end));
            info.setContainer(containerId);
            info.setTitle(title);
            info.setPage(numberPage++);
            mFragments.add(info);
        }
    }
    @Override
    public void startUpdate(ViewGroup container) {
        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction();
        }
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment=null;
        // Do we already have this fragment?
        PageFragmentInfo info=mFragments.get(position);
        if (info.getTag()==null) {
            fragment = getItem(position);
            String name = info.makeTag(container.getId());
            mCurTransaction.add(container.getId(), fragment, name);
        } else {
            String name = info.getTag();
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
    public Fragment createFragment(PageFragmentInfo info){
        Bundle args = new Bundle();
        args.putLong(CollectionActivity.ARG_TITLE_ID, info.getContainer());
        args.putString(PAGE_NUMBER, info.getTitle());
        args.putInt(GalleryPagerListener.CURRENT_PAGE_NUMBER, info.getPage());
        args.putString(TAG, gson.toJson(info.getMsg()));
        Fragment fragment=Fragment.instantiate(mActivity, info.getName(), args);
        return fragment;
    }
    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        PageFragmentInfo info=mFragments.get(position);
        return createFragment(info);
    }
    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return mFragments.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragments.get(position).getTitle();
    }
}
