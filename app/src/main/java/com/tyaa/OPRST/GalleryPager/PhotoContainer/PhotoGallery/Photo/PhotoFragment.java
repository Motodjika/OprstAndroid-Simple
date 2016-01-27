package com.tyaa.OPRST.GalleryPager.PhotoContainer.PhotoGallery.Photo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tyaa.OPRST.CollectionActivity;
import com.tyaa.OPRST.GalleryPager.BaseContainerFragment;
import com.tyaa.OPRST.GalleryPager.PhotoContainer.PhotoGallery.PhotoGalleryItem;
import com.tyaa.OPRST.R;

/**
 * Created by Павел on 10/11/2015.
 */
public class PhotoFragment extends Fragment {
    public static final String TAG = "com.tyaa.GalleryContainer.PhotoFragment.";
    public static final String EXTRA_RESULT =TAG + "result";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.gallery_item, container,
                false);

        ImageView imageView = (ImageView)v.findViewById(R.id.gallery_item_imageView);
        imageView.setImageResource(R.drawable.photo_default);
        TextView textView1=(TextView)v.findViewById(R.id.gallery_item_title);

        PhotoGalleryItem  item= (PhotoGalleryItem) getActivity().getIntent().
                getExtras().get(CollectionActivity.EXTRA_RESULT);

        textView1.setText(item.getPagetitle());
        Picasso.with(getContext()).load(item.getPreview()).into(imageView);
        return v;
    }

}