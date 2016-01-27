package com.tyaa.OPRST.DataService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.tyaa.OPRST.R;
import com.tyaa.OPRST.TitlePageAdapter.ActivityCallbacks;

/**
 * Created by Pavlo on 12/13/2015.
 */
public class DataReceiver extends BroadcastReceiver {
    public final static String TAG = DataReceiver.class.getSimpleName();
    public final static String PARAM_TAG = TAG+".tag";
    public final static String PARAM_STATUS =TAG+ ".status";

    public final static int STATUS_START = 100;
    public final static int STATUS_FINISH = 200;
    public final static int STATUS_ERROR = 400;
    public final static int INTERNET_ERROR = 500;

    protected ProgressCallback mProgressCallback;
    protected ActivityCallbacks mActivityCallbacks;
    // действия при получении сообщений
    public void onReceive(Context context, Intent intent) {
        int status = intent.getIntExtra(PARAM_STATUS, 0);
        mProgressCallback = (ProgressCallback) context;
        mActivityCallbacks = (ActivityCallbacks) context;

        switch (status){
            case STATUS_START:
                mProgressCallback.onShowLoading(intent);
            break;
            case STATUS_FINISH:
                mProgressCallback.onHideLoading();
                mActivityCallbacks.sendToFragment(intent);
                break;
            case STATUS_ERROR:
                mProgressCallback.onHideLoading();
                mActivityCallbacks.getErrorHandler().createMessage(
                        R.string.error_data_obtaining);
                break;
            case INTERNET_ERROR:
                mProgressCallback.onHideLoading();
                mActivityCallbacks.getErrorHandler().createMessage(
                        R.string.available_network_message);
                break;
            default:
                mProgressCallback.onHideLoading();
                break;
        }

    }
}