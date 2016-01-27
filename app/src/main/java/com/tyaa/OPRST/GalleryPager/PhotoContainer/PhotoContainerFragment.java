package com.tyaa.OPRST.GalleryPager.PhotoContainer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;
import com.tyaa.OPRST.CollectionActivity;
import com.tyaa.OPRST.DataService.DataService;
import com.tyaa.OPRST.GalleryPager.VideoContainer.VideoContainerItem;
import com.tyaa.OPRST.TitlePageAdapter.FragmentCallbacks;
import com.tyaa.OPRST.GalleryPager.BaseContainerFragment;
import com.tyaa.OPRST.GalleryPager.GalleryPagerAdapter;
import com.tyaa.OPRST.GalleryPager.GalleryPagerListener;
import com.tyaa.OPRST.DataService.MsgHolder;
import com.tyaa.OPRST.DataService.Method;
import com.tyaa.OPRST.R;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class PhotoContainerFragment extends BaseContainerFragment implements FragmentCallbacks {
    public static final String TAG = "com.tyaa.GalleryContainer.PhotoContainerFragment.";
    public static final String EXTRA_RESULT =TAG+ "result";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gallery_photo_container, container, false);
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) v.findViewById(R.id.page_photo_gallery);
        mViewPager.setAdapter(null);
        View mPager= v.findViewById(R.id.pager_panel);
        listener = new GalleryPagerListener(mPager, mViewPager);
        mViewPager.clearOnPageChangeListeners();
        mViewPager.addOnPageChangeListener(listener);
        return v;
    }

    @Override
    public void getItems() {
        if(mViewPager!=null && mItems==null && mViewPager.getAdapter()==null){
            MsgHolder holder=new MsgHolder();
            int width=getResources().getDimensionPixelSize(R.dimen.gallery_column_width);
            int height=getResources().getDimensionPixelSize(R.dimen.gallery_column_height);
            holder.setWidth(width);
            holder.setHeight(height);
            holder.setTag(getTag());
            holder.setExtra(getTag());
            holder.setMethod(Method.PhotoGallery);
            Intent mDataIntent = new Intent(getContext(), DataService.class);
            mDataIntent.putExtra(CollectionActivity.EXTRA_RESULT, (Parcelable) holder);
            mCallbacks.startService(mDataIntent);
        }else{
            onDataObtained();
        }
    }

    public void onDataObtained() {
        if(mCallbacks!=null) {
        if(mViewPager!=null && mItems!=null) {
            mGalleryPagerAdapter = new GalleryPagerAdapter(this, PhotoGalleryPageFragment.class,
                    mItems, count);
            mViewPager.setAdapter(mGalleryPagerAdapter);
            listener.setSize(mGalleryPagerAdapter.getCount());
            if(page!=0){
                mViewPager.setCurrentItem(page);
            }
        }else{
                mCallbacks.getErrorHandler().createMessage(
                        R.string.error_video_gallery_message);
            }
        }
    }

    @Override
    public void onReceiveIntent(Intent intent) {
        String gsonString=intent.getStringExtra(getTag());
        Type listType = new TypeToken<ArrayList<PhotoContainerItem>>() {}.getType();
        mItems=gson.fromJson(gsonString, listType);
        onDataObtained();
    }
}