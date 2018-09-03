package com.eighteen.goradar.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eighteen.goradar.R;
import com.eighteen.goradar.model.VideoModel;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hyzx on 2016/10/10.
 */
public class VideoAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater mLayoutInflater;
    private List<VideoModel> list,dblist;
    private String language,country;
    private Handler mHandler = null;
    public VideoAdapter(Context context, List<VideoModel> regionModelList, String language, String country){
        this.context = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.list = regionModelList;
        this.language=language;
        this.country=country;
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
        private TextView name,price,address;
        private ImageView imageView;
        private RelativeLayout rll_bg;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final   ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView  = mLayoutInflater.inflate(R.layout.video_item,null);
            holder.name = (TextView) convertView.findViewById( R.id.name);
            holder.imageView = (ImageView) convertView.findViewById( R.id.image);
            holder.rll_bg = (RelativeLayout)convertView.findViewById(R.id.rll_bg);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        SharedPreferences sharedPreferences= context.getSharedPreferences("video", Activity.MODE_PRIVATE);
        String name =sharedPreferences.getString(list.get(position).getId(), "");
//        notifyDataSetChanged();
        if (name.equals("1")){
            holder.name.setTextColor(context.getResources().getColor(R.color.contentcolor));
        }else{
            holder.name.setTextColor(context.getResources().getColor(R.color.title_color));
        }

        if (!TextUtils.isEmpty(list.get(position).getTitle())){
            holder.name.setText(list.get(position).getTitle());
        }
//        if (language.equals("en")){
//            if (country.equals("AS")){
//                holder.imageView.setImageResource(R.drawable.l0001);
//                if(list.get(position).getBitmap()==null){
//                    Glide.with(context).load(R.drawable.load).into(holder.imageView);
//                    new DownloadFileFromURL(holder.imageView,position,list).execute(list.get(position).getImageurl());
//
//                }else{
//                    holder.imageView.setImageBitmap(list.get(position).getBitmap());
//                }
//            }else{
//                Glide.with(context).load(list.get(position).getLogo_URL()).into(holder.imageView);
//            }
//
//        }else{
//            Glide.with(context).load(list.get(position).getLogo_URL()).into(holder.imageView);
//        }

        Glide.with(context).load(list.get(position).getLogo_URL()).into(holder.imageView);


        convertView.setBackgroundResource(R.drawable.listselectortwo);
        return convertView;
    }


    class DownloadFileFromURL extends AsyncTask<String, String, Bitmap> {

        public ImageView image;
         private int index;
       List<VideoModel> list;
        public DownloadFileFromURL(ImageView image,int index,List<VideoModel> list){
            this.image = image;
            this.index = index;
            this.list = list;
        }


        @Override
        protected Bitmap doInBackground(String... f_url) {
            Log.i("tag", "doInBackground: " + f_url[0]);
            Bitmap bitmap = null;
            //MediaMetadataRetriever 是android中定义好的一个类，提供了统一
            //的接口，用于从输入的媒体文件中取得帧和元数据；
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            try {
                //根据文件路径获取缩略图
                retriever.setDataSource(f_url[0], new HashMap());
                //获得第一帧图片
                bitmap = retriever.getFrameAtTime();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } finally {
                retriever.release();
            }
            return bitmap;
        }

        protected void onProgressUpdate(String... progress) {
            image.setImageResource(R.drawable.load);
        }

        @Override
        protected void onPostExecute(final Bitmap file_url) {
            Log.i("tag", index+"onPostExecute:fileurl"+file_url);
           //            notifyDataSetChanged();
//            image.setImageBitmap(file_url);
            list.get(index).setBitmap(file_url);
            notifyDataSetChanged();
        }
    }

    /**
     * 给出url，获取视频的第一帧
     *
     * @param url
     * @return
     */
    public static Bitmap getVideoThumbnail(String url) {
        Bitmap bitmap = null;
        //MediaMetadataRetriever 是android中定义好的一个类，提供了统一
        //的接口，用于从输入的媒体文件中取得帧和元数据；
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据文件路径获取缩略图
            retriever.setDataSource(url, new HashMap());
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;
    }
}
