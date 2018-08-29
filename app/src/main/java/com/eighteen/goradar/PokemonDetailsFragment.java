package com.eighteen.goradar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.eighteen.goradar.model.SearchModel;
import com.eighteen.goradar.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class that stores pokedex data as a fragment for access by
 * a screenslider pager adapter
 */

public class PokemonDetailsFragment extends Fragment {

    /** Stores data for swipe right */
    SearchModel previousPokemon;

    /** Store data for current page */
    SearchModel foundPokemon;

    /** Datea for swipe left */
    SearchModel nextPokemon;

    /** Holds all available pokemon data */
    PokemonData pokeDex;

    /** Stats of current pokemon */
    MoveStats moveStats;

    List<AttackMove> pokemonAttacks;
    List<SearchModel> pokemonList;

    TextView pname;
    TextView type;
    TextView weight;
    TextView maxCP;
    TextView candyToEvolve;
    TextView tempTextView;

    TextView title;
    Button mBtnBack;
    String name;
    public static final String ARG_PAGE = "page";
    private int mPageNumber;

    private AdView mAdView;

    private com.facebook.ads.AdView fackbook_ads;


    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static PokemonDetailsFragment create(int pageNumber) {
        PokemonDetailsFragment fragment = new PokemonDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        /** Mod 151 so that the pages can 'loop' and appear to be infinite */
        mPageNumber = getArguments().getInt(ARG_PAGE) % 151;

        name = getActivity().getIntent().getExtras().getString("pokemonName");

        pokemonList = new ArrayList<>();
        pokemonAttacks = new ArrayList<>();
        pokeDex = new PokemonData();
        moveStats = new MoveStats();

        this.pokemonList = pokeDex.getPokemonList();
        Iterator<SearchModel> itr = this.pokemonList.iterator();
        foundPokemon = itr.next();

        int i = 0;
        while (i != mPageNumber) {
            foundPokemon = itr.next();
            i++;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.activity_pokemon_list_info, container, false);

      /* fackbook_ads = new com.facebook.ads.AdView(getActivity(), getString(R.string.fackbook), AdSize.BANNER_HEIGHT_50);
        // Find the main layout of your activity
        // Add the ad view to your activity layout
        layout.addView(fackbook_ads);
        // Request to load an ad
        fackbook_ads.loadAd();*/

        mBtnBack=(Button)rootView.findViewById(R.id.backBtn);
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        mAdView = (AdView)rootView.findViewById(R.id.adView);
        //  创建AdRequest 加载广告横幅adview
        AdRequest adRequest = new AdRequest.Builder().build();
        // 最后，请求广告。
        mAdView.loadAd(adRequest);
        Log.i("tag","----------name"+name);
//        title.setText(name);

        setPokemonAttacks(rootView);

        return rootView;
    }

    void setPokemonAttacks(View v) {
        /**
         *  Get info about attacks:
         *  myAttacks [0-1] are primary attacks
         *  myAttacks[2-4] are special attacks
         */

        AttackMove tempAttack;
        pokemonAttacks = moveStats.getMoveList();
        Iterator<AttackMove> aItr = pokemonAttacks.iterator();
        tempAttack = aItr.next();
        while (aItr.hasNext()) {
            if (tempAttack.moveName.equals(foundPokemon.mainAttack1)) {

                tempTextView = (TextView) v.findViewById(R.id.AttackName1);
                tempTextView.setText(foundPokemon.mainAttack1);

                tempTextView = (TextView) v.findViewById(R.id.AttackType1);
                tempTextView.setText("Type: " + tempAttack.getType());

                tempTextView = (TextView) v.findViewById(R.id.DPS1);
                tempTextView.setText("DPS: " + tempAttack.getDps());

                tempTextView = (TextView) v.findViewById(R.id.Damage1);
                tempTextView.setText("Damage: " + tempAttack.getDamage());
            } else if (tempAttack.moveName.equals(foundPokemon.mainAttack2)) {

                tempTextView = (TextView) v.findViewById(R.id.AttackName2);
                tempTextView.setText(foundPokemon.mainAttack2);

                tempTextView = (TextView) v.findViewById(R.id.AttackType2);
                tempTextView.setText("Type: " + tempAttack.getType());

                tempTextView = (TextView) v.findViewById(R.id.DPS2);
                tempTextView.setText("DPS: " + tempAttack.getDps());

                tempTextView = (TextView) v.findViewById(R.id.Damage2);
                tempTextView.setText("Damage: " + tempAttack.getDamage());
            } else if (tempAttack.moveName.equals(foundPokemon.subAttack1)) {
                tempTextView = (TextView) v.findViewById(R.id.SpecialName1);
                tempTextView.setText(foundPokemon.subAttack1);

                tempTextView = (TextView) v.findViewById(R.id.SpecialType1);
                tempTextView.setText("Type: " + tempAttack.getType());

                tempTextView = (TextView) v.findViewById(R.id.SpecialDPS1);
                tempTextView.setText("DPS: " + tempAttack.getDps());

                tempTextView = (TextView) v.findViewById(R.id.SpecialDamage1);
                tempTextView.setText("Damage: " + tempAttack.getDamage());
            } else if (tempAttack.moveName.equals(foundPokemon.subAttack2)) {

                tempTextView = (TextView) v.findViewById(R.id.SpecialName2);
                tempTextView.setText(foundPokemon.subAttack2);

                tempTextView = (TextView) v.findViewById(R.id.SpecialType2);
                tempTextView.setText("Type: " + tempAttack.getType());

                tempTextView = (TextView) v.findViewById(R.id.SpecialDPS2);
                tempTextView.setText("DPS: " + tempAttack.getDps());

                tempTextView = (TextView) v.findViewById(R.id.SpecialDamage2);
                tempTextView.setText("Damage: " + tempAttack.getDamage());
            } else if (tempAttack.moveName.equals(foundPokemon.subAttack3)) {
                tempTextView = (TextView) v.findViewById(R.id.SpecialName3);
                tempTextView.setText(foundPokemon.subAttack3);

                tempTextView = (TextView) v.findViewById(R.id.SpecialType3);
                tempTextView.setText("Type: " + tempAttack.getType());

                tempTextView = (TextView) v.findViewById(R.id.SpecialDPS3);
                tempTextView.setText("DPS: " + tempAttack.getDps());

                tempTextView = (TextView) v.findViewById(R.id.SpecialDamage3);
                tempTextView.setText("Damage: " + tempAttack.getDamage());
            }
            mBtnBack=(Button)v.findViewById(R.id.backBtn);
            mBtnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().finish();
                }
            });
            tempAttack = aItr.next();
        }

        /** set text fields for pokemon info */
        pname = (TextView) v.findViewById(R.id.PokemonName);
        pname.setText(name);
//        pname.setText(foundPokemon.name);

        type = (TextView) v.findViewById(R.id.PokemonType);
        type.setText("Type(s): " + foundPokemon.type);
        if (!foundPokemon.subtype.equals("")) {
            type.setText(type.getText() + " / " + foundPokemon.subtype);
        }

        weight = (TextView) v.findViewById(R.id.PokemonWeight);
        weight.setText("Weight: " + foundPokemon.weight);

        maxCP = (TextView) v.findViewById(R.id.PokemonMaxCP);
        maxCP.setText("Max CP: " + foundPokemon.maxCP);

        candyToEvolve = (TextView) v.findViewById(R.id.PokemonEvoCost);
        candyToEvolve.setText("Evo Cost: " + foundPokemon.candyToEvolve);

        mBtnBack=(Button)v.findViewById(R.id.backBtn);
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }

    public int getPageNumber() {
        return mPageNumber;
    }
}


