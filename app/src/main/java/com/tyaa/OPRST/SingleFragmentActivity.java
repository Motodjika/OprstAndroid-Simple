package com.tyaa.OPRST;


import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

public abstract class SingleFragmentActivity extends FragmentActivity {
    protected abstract Fragment createFragment();
    protected abstract int getLayoutResId();
    public ErrorHandler mErrorHandler;

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mErrorHandler=new ErrorHandler();
        setContentView(getLayoutResId());
        if(isNetworkAvailable(getApplicationContext())){
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            fragment = createFragment();
            manager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .commit();
        }}else{
            showErrorToast(getString(R.string.available_network_message));
        }
    }
    protected void showErrorToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG)
                .show();
    }
    public class ErrorHandler extends Handler {
        @Override
        public void handleMessage(final Message message) {
            if(message.arg1!=0){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showErrorToast(getString(message.arg1));
                    }
                });
            }
        }
    }
}
