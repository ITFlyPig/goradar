package com.eighteen.goradar.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eighteen.goradar.model.DataModel;
import com.eighteen.goradar.R;

import java.util.List;

/**
 * Created by hyzx on 2016/10/10.
 */
public class GridViewAdapter extends BaseAdapter {
   private Context context;
     private LayoutInflater mLayoutInflater;
    private List<DataModel> list;
    private Handler handler;
    public GridViewAdapter(Context context, List<DataModel> regionModelList){
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
        private TextView textNum,type_one,type_two,text_title;
        private ImageView image_icon;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final   ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView  = mLayoutInflater.inflate(R.layout.grid_item_view,null);
            holder.textNum = (TextView) convertView.findViewById(R.id.textNum);
            holder.type_one = (TextView) convertView.findViewById(R.id.type_one);
            holder.type_two = (TextView) convertView.findViewById(R.id.type_two);
            holder.image_icon = (ImageView) convertView.findViewById(R.id.image_icon);
            holder.text_title = (TextView) convertView.findViewById(R.id.text_title);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.textNum.setText("#"+list.get(position).getListid());
        if (list.get(position).getType_1()==null||list.get(position).getType_1().equals("")){
            holder.type_one.setVisibility(View.INVISIBLE);
        }else{
            holder.type_one.setVisibility(View.VISIBLE);
            holder.type_one.setText(list.get(position).getType_1());
        }
        if (list.get(position).getType_2()==null||list.get(position).getType_2().equals("")){
            holder.type_two.setVisibility(View.INVISIBLE);
        }else{
            holder.type_two.setVisibility(View.VISIBLE);
            holder.type_two.setText(list.get(position).getType_2());
        }
        holder.image_icon.setBackgroundResource(list.get(position).getImageUrl());
        holder.text_title.setText(list.get(position).getName());
       /* SharedPreferences sharedPreferences= context.getSharedPreferences("zzz", Activity.MODE_PRIVATE);
        String name =sharedPreferences.getString(list.get(position).getListid(), "");
        if (name.equals("1")){
            holder.textViewtitle.setTextColor(context.getResources().getColor(R.color.contentcolor));
        }else{
            holder.textViewtitle.setTextColor(context.getResources().getColor(R.color.title_color));
        }

        if((position+1)%7==0){
            holder.textViewtitle.setText(list.get(position).getTitle());
            holder.textViewContext.setText(list.get(position).getDescriptor());
            holder.adView.setVisibility(View.VISIBLE);
            holder.load.setVisibility(View.VISIBLE);
            AdRequest request = new AdRequest.Builder().build();
            holder.adView.loadAd(request);

            holder.adView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    holder.load.setVisibility(View.GONE);
                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                }

                @Override
                public void onAdOpened() {
                    // Code to be executed when an ad opens an overlay that
                    // covers the screen.
                }

                @Override
                public void onAdLeftApplication() {
                    // Code to be executed when the user has left the app.
                }

                @Override
                public void onAdClosed() {
                    // Code to be executed when when the user is about to return
                    // to the app after tapping on an ad.
                }
            });

        }else{
            holder.adView.setVisibility(View.GONE);
            holder.load.setVisibility(View.GONE);
            holder.textViewtitle.setText(list.get(position).getTitle());
            holder.textViewContext.setText(list.get(position).getDescriptor());
        }*/
        convertView.setBackgroundResource(R.drawable.listselectortwo);
//        if (list.get(position).getaBoolean()==true){
//            //点击过
//            holder.textViewtitle.setTextColor(context.getResources().getColor(R.color.contentcolor));
////            convertView.setBackgroundResource(R.drawable.listselectortwo);
//        }else{
//            holder.textViewtitle.setTextColor(context.getResources().getColor(R.color.title_color));
//            convertView.setBackgroundResource(R.drawable.listselectortwo);
//        }


        return convertView;
    }

}
