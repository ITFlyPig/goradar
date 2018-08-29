package com.eighteen.goradar.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eighteen.goradar.adapter.GamesAdapter;
import com.eighteen.goradar.db.AssetsDatabaseManager;
import com.eighteen.goradar.db.GamesDao;
import com.eighteen.goradar.model.GamesModel;
import com.eighteen.goradar.util.StatusBarUtil;
import com.eighteen.goradar.view.SpacesItemDecoration;

import com.eighteen.goradar.R;
import com.umeng.analytics.MobclickAgent;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.log.DeviceLog;
import com.unity3d.ads.metadata.MediationMetaData;
import com.unity3d.ads.metadata.MetaData;
import com.unity3d.ads.metadata.PlayerMetaData;
import com.unity3d.ads.misc.Utilities;
import com.unity3d.ads.webview.WebView;
import com.vungle.publisher.EventListener;
import com.vungle.publisher.VunglePub;

import java.util.ArrayList;

/**
 *
 */
public class GamesActivity extends com.eighteen.goradar.base.BaseActivity {

    Button tv_Back;
    RecyclerView recyclerView;
    LinearLayout linear_back;
    //获取db数据库
    private GamesDao gamesDao;
    private ArrayList<GamesModel> gamesModelArrayList,picGamesModelArrayList;

    final private String defaultGameId = "1243120";
    private String incentivizedPlacementId;
    private static int ordinal = 1;
    private String interstitialPlacementId="goradar18";
//    final VunglePub vunglePub = VunglePub.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);
//        FacebookSdk.sdkInitialize(getApplicationContext());
        //sdk会对日志加密，防止攻击
        MobclickAgent.enableEncrypt(true);
        //该接口默认参数是true，即采集mac地址，但如果开发者需要在googleplay发布，考虑到审核风险，可以调用该接口，参数设置为false就不会采集mac地址。
        MobclickAgent.setCheckDevice(false);
        //初始化db数据管理类
        AssetsDatabaseManager.initManager(GamesActivity.this);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.appcolor));
        // 初始化 Publisher SDK
        // 设置应用程序后在 Vungle Dashboard 的应用程序主页上获取应用程序 ID
        final String app_id = "5827b3bad6089c175300013b";
