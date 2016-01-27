package com.tyaa.OPRST;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

/**
 * Created by Pavlo on 12/13/2015.
 */
public class ErrorHandler extends Handler {
    public final static String TAG = ErrorHandler.class.getSimpleName();
    public Activity mActivity;
    public ErrorHandler(Activity activity){
        mActivity=activity;
    }
    public void createMessage(int res){
        Message mes=new Message();
        mes.arg1=res;
        handleMessage(mes);
    }
    @Override
    public void handleMessage(final Message message) {
        if(message.arg1!=0){
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showErrorToast(mActivity.getString(message.arg1));
                }
            });
        }
    }
    protected void showErrorToast(String message) {
        Toast.makeText(mActivity, message, Toast.LENGTH_LONG)
                .show();
    }
}