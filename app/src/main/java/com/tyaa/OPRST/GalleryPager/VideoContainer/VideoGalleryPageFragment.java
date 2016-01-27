package com.tyaa.OPRST.GalleryPager.VideoContainer;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tyaa.OPRST.CollectionActivity;
import com.tyaa.OPRST.GalleryPager.AbstractGalleryPageFragment;
import com.tyaa.OPRST.GalleryPager.AbstractListener;
import com.tyaa.OPRST.GalleryPager.GalleryPagerAdapter;
import com.tyaa.OPRST.GalleryPager.VideoContainer.VideoGallery.VideoCommentFragment;
import com.tyaa.OPRST.R;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Pavlo on 12/06/2015.
 */
public class VideoGalleryPageFragment extends AbstractGalleryPageFragment {
    public static final String TAG = "VideoGalleryPageFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container  ,
                             Bundle savedInstanceState) {
        View convertView = inflater.inflate(R.layout.gallery_container_item, container, false);

        String gsonString =  getArguments().getString(GalleryPagerAdapter.TAG);
        Type listType = new TypeToken<ArrayList<VideoContainerItem>>() {}.getType();
        ArrayList<VideoContainerItem> mCollages=gson.fromJson(gsonString, listType);
        if(convertView!=null && mCollages!=null){
            if(mCollages.size()==1) {
                View view1=convertView.findViewById(R.id.gallery_item1);
                fillView(view1,mCollages.get(0));
            }else if(mCollages.size()==2){
                View view1=convertView.findViewById(R.id.gallery_item1);
                View view2=convertView.findViewById(R.id.gallery_item2);
                fillView(view1,mCollages.get(0));
                fillView(view2,mCollages.get(1));
            }}
        return convertView;
    }
    @Override
    protected AbstractListener getListener() {
        return new ClickListener();
    }

    private class ClickListener extends AbstractListener {
        private VideoContainerItem msg;

        @Override
        //тут мы преобразуем объект, посланный в адаптере в нужный нам класс
        public void setMsg(Object msg) {
            this.msg = (VideoContainerItem) msg;
        }

        public void onClick(View arg0) {
            //int pos = getArguments().getInt(CollectionActivity.ARG_SECTION_NUMBER);
            Bundle args = getArguments();
            if (args != null) {
                Long id=args.getLong(CollectionActivity.ARG_TITLE_ID);
                mCallbacks.onFragmentNext(id, VideoCommentFragment.class,msg);
            }
        }
    }
}