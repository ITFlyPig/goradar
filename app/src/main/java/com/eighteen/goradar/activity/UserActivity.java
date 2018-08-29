package com.eighteen.goradar.activity;

import android.app.Activity;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.eighteen.goradar.adapter.UserModelAdapter;
import com.eighteen.goradar.db.AssetsDatabaseManager;
import com.eighteen.goradar.db.UserDao;
import com.eighteen.goradar.model.SearchModel;
import com.eighteen.goradar.util.CharacterParser;
import com.eighteen.goradar.util.NetReceiver;
import com.eighteen.goradar.R;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Locale;

public class UserActivity extends Activity {
    NetReceiver mReceiver = new NetReceiver();
    IntentFilter mFilter = new IntentFilter();

    private Button mbackBtn;
    private Button mdeleteBtn;
    private ListView mlistView;
    private ArrayList<SearchModel> listdate,mListDate;
    private UserModelAdapter adapter;
    private EditText mClearEditText;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private UserDao mUserDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AssetsDatabaseManager.initManager(UserActivity.this);
        setContentView(R.layout.activity_user);
        init();
    }

    int[] imageId = new int[] {
            R.drawable.h001, R.drawable.h002, R.drawable.h003,
            R.drawable.h004, R.drawable.h005, R.drawable.h006,
            R.drawable.h007, R.drawable.h008, R.drawable.h009,
            R.drawable.h010, R.drawable.h011, R.drawable.h012,
            R.drawable.h013, R.drawable.h014, R.drawable.h015,
            R.drawable.h016, R.drawable.h017, R.drawable.h018,
            R.drawable.h019, R.drawable.h020, R.drawable.h021,
            R.drawable.h022, R.drawable.h023, R.drawable.h024,
            R.drawable.h025, R.drawable.h026, R.drawable.h027,
            R.drawable.h028, R.drawable.h029, R.drawable.h030,
            R.drawable.h031, R.drawable.h032, R.drawable.h033,
            R.drawable.h034, R.drawable.h035, R.drawable.h036,
            R.drawable.h037, R.drawable.h038, R.drawable.h039,
            R.drawable.h040, R.drawable.h041, R.drawable.h042,
            R.drawable.h043, R.drawable.h044, R.drawable.h045,
            R.drawable.h046, R.drawable.h047, R.drawable.h048,
            R.drawable.h049, R.drawable.h050, R.drawable.h051,
            R.drawable.h052, R.drawable.h053, R.drawable.h054,
            R.drawable.h055, R.drawable.h056, R.drawable.h057,
            R.drawable.h058, R.drawable.h059, R.drawable.h060,
            R.drawable.h061, R.drawable.h062, R.drawable.h063,
            R.drawable.h064, R.drawable.h065, R.drawable.h066,
            R.drawable.h067, R.drawable.h068, R.drawable.h069,
            R.drawable.h070, R.drawable.h071, R.drawable.h072,
            R.drawable.h073, R.drawable.h074, R.drawable.h075,
            R.drawable.h076, R.drawable.h077, R.drawable.h078,
            R.drawable.h079, R.drawable.h080, R.drawable.h081,
            R.drawable.h082, R.drawable.h083, R.drawable.h084,
            R.drawable.h085, R.drawable.h086, R.drawable.h087,
            R.drawable.h088, R.drawable.h089, R.drawable.h090,
            R.drawable.h091, R.drawable.h092, R.drawable.h093,
            R.drawable.h094, R.drawable.h095, R.drawable.h096,
            R.drawable.h097, R.drawable.h098, R.drawable.h099,
            R.drawable.h100, R.drawable.h101, R.drawable.h102,
            R.drawable.h103, R.drawable.h104, R.drawable.h105,
            R.drawable.h106, R.drawable.h107, R.drawable.h108,
            R.drawable.h109, R.drawable.h110, R.drawable.h111,
            R.drawable.h112, R.drawable.h113, R.drawable.h114,
            R.drawable.h115, R.drawable.h116, R.drawable.h117,
            R.drawable.h118, R.drawable.h119, R.drawable.h120,
            R.drawable.h121, R.drawable.h122, R.drawable.h123,
            R.drawable.h124, R.drawable.h125, R.drawable.h126,
            R.drawable.h127, R.drawable.h128, R.drawable.h129,
            R.drawable.h130, R.drawable.h131, R.drawable.h132,
            R.drawable.h133, R.drawable.h134, R.drawable.h135,
            R.drawable.h136, R.drawable.h137, R.drawable.h138,
            R.drawable.h139, R.drawable.h140, R.drawable.h141,
            R.drawable.h142, R.drawable.h143, R.drawable.h144,
            R.drawable.h145, R.drawable.h146, R.drawable.h147,
            R.drawable.h148, R.drawable.h149, R.drawable.h150,
            R.drawable.h151



    };

    public void init(){

        //sdk会对日志加密，防止攻击
        MobclickAgent.enableEncrypt(true);
        //该接口默认参数是true，即采集mac地址，但如果开发者需要在googleplay发布，考虑到审核风险，可以调用该接口，参数设置为false就不会采集mac地址。
        MobclickAgent.setCheckDevice(false);
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mReceiver, mFilter);
        mClearEditText = (EditText) findViewById(R.id.filter_edit);
        mbackBtn = (Button)findViewById(R.id.backBtn);
        mdeleteBtn= (Button)findViewById(R.id.deleteBtn);
        mlistView = (ListView)findViewById(R.id.listView);
        listdate= new ArrayList<SearchModel>();
        mListDate = new ArrayList<SearchModel>();
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        //获取本地语言
        String language = Locale.getDefault().getLanguage();
        Log.e("tag",language);
        String country = getResources().getConfiguration().locale.getCountry();
        Log.e("tag","country"+country);
        //获取的db数据库的数据
        mUserDao = new UserDao();
        if (language.equals("zh")){
            if(country.equals("TW")){
                listdate = mUserDao.queryLanguage("zh-tw");
            }else{
                listdate = mUserDao.queryLanguage("zh");
            }
        }else if(language.equals("en")){
            listdate = mUserDao.queryLanguage("en");
        }else if(language.equals("ja")){
            listdate = mUserDao.queryLanguage("ja");
        }else if(language.equals("it")){
            listdate = mUserDao.queryLanguage("it");
        }else if(language.equals("ko")){
            listdate = mUserDao.queryLanguage("ko");
        }else if(language.equals("fr")){
            listdate = mUserDao.queryLanguage("fr");
        }else if(language.equals("nl")){
            listdate = mUserDao.queryLanguage("nl");
        }else{
            listdate = mUserDao.queryLanguage("en");
        }
        Log.e("tag","data"+listdate.size());
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        for (int i = 0; i < listdate.size(); i++) {
            SearchModel mSearchModel  = new SearchModel();
            mSearchModel.setList_id(listdate.get(i).getList_id());
            mSearchModel.setPokemon_name(listdate.get(i).getPokemon_name());
            mSearchModel.setType1(listdate.get(i).getType1());
            mSearchModel.setType2(listdate.get(i).getType2());
            mSearchModel.setTotal(listdate.get(i).getTotal());
            mSearchModel.setHp(listdate.get(i).getHp());
            mSearchModel.setAttack(listdate.get(i).getAttack());
            mSearchModel.setDefense(listdate.get(i).getDefense());
            mSearchModel.setSp_attack(listdate.get(i).getSp_attack());
            mSearchModel.setSp_defense(listdate.get(i).getSp_defense());
            mSearchModel.setSpeed(listdate.get(i).getSpeed());
            mSearchModel.setLanguage(listdate.get(i).getLanguage());
            mSearchModel.setImageUrl(imageId[i]);
            String pinyin = characterParser.getSelling(listdate.get(i).getPokemon_name());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if(sortString.matches("[A-Z]")){
                mSearchModel.setSortLetters(sortString.toUpperCase());
            }else{
                mSearchModel.setSortLetters("#");
            }
            mListDate.add(mSearchModel);
        }




        //获取的sqlite数据
       /* addressHelper = new AddressHelper();
        addressHelper.OpenDatabase(UserActivity.this);
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
        Log.e("tag","data"+listdate.size());
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        for (int i = 0; i < listdate.size(); i++) {
            DataModel productModel  = new DataModel();
            productModel.setImageUrl(imageId[i]);
            productModel.setName(listdate.get(i).getName());
            String pinyin = characterParser.getSelling(listdate.get(i).getName());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if(sortString.matches("[A-Z]")){
                productModel.setSortLetters(sortString.toUpperCase());
            }else{
                productModel.setSortLetters("#");
            }
            mListDate.add(productModel);
        }*/



        adapter = new UserModelAdapter(this,mListDate);
        mlistView.setAdapter(adapter);
        mbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mdeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClearEditText.setText("");
                mListDate.clear();
                for (int i = 0; i < listdate.size(); i++) {
                    SearchModel productModel  = new SearchModel();
                    productModel.setImageUrl(imageId[i]);
                    productModel.setPokemon_name(listdate.get(i).getPokemon_name());

                    mListDate.add(productModel);
                }
                adapter = new UserModelAdapter(UserActivity.this,mListDate);
                mlistView.setAdapter(adapter);
            }
        });
        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                finish();
            }
        });

        //根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                filterData(s.toString());

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }


    /**
     * 根据输入框中的值来过滤数据并更新ListView
     * @param filterStr
     */
    private void filterData(String filterStr){
        //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
        if(TextUtils.isEmpty(filterStr.toString())){
            Log.e("tag","空");
            mListDate.clear();
            for (int i = 0; i < listdate.size(); i++) {
                SearchModel mSm  = new SearchModel();
                mSm.setImageUrl(imageId[i]);
                mSm.setPokemon_name(listdate.get(i).getPokemon_name());

                mListDate.add(mSm);
            }
            adapter = new UserModelAdapter(UserActivity.this,mListDate);
            mlistView.setAdapter(adapter);
        }else{
            Log.e("tag","插屏bu显示");
            ArrayList<SearchModel> ldate=new ArrayList<SearchModel>();
            for (int i = 0; i < mListDate.size(); i++) {
                SearchModel productModel  = new SearchModel();
                productModel.setPokemon_name(listdate.get(i).getPokemon_name());
                productModel.setImageUrl(imageId[i]);
                String name = listdate.get(i).getPokemon_name();
                if(name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())){
                    ldate.add(productModel);
                }


            }
            adapter = new UserModelAdapter(UserActivity.this,ldate);
            mlistView.setAdapter(adapter);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }


     /* public void onBackButtonClick(){
         finish();
    }

    public void onDeleteButtonClick(){
        finish();
    }*/

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();

        MobclickAgent.onResume(this);

    }





}
