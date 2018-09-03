package com.eighteen.goradar.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eighteen.goradar.R;
import com.eighteen.goradar.base.BaseActivity;
import com.eighteen.goradar.player.YoutubePlayerView;
import com.eighteen.goradar.util.PayStatusUtil;
import com.facebook.ads.Ad;
import com.facebook.ads.AdChoicesView;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.List;

public class YoutubeActivity extends BaseActivity {
    public static final String TAG=YoutubeActivity.class.getSimpleName();
    //一个页面可以播放多个视频，将所有的播放控件收集到这里进行维护，主要是控制离开页面时候的暂停
    private List<YoutubePlayerView> playerViewList;
    private View mVideoProgressView;
    private View mCustomView;//全屏显示的View
    private LinearLayout ll_player_container;
    private int mOriginalSystemUiVisibility;
    private int mOriginalOrientation;
    private WebChromeClient.CustomViewCallback mCustomViewCallback;
    private String videoUrl,title;
    //facebook
    private NativeAd nativeAd;
    private LinearLayout nativeAdContainer;
    private LinearLayout adView;
    //facebook
    private LinearLayout rll_title;
    InterstitialAd mInterstitialAd;
    private TextView title_tv;
    private ProgressBar pbLarge;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        videoUrl = getIntent().getExtras().getString("url");
        title = getIntent().getExtras().getString("title");
        title_tv = (TextView)findViewById(R.id.tv_title);
        pbLarge = (ProgressBar)findViewById(R.id.pbLarge);
        pbLarge.setVisibility(View.VISIBLE);
        title_tv.setText(title);
        showNativeAd();
        init();

    }

    private void init(){
        mAdView=(AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        // 插屏广告
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.ad_unit_id));
        AdRequest adReques = new AdRequest.Builder().build();
        mInterstitialAd.setAdListener(new com.google.android.gms.ads.AdListener() {
            @Override
            public void onAdClosed() {

            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                Log.i("tag", "onAdFailedToLoad: "+i);
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mInterstitialAd.show();
            }
        });
        mInterstitialAd.loadAd(adReques);

        rll_title=(LinearLayout)findViewById(R.id.rll_title);
        String videoID = YoutubePlayerView.parseIDfromVideoUrl(videoUrl);
        Log.i("Alex","视频的ID是=="+videoID);
        View youtubeView = LayoutInflater.from(this).inflate(R.layout.layout_youtube_player, null);
        YoutubePlayerView youtubePlayerView = (YoutubePlayerView) youtubeView.findViewById(R.id.youtubePlayerView);
        youtubePlayerView.setAutoPlayerHeight(this);
        youtubePlayerView.initialize(videoID, new YoutubePlayerCallBack(youtubePlayerView), mWebChromeClient);
        if(playerViewList == null){
            playerViewList = new ArrayList<>();
        }
        ll_player_container = (LinearLayout) findViewById(R.id.ll_player_container);
        ll_player_container.addView(youtubeView);
        playerViewList.add(youtubePlayerView);


    }

    public void onClickBack(View v){
        if (mInterstitialAd.isLoaded() && !PayStatusUtil.isSubAvailable()) {
            mInterstitialAd.show();
            finish();
        }else{
            finish();
        }
    }


    private class YoutubePlayerCallBack implements YoutubePlayerView.YouTubeListener {

        private YoutubePlayerView mYoutubeView;

        YoutubePlayerCallBack(YoutubePlayerView view){
            this.mYoutubeView = view;
            Log.i(TAG, "YoutubePlayerCallBack: ");
        }

        @Override
        public void onReady() {
            Log.i(TAG, "onReady: ");
            pbLarge.setVisibility(View.GONE);
        }

        @Override
        public void onStateChange(YoutubePlayerView.STATE state) {
            Log.i(TAG, "YoutubePlayerCallBack: "+"-----"+"onStateChange");
            if(state == YoutubePlayerView.STATE.PLAYING && mYoutubeView!=null){
                Log.i(TAG, "onStateChange: ");
                pbLarge.setVisibility(View.GONE);
                if(playerViewList!=null){
                    for(YoutubePlayerView v : playerViewList){
                        if (v != null && v != mYoutubeView && (v.getPlayerState() == YoutubePlayerView.STATE.PLAYING ||
                                v.getPlayerState() == YoutubePlayerView.STATE.PAUSED)) {
                            v.stop();
                        }
                    }
                }
            }
        }

        @Override
        public void onPlaybackQualityChange(String arg) {
            Log.i(TAG, "onPlaybackQualityChange: "+arg);
        }

        @Override
        public void onPlaybackRateChange(String arg) {
            Log.i(TAG, "onPlaybackRateChange: "+arg);
        }

        @Override
        public void onError(String arg) {
            Log.i(TAG, "onError: "+arg);
        }

        @Override
        public void onApiChange(String arg) {
            Log.i(TAG, "onApiChange: "+arg);
        }

        @Override
        public void onCurrentSecond(double second) {
            Log.i(TAG, "onCurrentSecond: "+second);
        }

        @Override
        public void onDuration(double duration) {
            Log.i(TAG, "onDuration: "+duration);
        }

        @Override
        public void logs(String log) {
            Log.i(TAG, "onDuration: "+log);
        }
    }

    private void showNativeAd() {

        nativeAd = new NativeAd(YoutubeActivity.this, "537944893042361_702877963215719");
        nativeAd.setAdListener(new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {
                Log.e(TAG, "onError: "+adError.getErrorCode()+"----"+adError.getErrorMessage());

            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Add the Ad view into the ad container.
                nativeAdContainer = (LinearLayout) findViewById(R.id.native_ad_container);
                LayoutInflater inflater = LayoutInflater.from(YoutubeActivity.this);
                adView = (LinearLayout) inflater.inflate(R.layout.native_ad_layout, nativeAdContainer, false);
                nativeAdContainer.addView(adView);

                // Create native UI using the ad metadata.
                ImageView nativeAdIcon = (ImageView) adView.findViewById(R.id.native_ad_icon);
                TextView nativeAdTitle = (TextView) adView.findViewById(R.id.native_ad_title);
                MediaView nativeAdMedia = (MediaView) adView.findViewById(R.id.native_ad_media);
                TextView nativeAdSocialContext = (TextView) adView.findViewById(R.id.native_ad_social_context);
                TextView nativeAdBody = (TextView) adView.findViewById(R.id.native_ad_body);
                Button nativeAdCallToAction = (Button) adView.findViewById(R.id.native_ad_call_to_action);

                // Set the Text.
//                nativeAdTitle.setText(nativeAd.getAdTitle());
                nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
//                nativeAdBody.setText(nativeAd.getAdBody());
                nativeAdCallToAction.setText(nativeAd.getAdCallToAction());

                // Download and display the ad icon.
                NativeAd.Image adIcon = nativeAd.getAdIcon();
//                NativeAd.downloadAndDisplayImage(adIcon, nativeAdIcon);

                // Download and display the cover image.
//                nativeAdMedia.setNativeAd(nativeAd);

                // Add the AdChoices icon
                LinearLayout adChoicesContainer = (LinearLayout) findViewById(R.id.ad_choices_container);
                AdChoicesView adChoicesView = new AdChoicesView(YoutubeActivity.this, nativeAd, true);
                adChoicesContainer.addView(adChoicesView);

                // Register the Title and CTA button to listen for clicks.
                List<View> clickableViews = new ArrayList<>();
                clickableViews.add(nativeAdTitle);
                clickableViews.add(nativeAdCallToAction);
//                nativeAd.registerViewForInteraction(nativeAdContainer, clickableViews);
//                nativeAd.loadAd(NativeAd.MediaCacheFlag.ALL);

            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });

//        nativeAd.setAdListener(new AdListener() {
//            @Override
//            public void onError(Ad ad, AdError error) {
////                Toast.makeText(YoutubeActivity.this, "onError: "+error.getErrorCode()+"----"+error.getErrorMessage(),Toast.LENGTH_LONG).show();
//                // Ad error callback
//                Log.e(TAG, "onError: "+error.getErrorCode()+"----"+error.getErrorMessage());
//            }
//
//            @Override
//            public void onAdLoaded(Ad ad) {
//
//                // Add the Ad view into the ad container.
//                nativeAdContainer = (LinearLayout) findViewById(R.id.native_ad_container);
//                LayoutInflater inflater = LayoutInflater.from(YoutubeActivity.this);
//                adView = (LinearLayout) inflater.inflate(R.layout.native_ad_layout, nativeAdContainer, false);
//                nativeAdContainer.addView(adView);
//
//                // Create native UI using the ad metadata.
//                ImageView nativeAdIcon = (ImageView) adView.findViewById(R.id.native_ad_icon);
//                TextView nativeAdTitle = (TextView) adView.findViewById(R.id.native_ad_title);
//                MediaView nativeAdMedia = (MediaView) adView.findViewById(R.id.native_ad_media);
//                TextView nativeAdSocialContext = (TextView) adView.findViewById(R.id.native_ad_social_context);
//                TextView nativeAdBody = (TextView) adView.findViewById(R.id.native_ad_body);
//                Button nativeAdCallToAction = (Button) adView.findViewById(R.id.native_ad_call_to_action);
//
//                // Set the Text.
////                nativeAdTitle.setText(nativeAd.getAdTitle());
//                nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
////                nativeAdBody.setText(nativeAd.getAdBody());
//                nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
//
//                // Download and display the ad icon.
//                NativeAd.Image adIcon = nativeAd.getAdIcon();
////                NativeAd.downloadAndDisplayImage(adIcon, nativeAdIcon);
//
//                // Download and display the cover image.
////                nativeAdMedia.setNativeAd(nativeAd);
//
//                // Add the AdChoices icon
//                LinearLayout adChoicesContainer = (LinearLayout) findViewById(R.id.ad_choices_container);
//                AdChoicesView adChoicesView = new AdChoicesView(YoutubeActivity.this, nativeAd, true);
//                adChoicesContainer.addView(adChoicesView);
//
//                // Register the Title and CTA button to listen for clicks.
//                List<View> clickableViews = new ArrayList<>();
//                clickableViews.add(nativeAdTitle);
//                clickableViews.add(nativeAdCallToAction);
////                nativeAd.registerViewForInteraction(nativeAdContainer, clickableViews);
////                nativeAd.loadAd(NativeAd.MediaCacheFlag.ALL);
//            }
//
//            @Override
//            public void onAdClicked(Ad ad) {
//                // Ad clicked callback
//                Log.e(TAG, "onAdClicked: ");
//            }
//
//            @Override
//            public void onLoggingImpression(Ad ad) {
//                // On logging impression callback
//                Log.e(TAG, "onLoggingImpression: ");
//            }
//        });

        // Request an ad
//        nativeAd.loadAd();
//        nativeAd.loadAd(NativeAd.MediaCacheFlag.ALL);
    }


    /**
     * 用于全屏显示的代码
     */
    private WebChromeClient mWebChromeClient = new WebChromeClient(){

        @Override
        public View getVideoLoadingProgressView() {
            if (mVideoProgressView == null) {
                LayoutInflater inflater = LayoutInflater.from(YoutubeActivity.this);
                mVideoProgressView = inflater.inflate(R.layout.video_layout_loading, null);
            }
            return mVideoProgressView;
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            // if a view already exists then immediately terminate the new one
            if (mCustomView != null) {
                onHideCustomView();
                return;
            }

            // 1. Stash the current state
            mCustomView = view;
            mOriginalSystemUiVisibility =getWindow().getDecorView().getSystemUiVisibility();
            mOriginalOrientation =getRequestedOrientation();
            Log.i("Alex","原来的屏幕方向是"+mOriginalOrientation);
            // 2. Stash the custom view callback
            mCustomViewCallback = callback;

            // 3. Add the custom view to the view hierarchy
            FrameLayout decor = (FrameLayout)getWindow().getDecorView();
            decor.addView(mCustomView, new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
//            if(mVideoFullScreenBack!=null){
//                mVideoFullScreenBack.setVisibility(View.VISIBLE);
//            }
            rll_title.setVisibility(View.GONE);
            // 4. Change the state of the window
             getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_IMMERSIVE);
           setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            mOriginalOrientation =getRequestedOrientation();
            Log.i("Alex","原来的屏幕方向是--"+mOriginalOrientation);
        }

        @Override
        public void onHideCustomView() {

            // 1. Remove the custom view
            FrameLayout decor = (FrameLayout)getWindow().getDecorView();
            decor.removeView(mCustomView);
            mCustomView = null;


            // 2. Restore the state to it's original form
           getWindow().getDecorView().setSystemUiVisibility(mOriginalSystemUiVisibility);
//            AlertToast("竖屏"+mOriginalOrientation);
            Log.i(TAG, "onHideCustomView: "+mOriginalOrientation);
//           setRequestedOrientation(mOriginalOrientation);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
            rll_title.setVisibility(View.VISIBLE);
            // 3. Call the custom view callback
            if(mCustomViewCallback!=null){
                mCustomViewCallback.onCustomViewHidden();
                mCustomViewCallback = null;
            }

        }
    };

    @Override
    public void onPause() {
        //视频播放器当页面停止的时候所有的视频播放全部暂停
        if(playerViewList!=null){
            for(YoutubePlayerView v : playerViewList){
                if(v.getPlayerState() == YoutubePlayerView.STATE.PLAYING ){
                    v.pause();
                }else if(v.getPlayerState() == YoutubePlayerView.STATE.BUFFERING){
                    v.stop();
                }
            }
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (playerViewList != null) {
            for (YoutubePlayerView v : playerViewList) {
                if (v != null) v.onDestroy();
            }
        }
    }
    public boolean closeFullScreen(){
        if(mCustomView!=null && mCustomViewCallback!=null){
            mWebChromeClient.onHideCustomView();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        Log.i("Alex", "进入onBackPressed方法");
        closeFullScreen();
        super.onBackPressed();
    }
}
