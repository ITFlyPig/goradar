package com.eighteen.goradar.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;
import com.eighteen.goradar.util.StatusBarUtil;
import com.eighteen.goradar.R;

import java.util.Locale;


public class StartActivity extends Activity {
    private static final String TAG = StartActivity.class.getSimpleName();
    private NativeExpressAdView adView;
    private TextView timeTextView;
    private ImageView img_start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.appcolor));
        initView();
    }

    private void initView(){
        adView = (NativeExpressAdView)findViewById(R.id.nativeAdView);
        img_start  = (ImageView) findViewById(R.id.img_start);
        timeTextView= (TextView) findViewById(R.id.mTasksView);
        //获取本地语言
        String language = Locale.getDefault().getLanguage();
        Log.e("tag",language);
        String country = getResources().getConfiguration().locale.getCountry();
        Log.e("tag","country"+country);
        if (language.equals("en")){
            adView.setVisibility(View.GONE);
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    Intent intent = new Intent(StartActivity.this,MapsActivity.class);//新建一个意图，也就是跳转的界面
                    startActivity(intent);//开始跳转
                    finish();
                }
            }, 2000);
        }else{
            img_start.setVisibility(View.GONE);
            adView.setVisibility(View.VISIBLE);
            initVariable();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // 如果正在进行下载，请保存参考，以便以后查询
    }


    private void initVariable() {
        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Log.i(TAG, "onAdLoaded: ");
                 startNormalCountDownTime(5);
            }
            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.i(TAG, "onAdFailedToLoad: ");
                adView.setVisibility(View.GONE);
                img_start.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(StartActivity.this,MapsActivity.class);//新建一个意图，也就是跳转的界面
                        startActivity(intent);//开始跳转
                        finish();
                    }
                }, 2000);
            }

            @Override
            public void onAdOpened() {

            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
            }
        });
    }

    /**
     * 倒数计时器
     * @param time
     */
    private void startNormalCountDownTime(long time) {
        /**
         * 最简单的倒计时类，实现了官方的CountDownTimer类（没有特殊要求的话可以使用）
         * 即使退出activity，倒计时还能进行，因为是创建了后台的线程。
         * 有onTick，onFinsh、cancel和start方法
         */
        CountDownTimer timer = new CountDownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d(TAG, "onTick  " + millisUntilFinished / 1000);
                timeTextView.setVisibility(View.VISIBLE);
                timeTextView.setText(getString(R.string.skip)+"  " + millisUntilFinished / 1000  );
            }

            @Override
            public void onFinish() {
                Log.d(TAG, "onFinish -- 倒计时结束");
                Intent intent = new Intent(StartActivity.this,MapsActivity.class);//新建一个意图，也就是跳转的界面
                startActivity(intent);//开始跳转
                finish();
            }
        };
        timer.start();
    }

}
