package com.eighteen.goradar.activity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.SkuDetails;
import com.eighteen.goradar.base.BaseApplication;
import com.eighteen.goradar.fragment.MapTwoFragment;
import com.eighteen.goradar.fragment.VideoListFragment;
import com.eighteen.goradar.model.EventModel;
import com.eighteen.goradar.util.Constant;
import com.eighteen.goradar.util.GoogleBillingUtil;
import com.eighteen.goradar.util.PayStatusUtil;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.eighteen.goradar.PokemonListView;
import com.eighteen.goradar.adapter.MyAdapter;
import com.eighteen.goradar.fragment.BaseFragment;
import com.eighteen.goradar.fragment.GuideFragment;
import com.eighteen.goradar.fragment.JumpGooglePlayFragment;
import com.eighteen.goradar.fragment.MapFragment;
import com.eighteen.goradar.fragment.PokedexFragment;
import com.eighteen.goradar.util.Advertisement;
import com.eighteen.goradar.util.NetReceiver;
import com.eighteen.goradar.util.StatusBarUtil;
import com.eighteen.goradar.R;
import com.umeng.analytics.MobclickAgent;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.metadata.MediationMetaData;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final String TAG = "MapsActivity";
    NetReceiver mReceiver = new NetReceiver();
    IntentFilter mFilter = new IntentFilter();
    private GoogleMap mMap;
    private UiSettings uiSettings;
    private AdView mAdView;
    private Button moreBtn,searchBtn;
    private String language;
    private String country;
    private RelativeLayout us_rela,feimeiguo_rela;
    //非美国
    private Button games_tv;
    private SmartTabLayout mUITablayoutlive;
    private ViewPager mUIViewpagerlive;
    private List<String> listTitle;
    private MyAdapter liveHomepageAdapter;
    private FragmentManager mFragmentManager;
    private MapFragment dynamicFragment;
    private MapTwoFragment mapFragment2;
    private PokedexFragment staticFragment;
    private GuideFragment wuWuFragment;
    private JumpGooglePlayFragment jumpGooglePlayFragment;
    private VideoListFragment videoListFragment;

    private ArrayList<BaseFragment> list_fragment;
    private LinearLayout linear_game;
    private ImageView help;
    String mapUrl_en="",mapUrl_tw="",mapUrl_jp="";

    private MyOnPurchaseFinishedListener mOnPurchaseFinishedListener = new MyOnPurchaseFinishedListener();//购买回调接口
    private MyOnQueryFinishedListener mOnQueryFinishedListener = new MyOnQueryFinishedListener();//查询回调接口
    private MyOnStartSetupFinishedListener mOnStartSetupFinishedListener = new MyOnStartSetupFinishedListener();//启动结果回调接口
    private GoogleBillingUtil googleBillingUtil;
    private static  String SUB_ID;//订阅的id
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //sdk会对日志加密，防止攻击
        MobclickAgent.enableEncrypt(true);
        //该接口默认参数是true，即采集mac地址，但如果开发者需要在googleplay发布，考虑到审核风险，可以调用该接口，参数设置为false就不会采集mac地址。
        MobclickAgent.setCheckDevice(false);
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mReceiver, mFilter);
        SupportMapFragment  mapFragment = (SupportMapFragment)this.getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.appcolor));

        init();
      /*  if(UnityAds.isReady()) {
            MediationMetaData mediationMetaData = new MediationMetaData(this);
            mediationMetaData.setOrdinal(1);
            mediationMetaData.commit();
            UnityAds.show(this);
        }*/
        mAdView = (AdView)findViewById(R.id.adView);
        AdRequest adRe = new AdRequest.Builder().build();
        mAdView.loadAd(adRe);

        if (PayStatusUtil.isSubAvailable()) {
            mAdView.setVisibility(View.GONE);
        } else {
            mAdView.setVisibility(View.VISIBLE);
        }

        EventBus.getDefault().register(this);

        SUB_ID = GoogleBillingUtil.getInstance().subsSKUS[0];

    }

    private void init() {
        //获取本地语言
        language = Locale.getDefault().getLanguage();
        Log.e("tag",language);
        country = getResources().getConfiguration().locale.getCountry();
        Log.e("tag","country"+country);

        help = (ImageView) findViewById(R.id.help);
        //原生
        moreBtn = (Button)findViewById(R.id.moreBtn);
        searchBtn = (Button)findViewById(R.id.searchBtn);
        us_rela = (RelativeLayout)findViewById(R.id.us_rela);
        linear_game = (LinearLayout)findViewById(R.id.linear_game);
        feimeiguo_rela = (RelativeLayout)findViewById(R.id.feimeiguo_rela);
        games_tv = (Button) findViewById(R.id.games_tv);
        mUITablayoutlive=(SmartTabLayout)findViewById(R.id.tbl_live);
        mUIViewpagerlive=(ViewPager)findViewById(R.id.vp_live);
        mFragmentManager =MapsActivity.this.getSupportFragmentManager();
        fragmentChange(0);
        //如果美国googlemap 其他h5


        if(language.equals("en")){
            if(country.equals("AU")){
                feimeiguo_rela.setVisibility(View.VISIBLE);
                us_rela.setVisibility(View.GONE);
            }else if(country.equals("CA")){
                feimeiguo_rela.setVisibility(View.VISIBLE);
                us_rela.setVisibility(View.GONE);
            }else if(country.equals("GB")){
                feimeiguo_rela.setVisibility(View.VISIBLE);
                us_rela.setVisibility(View.GONE);
            }else if(country.equals("IE")){
                feimeiguo_rela.setVisibility(View.VISIBLE);
                us_rela.setVisibility(View.GONE);
            }else if(country.equals("IE_EURO")){
                feimeiguo_rela.setVisibility(View.VISIBLE);
                us_rela.setVisibility(View.GONE);
            }else if(country.equals("NZ")){
                feimeiguo_rela.setVisibility(View.VISIBLE);
                us_rela.setVisibility(View.GONE);
            }else if(country.equals("ZA")){
                feimeiguo_rela.setVisibility(View.VISIBLE);
                us_rela.setVisibility(View.GONE);
            }else if(country.equals("SG")){
                help.setVisibility(View.VISIBLE);
                feimeiguo_rela.setVisibility(View.VISIBLE);
                us_rela.setVisibility(View.GONE);
            }else{
                feimeiguo_rela.setVisibility(View.GONE);
                us_rela.setVisibility(View.VISIBLE);
            }
        }else if(language.equals("zh")){
            feimeiguo_rela.setVisibility(View.VISIBLE);
            us_rela.setVisibility(View.GONE);
            Log.i(TAG, country+"init: "+language);
            if(country.equals("TW")){
                Log.i(TAG, "init: "+country);
                help.setVisibility(View.VISIBLE);
                //// TODO: 2017/5/19 澳门
            }else if(country.equals("MO")){
                help.setVisibility(View.VISIBLE);
                //// TODO: 2017/5/19 香港
            }else if(country.equals("HK")){
                help.setVisibility(View.VISIBLE);
            }else{
                help.setVisibility(View.GONE);
            }

        }else if(language.equals("ja")){
            help.setVisibility(View.VISIBLE);
            feimeiguo_rela.setVisibility(View.VISIBLE);
            us_rela.setVisibility(View.GONE);
        } else if(language.equals("fr")){
            help.setVisibility(View.GONE);
            feimeiguo_rela.setVisibility(View.VISIBLE);
            us_rela.setVisibility(View.GONE);
        } else if(language.equals("de")){
            help.setVisibility(View.GONE);
            feimeiguo_rela.setVisibility(View.VISIBLE);
            us_rela.setVisibility(View.GONE);
        }else if(language.equals("pt-br")){
            help.setVisibility(View.GONE);
            feimeiguo_rela.setVisibility(View.VISIBLE);
            us_rela.setVisibility(View.GONE);
        }else if(language.equals("pt-pt")){
            help.setVisibility(View.GONE);
            feimeiguo_rela.setVisibility(View.VISIBLE);
            us_rela.setVisibility(View.GONE);
        } else if(language.equals("ms")){
            help.setVisibility(View.GONE);
            feimeiguo_rela.setVisibility(View.VISIBLE);
            us_rela.setVisibility(View.GONE);
        }else if(language.equals("th")){
            help.setVisibility(View.GONE);
            feimeiguo_rela.setVisibility(View.VISIBLE);
            us_rela.setVisibility(View.GONE);
        }else if(language.equals("it")){
            help.setVisibility(View.GONE);
            feimeiguo_rela.setVisibility(View.VISIBLE);
            us_rela.setVisibility(View.GONE);
        }else if(language.equals("nl")){
            help.setVisibility(View.GONE);
            feimeiguo_rela.setVisibility(View.VISIBLE);
            us_rela.setVisibility(View.GONE);
        }else if(language.equals("ru")){
            help.setVisibility(View.GONE);
            feimeiguo_rela.setVisibility(View.VISIBLE);
            us_rela.setVisibility(View.GONE);
        }else if(language.equals("sv")){
            help.setVisibility(View.GONE);
            feimeiguo_rela.setVisibility(View.VISIBLE);
            us_rela.setVisibility(View.GONE);
        }else if(language.equals("th")){
            help.setVisibility(View.GONE);
            feimeiguo_rela.setVisibility(View.VISIBLE);
            us_rela.setVisibility(View.GONE);
        }else if(language.equals("ms")){
            help.setVisibility(View.GONE);
            feimeiguo_rela.setVisibility(View.VISIBLE);
            us_rela.setVisibility(View.GONE);
        }else if(language.equals("es")){
            help.setVisibility(View.GONE);
            feimeiguo_rela.setVisibility(View.VISIBLE);
            us_rela.setVisibility(View.GONE);
        }else if(language.equals("da")){
            help.setVisibility(View.GONE);
            feimeiguo_rela.setVisibility(View.VISIBLE);
            us_rela.setVisibility(View.GONE);
        }else{
            help.setVisibility(View.GONE);
            feimeiguo_rela.setVisibility(View.VISIBLE);
            us_rela.setVisibility(View.GONE);
        }

        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //先拿到单例模式里面的 实例  因为封装的是MAP  传入KEY去拿值 在显现广告
                    if (!PayStatusUtil.isSubAvailable())
                    Advertisement.getInstance().show(getString(R.string.ad_unit_id));
                } catch (Exception e) {
                    Log.d(TAG, "setContentView: 显示广告失败");
                }
                Intent intent=new Intent(MapsActivity.this,PokemonListView.class);
                startActivity(intent);
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* try {
                    //先拿到单例模式里面的 实例  因为封装的是MAP  传入KEY去拿值 在显现广告
                    Advertisement.getInstance().show(getString(R.string.ad_unit_id));
                } catch (Exception e) {
                    Log.d(TAG, "setContentView: 显示广告失败");
                }*/
                Intent intent=new Intent(MapsActivity.this,UserActivity.class);
                startActivity(intent);
            }
        });

        games_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MapsActivity.this,GamesActivity.class);
                startActivity(intent);
            }
        });
        linear_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MapsActivity.this,GamesActivity.class);
                startActivity(intent);
            }
        });
         String   addStr = getJson("GuideData.json");
        //将读出的字符串转换成JSONobject

        try {
            JSONObject jsonObject = new JSONObject(addStr);
            mapUrl_en =   jsonObject.getString("mapUrl_en");
            mapUrl_tw =   jsonObject.getString("helpUrl_zh-tw");
            mapUrl_jp =  jsonObject.getString("helpUrl_jp");

        }catch (Exception e){

        }

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MapsActivity.this,HelpWebViewActivity.class);
                intent.putExtra("index",0);
                //加载需要显示的网页
                if (language.equals("zh")){
                    Log.i(TAG, country+"initData: "+language);
                    //// TODO: 2017/5/19 台湾
                    if(country.equals("TW")){
                        intent.putExtra("url",mapUrl_tw);
                        //// TODO: 2017/5/19 澳门
                    }else if(country.equals("MO")){
                        intent.putExtra("url",mapUrl_tw);
                        //// TODO: 2017/5/19 香港
                    }else if(country.equals("HK")){
                        Log.i(TAG, "init: "+country);
                        intent.putExtra("url",mapUrl_tw);
                    }else{

                    }

                }else if(language.equals("en")){
                    //todo新加坡
                    if(country.equals("SG")){
                        intent.putExtra("url",mapUrl_tw);
                    }
                }else if(language.equals("ja")){
                    intent.putExtra("url",mapUrl_jp);
                }else{
                    intent.putExtra("url",mapUrl_en);
                }
                startActivity(intent);
            }
        });
    }

    //读取方法
    public String getJson(String fileName) {

        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = MapsActivity.this.getAssets();
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


    public void  onSearchButtonClick(View v){
        try {
            //先拿到单例模式里面的 实例  因为封装的是MAP  传入KEY去拿值 在显现广告
            Advertisement.getInstance().show(getString(R.string.ad_unit_id));
        } catch (Exception e) {
            Log.d(TAG, "setContentView: 显示广告失败");
        }
        Intent intent=new Intent(MapsActivity.this,UserActivity.class);
        startActivity(intent);
    }

    public void onMoreButtonClick(View v){
                try {
                    //先拿到单例模式里面的 实例  因为封装的是MAP  传入KEY去拿值 在显现广告
                    Advertisement.getInstance().show(getString(R.string.ad_unit_id));
                } catch (Exception e) {
                    Log.d(TAG, "setContentView: 显示广告失败");
                }

        Intent intent=new Intent(MapsActivity.this,PokemonListView.class);
        startActivity(intent);

    }

    private void fragmentChange(int  pageIndex){

        list_fragment=new ArrayList<BaseFragment>();
        dynamicFragment=new MapFragment();
        staticFragment=new PokedexFragment();
        mapFragment2 = new MapTwoFragment();
        wuWuFragment=new GuideFragment();
        jumpGooglePlayFragment=new JumpGooglePlayFragment();
        videoListFragment=new VideoListFragment();
        list_fragment.add(dynamicFragment);
        list_fragment.add(mapFragment2);
//        list_fragment.add(videoListFragment);
//        list_fragment.add(jumpGooglePlayFragment);
        list_fragment.add(staticFragment);
        list_fragment.add(wuWuFragment);

        listTitle=new ArrayList<>();

        listTitle.add(getResources().getString(R.string.corpe));
        listTitle.add(getResources().getString(R.string.map));
//        listTitle.add("Video");
//        listTitle.add(getResources().getString(R.string.recommend));
        listTitle.add(getResources().getString(R.string.botany));
        listTitle.add(getResources().getString(R.string.lv_calc));

        liveHomepageAdapter=new MyAdapter(mFragmentManager,list_fragment,listTitle);
        mUIViewpagerlive.setAdapter(liveHomepageAdapter);
        mUITablayoutlive.setViewPager(mUIViewpagerlive);
        mUIViewpagerlive.setOffscreenPageLimit(4);
        mUITablayoutlive.getTabAt(0).setSelected(true);
        final LinearLayout lyTabs = (LinearLayout) mUITablayoutlive.getChildAt(0);
        changeTabsTitleTypeFace(lyTabs, 0);
    }
    public void changeTabsTitleTypeFace(LinearLayout ly, int position) {
        for (int j = 0; j < ly.getChildCount(); j++) {
            TextView tvTabTitle = (TextView) ly.getChildAt(j);
            tvTabTitle.setTypeface(null, Typeface.NORMAL);
            tvTabTitle.setTag(j);
//            tvTabTitle.setOnClickListener(onTabOnClick);
        }
    }




   /* private   View.OnClickListener onTabOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
              int pos=(int)v.getTag();
            if(pos==0){
                try {
                    //先拿到单例模式里面的 实例  因为封装的是MAP  传入KEY去拿值 在显现广告
                    Advertisement.getInstance().show(getString(R.string.ad_unit_id));
                } catch (Exception e) {
                    Log.d(TAG, "setContentView: 显示广告失败");
                }
            }else if(pos==1){
                try {
                    //先拿到单例模式里面的 实例  因为封装的是MAP  传入KEY去拿值 在显现广告
                    Advertisement.getInstance().show(getString(R.string.ad_unit_id));
                } catch (Exception e) {
                    Log.d(TAG, "setContentView: 显示广告失败");
                }
            }else if(pos==2){
                try {
                    //先拿到单例模式里面的 实例  因为封装的是MAP  传入KEY去拿值 在显现广告
                    Advertisement.getInstance().show(getString(R.string.ad_unit_id));
                } catch (Exception e) {
                    Log.d(TAG, "setContentView: 显示广告失败");
                }
            }else{

            }
        }
    };*/



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (mMap!=null) {
            setUpMap();
        }
    }



    private void setUpMap(){

        Boolean bookean = isOPen(MapsActivity.this);
        if (!bookean){
            //GPS未开启
            toggleGPS();
        }

        uiSettings =  mMap.getUiSettings();
        //当我点击谷歌地图上的标记,“导航”和“GPS指针”按钮，设置隐藏
        uiSettings.setMapToolbarEnabled(false);
        //显示缩放按钮
        uiSettings.setZoomControlsEnabled(false);
        //显示指南针
        uiSettings.setCompassEnabled(true);
        //显示自己位置的按钮
        uiSettings.setMyLocationButtonEnabled(true);
        //启动地图滚动手势
        uiSettings.setScrollGesturesEnabled(true);
        //启动地图缩放手势
        uiSettings.setZoomGesturesEnabled(true);
        //启动地图倾斜手势
        uiSettings.setTiltGesturesEnabled(true);
        //启动地图旋转手势
        uiSettings.setRotateGesturesEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //显示交通信息
        mMap.setTrafficEnabled(true);
        //显示自己的位置
        mMap.setMyLocationEnabled(true);
    }

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     * @param context
     * @return true 表示开启
     */
    public   boolean isOPen(Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
    }


    /**
     * 强制帮用户打开GPS
     * @param
     */
    private void toggleGPS() {
        Intent gpsIntent = new Intent();
        gpsIntent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
        gpsIntent.addCategory("android.intent.category.ALTERNATIVE");
        gpsIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(MapsActivity.this, 0, gpsIntent, 0).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
//        stopService(new Intent(MapsActivity.this, CalculateOverlayService.class));
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        MobclickAgent.onPause(this);
        super.onDestroy();
    }


    //查询商品信息回调接口
    private class MyOnQueryFinishedListener implements GoogleBillingUtil.OnQueryFinishedListener {
        @Override
        public void onQuerySuccess(String skuType, List<SkuDetails> list) {
            Log.d("wyl", "查询商品信息回调接口 onQuerySuccess");
            if (list != null) {
                for (SkuDetails skuDetails : list) {
                    String log = "";
                    if (skuType == BillingClient.SkuType.INAPP) {
                        log += "内购的商品:";
                    } else if (skuType == BillingClient.SkuType.SUBS) {
                        log += "订阅的商品:";
                    }
                    Log.d("wyl", log + skuDetails.getTitle() + " 序列号：" + skuDetails.getSku() + " 价格：" + skuDetails.getPrice());
                }
            }


            //查询成功，返回商品列表，
            //skuDetails.getPrice()获得价格(文本)
            //skuDetails.getType()获得类型 sub或者inapp,因为sub和inapp的查询结果都走这里，所以需要判断。
            //googleBillingUtil.getSubsPositionBySku(skuDetails.getSku())获得当前subs sku的序号
            //googleBillingUtil.getInAppPositionBySku(skuDetails.getSku())获得当前inapp suk的序号
        }

        @Override
        public void onQueryFail(int responseCode) {
            Log.d("wyl", "查询商品信息回调接口 onQueryFail");
            //查询失败

        }

        @Override
        public void onQueryError() {
            //查询错误
            Log.d("wyl", "查询商品信息回调接口 onQueryError");
        }
    }

    //服务初始化结果回调接口
    private class MyOnStartSetupFinishedListener implements GoogleBillingUtil.OnStartSetupFinishedListener {
        @Override
        public void onSetupSuccess() {
            Log.d("wyl", "服务初始化结果回调接口 onSetupSuccess");


            Log.d("wyl", "开始查询已经购买商品");
            List<Purchase> inapps = googleBillingUtil.queryPurchasesInApp();
            if (inapps != null) {
                for (Purchase inapp : inapps) {
                    Log.d("wyl", "已经购买的商品：" + inapp.getSku());
                }
            }
            Log.d("wyl", "开始查询已经订阅商品");
            List<Purchase> subs = googleBillingUtil.queryPurchasesSubs();
            if (subs != null) {
                for (Purchase sub : subs) {
                    Log.d("wyl", "已经订阅的商品：" + sub.getSku());
                }
            }

            int size = googleBillingUtil.getPurchasesSizeSubs();
            Log.d("wyl", "获取有效订阅的数量：" + size);
            handleQueryResult(size);



//            Toast.makeText(BaseApplication.getInstance(), "DownPro 有效订阅的数量：" + size + ":::" + (subs == null ? 0 :subs.size()), Toast.LENGTH_LONG).show();

        }

        @Override
        public void onSetupFail(int responseCode) {
            Log.d("wyl", "服务初始化结果回调接口 onSetupFail");
            handleQueryResult(-1);

        }

        @Override
        public void onSetupError() {
            Log.d("wyl", "服务初始化结果回调接口 onSetupError");
            handleQueryResult(-1);

        }
    }

    //购买商品回调接口
    private class MyOnPurchaseFinishedListener implements GoogleBillingUtil.OnPurchaseFinishedListener {
        @Override
        public void onPurchaseSuccess(List<Purchase> list) {
            //内购或者订阅成功,可以通过purchase.getSku()获取suk进而来判断是哪个商品
            Log.d("wyl", "购买商品回调接口 onPurchaseSuccess");
            if (list != null && list.size() > 0) {

                //订阅成功，取消弹出框
                EventBus.getDefault().post(new EventModel(Constant.Event.DISS_DIALOG));


                for (Purchase purchase : list) {
                    String sku = purchase.getSku();
                    if (!TextUtils.isEmpty(sku) && TextUtils.equals(sku, SUB_ID)) {//订阅商品成功，记录
                        PayStatusUtil.savePaySubStatus(true);
                    }

                    String log = "";
                    if (googleBillingUtil.handlePurchase(purchase)) {
                        log = log + "商品序列号：" + purchase.getSku();
                        Log.d("wyl", " 尚明" + "购买的商品通过验证：" + purchase.getSignature());
                    } else {
                        log = log + "商品序列号：" + purchase.getSku();
                        Log.d("wyl", "购买的商品未通过验证：" + purchase.getSignature());
                    }
                    Log.d("wyl", "购买或者订阅成功：" + log);
                }
            }
        }

        @Override
        public void onPurchaseFail(int responseCode) {
            Log.d("wyl", "购买商品回调接口 onPurchaseFail：" + responseCode);

        }

        @Override
        public void onPurchaseError() {
            Log.d("wyl", "购买商品回调接口 onPurchaseError");

        }

    }




    @Subscribe
    public void onSubEvent(EventModel eventModel) {
        if (eventModel == null) {
            return;

        }
        if (eventModel.code == Constant.Event.QUERY_SUB) {
            if (googleBillingUtil != null && googleBillingUtil.isReady()) {
                int size = googleBillingUtil.getPurchasesSizeSubs();
                handleQueryResult(size);
            } else {//走正常的流程

                initGoogleBilling();
            }
        } else if (eventModel.code == Constant.Event.QUERY_SUB_AND_BUY) {//先查询订阅是否有效，无效的话开始订阅，有效的话取消弹窗
            if (googleBillingUtil != null && googleBillingUtil.isReady()) {
                int size = googleBillingUtil.getPurchasesSizeSubs();
                handleQueryResult(size);
            } else {//走正常的流程

                initGoogleBilling();
            }
        } else if (eventModel.code == Constant.Event.DISS_DIALOG) {
            if (mAdView != null) {
                mAdView.setVisibility(View.GONE);
            }
        } else if (eventModel.code == Constant.Event.SHOW_DIALOG) {
            if (mAdView != null) {
                mAdView.setVisibility(View.VISIBLE);
            }
        }

    }

    /**
     * 处理查询的结果
     * @param size
     */
    private void handleQueryResult(int size) {
        Log.d("wyl", "获取有效订阅的数量：" + size);
        PayStatusUtil.savePaySubStatus(size > 0 ? true : false);

        if (PayStatusUtil.isSubAvailable()) {//取消弹出弹窗
            EventBus.getDefault().post(new EventModel(Constant.Event.DISS_DIALOG));
        } else {//开始订阅
            googleBillingUtil.purchaseSubs(this, SUB_ID);
        }

    }


    /**
     * 初始化谷歌内购
     */
    private void initGoogleBilling() {
        GoogleBillingUtil.cleanListener();
        googleBillingUtil = GoogleBillingUtil.getInstance()
                .setOnPurchaseFinishedListener(mOnPurchaseFinishedListener)
                .setOnQueryFinishedListener(mOnQueryFinishedListener)
                .setOnStartSetupFinishedListener(mOnStartSetupFinishedListener)
                .build();
    }


}
