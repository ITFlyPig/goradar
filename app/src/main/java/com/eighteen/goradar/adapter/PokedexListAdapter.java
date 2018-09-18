package com.eighteen.goradar.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eighteen.goradar.PokemonListInfoActivity;
import com.eighteen.goradar.R;
import com.eighteen.goradar.model.SearchModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dellpc on 2016/11/7.
 */
public class PokedexListAdapter extends RecyclerView.Adapter<PokedexListAdapter.DynamicViewHolder>  {

    private  LayoutInflater mLayoutInflater;
    private  Context mContext;
    private ArrayList<SearchModel> listData;
    public PokedexListAdapter(Context context, ArrayList<SearchModel> mlistData) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.listData = mlistData;
    }

    @Override
    public DynamicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DynamicViewHolder(mLayoutInflater.inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(DynamicViewHolder holder, int position) {
        holder.textViewtitle.setText(listData.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return  listData.size();
    }

    public  class DynamicViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textview_text)
        TextView textViewtitle;

        DynamicViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext,PokemonListInfoActivity.class);
                    //Putting the Id of image as an extra in intent
                    intent.putExtra("pokemonName",listData.get(getPosition()).getName() );
                    intent.putExtra("list_id",listData.get(getPosition()).getList_id() );
                    intent.putExtra("language",listData.get(getPosition()).getLanguage());

                    mContext.startActivity(intent);
                }
            });
        }
    }
}
