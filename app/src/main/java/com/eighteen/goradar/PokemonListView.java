package com.eighteen.goradar;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.eighteen.goradar.model.EventModel;
import com.eighteen.goradar.util.Constant;
import com.eighteen.goradar.util.PayStatusUtil;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.eighteen.goradar.adapter.PokeModelAdapter;
import com.eighteen.goradar.db.AssetsDatabaseManager;
import com.eighteen.goradar.db.UserDao;
import com.eighteen.goradar.model.SearchModel;
import com.eighteen.goradar.util.Advertisement;
import com.eighteen.goradar.util.NetReceiver;
import com.eighteen.goradar.R;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class PokemonListView extends Activity implements SearchView.OnQueryTextListener, View.OnClickListener {

    private InterstitialAd interstitial;
    private AdView mAdView;
    NetReceiver mReceiver = new NetReceiver();
    IntentFilter mFilter = new IntentFilter();


    private PokemonData myPokedex;
    private ListView pListView;
    List<String> pNames;

    private PokeModelAdapter pAdapter;
    //获取db数据库
    private UserDao mUserDao;
    private List<SearchModel> listdata;
    private TextView tvSub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokemon_list);
        //初始化db数据管理类
        AssetsDatabaseManager.initManager(getApplication());
        //sdk会对日志加密，防止攻击
        MobclickAgent.enableEncrypt(true);
        //该接口默认参数是true，即采集mac地址，但如果开发者需要在googleplay发布，考虑到审核风险，可以调用该接口，参数设置为false就不会采集mac地址。
        MobclickAgent.setCheckDevice(false);
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mReceiver, mFilter);
        PreferenceManager.setDefaultValues(getApplicationContext(), R.xml.pref_general, false);


        initfindid();

        EventBus.getDefault().register(this);

    }

    public void onMoreButtonClick(View v){
      finish();
    }


    private void   initfindid(){
        tvSub = findViewById(R.id.tv_sub);
        tvSub.setOnClickListener(this);

        AssetsDatabaseManager.initManager(PokemonListView.this);
        // 插屏广告
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(getString(R.string.ad_unit_id));
        AdRequest request = new AdRequest.Builder().build();
        interstitial.loadAd(request);

        mAdView = (AdView)findViewById(R.id.adView);
        //  创建AdRequest 加载广告横幅adview
        AdRequest adRequest = new AdRequest.Builder().build();
        // 最后，请求广告。
        mAdView.loadAd(adRequest);
        if (PayStatusUtil.isSubAvailable()) {
            mAdView.setVisibility(View.GONE);
        } else {
            mAdView.setVisibility(View.VISIBLE);
        }

        myPokedex = new PokemonData();
        pNames = new ArrayList<>();
        //获取本地语言
        String language = Locale.getDefault().getLanguage();
        Log.e("tag",language);
        String country = getResources().getConfiguration().locale.getCountry();
        Log.e("tag","country"+country);
        //获取的db数据库的数据
        mUserDao = new UserDao();
        listdata=new ArrayList<SearchModel>();
        if (language.equals("zh")){
            if(country.equals("TW")){
                listdata = mUserDao.queryLanguage("zh-tw");
            }else{
                listdata = mUserDao.queryLanguage("zh");
            }
        }else if(language.equals("en")){
            listdata = mUserDao.queryLanguage("en");
        }else if(language.equals("ja")){
            listdata = mUserDao.queryLanguage("ja");
        }else if(language.equals("it")){
            listdata = mUserDao.queryLanguage("it");
        }else if(language.equals("ko")){
            listdata = mUserDao.queryLanguage("ko");
        }else if(language.equals("fr")){
            listdata = mUserDao.queryLanguage("fr");
        }else if(language.equals("nl")){
            listdata = mUserDao.queryLanguage("nl");
        }else{
            listdata = mUserDao.queryLanguage("en");
        }

        /** Set Adapter for searchbar and listview */
        pListView = (ListView) findViewById(R.id.pList);

         pAdapter = new PokeModelAdapter(this, listdata);
        pListView.setAdapter(pAdapter);

        pListView.setTextFilterEnabled(true);


        pListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    //先拿到单例模式里面的 实例  因为封装的是MAP  传入KEY去拿值 在显现广告
                    if (!PayStatusUtil.isSubAvailable())
                    Advertisement.getInstance().show(getString(R.string.ad_unit_id));
                } catch (Exception e) {
                    Log.d("tag", "setContentView: 显示广告失败");
                }
               /* if (interstitial.isLoaded()) {
                    interstitial.show();
                    Log.i("tag","插屏显示");
                }else{
                    Log.i("tag","插屏bu显示");
                }*/
                int itemPosition = position;
                Intent intent = new Intent(getApplicationContext(), PokemonListInfoActivity.class);
                intent.putExtra("pokemonName",listdata.get(position).getName() );
                intent.putExtra("list_id",listdata.get(position).getList_id() );
                intent.putExtra("language",listdata.get(position).getLanguage() );
               
                startActivity(intent);
            }
        });
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
        MobclickAgent.onResume(this);

    }


    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
//        stopService(new Intent(PokemonListView.this, CalculateOverlayService.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        EventBus.getDefault().unregister(this);
//        stopService(new Intent(PokemonListView.this, CalculateOverlayService.class));
    }



    @Override
    public boolean onQueryTextChange(String newText)
    {

        if (TextUtils.isEmpty(newText)) {
            pAdapter = new PokeModelAdapter(this, listdata);
            pListView.setAdapter(pAdapter);

        } else {
          
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

   

    /** Send back to home screen android back button pressed */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
          
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sub:
                //开始订阅
                EventBus.getDefault().post(new EventModel(Constant.Event.QUERY_SUB_AND_BUY));
                break;
        }
    }


    @Subscribe
    public void onEvent(EventModel eventModel) {
        if (eventModel == null) {
            return;
        }
        if (eventModel.code == Constant.Event.DISS_DIALOG) {
            if (mAdView != null) {
                mAdView.setVisibility(View.GONE);
            }

        } if (eventModel.code == Constant.Event.SHOW_DIALOG) {
            if (mAdView != null) {
                mAdView.setVisibility(View.VISIBLE);
            }
        }

    }


}