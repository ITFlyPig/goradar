package com.eighteen.goradar.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eighteen.goradar.PreferenceUtil;
import com.eighteen.goradar.R;
import com.eighteen.goradar.model.EventModel;
import com.eighteen.goradar.util.Constant;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.umeng.analytics.MobclickAgent;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.log.DeviceLog;
import com.unity3d.ads.metadata.MediationMetaData;
import com.unity3d.ads.metadata.MetaData;
import com.unity3d.ads.metadata.PlayerMetaData;
import com.vungle.publisher.EventListener;
import com.vungle.publisher.VunglePub;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

import butterknife.BindView;

/**
 * Created by dellpc on 2016/11/5.
 */
public class MapFragment extends BaseFragment {
    public final static String TAG="MapFragment";
    @BindView(R.id.webView)
    WebView mWebView;
    //    @Bind(R.id.maps_nativeAdView)
//    NativeExpressAdView nativeExpressAdView;
    private TextView load;
    private ProgressBar pb;
    private String language,country;
    @BindView(R.id.adView)
    AdView mAdView;
    private final static String fileName = "GuideData.json";
    String addStr;
    private ImageView webview_back;
    private Dialog dialog;

    final private String defaultGameId = "1243120";
    private String incentivizedPlacementId;
    private static int ordinal = 1;
    private String interstitialPlacementId="goradar18";
    final VunglePub vunglePub = VunglePub.getInstance();
    //将读出的字符串转换成JSONobject
    String mapUrl_en ="",mapUrl_tw="",mapUrl_jp="";
    private static boolean mShowed = false;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_maps;
    }

    @Override
    protected void initData(View  v,Bundle savedInstanceState) {

        addStr = getJson(fileName);
        Log.i(TAG, "initData: "+addStr.toString());
        try {
            JSONObject jsonObject = new JSONObject(addStr);
            mapUrl_en =   jsonObject.getString("mapUrl_en");
            mapUrl_tw =   jsonObject.getString("mapUrl_zh-tw");
            mapUrl_jp =  jsonObject.getString("mapUrl_jp");
            Log.i(TAG, "mapUrl_en: "+mapUrl_en);
            Log.i(TAG, "mapUrl_tw: "+mapUrl_tw);
            Log.i(TAG, "mapUrl_jp: "+mapUrl_jp);
        }catch (Exception e){

        }
        //sdk会对日志加密，防止攻击
        MobclickAgent.enableEncrypt(true);
        //该接口默认参数是true，即采集mac地址，但如果开发者需要在googleplay发布，考虑到审核风险，可以调用该接口，参数设置为false就不会采集mac地址。
        MobclickAgent.setCheckDevice(false);


        //获取本地语言
        language = Locale.getDefault().getLanguage();
        Log.e("tag",language);
        country = getResources().getConfiguration().locale.getCountry();
        Log.e("tag","country"+country);
        mAdView=(AdView)v.findViewById(R.id.adView);
        //  创建AdRequest 加载广告横幅adview
        AdRequest adRequest = new AdRequest.Builder().build();
        // 最后，请求广告。
        mAdView.loadAd(adRequest);
        pb = (ProgressBar) v.findViewById(R.id.pb);
        pb.setMax(100);

        webview_back = (ImageView)v.findViewById(R.id.webview_back);
        load=(TextView)v.findViewById(R.id.load);
        mWebView=(WebView) v.findViewById(R.id.webView);
        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式
        // 开启 DOM storage API 功能
        mWebView.getSettings().setDomStorageEnabled(true);
        //开启 database storage API 功能
        mWebView.getSettings().setDatabaseEnabled(true);
        String cacheDirPath = getActivity().getFilesDir().getAbsolutePath()+"zzz";
        Log.i("tag", "cacheDirPath="+cacheDirPath);
        //设置数据库缓存路径
        mWebView.getSettings().setDatabasePath(cacheDirPath);
        //设置  Application Caches 缓存目录
        mWebView.getSettings().setAppCachePath(cacheDirPath);
        //开启 Application Caches 功能
        mWebView.getSettings().setAppCacheEnabled(true);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setSupportZoom(false);
        webSettings.setAppCacheEnabled(true);
        //设置 缓存模式
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        ConnectivityManager cm = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info!=null){
            if(info.isAvailable())
            {
                webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
            }else
            {
                webSettings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);//不使用网络，只加载缓存
            }
        }

        //设置WebView属性，能够执行Javascript脚本
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new MyWebChromeClient());


        webview_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MapFragment.this.getActivity(),"webview返回",Toast.LENGTH_LONG).show();
                mWebView.goBack();// 返回前一个页面
            }
        });


        // 设置应用程序后在 Vungle Dashboard 的应用程序主页上获取应用程序 ID
        final String app_id = "5827b3bad6089c175300013b";

        // 初始化 Publisher SDK
