package com.eighteen.goradar.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.eighteen.goradar.R;
import com.eighteen.goradar.adapter.PlantsAdapter;
import com.eighteen.goradar.db.AddressHelper;
import com.eighteen.goradar.db.AssetsDatabaseManager;
import com.eighteen.goradar.db.UserDao;
import com.eighteen.goradar.model.DataModel;
import com.eighteen.goradar.model.SearchModel;
import com.eighteen.goradar.util.Advertisement;
import com.eighteen.goradar.view.SpacesItemDecoration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;

/**
 * Created by dellpc on 2016/11/5.
 */
public class PokedexFragment extends BaseFragment {
    public final static String TAG="PokedexFragment";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.adView)
    AdView mAdView;
    //获取db数据库
    private UserDao mUserDao;
    private ArrayList<SearchModel> searcheData;

    private AddressHelper addressHelper;
    private String country,language;
    private ArrayList<DataModel> listdate,mListDate;
    private Boolean isVisible;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_pokemon_list;
    }

    @Nullable
    @Override
    protected void initData(View v,Bundle savedInstanceState) {
        //初始化db数据管理类
        AssetsDatabaseManager.initManager(PokedexFragment.this.getActivity());
        //sdk会对日志加密，防止攻击
        MobclickAgent.enableEncrypt(true);
        //该接口默认参数是true，即采集mac地址，但如果开发者需要在googleplay发布，考虑到审核风险，可以调用该接口，参数设置为false就不会采集mac地址。
        MobclickAgent.setCheckDevice(false);
        //获取本地语言
        language = Locale.getDefault().getLanguage();
        Log.e("tag",language);
        country = getResources().getConfiguration().locale.getCountry();
        Log.e("tag","country"+country);
        recyclerView=(RecyclerView)v.findViewById(R.id.recyclerView);
        mAdView=(AdView)v.findViewById(R.id.adView);
        //  创建AdRequest 加载广告横幅adview
        AdRequest adRequest = new AdRequest.Builder().build();
        // 最后，请求广告。
        mAdView.loadAd(adRequest);

        getGridData();

    }

    private void getGridData(){
        addressHelper = new AddressHelper();
        addressHelper.OpenDatabase(getContext());
        listdate=new ArrayList<DataModel>();
        mListDate=new ArrayList<DataModel>();
        addressHelper = new AddressHelper();
        addressHelper.OpenDatabase(getContext());
        if (language.equals("zh")){
            if(country.equals("TW")){
                listdate = addressHelper.queryLanguage("zh-tw");
            }else{
                listdate = addressHelper.queryLanguage("zh-tw");
            }
        }else if(language.equals("en")){
            listdate = addressHelper.queryLanguage("en");
        }else if(language.equals("ja")){
            listdate = addressHelper.queryLanguage("ja");
        }else{
            listdate = addressHelper.queryLanguage("en");
        }
        for (int i = 0; i < listdate.size(); i++) {
            DataModel productModel  = new DataModel();
            productModel.setImageUrl(imageId[i]);
            productModel.setName(listdate.get(i).getName());
            productModel.setListid(listdate.get(i).getListid());
            productModel.setLanguage(listdate.get(i).getLanguage());
            productModel.setType_1(listdate.get(i).getType_1());
            productModel.setType_2(listdate.get(i).getType_2());
            mListDate.add(productModel);
        }
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.item_decoration);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        recyclerView.setAdapter(new PlantsAdapter(getActivity(),mListDate));

    }



    int[] imageId = new int[] {
            R.drawable.h001, R.drawable.h002,R.drawable.h003,
            R.drawable.h004, R.drawable.h005,R.drawable.h006,
            R.drawable.h007, R.drawable.h008,R.drawable.h009,
            R.drawable.h010, R.drawable.h011,R.drawable.h012,
            R.drawable.h013, R.drawable.h014,R.drawable.h015,
            R.drawable.h016, R.drawable.h017,R.drawable.h018,
            R.drawable.h019, R.drawable.h020,R.drawable.h021,
            R.drawable.h022, R.drawable.h023,R.drawable.h024,
            R.drawable.h025, R.drawable.h026,R.drawable.h027,
            R.drawable.h028, R.drawable.h029,R.drawable.h030,
            R.drawable.h031, R.drawable.h032,R.drawable.h033,
            R.drawable.h034, R.drawable.h035,R.drawable.h036,
            R.drawable.h037, R.drawable.h038,R.drawable.h039,
            R.drawable.h040, R.drawable.h041,R.drawable.h042,
            R.drawable.h043, R.drawable.h044,R.drawable.h045,
            R.drawable.h046, R.drawable.h047,R.drawable.h048,
            R.drawable.h049, R.drawable.h050,R.drawable.h051,
            R.drawable.h052, R.drawable.h053,R.drawable.h054,
            R.drawable.h055, R.drawable.h056,R.drawable.h057,
            R.drawable.h058, R.drawable.h059,R.drawable.h060,
            R.drawable.h061, R.drawable.h062,R.drawable.h063,
            R.drawable.h064, R.drawable.h065,R.drawable.h066,
            R.drawable.h067, R.drawable.h068,R.drawable.h069,
            R.drawable.h070, R.drawable.h071,R.drawable.h072,
            R.drawable.h073, R.drawable.h074,R.drawable.h075,
            R.drawable.h076, R.drawable.h077,R.drawable.h078,
            R.drawable.h079, R.drawable.h080,R.drawable.h081,
            R.drawable.h082, R.drawable.h083,R.drawable.h084,
            R.drawable.h085, R.drawable.h086,R.drawable.h087,
            R.drawable.h088, R.drawable.h089,R.drawable.h090,
            R.drawable.h091, R.drawable.h092,R.drawable.h093,
            R.drawable.h094, R.drawable.h095,R.drawable.h096,
            R.drawable.h097, R.drawable.h098,R.drawable.h099,
            R.drawable.h100,R.drawable.h101,R.drawable.h102,
            R.drawable.h103,R.drawable.h104,R.drawable.h105,
            R.drawable.h106,R.drawable.h107,R.drawable.h108,
            R.drawable.h109,R.drawable.h110,R.drawable.h111,
            R.drawable.h112,R.drawable.h113,R.drawable.h114,
            R.drawable.h115,R.drawable.h116,R.drawable.h117,
            R.drawable.h118,R.drawable.h119,R.drawable.h120,
            R.drawable.h121,R.drawable.h122,R.drawable.h123,
            R.drawable.h124,R.drawable.h125,R.drawable.h126,
            R.drawable.h127,R.drawable.h128,R.drawable.h129,
            R.drawable.h130,R.drawable.h131,R.drawable.h132,
            R.drawable.h133,R.drawable.h134,R.drawable.h135,
            R.drawable.h136,R.drawable.h137,R.drawable.h138,
            R.drawable.h139,R.drawable.h140,R.drawable.h141,
            R.drawable.h142,R.drawable.h143,R.drawable.h144,
            R.drawable.h145,R.drawable.h146,R.drawable.h147,
            R.drawable.h148,R.drawable.h149,R.drawable.h150,
            R.drawable.h151
    };

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(getUserVisibleHint()) {
            isVisible = true;
            Log.i("tag","pokedex=---- true");
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
        } else {
            isVisible = false;
            Log.i("tag","pokedex=---- false");
        }
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
