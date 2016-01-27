package com.tyaa.OPRST.DataService;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.tyaa.OPRST.CollectionActivity;

import java.util.ArrayList;

/**
 * Created by Pavlo on 12/13/2015.
 */
public class DataService extends Service implements ServiceCallback {
    public static final String TAG =DataService.class.getSimpleName();
    public DataService (){
        super();
        mDataExecutor = new DataExecutor(getBaseContext(),DataService.this);
        mDataExecutor.start();
    }
    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
    private DataExecutor mDataExecutor;

    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mDataExecutor.quit();
    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(isNetworkAvailable(getBaseContext())) {
            MsgHolder holder = intent.getParcelableExtra(CollectionActivity.EXTRA_RESULT);
            mDataExecutor.getData(holder);
            onShowLoading(holder.getTag());
        }else{
            onInternetError();
        }
        return START_REDELIVER_INTENT;
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDataObtained(MsgHolder msg,String jsonString) {
        Intent intent = new Intent(CollectionActivity.BROADCAST_ACTION);
        if(jsonString!=null) {
            intent.putExtra(DataReceiver.PARAM_STATUS, DataReceiver.STATUS_FINISH);
            intent.putExtra(DataReceiver.PARAM_TAG, msg.getTag());
            intent.putExtra(msg.getExtra(), jsonString);
        }else{
            intent.putExtra(DataReceiver.PARAM_STATUS, DataReceiver.STATUS_ERROR);
        }
        sendBroadcast(intent);
    }

    public void onShowLoading(String tag) {
        Intent intent = new Intent(CollectionActivity.BROADCAST_ACTION);
        intent.putExtra(DataReceiver.PARAM_TAG, tag);
        intent.putExtra(DataReceiver.PARAM_STATUS, DataReceiver.STATUS_START);
        sendBroadcast(intent);
    }
    public void onInternetError(){
        Intent intent = new Intent(CollectionActivity.BROADCAST_ACTION);
        intent.putExtra(DataReceiver.PARAM_STATUS, DataReceiver.INTERNET_ERROR);
        sendBroadcast(intent);
    }
}
