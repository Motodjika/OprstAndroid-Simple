package com.tyaa.OPRST.Share;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tyaa.OPRST.R;

/**
 * Класс фрагмента экрана создания ссылки на страницу события в "социальных сетях"
 */
public class ShareFragment extends Fragment {

    private String mShareUrl;
    private WebView mShareWebView;

    private Typeface mTimesNewRomanTypeface;

    public static final String EXTRA_SHARE_URL = "com.itstep.mariupol.afishamariupol.share_url";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        mShareUrl = getActivity().getIntent().getStringExtra(EXTRA_SHARE_URL);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share, container, false);

        final ProgressBar progressBar = (ProgressBar)view.findViewById(R.id.shareProgressBar);
        progressBar.setMax(100); // значения в диапазоне 0-100
        final TextView titleTextView = (TextView)view.findViewById(R.id.shareTitleTextView);
        if (Typeface.createFromAsset(getActivity().getAssets(), "fonts/times.ttf") != null) {
            mTimesNewRomanTypeface =
                    Typeface.createFromAsset(getActivity().getAssets(), "fonts/times.ttf");
            titleTextView.setTypeface(mTimesNewRomanTypeface);
        }

        mShareWebView = (WebView) view.findViewById(R.id.shareWebView);

        mShareWebView.getSettings().setJavaScriptEnabled(true);

        mShareWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        mShareWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView webView, int progress) {
                if (progress == 100) {
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(progress);
                }
            }

            public void onReceivedTitle(WebView webView, String title) {
                titleTextView.setText(title);
            }
        });

        mShareWebView.loadUrl(mShareUrl);

        return view;
    }
}
