package com.eighteen.goradar.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.eighteen.goradar.util.StatusBarUtil;
import com.eighteen.goradar.R;

import java.util.List;

public class OpenActivity extends Activity {
    private static final String TAG = "OpenActivity";
    private NativeExpressAdView adView;
    private TextView timeTextView;
    private int displaySecond = 4;
    private boolean skip = true;
    private int mTotalProgress;
    private int mCurrentProgress;
    StorageReference mStorageRef;  //mStorageRef 以前用于传输数据。
    FirebaseStorage storage = FirebaseStorage.getInstance();
    // Create a storage reference from our app
    VideoController mVideoController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.appcolor));
        adView = (NativeExpressAdView)findViewById(R.id.nativeAdView);
        adView.setVideoOptions(new VideoOptions.Builder()
                .setStartMuted(true)
                .build());
        timeTextView= (TextView) findViewById(R.id.mTasksView);

        /*timeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OpenActivity.this,MapsActivity.class);//新建一个意图，也就是跳转的界面
                startActivity(intent);//开始跳转
                finish();
            }
        });*/
        mVideoController = adView.getVideoController();
        initVariable();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // 如果正在进行下载，请保存参考，以便以后查询
        if (mStorageRef != null) {
            outState.putString("reference", mStorageRef.toString());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // 如果有一个下载进度，得到参考和创建一个新的storagereference
        final String stringRef = savedInstanceState.getString("reference");
        if (stringRef == null) {
            return;
        }
        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl(stringRef);

        // 找到所有downloadtasks下这storagereference（在这个例子中，应该有一个）
        // Find all DownloadTasks under this StorageReference (in this example, there should be one)
        List tasks = mStorageRef.getActiveDownloadTasks();

    }


    private void initVariable() {
        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);
        mVideoController.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
            @Override
            public void onVideoEnd() {
                Log.i(TAG, "Video playback is finished.");
                super.onVideoEnd();
            }
        });
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                startNormalCountDownTime(5);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                adView.setVisibility(View.GONE);
                Intent intent = new Intent(OpenActivity.this,StartActivity.class);//新建一个意图，也就是跳转的界面
                startActivity(intent);//开始跳转
                finish();

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
                Intent intent = new Intent(OpenActivity.this,MapsActivity.class);//新建一个意图，也就是跳转的界面
                startActivity(intent);//开始跳转
                finish();
            }
        };
        timer.start();

    }

}
