package com.eighteen.goradar.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.eighteen.goradar.R;
import com.eighteen.goradar.activity.YoutubeActivity;
import com.eighteen.goradar.adapter.VideoAdapter;
import com.eighteen.goradar.db.AssetsDatabaseManager;
import com.eighteen.goradar.db.VideoDao;
import com.eighteen.goradar.model.VideoModel;
import com.eighteen.goradar.util.Advertisement;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;

/**
 * Created by dellpc on 2016/11/5.
 */
public class VideoListFragment extends BaseFragment {
    public final static String TAG="VideoListFragment";
    private String language,country;
    private ListView listView;
    private ProgressBar pbLarge;
    //获取db数据库
    private VideoDao mVideoDao;
    private ArrayList<VideoModel> listdata;
    private VideoAdapter adapter;
    @BindView(R.id.adView)
    AdView mAdView;

    @Override
    protected int getLayoutResource() {
        return R.layout.videolistlayout;
    }

    @Nullable
    @Override
    protected void initData(View v,Bundle savedInstanceState) {

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
        pbLarge= (ProgressBar)v.findViewById(R.id.pbLarge);
        listView=(ListView)v.findViewById(R.id.listView);
        //初始化db数据管理类
        AssetsDatabaseManager.initManager(VideoListFragment.this.getActivity());
        //获取的db数据库的数据
        mVideoDao = new VideoDao();
        listdata=new ArrayList<VideoModel>();
        listdata = mVideoDao.queryLanguage("others");
        adapter = new VideoAdapter(VideoListFragment.this.getActivity(),listdata,language,country);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //实例化SharedPreferences对象（第一步）
                SharedPreferences mySharedPreferences= VideoListFragment.this.getActivity().getSharedPreferences("video",
                        Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = mySharedPreferences.edit();
                //用putString的方法保存数据
                editor.putString(listdata.get(position).getId(), "1");
                editor.commit();
                adapter.notifyDataSetChanged();
                try {
                    //先拿到单例模式里面的 实例  因为封装的是MAP  传入KEY去拿值 在显现广告
                    if (Advertisement.getInstance().show(getString(R.string.ad_unit_id))){
                        Advertisement.getInstance().show(getString(R.string.ad_unit_id));
                        if (!TextUtils.isEmpty(listdata.get(position).getUrl())){
                            Intent intent=new Intent(VideoListFragment.this.getActivity(),YoutubeActivity.class);
                            intent.putExtra("url",listdata.get(position).getUrl());
                            intent.putExtra("title",listdata.get(position).getTitle());
                            startActivity(intent);
                        }
                    }else{
                        if (!TextUtils.isEmpty(listdata.get(position).getUrl())){
                            Intent intent=new Intent(VideoListFragment.this.getActivity(),YoutubeActivity.class);
                            intent.putExtra("url",listdata.get(position).getUrl());
                            intent.putExtra("title",listdata.get(position).getTitle());
                            startActivity(intent);
                        }
                    }
                } catch (Exception e) {
                    Log.d(TAG, "setContentView: 显示广告失败");
                }


            }
        });


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
    protected void initVariables(Bundle savedInstanceState) {

    }

    @Override
    protected void handleUIEvent() {

    }
}
