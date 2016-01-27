package com.tyaa.OPRST.GalleryPager.PhotoContainer;

import android.os.Parcel;
import android.util.Log;

import com.tyaa.OPRST.GalleryPager.AdapterItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Kitsune on 10.10.2015.
 */
public class PhotoContainerItem extends AdapterItem {
    public static final String TAG = "PhotoContainerItem";
    public static final String CAPTION = "photo preview";

    public PhotoContainerItem() {}

    @Override
    public String getCaption() {
        return CAPTION;
    }

    protected String our;
    public String isOur() {
        return our;
    }

    public void setOur(String our) {
        this.our = our;
    }

    public PhotoContainerItem(String id, String pagetitle, String preview, String thumb,
                              String our) {
        setId(id);
        setPagetitle(pagetitle);
        setPreview(preview);
        setThumb(thumb);
        setOur(our);
    }
    public PhotoContainerItem(PhotoContainerItem item){
        setId(item.id);
        setPagetitle(item.pagetitle);
        setPreview(item.preview);
        setThumb(item.thumb);
        setOur(item.our);
    }
    public static PhotoContainerItem fromJson(JSONObject object,String endPoint) {
        String id=null, pagetitle=null,preview=null,thumb=null,our=null;
        try {
            id = object.getString("id");
            pagetitle= object.getString("pagetitle");
            thumb= endPoint+object.getString("thumb");
            preview= endPoint+object.getString("preview");
            our = object.getString("our");
            return new PhotoContainerItem(id,pagetitle,preview,thumb,our);
        } catch (JSONException ioj) {
            Log.i(TAG, "Get PhotoGallery", ioj);
        }
        return null;
    }

    //преобразует JSON массив в массив объектов PhotoGalleryItem используя метод fromJson
    public static ArrayList<PhotoContainerItem> photoArray(JSONArray json, String endPoint){
        ArrayList<PhotoContainerItem> mCollages = new ArrayList<PhotoContainerItem>();
        if(json!=null){
        try {
            for (int index = 0; index < json.length(); ++index) {
                PhotoContainerItem item = PhotoContainerItem.fromJson(json.
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

    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshalled representation.
     *
     * @return a bitmask indicating the set of special object types marshalled
     * by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(pagetitle);
        dest.writeString(preview);
        dest.writeString(thumb);
        dest.writeString(our);
    }
    protected PhotoContainerItem(Parcel src) {
        id = src.readString();
        pagetitle = src.readString();
        preview = src.readString();
        thumb=src.readString();
        our=src.readString();
    }
    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static Creator<PhotoContainerItem> CREATOR = new Creator<PhotoContainerItem>() {
        public PhotoContainerItem createFromParcel(Parcel in) {
            return new PhotoContainerItem(in);
        }
        public PhotoContainerItem[] newArray(int size) {
            return new PhotoContainerItem[size];
        }
    };
}