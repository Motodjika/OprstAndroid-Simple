package com.tyaa.OPRST.TitleFragments;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.tyaa.OPRST.TitlePageAdapter.ActivityCallbacks;

/**
 * Created by Pavlo on 10/20/2015.
 */
public abstract class BaseTitleFragment extends Fragment{
    public static final String TAG = "BaseTitleFragment";
    protected ActivityCallbacks mCallbacks;

    public ActivityCallbacks getCallbacks() {
        return mCallbacks;
    }

    public void setCallbacks(ActivityCallbacks callbacks) {
        mCallbacks = callbacks;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try {
            mCallbacks = (ActivityCallbacks) context;
        }catch (ClassCastException ex){}
    }
    @Override
    public void onDetach(){
        super.onDetach();
        mCallbacks=null;
    }
}
