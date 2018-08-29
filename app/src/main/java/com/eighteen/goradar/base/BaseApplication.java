package com.eighteen.goradar.base;

import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.millennialmedia.MMException;
import com.millennialmedia.MMSDK;
import com.eighteen.goradar.util.Advertisement;
import com.eighteen.goradar.R;
import com.unity3d.ads.metadata.MediationMetaData;

/**
 * Created by ${张志珍} on 2016/12/22.
 */

public class BaseApplication extends MultiDexApplication {
    private static final String TAG = BaseApplication.class.getSimpleName();

    private static BaseApplication INSTANCE;

    public static BaseApplication getInstance() {
        return INSTANCE;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        if (INSTANCE == null) {
            INSTANCE = this;
        }
        MediationMetaData mediationMetaData = new MediationMetaData(this);
        mediationMetaData.setName("Example mediation network");
        mediationMetaData.setVersion("1.2.3.4");
        mediationMetaData.commit();




        try {
            MMSDK.initialize(this);
        }catch (MMException e){
            Log.e(TAG, "初始化MM广告SDK错误");
        }

        try {
            Advertisement.getInstance().init(this, getString(R.string.ad_unit_id),getString(R.string.ad_unit_id));
        } catch (Exception e) {
            Log.e(TAG, "初始化广告SDK错误");
        }
    }


}
