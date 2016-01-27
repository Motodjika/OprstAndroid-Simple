package com.tyaa.OPRST.TitleFragments.Us;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tyaa.OPRST.CollectionActivity;
import com.tyaa.OPRST.GalleryPager.BaseContainerFragment;
import com.tyaa.OPRST.R;
import com.tyaa.OPRST.TitleFragments.BaseTitleFragment;

import java.util.ArrayList;

/**
 * Created by Pavlo on 12/04/2015.
 */
public class UsFragment extends BaseTitleFragment {
    private static final String TAG = "UsFragment";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contact, container,
                false);
        return v;
    }
}
