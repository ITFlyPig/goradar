package com.eighteen.goradar.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eighteen.goradar.PokemonListInfoActivity;
import com.eighteen.goradar.R;
import com.eighteen.goradar.model.DataModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dellpc on 2016/11/7.
 */
public class PlantsAdapter extends RecyclerView.Adapter<PlantsAdapter.DynamicViewHolder>  {

    private  LayoutInflater mLayoutInflater;
    private  Context mContext;
    private ArrayList<DataModel> listData;
    public PlantsAdapter(Context context, ArrayList<DataModel> mlistData) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.listData = mlistData;
    }

    @Override
    public DynamicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DynamicViewHolder(mLayoutInflater.inflate(R.layout.grid_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(DynamicViewHolder holder, int position) {
        holder.textNum.setText("#"+listData.get(position).getListid());
        if (listData.get(position).getType_1()==null||listData.get(position).getType_1().equals("")){
            holder.type_one.setVisibility(View.INVISIBLE);
        }else{
            holder.type_one.setVisibility(View.VISIBLE);
            holder.type_one.setText(listData.get(position).getType_1());
        }
        if (listData.get(position).getType_2()==null||listData.get(position).getType_2().equals("")){
            holder.type_two.setVisibility(View.INVISIBLE);
        }else{
            holder.type_two.setVisibility(View.VISIBLE);
            holder.type_two.setText(listData.get(position).getType_2());
        }
        holder.image_icon.setBackgroundResource(listData.get(position).getImageUrl());
        holder.text_title.setText(listData.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return  listData.size();
    }

    public  class DynamicViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_icon)
        ImageView image_icon;
        @BindView(R.id.type_one)
        TextView type_one;
        @BindView(R.id.textNum)
        TextView textNum;
        @BindView(R.id.text_title)
        TextView text_title;
        @BindView(R.id.type_two)
        TextView type_two;

        DynamicViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext,PokemonListInfoActivity.class);
                    //Putting the Id of image as an extra in intent
                    intent.putExtra("pokemonName",listData.get(getPosition()).getName() );
                    intent.putExtra("list_id",listData.get(getPosition()).getListid() );
                    intent.putExtra("language",listData.get(getPosition()).getLanguage());
                    //Here we will pass the previously created intent as parameter
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