//        vunglePub.init(GamesActivity.this, app_id);
//        vunglePub.setEventListeners(vungleDefaultListener, vungleSecondListener);
        init();
    }

    private  void  init() {
        final GamesActivity self = this;
        final UnityAdsListener unityAdsListener = new UnityAdsListener();
        if(Build.VERSION.SDK_INT >= 19) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        UnityAds.setListener(unityAdsListener);
        UnityAds.setDebugMode(true);
        MediationMetaData mediationMetaData = new MediationMetaData(this);
        mediationMetaData.setName("Example mediation network");
//        mediationMetaData.setVersion("v12345");
        mediationMetaData.setVersion("1");
        mediationMetaData.commit();
        MetaData debugMetaData = new MetaData(this);
        debugMetaData.set("test.debugOverlayEnabled", true);
        debugMetaData.commit();
       //广告显示
        PlayerMetaData playerMetaData = new PlayerMetaData(self);
        playerMetaData.setServerId("goradar18");
        playerMetaData.commit();
        MediationMetaData ordinalMetaData = new MediationMetaData(self);
        ordinalMetaData.setOrdinal(ordinal++);
        ordinalMetaData.commit();
//        UnityAds.show(self, interstitialPlacementId);
        UnityAds.initialize(self, "1243120", unityAdsListener);
        Toast.makeText(GamesActivity.this,"You can play all games ,without having to download.\n" +
                "不用下載獲得，點擊直接玩遊戲\n",Toast.LENGTH_LONG).show();
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        tv_Back=(Button)findViewById(R.id.back_tv);
        linear_back=(LinearLayout)findViewById(R.id.linear_back);
        //获取的db数据库的数据
        gamesDao= new GamesDao();
        gamesModelArrayList=new ArrayList<GamesModel>();
        picGamesModelArrayList=new ArrayList<GamesModel>();
        gamesModelArrayList = gamesDao.query();

        for(int i=0;i<gamesModelArrayList.size();i++){
            GamesModel productModel  = new GamesModel();
            productModel.setImageUrl(imageId[i]);
            productModel.setGame_name(gamesModelArrayList.get(i).getGame_name());
            productModel.setUrl(gamesModelArrayList.get(i).getUrl());
            picGamesModelArrayList.add(productModel);
        }
        recyclerView.setLayoutManager(new GridLayoutManager(GamesActivity.this,3));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.item_decoration);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        recyclerView.setAdapter(new GamesAdapter(GamesActivity.this,picGamesModelArrayList));

        tv_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("tag", "onClick: ");
                finish();
                PlayerMetaData playerMetaData = new PlayerMetaData(self);
                playerMetaData.setServerId("goradar18");
                playerMetaData.commit();

                MediationMetaData ordinalMetaData = new MediationMetaData(self);
                ordinalMetaData.setOrdinal(ordinal++);
                ordinalMetaData.commit();

                UnityAds.show(self, interstitialPlacementId);

            }
        });
        linear_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("tag", "onClick: ");
                finish();
                PlayerMetaData playerMetaData = new PlayerMetaData(self);
                playerMetaData.setServerId("goradar18");
                playerMetaData.commit();
                MediationMetaData ordinalMetaData = new MediationMetaData(self);
                ordinalMetaData.setOrdinal(ordinal++);
                ordinalMetaData.commit();
                UnityAds.show(self, interstitialPlacementId);

            }
        });
    }

    private final EventListener vungleDefaultListener = new EventListener() {
        @Deprecated
        public void onVideoView(boolean isCompletedView, int watchedMillis, int videoDurationMillis) {
            // This method is deprecated and will be removed. Please use onAdEnd() instead.
        }

        @Override
        public void onAdStart() {
            // Called before playing an ad.
        }

        @Override
        public void onAdUnavailable(String reason) {
            // Called when VunglePub.playAd() was called but no ad is available to show to the user.
        }

        @Override
        public void onAdEnd(boolean wasSuccessfulView, boolean wasCallToActionClicked) {
            // Called when the user leaves the ad and control is returned to your application.
        }

        @Override
        public void onAdPlayableChanged(boolean isAdPlayable) {
            // Called when ad playability changes.
            Log.d("DefaultListener", "This is a default eventlistener.");
            final boolean enabled = isAdPlayable;

        }
    };

    private final EventListener vungleSecondListener = new EventListener() {
        // Vungle SDK allows for multiple listeners to be attached. This secondary event listener is only
        // going to print some logs for now, but it could be used to Pause music, update a badge icon, etc.
        @Deprecated
        public void onVideoView(boolean isCompletedView, int watchedMillis, int videoDurationMillis) {}

        @Override
        public void onAdStart() {}

        @Override
        public void onAdUnavailable(String reason) {}

        @Override
        public void onAdEnd(boolean wasSuccessfulView, boolean wasCallToActionClicked) {}

        @Override
        public void onAdPlayableChanged(boolean isAdPlayable) {
            Log.d("SecondListener", String.format("This is a second event listener! Ad playability has changed, and is now: %s", isAdPlayable));
        }
    };


    private class UnityAdsListener implements IUnityAdsListener {

        @Override
        public void onUnityAdsReady(final String zoneId) {
            DeviceLog.debug("onUnityAdsReady: " + zoneId);
//            toast("Ready", zoneId);
        }

        @Override
        public void onUnityAdsStart(String zoneId) {
            DeviceLog.debug("onUnityAdsStart: " + zoneId);
            Log.i("tag", "onUnityAdsStart: " + zoneId);
//            toast("Start", zoneId);
        }

        @Override
        public void onUnityAdsFinish(String zoneId, UnityAds.FinishState result) {
            DeviceLog.debug("onUnityAdsFinish: " + zoneId + " - " + result);
            Log.i("tag", "onUnityAdsFinish: " + zoneId + " - " + result);
//            toast("Finish", zoneId + " " + result);
        }

        @Override
        public void onUnityAdsError(UnityAds.UnityAdsError error, String message) {
            DeviceLog.debug("onUnityAdsError: " + error + " - " + message);
            Log.i("tag", "onUnityAdsError: "+error + " " + message);

//            vunglePub.playAd();
        }

        private void toast(String callback, String msg) {
//            Toast.makeText(getApplicationContext(), callback + ": " + msg, Toast.LENGTH_SHORT).show();
        }
    }

    int[] imageId = new int[] {
            R.drawable.l001, R.drawable.l002,R.drawable.l003,
            R.drawable.l004, R.drawable.l005,R.drawable.l006,
            R.drawable.l007, R.drawable.l008,R.drawable.l009,
    };


    @Override
    public void onPause() {
        MobclickAgent.onPause(this);
        super.onPause();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();

        MobclickAgent.onResume(this);

    }
}
