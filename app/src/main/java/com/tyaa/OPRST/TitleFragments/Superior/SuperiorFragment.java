package com.tyaa.OPRST.TitleFragments.Superior;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.tyaa.OPRST.Cache.PhotoContainerItemCache;
import com.tyaa.OPRST.Cache.VideoContainerItemCache;
import com.tyaa.OPRST.CollectionActivity;
import com.tyaa.OPRST.DataService.Method;
import com.tyaa.OPRST.DataService.MsgHolder;
import com.tyaa.OPRST.DataService.DataService;
import com.tyaa.OPRST.GalleryPager.BaseContainerFragment;
import com.tyaa.OPRST.GalleryPager.GalleryPagerAdapter;
import com.tyaa.OPRST.GalleryPager.PhotoContainer.PhotoContainerFragment;
import com.tyaa.OPRST.GalleryPager.PhotoContainer.PhotoContainerItem;
import com.tyaa.OPRST.GalleryPager.VideoContainer.VideoContainerFragment;
import com.tyaa.OPRST.GalleryPager.VideoContainer.VideoContainerItem;
import com.tyaa.OPRST.R;
import com.tyaa.OPRST.TitlePageAdapter.FragmentCallbacks;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Pavlo on 12/02/2015.
 */
public class SuperiorFragment extends BaseContainerFragment {
    private static final String TAG = SuperiorFragment.class.getSimpleName();

    @Override
    protected void getItems() {
        Intent mDataIntent1 = new Intent(getContext(), DataService.class);
        MsgHolder holder=new MsgHolder();
        int width=getResources().getDimensionPixelSize(R.dimen.gallery_column_width);
        int height=getResources().getDimensionPixelSize(R.dimen.gallery_column_height);
        holder.setWidth(width);
        holder.setHeight(height);
        holder.setTag(getTag());
        holder.setExtra(getTag()+1);
        holder.setMethod(Method.SuperImportant);
        mDataIntent1.putExtra(CollectionActivity.EXTRA_RESULT, (Parcelable) holder);
        mCallbacks.startService(mDataIntent1);
        Intent mDataIntent2 = new Intent(getContext(), DataService.class);
        holder.setExtra(getTag()+2);
        mDataIntent2.putExtra(CollectionActivity.EXTRA_RESULT, (Parcelable) holder);
        mCallbacks.startService(mDataIntent1);
    }
    @Override
    public void onReceiveIntent(Intent intent) {
        String gsonString1 =  intent.getStringExtra(getTag()+1);
        if(gsonString1!=null){
            Type listType = new TypeToken<ArrayList<PhotoContainerItem>>() {}.getType();
            ArrayList<PhotoContainerItem> items=gson.fromJson(gsonString1, listType);
            fillPhotos(items);
        }

        String gsonString2 =  intent.getStringExtra(getTag()+2);
        if(gsonString2!=null){
            Type listType = new TypeToken<ArrayList<VideoContainerItem>>() {}.getType();
            ArrayList<VideoContainerItem> items=gson.fromJson(gsonString2, listType);
            fillVideos(items);
        }

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);
    }
    private ImageView imageVideoView1,imageVideoView2,imageVideoView3;
    private ImageView imagePhotoView1,imagePhotoView2,imagePhotoView3;
    private Button bVidos, bFotos;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_superior, container,
                false);
        bFotos=(Button)v.findViewById(R.id.button_photo_grid_title);
        bVidos=(Button)v.findViewById(R.id.button_video_grid_title);

        Bundle args=getArguments();
        if(args!=null) {
            final Long id = args.getLong(CollectionActivity.ARG_TITLE_ID);
            bFotos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallbacks.onFragmentNext(id, PhotoContainerFragment.class, null);
                }
            });
            bVidos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallbacks.onFragmentNext(id, VideoContainerFragment.class,null);
                }
            });
        }

        imageVideoView1 = (ImageView)v.findViewById(R.id.superior_video_view1);
        imageVideoView2 = (ImageView)v.findViewById(R.id.superior_video_view2);
        imageVideoView3 = (ImageView)v.findViewById(R.id.superior_video_view3);

        imagePhotoView1 = (ImageView) v.findViewById(R.id.superior_photo_view1);
        imagePhotoView2 = (ImageView) v.findViewById(R.id.superior_photo_view2);
        imagePhotoView3 = (ImageView) v.findViewById(R.id.superior_photo_view3);

        return v;
    }
    void fillVideos(ArrayList<VideoContainerItem> collages){
        if(collages!=null){
        Picasso.with(getContext()).load(collages.get(0).getPreview()).into(imageVideoView1);
        Picasso.with(getContext()).load(collages.get(1).getPreview()).into(imageVideoView2);
        Picasso.with(getContext()).load(collages.get(2).getPreview()).into(imageVideoView3);
    }}


    void fillPhotos(ArrayList<PhotoContainerItem> collages){
        if(collages!=null) {
            Picasso.with(getContext()).load(collages.get(0).getPreview()).into(imagePhotoView1);
            Picasso.with(getContext()).load(collages.get(1).getPreview()).into(imagePhotoView2);
            Picasso.with(getContext()).load(collages.get(2).getPreview()).into(imagePhotoView3);
        }
    }
}