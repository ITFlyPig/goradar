package com.eighteen.goradar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eighteen.goradar.model.SearchModel;
import com.eighteen.goradar.R;

import java.util.List;

/**
 * Created by hyzx on 2016/10/10.
 */
public class UserModelAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater mLayoutInflater;
    private List<SearchModel> list;
    public UserModelAdapter(Context context, List<SearchModel> regionModelList){
        this.context = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.list = regionModelList;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    static class ViewHolder{
        private TextView textViewtitle,textViewContext,load;
        private ImageView head_image;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final   ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView  = mLayoutInflater.inflate(R.layout.user_list_item,null);
            holder.head_image = (ImageView) convertView.findViewById(R.id.head_image);
            holder.textViewContext = (TextView) convertView.findViewById(R.id.textview_text);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        convertView.setBackgroundResource(R.drawable.listselectortwo);
        holder.textViewContext.setText(list.get(position).getPokemon_name());
        holder.head_image.setBackgroundResource(R.drawable.pokemon);

        return convertView;
    }

}
