package com.tyaa.OPRST.TitlePageAdapter;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Handler;

import com.tyaa.OPRST.CollectionActivity;
import com.tyaa.OPRST.DataService.DataService;
import com.tyaa.OPRST.ErrorHandler;
import com.tyaa.OPRST.GalleryPager.VideoContainer.VideoContainerItem;

/**
 * Created by Pavlo on 12/02/2015.
 */
public interface ActivityCallbacks{
    void onFragmentNext(long prev, Class<?> next, @Nullable Object msg);
    void onFragmentPrev(long next, @Nullable Object msg);
    ErrorHandler getErrorHandler();
    void sendToFragment(Intent intent);
    ComponentName startService(Intent service);
    boolean stopService(Intent name);
}
