package com.eighteen.goradar.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eighteen.goradar.R;
import com.eighteen.goradar.activity.WebViewActivity;
import com.eighteen.goradar.model.GamesModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dellpc on 2016/11/7.
 */
public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.DynamicViewHolder>  {

    private  LayoutInflater mLayoutInflater;
    private  Context mContext;
    private ArrayList<GamesModel> listData;
    public GamesAdapter(Context context, ArrayList<GamesModel> mlistData) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.listData = mlistData;
    }

    @Override
    public DynamicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DynamicViewHolder(mLayoutInflater.inflate(R.layout.games_item, parent, false));
    }

    @Override
    public void onBindViewHolder(DynamicViewHolder holder, int position) {
        if (!TextUtils.isEmpty("listData.get(position).getImageUrl()")){
            holder.pic.setBackgroundResource(listData.get(position).getImageUrl());
        }
        if (!TextUtils.isEmpty("listData.get(position).getGame_name()")){
            holder.mTextView.setText(listData.get(position).getGame_name());
        }


    }

    @Override
    public int getItemCount() {
        return  listData.size();
    }

    public  class DynamicViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView mTextView;
        @BindView(R.id.pic)
        ImageView pic;

        DynamicViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, WebViewActivity.class);

                    intent.putExtra("index",getPosition());
                    intent.putExtra("url",listData.get(getPosition()).getUrl());
//                    intent.putExtra("feature",listData.get(getPosition()).getFeature());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
