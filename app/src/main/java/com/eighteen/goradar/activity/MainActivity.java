package com.eighteen.goradar.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.SkuDetails;
import com.eighteen.goradar.base.BaseApplication;
import com.eighteen.goradar.model.EventModel;
import com.eighteen.goradar.util.Constant;
import com.eighteen.goradar.util.GoogleBillingUtil;
import com.eighteen.goradar.util.PayStatusUtil;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.eighteen.goradar.adapter.MyAdapter;
import com.eighteen.goradar.fragment.BaseFragment;
import com.eighteen.goradar.fragment.GuideFragment;
import com.eighteen.goradar.fragment.MapFragment;
import com.eighteen.goradar.fragment.PokedexFragment;
import com.eighteen.goradar.util.Advertisement;
import com.eighteen.goradar.view.NewViewPager;
import com.eighteen.goradar.R;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
//    @Bind(R.id.games_tv)
    TextView tv_Games;
    private SmartTabLayout mUITablayoutlive;
    private NewViewPager mUIViewpagerlive;
    private List<BaseFragment> list_fragment;


    private List<String> listTitle;
    private MyAdapter liveHomepageAdapter;
    private FragmentManager mFragmentManager;

    private MapFragment dynamicFragment;
    private PokedexFragment staticFragment;
    private GuideFragment wuWuFragment;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //sdk会对日志加密，防止攻击
        MobclickAgent.enableEncrypt(true);
        //该接口默认参数是true，即采集mac地址，但如果开发者需要在googleplay发布，考虑到审核风险，可以调用该接口，参数设置为false就不会采集mac地址。
        MobclickAgent.setCheckDevice(false);
        ButterKnife.bind(MainActivity.this);
//        getSupportActionBar().hide();
        init();




    }



    private void init(){

        mUITablayoutlive=(SmartTabLayout)findViewById(R.id.tbl_live);
        mUIViewpagerlive=(NewViewPager)findViewById(R.id.vp_live);
        mFragmentManager =MainActivity.this.getSupportFragmentManager();
        tv_Games=(TextView)findViewById(R.id.games_tv);
        tv_Games.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,GamesActivity.class);
                startActivity(intent);
            }
        });
        fragmentChange(0);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                try {
                    //先拿到单例模式里面的 实例  因为封装的是MAP  传入KEY去拿值 在显现广告
                    Advertisement.getInstance().show(getString(R.string.ad_unit_id));
                } catch (Exception e) {
                    Log.d("tag", "setContentView: 显示广告失败");
                }
            }
        }, 15*1000); // 15秒
    }

    private void fragmentChange(int  pageIndex){

        list_fragment=new ArrayList<BaseFragment>();
        dynamicFragment=new MapFragment();
        staticFragment=new PokedexFragment();
        wuWuFragment=new GuideFragment();
       /* OneFragment oneFragment = new OneFragment();
        TwoFragment twoFragment = new TwoFragment();
        ThreeFragment threeFragment = new ThreeFragment();*/
        list_fragment.add(dynamicFragment);
        list_fragment.add(staticFragment);
        list_fragment.add(wuWuFragment);
        listTitle=new ArrayList<>();

        listTitle.add(getResources().getString(R.string.corpe));
        listTitle.add(getResources().getString(R.string.botany));
        listTitle.add(getResources().getString(R.string.level_screen));

        liveHomepageAdapter=new MyAdapter(mFragmentManager,list_fragment,listTitle);
        mUIViewpagerlive.setAdapter(liveHomepageAdapter);
        mUITablayoutlive.setViewPager(mUIViewpagerlive);
        mUITablayoutlive.getTabAt(0).setSelected(true);
        final LinearLayout lyTabs = (LinearLayout) mUITablayoutlive.getChildAt(0);
        changeTabsTitleTypeFace(lyTabs, 0);

    }
    public void changeTabsTitleTypeFace(LinearLayout ly, int position) {
        for (int j = 0; j < ly.getChildCount(); j++) {
            TextView tvTabTitle = (TextView) ly.getChildAt(j);
            tvTabTitle.setTypeface(null, Typeface.NORMAL);
        }
    }



    @Override
    public void onPause() {

        super.onPause();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();

        MobclickAgent.onResume(this);

    }





}
