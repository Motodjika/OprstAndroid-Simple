package com.tyaa.OPRST.DataService;

import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.tyaa.OPRST.Cache.PhotoContainerItemCache;
import com.tyaa.OPRST.Cache.Serializator;
import com.tyaa.OPRST.Cache.VideoContainerItemCache;
import com.tyaa.OPRST.GalleryPager.PhotoContainer.PhotoContainerItem;
import com.tyaa.OPRST.GalleryPager.VideoContainer.VideoContainerItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pavlo on 12/06/2015.
 */
public class DataExecutor extends HandlerThread{
    private static final String TAG = DataExecutor.class.getSimpleName();
    private static final String PHOTO_GALLERY = TAG+".photo_gallery";
    private static final String VIDEO_GALLERY = TAG+".video_gallery";
    private Handler mWorkerHandler;
    private Map<String, MsgHolder> mRequestMap = new HashMap<>();
    private ServiceCallback mCallback;
    private Context mContext;
    private PhotoContainerItemCache mPhotoContainerItemCache;
    private VideoContainerItemCache mVideoContainerItemCache;
    private Gson gson;

    public DataExecutor(Context context, ServiceCallback callback) {
        super(TAG);
        mContext=context;
        mCallback = callback;
        gson=new Gson();
    }
    public void getData(MsgHolder message) {
        String key=message.getExtra();
        Method method=message.getMethod();
        if(!mRequestMap.containsKey(key)) {
            mRequestMap.put(key, message);
            mWorkerHandler.obtainMessage(method.getValue(),key).sendToTarget();
        }
    }
    @Override
    protected void onLooperPrepared() {
        mWorkerHandler = new Handler(getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                String key =(String)  msg.obj;
                handleRequest(key, msg.what);
                return true;
            }
        });
    }

    private void handleRequest(final String key, final int what) {
            MsgHolder msg = mRequestMap.get(key);
            Object  items=null;
            SiteConnector connector=new SiteConnector();
            try {
                //мы обращаемся к внутреннему массиву перечисления, отчёт идёт с 0
                //и не зависит от номера медода()
                Method method = Method.values()[what];
                switch (method){
                    case PhotoGallery:
                        items=getPhotoContainerItems(msg);
                        break;
                    case PhotoGalleryItems:
                        items=connector.fetchPhotoGalleryItems(msg.getId(),msg.getWidth(),msg.getHeight());
                        break;
                    case VideoGallery:
                        items=getVideoContainerItems(msg);
                        break;
                    case VideoComments:
                        items=connector.getVideoComments(msg.getId());
                        break;
                    case VideoLike:
                        items=connector.vote(msg.getUrlVote());
                        break;
                    case SuperImportant:
                        if((msg.getTag()+1).equals(msg.getExtra())){
                            items=getLatestPhoto(msg);
                        }else if((msg.getTag()+2).equals(msg.getExtra())){
                            items=getLatestVideo(msg);
                        }
                        break;
                }
                mRequestMap.remove(key);
            } catch (Exception ex) {
                Log.e(TAG, "Error data obtaining: ", ex);
            }
        mCallback.onDataObtained(msg,gson.toJson(items));
    }
    private ArrayList<PhotoContainerItem> getPhotoContainerItems(MsgHolder msg){
        mPhotoContainerItemCache=new PhotoContainerItemCache(mContext,PHOTO_GALLERY,
                msg.getWidth(),msg.getHeight());
        return mPhotoContainerItemCache.get();
    }
    private ArrayList<VideoContainerItem> getVideoContainerItems(MsgHolder msg){
        mVideoContainerItemCache=new VideoContainerItemCache(mContext,VIDEO_GALLERY,
                msg.getWidth(),msg.getHeight(),msg.getProjects());
        return mVideoContainerItemCache.get();
    }
    private ArrayList<PhotoContainerItem> getLatestPhoto(MsgHolder msg){
        mPhotoContainerItemCache=new PhotoContainerItemCache(mContext,PHOTO_GALLERY,
                msg.getWidth(),msg.getHeight());
        ArrayList<PhotoContainerItem> items=new ArrayList<>(
                mPhotoContainerItemCache.get().subList(0,3));
        return items;
    }
    private ArrayList<VideoContainerItem> getLatestVideo(MsgHolder msg){
        mVideoContainerItemCache=new VideoContainerItemCache(mContext,VIDEO_GALLERY,
                msg.getWidth(),msg.getHeight(),null);
        ArrayList<VideoContainerItem> items=new ArrayList<>(
                mVideoContainerItemCache.get().subList(0,3));
        return items;
    }
}
