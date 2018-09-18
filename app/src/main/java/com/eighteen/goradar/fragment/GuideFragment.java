package com.eighteen.goradar.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eighteen.goradar.R;
import com.eighteen.goradar.db.AssetsDatabaseManager;
import com.eighteen.goradar.util.Advertisement;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.umeng.analytics.MobclickAgent;

import java.util.Locale;

import butterknife.BindView;

/**
 * Created by dellpc on 2016/11/5.
 */
public class GuideFragment extends BaseFragment {
    private static final String TAG = "GuideFragment";
    @BindView(R.id.webView)
    WebView webview;
    @BindView(R.id.left)
    ImageView goback;
    @BindView(R.id.right)
    ImageView goForward;
    @BindView(R.id.adView)
    AdView mAdView;
    private String country,language;
    private TextView load;
    private LinearLayout linear_right,linear_left;
    // private View mLoadingView;
    private ProgressBar web_progressbar;
    ProgressBar pb;

    private Boolean isVisible;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_guide_view;
    }

    @Override
    protected void initData(View v,Bundle savedInstanceState) {
        AssetsDatabaseManager.initManager(getContext());

        //sdk会对日志加密，防止攻击
        MobclickAgent.enableEncrypt(true);
        //该接口默认参数是true，即采集mac地址，但如果开发者需要在googleplay发布，考虑到审核风险，可以调用该接口，参数设置为false就不会采集mac地址。
        MobclickAgent.setCheckDevice(false);


//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//                try {
//                    //先拿到单例模式里面的 实例  因为封装的是MAP  传入KEY去拿值 在显现广告
//                    Advertisement.getInstance().show(getString(R.string.ad_unit_id));
//                } catch (Exception e) {
//                    Log.d(TAG, "setContentView: 显示广告失败");
//                }
//            }
//        }, 105*1000); // 15秒   15*1000
        //获取本地语言
        language = Locale.getDefault().getLanguage();
        Log.e("tag",language);
        country = getResources().getConfiguration().locale.getCountry();
        Log.e("tag","country"+country);
        pb = (ProgressBar) v.findViewById(R.id.pb);
        pb.setMax(100);
        webview = (WebView)v.findViewById(R.id.webView);
        goback=(ImageView)v.findViewById(R.id.left);
        goForward=(ImageView)v.findViewById(R.id.right);
        linear_right=(LinearLayout) v.findViewById(R.id.linear_right);
        linear_left=(LinearLayout)v.findViewById(R.id.linear_left);
        mAdView=(AdView)v.findViewById(R.id.adView);
        load=(TextView)v.findViewById(R.id.load);


        //  创建AdRequest 加载广告横幅adview
        AdRequest  adRe = new AdRequest.Builder().build();
        mAdView.loadAd(adRe);
        //h5
        getWebContent();
    }




    private void getWebContent(){
        WebSettings webSettings = webview.getSettings();
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setSupportZoom(false);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // 开启 DOM storage API 功能
        webSettings.setDomStorageEnabled(true);
        //开启 database storage API 功能
        webSettings.setDatabaseEnabled(true);
        String cacheDirPath = getActivity().getFilesDir().getAbsolutePath()+"zzz";
        Log.i("tag", "cacheDirPath="+cacheDirPath);
        //设置数据库缓存路径
        webSettings.setDatabasePath(cacheDirPath);
        webSettings.setAppCacheMaxSize(5*1024*1024);
        //设置  Application Caches 缓存目录
        webSettings.setAppCachePath(cacheDirPath);
        //开启 Application Caches 功能
        webSettings.setAppCacheEnabled(true);
        ConnectivityManager cm = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if(info.isAvailable())
        {
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        }else
        {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);//不使用网络，只加载缓存
        }

        //设置WebView属性，能够执行Javascript脚本
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebChromeClient(new MyWebChromeClient());

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
                goback.setEnabled(webview.canGoBack());
                goForward.setEnabled(webview.canGoForward());
                if (goback.isEnabled()){
                    goback.setBackgroundResource(R.drawable.pressleft);
                }else{
                    goback.setBackgroundResource(R.drawable.defaultleft);
                }
                if (goForward.isEnabled()){
                    goForward.setBackgroundResource(R.drawable.pressright);
                }else{
                    goForward.setBackgroundResource(R.drawable.defaultright);
                }

            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (goback.isEnabled()){
                    goback.setBackgroundResource(R.drawable.pressleft);
                }else{
                    goback.setBackgroundResource(R.drawable.defaultleft);
                }
                if (goForward.isEnabled()){
                    goForward.setBackgroundResource(R.drawable.pressright);
                }else{
                    goForward.setBackgroundResource(R.drawable.defaultright);
                }
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (webview.canGoBack()){
                    goback.setBackgroundResource(R.drawable.pressleft);
                }else{
                    goback.setBackgroundResource(R.drawable.defaultleft);
                }
                if (webview.canGoForward()){
                    goForward.setBackgroundResource(R.drawable.pressright);
                }else{
                    goForward.setBackgroundResource(R.drawable.defaultright);
                }

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (webview.canGoBack()){
                    goback.setBackgroundResource(R.drawable.pressleft);
                }else{
                    goback.setBackgroundResource(R.drawable.defaultleft);
                }
                if (webview.canGoForward()){
                    goForward.setBackgroundResource(R.drawable.pressright);
                }else{
                    goForward.setBackgroundResource(R.drawable.defaultright);
                }
            }
        });

        if(language.equals("en")){
            //加载需要显示的网页
            webview.loadUrl("http://gujia.kuaizhan.com/38/38/p374752251cf12e");

        }else if(language.equals("zh")){
            webview.loadUrl("http://gujia.kuaizhan.com/14/24/p374752332faff4");

        }else if(language.equals("ja")){
            webview.loadUrl("http://gujia.kuaizhan.com/20/83/p3747524165f141");

        } else if(language.equals("fr")){
            webview.loadUrl("http://gujia.kuaizhan.com/84/38/p3747525219ccbe");

        } else if(language.equals("de")){
            webview.loadUrl("http://gujia.kuaizhan.com/8/47/p3747524649527b");

        }else if(language.equals("pt-br")){
            webview.loadUrl("http://gujia.kuaizhan.com/20/59/p374752677c1297");

        }else if(language.equals("pt-pt")){
            webview.loadUrl("http://gujia.kuaizhan.com/20/59/p374752677c1297");

        } else if(language.equals("pt-ms")){
            webview.loadUrl("http://gujia.kuaizhan.com/21/28/p37475261752530");

        }else if(language.equals("pt-th")){
            webview.loadUrl("http://gujia.kuaizhan.com/78/6/p374752572f7c70");
            ;
        }else{
            webview.loadUrl("http://gujia.kuaizhan.com/38/38/p374752251cf12e");

        }



        linear_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //前进
                if (webview.canGoForward()){
                    webview.goForward();
                }else{

                }
            }
        });
        linear_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //来判断是否能回退网页 后退
                if (webview.canGoBack()){
                    webview.goBack();
                }else{

                }
            }
        });
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //来判断是否能回退网页 后退
                if (webview.canGoBack()){
                    webview.goBack();
                }else{

                }
            }
        });
        goForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //前进
                if (webview.canGoForward()){
                    webview.goForward();
                }else{
                }
            }
        });

       /* webview.setWebViewClient(new WebViewClient()
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
        });*/
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
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisible = true;
            Log.i("tag","guide=---- true");

            new Handler().postDelayed(new Runnable() {
                public void run() {
                    try {
                        //先拿到单例模式里面的 实例  因为封装的是MAP  传入KEY去拿值 在显现广告
                        Advertisement.getInstance().show(getString(R.string.ad_unit_id));
                    } catch (Exception e) {
                        Log.d(TAG, "setContentView: 显示广告失败");
                    }
                }
            }, 5*1000); // 5秒
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    try {
                        //先拿到单例模式里面的 实例  因为封装的是MAP  传入KEY去拿值 在显现广告
                        Advertisement.getInstance().show(getString(R.string.ad_unit_id));
                    } catch (Exception e) {
                        Log.d(TAG, "setContentView: 显示广告失败");
                    }
                }
            }, 105*1000); // 5秒
        } else {
            isVisible = false;
            Log.i("tag","guide=---- false");
        }
    }


    @Override
    public void onPause() {
        super.onPause();

        MobclickAgent.onPageEnd(getFragmentName());

    }



    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getFragmentName());

        if (webview.canGoBack()){
            goback.setBackgroundResource(R.drawable.pressleft);
        }else{
            goback.setBackgroundResource(R.drawable.defaultleft);
        }
        if (webview.canGoForward()){
            goForward.setBackgroundResource(R.drawable.pressright);
        }else{
            goForward.setBackgroundResource(R.drawable.defaultright);
        }

    }


    @Override
    protected void initVariables(Bundle savedInstanceState) {

    }

    @Override
    protected void handleUIEvent() {

    }
}
