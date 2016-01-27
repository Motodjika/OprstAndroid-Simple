package com.tyaa.OPRST.GalleryPager;

import java.io.Serializable;

/**
 * Created by Pavlo on 12/10/2015.
 */
public class PageFragmentInfo implements Serializable{
    private static final String TAG = PageFragmentInfo.class.getSimpleName() ;
    private String tag;
    private String name;
    private String title;
    private int page;
    private long container;
    private Object msg;

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public String makeTag(int container){
        tag=TAG+ ":" + this.container+ ":" +container+ ":" + page;
        return tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int level) {
        this.page = level;
    }


    public long getContainer() {
        return container;
    }

    public void setContainer(long container) {
        this.container = container;
    }
}
