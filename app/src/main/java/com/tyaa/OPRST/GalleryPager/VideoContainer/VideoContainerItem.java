package com.tyaa.OPRST.GalleryPager.VideoContainer;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;


import com.tyaa.OPRST.GalleryPager.PhotoContainer.PhotoContainerItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Pavlo on 10/20/2015.
 */
public class VideoContainerItem extends PhotoContainerItem {
    public static final String TAG = "VideoContainerItem";
    public static final String CAPTION = "video preview";

    public VideoContainerItem(){}

    @Override
    public String getCaption() {
        return CAPTION;
    }

    private String youtube;

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    private String project;

    public VideoContainerItem(String id, String pagetitle, String caption, String preview,
                              String our, String youtube,String project) {
        super(id,pagetitle,caption,preview,our);
        setYoutube(youtube);
    }
    public VideoContainerItem(PhotoContainerItem item, String youtube,String project){
        super(item);
        setYoutube(youtube);
        setProject(project);
    }
    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }
    public static VideoContainerItem fromJson(JSONObject object,String endPoint) {
        PhotoContainerItem item=PhotoContainerItem.fromJson(object,endPoint);
        String youtube=null,project=null;
        try {
            youtube= object.getString("youtube");
            project= object.getString("project");
            return new VideoContainerItem(item,youtube,project);
        } catch (JSONException ioj) {
            Log.i(TAG, "Get VideoGallery", ioj);
        }
        return null;
    }

    //преобразует JSON массив в массив объектов VideoContainerItem используя метод fromJson
    public static ArrayList<VideoContainerItem> videoArray(JSONArray json, String endPoint){
        ArrayList<VideoContainerItem> mCollages = new ArrayList<VideoContainerItem>();
        if(json!=null){
        try {
            for (int index = 0; index < json.length(); ++index) {
                VideoContainerItem item = VideoContainerItem.fromJson(json.
                        getJSONObject(index), endPoint);
                if (null != item) {
                    mCollages.add(item);
                }
            }
        } catch (JSONException ioj) {
            Log.e(TAG, "Failed to convert json", ioj);
        }}
        return mCollages;
    }
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(pagetitle);
        dest.writeString(preview);
        dest.writeString(thumb);
        dest.writeString(our);
        dest.writeString(youtube);
        dest.writeString(project);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static Creator<VideoContainerItem> CREATOR = new Creator<VideoContainerItem>() {
        public VideoContainerItem createFromParcel(Parcel in) {
            return new VideoContainerItem(in);
        }

        public VideoContainerItem[] newArray(int size) {
            return new VideoContainerItem[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    protected VideoContainerItem(Parcel src) {
        id = src.readString();
        pagetitle = src.readString();
        preview = src.readString();
        thumb=src.readString();
        our=src.readString();
        youtube = src.readString();
        project = src.readString();
    }
}
