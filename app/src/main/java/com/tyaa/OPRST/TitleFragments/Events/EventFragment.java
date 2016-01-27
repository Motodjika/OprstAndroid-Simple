package com.tyaa.OPRST.TitleFragments.Events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tyaa.OPRST.CollectionActivity;
import com.tyaa.OPRST.GalleryPager.BaseContainerFragment;
import com.tyaa.OPRST.GalleryPager.PhotoContainer.PhotoContainerFragment;
import com.tyaa.OPRST.GalleryPager.VideoContainer.VideoContainerFragment;
import com.tyaa.OPRST.R;
import com.tyaa.OPRST.TitleFragments.BaseTitleFragment;

/**
 * Created by Pavlo on 12/04/2015.
 */
public class EventFragment extends BaseTitleFragment {
    private static final String TAG = "EventFragment";

    @Override
    public void onDetach(){
        super.onDetach();
        mCallbacks=null;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_event, container,
                false);
        Button mPhotoButton= (Button) v.findViewById(R.id.fragment_event_photos_button);
        Bundle args=getArguments();
        if(args!=null) {
            final Long id = args.getLong(CollectionActivity.ARG_TITLE_ID);
            mPhotoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallbacks.onFragmentNext(id, PhotoContainerFragment.class,null);
                }
            });
            Button mVideoButton = (Button) v.findViewById(R.id.fragment_event_vidos_button);
            mVideoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallbacks.onFragmentNext(id, VideoContainerFragment.class,null);
                }
            });
        }
        return v;
    }
}
