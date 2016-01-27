package com.tyaa.OPRST.GalleryPager.PhotoContainer;

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
import com.tyaa.OPRST.GalleryPager.PhotoContainer.PhotoGallery.PhotoGalleryFragment;
import com.tyaa.OPRST.GalleryPager.PhotoContainer.PhotoGallery.PhotoImagePageFragment;
import com.tyaa.OPRST.GalleryPager.VideoContainer.VideoContainerFragment;
import com.tyaa.OPRST.GalleryPager.VideoContainer.VideoContainerItem;
import com.tyaa.OPRST.R;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Pavlo on 12/06/2015.
 */
public class PhotoGalleryPageFragment extends AbstractGalleryPageFragment {
    public static final String TAG = "PhotoGalleryPageFragment";

    @Override
    protected AbstractListener getListener() {
        return new ClickListener();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container  ,
                             Bundle savedInstanceState) {
        View convertView = inflater.inflate(R.layout.gallery_container_item, container, false);
        gson=new Gson();
        String gsonString =  getArguments().getString(GalleryPagerAdapter.TAG);
        Type listType = new TypeToken<ArrayList<PhotoContainerItem>>() {}.getType();
        ArrayList<PhotoContainerItem> mCollages=gson.fromJson(gsonString, listType);
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
    private class ClickListener extends AbstractListener {
        private PhotoContainerItem msg;
        @Override
        //тут мы преобразуем объект, посланный в адаптере в нужный нам класс
        public void setMsg(Object msg) {
            this.msg = (PhotoContainerItem) msg;
        }

        public void onClick(View arg0) {
            //int pos = getArguments().getInt(CollectionActivity.ARG_SECTION_NUMBER);
            Bundle args = getArguments();
            if (args != null) {
                Long id=args.getLong(CollectionActivity.ARG_TITLE_ID);
                mCallbacks.onFragmentNext(id, PhotoGalleryFragment.class,msg);
            }
        }
    }
}