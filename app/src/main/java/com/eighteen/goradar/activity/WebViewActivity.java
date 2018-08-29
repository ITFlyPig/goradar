package com.eighteen.goradar.activity;

import android.app.Activity;
import android.graphics.Bitmap;
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
import com.eighteen.goradar.util.Advertisement;
import com.eighteen.goradar.R;


public class WebViewActivity extends Activity {
	private WebView webview;
	private String url;
	private int index;
	private ImageButton back_tv;
	private ProgressBar web_progressbar;
	ProgressBar pb;

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
		findViews();
	}

	private void findViews(){
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

				try {
					//先拿到单例模式里面的 实例  因为封装的是MAP  传入KEY去拿值 在显现广告
					Advertisement.getInstance().show(getString(R.string.ad_unit_id));
				} catch (Exception e) {
					Log.d("tag", "setContentView: 显示广告失败");
				}
				finish();
			}
		});
	}

	;


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
}