//        vunglePub.init(MapFragment.this.getActivity(), app_id);

//        vunglePub.setEventListeners(vungleDefaultListener, vungleSecondListener);

        final MapFragment.UnityAdsListener unityAdsListener = new MapFragment.UnityAdsListener();
        if(Build.VERSION.SDK_INT >= 19) {
            com.unity3d.ads.webview.WebView.setWebContentsDebuggingEnabled(true);
        }
        UnityAds.setListener(unityAdsListener);
        UnityAds.setDebugMode(true);
        MediationMetaData mediationMetaData = new MediationMetaData(MapFragment.this.getActivity());
        mediationMetaData.setName("Example mediation network");
//        mediationMetaData.setVersion("v12345");
        mediationMetaData.setVersion("1");
        mediationMetaData.commit();
        MetaData debugMetaData = new MetaData(MapFragment.this.getActivity());
        debugMetaData.set("test.debugOverlayEnabled", true);
        debugMetaData.commit();
        //广告显示
        PlayerMetaData playerMetaData = new PlayerMetaData(MapFragment.this.getActivity());
        playerMetaData.setServerId("goradar18");
        playerMetaData.commit();
        MediationMetaData ordinalMetaData = new MediationMetaData(MapFragment.this.getActivity());
        ordinalMetaData.setOrdinal(ordinal++);
        ordinalMetaData.commit();

        UnityAds.initialize(MapFragment.this.getActivity(), "1243120", unityAdsListener);
        String islock =  (String) PreferenceUtil.getInstance().get("lock","2");
        Log.i(TAG, "islock: "+islock);
//        if(!islock.equals("true")){//没有设置模式，默认随机
//            new Handler().postDelayed(new Runnable() {
//                public void run() {
//                    Log.i(TAG, "5秒到了 ");
//                    delete_music_Dialog();
//                }
//            }, 5000);
//
//        }

        //加载需要显示的网页
        if (language.equals("zh")){
            Log.i(TAG, country+"initData: "+language);
            //// TODO: 2017/5/19 台湾
            Log.i(TAG, "mapUrl_en: "+mapUrl_en);
            Log.i(TAG, "mapUrl_tw: "+mapUrl_tw);
            Log.i(TAG, "mapUrl_jp: "+mapUrl_jp);
            if(country.equals("TW")){
                Log.i(TAG, "init: "+country);
                Log.i(TAG, "init: "+mapUrl_tw);
                webview_back.setVisibility(View.VISIBLE);
                mWebView.loadUrl(mapUrl_tw);
                //// TODO: 2017/5/19 澳门
            }else if(country.equals("MO")){
                Log.i(TAG, "init: "+country);
                webview_back.setVisibility(View.VISIBLE);
                mWebView.loadUrl(mapUrl_tw);
                //// TODO: 2017/5/19 香港
            }else if(country.equals("HK")){
                Log.i(TAG, "init: "+country);
                webview_back.setVisibility(View.VISIBLE);
                mWebView.loadUrl(mapUrl_tw);
            }else{
                Log.i(TAG, "initData: "+mapUrl_en);
                mWebView.loadUrl(mapUrl_en);
                webview_back.setVisibility(View.GONE);
            }
        }else if(language.equals("en")){
            webview_back.setVisibility(View.GONE);
            //todo新加坡
            if(country.equals("SG")){
                webview_back.setVisibility(View.VISIBLE);
                mWebView.loadUrl(mapUrl_tw);
            }
        }else if(language.equals("ja")){

            webview_back.setVisibility(View.GONE);
            mWebView.loadUrl(mapUrl_jp);

        }else{
            webview_back.setVisibility(View.GONE);
            mWebView.loadUrl(mapUrl_en);
        }




        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

            }
        });

        mWebView.setWebViewClient(new WebViewClient()
        {

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url)
            {
                if (url.contains("[tag]"))
                {
                    String localPath = url.replaceFirst("^http.*[tag]\\]", "");
                    try
                    {
                        InputStream is = getActivity().getAssets().open(localPath);
                        Log.d("tag", "shouldInterceptRequest: localPath " + localPath);
                        String mimeType = "text/javascript";
                        if (localPath.endsWith("css"))
                        {
                            mimeType = "text/css";
                        }
                        return new WebResourceResponse(mimeType, "UTF-8", is);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        return null;
                    }
                }
                else {
                    return null;
                }
            }
        });
    }



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
                vunglePub.playAd();
