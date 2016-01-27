package com.tyaa.OPRST.GalleryPager.VideoContainer.VideoGallery.Video;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.tyaa.OPRST.GalleryPager.VideoContainer.VideoGallery.VideoCommentFragment;
import com.tyaa.OPRST.R;
import com.tyaa.OPRST.SingleFragmentActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Created by Павел on 10/11/2015.
 */
public class VideoActivity extends SingleFragmentActivity implements
        YouTubePlayer.OnInitializedListener,YouTubePlayer.Provider {
    private static final String TAG = "VideoActivity";
    private static final String KEY = "AIzaSyAh7ljWeYrnDb2BBEDGUEXoj7lsACOlITU";
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    private YouTubePlayerSupportFragment playerFragment=null;
    @Override
    protected Fragment createFragment() {
        if(playerFragment==null){
        playerFragment = YouTubePlayerSupportFragment.newInstance();}
        initialize(KEY, this);
        return playerFragment;
    }

    @Override
    public void initialize(String s, YouTubePlayer.OnInitializedListener onInitializedListener) {
        playerFragment.initialize(s, onInitializedListener);
    }
    private class fullscrenListener implements YouTubePlayer.OnFullscreenListener{
    public fullscrenListener(YouTubePlayer player) {
        if(player!=null){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            player.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION
                    | YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI);
            player.setFullscreen(false);
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            player.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI);
            player.setFullscreen(false);
        }}
    }
    @Override
    public void onFullscreen(boolean b) {

    }
    }
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            showErrorToast(errorReason.toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            initialize(KEY, this);
        }
    }
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {
        if (!wasRestored) {
            String url = (String) getIntent().
                    getExtras().get(VideoCommentFragment.EXTRA_RESULT);
            Document doc = Jsoup.parse(url);
            Element link = doc.select("iframe").first();
            String hlink = link.absUrl("src");
            String[] split = hlink.split("/");
            String youtube = split[split.length - 1];
            player.setOnFullscreenListener(new fullscrenListener(player));
            player.setShowFullscreenButton(false);
            player.cueVideo(youtube);
        }
    }
}
