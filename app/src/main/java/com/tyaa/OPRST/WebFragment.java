package com.tyaa.OPRST;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.tyaa.OPRST.TitleFragments.Advertising.AdvertisingFragment;

/**
 * Created by Kitsune on 05.12.2015.
 */
public class WebFragment extends Fragment {
    public static final String TAG = "WebFragment";
    private String mUrl;
    private WebView mWebView;
    private Gson gson;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        gson = new Gson();
        String gsonString = getArguments().getString(CollectionActivity.ARG_MESSAGE);
        mUrl = gson.fromJson(gsonString,String.class);
    }
    ProgressBar progressBar;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar  =(ProgressBar)view.getRootView().
                findViewById(R.id.pbHeaderProgress);
        progressBar.setMax(100);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_market_web_view, container,false);

        mWebView =(WebView)v.findViewById(R.id.webView);

        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                mWebView.loadUrl("javascript:(function() { " +
                        "document.getElementsByTagName('header')[0].style.display='none'; "
                        +"})()");
                mWebView.loadUrl("javascript:(function() { " +
                        "document.getElementsByTagName('article')[0].style.display='none';"
                        +"})()");
                mWebView.loadUrl("javascript:(function() { " +
                       "document.getElementsByTagName('footer')[0].style.display='none';"
                        +"})()");
                mWebView.loadUrl("javascript:(function() { " +
                        "document.getElementsByTagName('ul')[0].style.display='inline-block';"
                        +"})()");
            }
        });



        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView webView, int progress){
                if(progress==100){
                    progressBar.setVisibility(View.GONE);
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(progress);
                }
            }
        });

        mWebView.loadUrl(mUrl);

        return v;
    }
}