//            toast("Error", error + " " + message);
        }

        private void toast(String callback, String msg) {
//            Toast.makeText(getApplicationContext(), callback + ": " + msg, Toast.LENGTH_SHORT).show();
        }
    }


    private TextView rating_but,watch_video_ad_but;
    private Boolean isClickrating=true,isClickads=true;

    private void delete_music_Dialog(){
        dialog= new Dialog(MapFragment.this.getActivity(),R.style.dialog);
        View view = View.inflate(MapFragment.this.getActivity(), R.layout.dialogunlock, null);
        rating_but = (TextView)view.findViewById(R.id.rating_but);
        watch_video_ad_but = (TextView)view.findViewById(R.id.watch_video_ad_but);
//        rating_but.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                isClickrating=false;
//                try {
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.eighteen.goradar")));
//                } catch (android.content.ActivityNotFoundException anfe) {
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.eighteen.goradar")));
//                }
//                if (isClickrating==false&&isClickads==false){
//                    Log.i(TAG, isClickrating+"if: "+isClickads);
//                    PreferenceUtil.getInstance().put("lock","true");
//                    dialog.dismiss();
//                }else{
//                    Log.i(TAG, isClickrating+"delete_music_Dialog: "+isClickads);
//                }
//            }
//        });
        watch_video_ad_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开始查询订阅是否有效
                EventBus.getDefault().post(new EventModel(Constant.Event.QUERY_SUB_AND_BUY));
            }
        });

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.BOTTOM);
        lp.width = LinearLayout.LayoutParams.FILL_PARENT;
        lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCancelable(false);
        //点击dialog之外的区域禁止取消dialog
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }



    DialogInterface.OnKeyListener keylistener = new DialogInterface.OnKeyListener(){
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    } ;

    //读取方法
    public String getJson(String fileName) {

        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = MapFragment.this.getActivity().getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getFragmentName());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getFragmentName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


    final class MyWebChromeClient extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            result.confirm();
            return true;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            pb.setProgress(newProgress);
            if(newProgress==100){
                pb.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }

    }


    @Override
    protected void initVariables(Bundle savedInstanceState) {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

        delete_music_Dialog();
    }

    @Override
    protected void handleUIEvent() {

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


    @Subscribe
    public void onEvent(EventModel eventModel) {
        if (eventModel == null) {
            return;
        }
        if (eventModel.code == Constant.Event.SHOW_DIALOG) {
            delete_music_Dialog();
        } else if (eventModel.code == Constant.Event.DISS_DIALOG) {
            Log.d("wly", "订阅成功，隐藏弹出框");
            if (dialog != null) {
                dialog.dismiss();
            }
        }

    }



}
