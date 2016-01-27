package com.tyaa.OPRST.TitleFragments.Projects;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tyaa.OPRST.GalleryPager.GalleryPagerListener;
import com.tyaa.OPRST.GalleryPager.VideoContainer.VideoContainerFragment;
import com.tyaa.OPRST.R;

/**
 * Created by Pavlo on 12/12/2015.
 */
public class ProjectVideoContainer extends VideoContainerFragment {
    public static final String TAG =ProjectVideoContainer.class.getSimpleName();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gallery_video_container_projects, container, false);
        // Set up the ViewPager with the sections adapter.
        mViewPager= (ViewPager) v.findViewById(R.id.page_project_video_gallery);
        mViewPager.setAdapter(null);
        View mPager= v.findViewById(R.id.pager_panel);
        listener = new GalleryPagerListener(mPager, mViewPager);
        mViewPager.clearOnPageChangeListeners();
        mViewPager.addOnPageChangeListener(listener);

        //int page=getArguments().getInt(GalleryPagerListener.CURRENT_PAGE_NUMBER);
        return v;
    }
}
