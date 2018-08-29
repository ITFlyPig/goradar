package com.eighteen.goradar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.eighteen.goradar.db.AssetsDatabaseManager;
import com.eighteen.goradar.db.UserDao;
import com.eighteen.goradar.model.SearchModel;
import com.eighteen.goradar.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;


public class PokemonDetailsWithFragments extends FragmentActivity {

    String name; //name of pokemon on focused page
    String list_id;

    List<SearchModel> pokemonList; //iterate through this list populate viewpager
    PokemonData pokeDex;
    SearchModel foundPokemon;

    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 1000;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;


    //根据传过来的name获取数据库的英文名
    private UserDao mUserDao;
    private List<SearchModel> listdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokemon_details_pager);
        name = getIntent().getExtras().getString("pokemonName");
        list_id = getIntent().getExtras().getString("list_id");
//        Toast.makeText(PokemonDetailsWithFragments.this,list_id,Toast.LENGTH_LONG).show();
        Initialization();
    }

    public void Initialization(){
        //初始化db数据管理类
        AssetsDatabaseManager.initManager(PokemonDetailsWithFragments.this);
        //获取的db数据库的数据
        mUserDao = new UserDao();
        listdata=new ArrayList<SearchModel>();
        //获取本地语言
        String language = Locale.getDefault().getLanguage();
        Log.e("tag",language);
        String country = getResources().getConfiguration().locale.getCountry();
        Log.e("tag","country"+country);

        listdata = mUserDao.queryPokemon_name(list_id);

        Log.e("tag",listdata.size()+"country"+listdata.toString());
        String mPokemon_name="";
        if (listdata.size()>0){
            mPokemon_name = listdata.get(0).getName();
        }
        pokemonList = new ArrayList<>();
        pokeDex = new PokemonData();
        this.pokemonList = pokeDex.getPokemonList();
        Iterator<SearchModel> itr = this.pokemonList.iterator();
        foundPokemon = itr.next();


        /**
         * Get the page we want to focus by
         * iterating until name is found. Focus page is index-1
         */
        int focusPage = 0;
        if (!mPokemon_name.equals(foundPokemon.name)) {
            while (!mPokemon_name.equals(foundPokemon.name)) {
                focusPage++;
                foundPokemon = itr.next();
            }
        }

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        //give the adapter multiple sets of 'pages' to provide a circular effect
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(focusPage+302);

    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent;
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.EvoMenu:
                myIntent = new Intent(this, EvolutionCalc.class);
                PokemonDetailsWithFragments.this.startActivity(myIntent);
                finish();
                return true;
            case R.id.PDexMenu:
                myIntent = new Intent(this, PokemonListView.class);
                PokemonDetailsWithFragments.this.startActivity(myIntent);
                finish();
                return true;
            case R.id.PCalcMenu:
                myIntent = new Intent(this, PidgeyCalculator.class);
                PokemonDetailsWithFragments.this.startActivity(myIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    /**
     * A simple pager adapter that represents 5 {@link PokemonDetailsFragment} objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PokemonDetailsFragment.create(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

}