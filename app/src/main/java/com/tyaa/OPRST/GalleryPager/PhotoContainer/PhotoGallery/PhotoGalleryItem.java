package com.tyaa.OPRST.GalleryPager.PhotoContainer.PhotoGallery;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.tyaa.OPRST.GalleryPager.AdapterItem;
import com.tyaa.OPRST.TitlePageAdapter.AbstractOnPageChangeListener;
import com.tyaa.OPRST.GalleryPager.PhotoContainer.PhotoContainerItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Павел on 10/10/2015.
 */
public class PhotoGalleryItem extends AdapterItem {
    public static final String TAG = "PhotoGalleryItem";

    public PhotoGalleryItem(){}
    //конструктор объекта PhotoGalleryItem из контейнера
    protected PhotoGalleryItem(Parcel in) {
        preview = in.readString();
        thumb = in.readString();
    }
    //конструктор объекта PhotoGalleryItem из другого PhotoGalleryItem
    public PhotoGalleryItem(PhotoGalleryItem item){
        setPreview(item.preview);
        setThumb(item.thumb);
    }
    @Override
    public String getCaption() {
        return PhotoContainerItem.CAPTION;
    }
    public PhotoGalleryItem(String preview,String thumb) {
        setPreview(preview);
        setThumb(thumb);
    }
    public static PhotoGalleryItem fromJson(JSONObject object,String endPoint) {
        String preview=null,thumb=null;
        try {
            preview= endPoint+object.getString("preview");
            thumb= endPoint+object.getString("thumb");
            return new PhotoGalleryItem(preview,thumb);
        } catch (JSONException ioj) {
            Log.i(TAG, "Get PhotoGalleryItem", ioj);
        }
        return null;
    }

    //преобразует JSON массив в массив объектов PhotoGalleryItem используя метод fromJson
    public static ArrayList<PhotoGalleryItem> photoArray(JSONArray json, String endPoint){
        ArrayList<PhotoGalleryItem> mCollages = new ArrayList<PhotoGalleryItem>();
        if(json!=null){
        try {
            for (int index = 0; index < json.length(); ++index) {
                PhotoGalleryItem item = PhotoGalleryItem.fromJson(json.
                        getJSONObject(index), endPoint);
                if (null != item) {
                    item.setPagetitle(String.valueOf(index+1));
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

    //записываем поля PhotoGalleryItem в контейнер
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(preview);
        out.writeString(thumb);
    }

    // реализацция этих методов восстанавливает объект из контейнера
    public static Parcelable.Creator<PhotoGalleryItem> CREATOR = new Parcelable.Creator<PhotoGalleryItem>() {
        public PhotoGalleryItem createFromParcel(Parcel in) {
            return new PhotoGalleryItem(in);
        }

        public PhotoGalleryItem[] newArray(int size) {
            return new PhotoGalleryItem[size];
        }
    };
}
