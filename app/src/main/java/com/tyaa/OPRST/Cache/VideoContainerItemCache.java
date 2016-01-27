package com.tyaa.OPRST.Cache;

import android.content.Context;
import android.graphics.Point;

import com.tyaa.OPRST.DataService.SiteConnector;
import com.tyaa.OPRST.GalleryPager.VideoContainer.VideoContainerItem;
import com.tyaa.OPRST.TitleFragments.Projects.Project;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Pavlo on 11/16/2015.
 */
public class VideoContainerItemCache extends Serializator {
    public static final String TAG = "VideoContainerItemCache";
    private static class StringHash implements Serializable {
        public static String value;
    }
    private static final String HASH=TAG+"ids.dat";
    private Point size;
    private SiteConnector connector;
    private Project[] mProjects;
    public VideoContainerItemCache(Context context,String filename,
                                   String width,String height,Project[] projects){
        super(context,filename);
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
        mProjects=projects;

        connector=new SiteConnector();
    }
    public void put(ArrayList<VideoContainerItem> items){
        Serialize(items);
    }
    public ArrayList<VideoContainerItem> get(){
        ArrayList<VideoContainerItem> items=(ArrayList<VideoContainerItem>)Desserialize();
        if(items==null){
            items=connector.fetchVideoItems(String.valueOf(size.x),
                    String.valueOf(size.y));
            Serialize(items);
        }else{
            String itemsIdHash=connector.fetchVideoItemId();
            StringHash.value = (String) Desserialize(HASH);
            if(itemsIdHash==null || StringHash.value==null ||
                    !itemsIdHash.equals(StringHash.value)) {
                items = connector.fetchVideoItems(String.valueOf(size.x),
                        String.valueOf(size.y));
                String mHashValue="";
                for(VideoContainerItem item:items){
                    mHashValue+=item.getId();
                }
                StringHash.value=md5(mHashValue);
                Serialize(items);
                Serialize(StringHash.value,HASH);}
        }
        ArrayList<VideoContainerItem> itemsSlice=null;
        if(mProjects!=null){
            itemsSlice=new ArrayList<>();
            for(VideoContainerItem item:items){
                for(Project project:mProjects)
                    if(project.getValue()==Integer.valueOf(item.getProject())){
                        itemsSlice.add(item);
                    }
            }
            if(itemsSlice.size()==0){items=null;}else{
                items=itemsSlice;
            }
        }
        return items;
    }
}
