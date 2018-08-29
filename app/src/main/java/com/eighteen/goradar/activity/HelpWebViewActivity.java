package com.eighteen.goradar.activity;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.eighteen.goradar.R;
import com.eighteen.goradar.fragment.MapFragment;
import com.eighteen.goradar.util.Advertisement;
import com.umeng.analytics.MobclickAgent;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.log.DeviceLog;
import com.unity3d.ads.metadata.MediationMetaData;
import com.unity3d.ads.metadata.MetaData;
import com.unity3d.ads.metadata.PlayerMetaData;
import com.vungle.publisher.EventListener;
import com.vungle.publisher.VunglePub;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class HelpWebViewActivity extends Activity {
	private WebView webview;
	private String url;
	private int index;
	private ImageButton back_tv;
	private ProgressBar web_progressbar;
	ProgressBar pb;

	// TODO: 2017/5/23  unity-ads 广告
	 private String defaultGameId = "1243120";
	private String incentivizedPlacementId;
	private static int ordinal = 1;
	private String interstitialPlacementId="goradar18";
	// TODO: 2017/5/23  unity-ads 广告
	// get the VunglePub instance
	final VunglePub vunglePub = VunglePub.getInstance();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//无title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//全屏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		Bundle bundle = new Bundle();
		bundle = this.getIntent().getExtras();
		index = bundle.getInt("index");
		url = getIntent().getStringExtra("url");
		setContentView(R.layout.webview_view);
		String   addStr = getJson("GuideData.json");
		// 初始化 Publisher SDK
		// 设置应用程序后在 Vungle Dashboard 的应用程序主页上获取应用程序 ID
		final String app_id = "5827b3bad6089c175300013b";
		vunglePub.init(HelpWebViewActivity.this, app_id);

		vunglePub.setEventListeners(vungleDefaultListener, vungleSecondListener);

		findViews();
	}

	//读取方法
	public String getJson(String fileName) {

		//将json数据变成字符串
		StringBuilder stringBuilder = new StringBuilder();
		try {
			//获取assets资源管理器
			AssetManager assetManager = HelpWebViewActivity.this.getAssets();
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
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	/** Called when returning to the activity */
	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	private void findViews(){
		// TODO: 2017/5/23  unity-ads 广告
		final HelpWebViewActivity self = this;
		final UnityAdsListener unityAdsListener = new UnityAdsListener();
		if(Build.VERSION.SDK_INT >= 19) {
			com.unity3d.ads.webview.WebView.setWebContentsDebuggingEnabled(true);
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
		UnityAds.initialize(self, "1243120", unityAdsListener);
		// TODO: 2017/5/23  unity-ads 广告


		pb = (ProgressBar) findViewById(R.id.pb);
		pb.setMax(100);

		AdView mAdView = (AdView)findViewById(R.id.adView);
        //创建AdRequest 加载广告adview
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);

		webview = (WebView) findViewById(R.id.webView);

		WebSettings webSettings = webview.getSettings();
		webSettings.setSavePassword(false);
		webSettings.setSaveFormData(false);
		webSettings.setSupportZoom(false);
		webview.setWebChromeClient(new MyWebChromeClient());
		//设置WebView属性，能够执行Javascript脚本
		webview.getSettings().setJavaScriptEnabled(true);
		webview.setWebChromeClient(new MyWebChromeClient());

		//加载需要显示的网页
		webview.loadUrl(url);
		webview.setWebViewClient(new WebViewClient() {
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
		back_tv = (ImageButton)findViewById(R.id.back_tv);
		back_tv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
				PlayerMetaData playerMetaData = new PlayerMetaData(self);
				playerMetaData.setServerId(interstitialPlacementId);
				playerMetaData.commit();
				MediationMetaData ordinalMetaData = new MediationMetaData(self);
				ordinalMetaData.setOrdinal(ordinal++);
				ordinalMetaData.commit();
				UnityAds.show(self, interstitialPlacementId);

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
		}

		private void toast(String callback, String msg) {
//            Toast.makeText(getApplicationContext(), callback + ": " + msg, Toast.LENGTH_SHORT).show();
		}
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
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0){
			webview.goBack();
		}
		return super.dispatchKeyEvent(event);
	}
	//这个方法就可以监听按钮返回键或者简直返回键操作了 return false就禁止返回


	@Override
	protected void onDestroy() {
		super.onDestroy();
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


}
