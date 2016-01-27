package com.tyaa.OPRST.TitleFragments.Advertising;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tyaa.OPRST.CollectionActivity;
import com.tyaa.OPRST.GalleryPager.BaseContainerFragment;
import com.tyaa.OPRST.R;
import com.tyaa.OPRST.TitleFragments.BaseTitleFragment;
import com.tyaa.OPRST.WebFragment;

/**
 * Created by Pavlo on 12/04/2015.
 */
public class AdvertisingFragment extends BaseTitleFragment {
    private static final String TAG =
            new RuntimeException().getStackTrace()[0].getClassName();
    public static final String EXTRA_UML_MARKET = "http://oprst.com.ua/reklama";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
    public Button mMarketButton;
    public Button mPartnersButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_market, container,
                false);


        final String uMarket= "http://oprst.com.ua/reklama";
        final String uPartners = "http://oprst.com.ua/partners.html";

        final Bundle args;
        if(getArguments()==null){
            args=new Bundle();
        }else{
            args=getArguments();
        }
        final Long id=args.getLong(CollectionActivity.ARG_TITLE_ID);

        mMarketButton=(Button)v.findViewById(R.id.MarketButton);
        mMarketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Uri UrlButton = null;
                UrlButton = Uri.parse(uMarket);

                Intent i = new Intent(Intent.ACTION_VIEW, UrlButton);
                startActivity(i);
                //mCallbacks.onFragmentNext(id,
                   //     WebFragment.class, uMarket);

            }
        });

        mPartnersButton=(Button)v.findViewById(R.id.PartnersButton);
        mPartnersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri UrlButton = null;
                UrlButton = Uri.parse(uPartners);

                Intent i = new Intent(Intent.ACTION_VIEW, UrlButton);
                startActivity(i);

                //mCallbacks.onFragmentNext(id,
                  //      WebFragment.class, uPartners);

            }
        });
        return v;
    }


}