package com.tyaa.OPRST.DataService;

import android.content.Intent;

/**
 * Created by Pavlo on 12/13/2015.
 */
public interface ProgressCallback {
    void onShowLoading(Intent intent);
    void onHideLoading();
}
