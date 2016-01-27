package com.tyaa.OPRST.GalleryPager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.tyaa.OPRST.TitlePageAdapter.ActivityCallbacks;
import com.tyaa.OPRST.R;

/**
 * Created by Pavlo on 12/05/2015.
 */
public abstract class AbstractGalleryPageFragment<T> extends Fragment {
    public static final String TAG = "AbstractGalleryPageFragment";
    abstract protected AbstractListener getListener();
    protected Gson gson=new Gson();
    protected ActivityCallbacks mCallbacks;

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
    protected void fillView(View view, AdapterItem item){
        //при повороте экрана вторая вьюшка становится равно null
        if(view!=null) {
            ImageView imageView = (ImageView) view
                    .findViewById(R.id.gallery_item_imageView);
            TextView textView1 = (TextView) view
                    .findViewById(R.id.gallery_item_title);

            textView1.setText(item.getPagetitle());

            java.lang.String url = item.getThumb();
            Picasso.with(getContext()).load(url).into(imageView);
            AbstractListener listener = getListener();
            listener.setMsg(item);
            imageView.setOnClickListener(listener);
        }
    }
}
