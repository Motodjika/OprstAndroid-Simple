package com.tyaa.OPRST.GalleryPager.PhotoContainer.PhotoGallery.Photo;

import android.support.v4.app.Fragment;

import com.tyaa.OPRST.R;
import com.tyaa.OPRST.SingleFragmentActivity;

/**
 * Created by Павел on 10/11/2015.
 */
public class PhotoActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {
        PhotoFragment fragment=new PhotoFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }
}