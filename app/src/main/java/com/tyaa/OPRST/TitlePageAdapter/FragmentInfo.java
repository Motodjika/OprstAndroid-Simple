package com.tyaa.OPRST.TitlePageAdapter;

import java.io.Serializable;

/**
 * Created by Pavlo on 12/10/2015.
 */
public class FragmentInfo implements Serializable{
    private static final String TAG = FragmentInfo.class.getSimpleName() ;
    private String tag;
    private String name;
    private int level;
    private int pos;
    private int container;
    private long itemId;
    private Object msg;

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long makeId(int size){
        itemId=level*size+pos+1;
        return itemId;
    }
    public String makeTag(int container){
        tag=TAG + container + ":" + itemId;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getContainer() {
        return container;
    }

    public void setContainer(int container) {
        this.container = container;
    }
}
