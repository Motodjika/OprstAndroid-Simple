package com.tyaa.OPRST.GalleryPager.VideoContainer.VideoGallery;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.tyaa.OPRST.CollectionActivity;
import com.tyaa.OPRST.DataService.DataService;
import com.tyaa.OPRST.TitlePageAdapter.FragmentCallbacks;
import com.tyaa.OPRST.GalleryPager.BaseContainerFragment;
import com.tyaa.OPRST.DataService.MsgHolder;
import com.tyaa.OPRST.GalleryPager.VideoContainer.VideoContainerItem;
import com.tyaa.OPRST.GalleryPager.VideoContainer.VideoGallery.Video.VideoActivity;
import com.tyaa.OPRST.DataService.Method;
import com.tyaa.OPRST.R;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Павел on 10/11/2015.
 */
public class VideoCommentFragment extends BaseContainerFragment implements FragmentCallbacks {
    public static final String TAG = "VideoContainerFragment";
    public static final String EXTRA_RESULT = "com.tyaa.GalleryContainer.VideoContainerFragment.result";

    private GridView mGrid;
    private VideoContainerItem item;
    protected void getItems() {
        if(mGrid!=null && mItems==null && mGrid.getAdapter()==null) {
            String gsonString = getArguments().getString(CollectionActivity.ARG_MESSAGE);
            Type listType = new TypeToken<VideoContainerItem>() {}.getType();
            item=gson.fromJson(gsonString, listType);
            VideoCommentItem.setId(item.getId());
            VideoCommentItem.setPreview(item.getPreview());
            VideoCommentItem.setYoutube(item.getYoutube());
            MsgHolder holder = new MsgHolder();
            holder.setId(item.getId());
            holder.setTag(getTag());
            holder.setExtra(getTag());
            holder.setMethod(Method.VideoComments);
            Intent mDataIntent = new Intent(getContext(), DataService.class);
            mDataIntent.putExtra(CollectionActivity.EXTRA_RESULT, (Parcelable) holder);
            mCallbacks.startService(mDataIntent);
        }else{
            onDataObtained();
        }
    }

    public void onDataObtained() {
        if(mCallbacks!=null) {
            if(mGrid!=null && mItems!=null) {
                mGrid.setAdapter(new VideoCommentAdapter(this,mItems));
            }else{
                mCallbacks.getErrorHandler().createMessage(
                        R.string.error_video_gallery_message);
            }
        }
    }

    @Override
    public void onReceiveIntent(Intent intent) {
        String gsonString=intent.getStringExtra(getTag());
        Type listType = new TypeToken<ArrayList<VideoCommentItem>>() {}.getType();
        mItems=gson.fromJson(gsonString, listType);
        onDataObtained();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_comment_video, container, false);


        mGrid = (GridView)v.findViewById(R.id.itemGrid);
        mGrid.setAdapter(null);
        getItems();
        TextView textView=(TextView)v.findViewById(R.id.photoTitle);
        textView.setText(item.getPagetitle());
        ImageView imageView = (ImageView)v.findViewById(R.id.imageView);
        Picasso.with(getContext()).load(item.getPreview()).into(imageView);

        imageView.setOnClickListener(new ClickListener(item));
        return v;
    }

    private class ClickListener implements View.OnClickListener {
        private ClickListener(VideoContainerItem item){
            msg=item;
        }
        private VideoContainerItem msg;
        public void onClick(View arg0) {
            //Fragment fragment=YouTubePlayerFragment.newInstance(msg.getYoutube());
            //mCallbacks.onFragmentNext(VideoCommentFragment.this, fragment);
            Intent i = new Intent(getActivity(), VideoActivity.class);
            i.putExtra(EXTRA_RESULT, msg.getYoutube());
            getContext().startActivity(i);
        }
    }
}
