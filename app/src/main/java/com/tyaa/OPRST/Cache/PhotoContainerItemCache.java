package com.tyaa.OPRST.Cache;

import android.content.Context;
import android.graphics.Point;

import com.tyaa.OPRST.DataService.SiteConnector;
import com.tyaa.OPRST.GalleryPager.PhotoContainer.PhotoContainerItem;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Pavlo on 11/15/2015.
 */
public class PhotoContainerItemCache extends Serializator {
    public static final String TAG = "PhotoContainerItemCache";
    private static class StringHash implements Serializable {
         public static String value;
    }
    private static final String HASH=TAG+"ids.dat";
    private Point size;
    private SiteConnector connector;
    public PhotoContainerItemCache(Context context,String filename,
                                   String width,String height){
        super(context);
        size=new Point(Integer.valueOf(width),Integer.valueOf(height));
        String extension = "";
        String file="";
        int i = filename.lastIndexOf('.');
        if (i > 0) {
            extension = filename.substring(i+1);
            file=filename.substring(0,i);
        }
        filename=file+width+"x"+ height+extension;
        setFilename(filename);

        connector=new SiteConnector();
    }
    public void put(ArrayList<PhotoContainerItem> items){
        Serialize(items);
    }
    public ArrayList<PhotoContainerItem> get(){
        ArrayList<PhotoContainerItem> items=(ArrayList<PhotoContainerItem>)Desserialize();
        if(items==null){
            items=connector.fetchPhotoItems(String.valueOf(size.x),
                    String.valueOf(size.y));
            Serialize(items);
        }else{
            String itemsIdHash=connector.fetchPhotoItemsId();
            StringHash.value = (String) Desserialize(HASH);
            if(itemsIdHash==null || StringHash.value==null ||
                    !itemsIdHash.equals(StringHash.value)) {
            items = connector.fetchPhotoItems(String.valueOf(size.x),
                    String.valueOf(size.y));
            String mHashValue="";
            for(PhotoContainerItem item:items){
                mHashValue+=item.getId();}
            StringHash.value=md5(mHashValue);
            Serialize(items);
            Serialize(StringHash.value,HASH);}
        }
        return items;
    }
}
