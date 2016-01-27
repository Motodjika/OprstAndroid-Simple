package com.tyaa.OPRST.GalleryPager;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Pavlo on 10/18/2015.
 */
public abstract class AdapterItem implements Serializable,Parcelable {
    public AdapterItem(){}

    protected String id;
    protected String pagetitle;
    protected String preview;
    protected String thumb;

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPagetitle() {
        return pagetitle;
    }

    public void setPagetitle(String pagetitle) {
        this.pagetitle = pagetitle;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }
    public abstract String getCaption();
}