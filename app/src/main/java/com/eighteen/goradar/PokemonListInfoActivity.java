package com.eighteen.goradar;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.eighteen.goradar.db.AssetsDatabaseManager;
import com.eighteen.goradar.db.UserDao;
import com.eighteen.goradar.model.SearchModel;
import com.eighteen.goradar.R;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

public class PokemonListInfoActivity extends Activity {

    private TextView mPokemonName;
    private String getExtrasName,getExtraslist_id,language;

    private List<SearchModel> pokemonList;
    private List<AttackMove> pokemonAttacks;
    private PokemonData pokeDex;
    private MoveStats moveStats;
    private SearchModel foundPokemon;


   private AdView mAdView;
    //获取db数据库
    private UserDao mUserDao;
    private List<SearchModel> listdata;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_list_info);
        //初始化db数据管理类
        AssetsDatabaseManager.initManager(getApplication());
        //sdk会对日志加密，防止攻击
        MobclickAgent.enableEncrypt(true);
        //该接口默认参数是true，即采集mac地址，但如果开发者需要在googleplay发布，考虑到审核风险，可以调用该接口，参数设置为false就不会采集mac地址。
        MobclickAgent.setCheckDevice(false);
        init();
    }

    private void init(){
        getExtrasName = getIntent().getExtras().getString("pokemonName");
        getExtraslist_id = getIntent().getExtras().getString("list_id");
        language = getIntent().getExtras().getString("language");
        mPokemonName = (TextView)findViewById(R.id.PokemonName);
        mPokemonName.setText(getExtrasName);


       mAdView = (AdView)findViewById(R.id.adView);
        //  创建AdRequest 加载广告横幅adview
        AdRequest adRe = new AdRequest.Builder().build();
        // 最后，请求广告。
        mAdView.loadAd(adRe);

//        layout = (RelativeLayout)findViewById(R.id.activityLayout);




        //获取的db数据库的数据
        mUserDao = new UserDao();
        listdata=new ArrayList<SearchModel>();
        TextView tv_types=(TextView)findViewById(R.id.tv_types);
        TextView tv_total=(TextView)findViewById(R.id.tv_total);
        TextView tv_hp=(TextView)findViewById(R.id.tv_hp);
        TextView tv_Attack=(TextView)findViewById(R.id.tv_Attack);
        TextView tv_defense=(TextView)findViewById(R.id.tv_defense);
        TextView tv_Sp_attack=(TextView)findViewById(R.id.tv_Sp_attack);
        TextView tv_sp_defense=(TextView)findViewById(R.id.tv_sp_defense);
        TextView tv_speed=(TextView)findViewById(R.id.tv_speed);
        listdata = mUserDao.query_list_name(getExtraslist_id,language);
        if (listdata.size()>0){
            if (!TextUtils.isEmpty(listdata.get(0).getType1())&&!TextUtils.isEmpty(listdata.get(0).getType2())) {
                tv_types.setText(listdata.get(0).getType1() + "   " + listdata.get(0).getType2());
            }else if (!TextUtils.isEmpty(listdata.get(0).getType1())){
                tv_types.setText(listdata.get(0).getType1());
            }else if (!TextUtils.isEmpty(listdata.get(0).getType2())){
                tv_types.setText(listdata.get(0).getType2());
            }
            tv_total.setText(listdata.get(0).getTotal());
            tv_hp.setText(listdata.get(0).getHp());
            tv_Attack.setText(listdata.get(0).getAttack());
            tv_defense .setText(listdata.get(0).getDefense());
            tv_Sp_attack.setText(listdata.get(0).getSp_attack());
            tv_sp_defense.setText(listdata.get(0).getSp_defense());
            tv_speed.setText(listdata.get(0).getSpeed());
        }

    }

    @Override
    public void onPause() {
        MobclickAgent.onPause(this);
        super.onPause();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();

        MobclickAgent.onResume(this);

    }

    public void onBackButtonClick(View v){
        finish();
    }
}
