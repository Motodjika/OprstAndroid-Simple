package com.tyaa.OPRST.DataService;

/**
 * Created by Pavlo on 12/07/2015.
 */
public enum Method{
    PhotoGallery(0),
    PhotoGalleryItems(1),
    VideoGallery(2),
    VideoComments(3),
    VideoLike(4),
    SuperImportant(5);
    private final int value;
    Method(int value) {
        this.value = value;
    }
    public static Method getMethod(int value){
        for(Method method:values()){
            if(method.value==value){
                return method;
            }
        }
        return null;
    }
    public int getValue() {
        return value;
    }
}
