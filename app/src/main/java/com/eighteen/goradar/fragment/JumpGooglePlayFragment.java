package com.eighteen.goradar.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eighteen.goradar.R;
import com.umeng.analytics.MobclickAgent;

import java.util.Locale;

/**
 * Created by dellpc on 2016/11/5.
 */
public class JumpGooglePlayFragment extends BaseFragment {
    public final static String TAG="PokedexFragment";
    WebView webView;
    private String language,country;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_jump_googleplay;
    }

    @Nullable
    @Override
    protected void initData(View v,Bundle savedInstanceState) {

        //获取本地语言
        language = Locale.getDefault().getLanguage();
        Log.e("tag",language);
        country = getResources().getConfiguration().locale.getCountry();
        Log.e("tag","country"+country);
        webView = (WebView) v.findViewById(R.id.webView);

        TextView iv_name=(TextView)v.findViewById(R.id.iv_name);
        TextView iv_type=(TextView)v.findViewById(R.id.iv_type);

        TextView name_ktv=(TextView)v.findViewById(R.id.name_ktv);
        TextView type_ktv=(TextView)v.findViewById(R.id.type_ktv);

      /*  TextView name_mine=(TextView)v.findViewById(R.id.name_mine);
        TextView type_mine=(TextView)v.findViewById(R.id.type_mine);

        TextView name_qr=(TextView)v.findViewById(R.id.name_qr);
        TextView type_qr=(TextView)v.findViewById(R.id.type_qr);

        TextView name_music=(TextView)v.findViewById(R.id.name_music);
        TextView type_music=(TextView)v.findViewById(R.id.type_music);
*/

        if (language.equals("zh")){
            Log.i(TAG, country+"initData: "+language);
            if (country.equals("CN")){
                iv_name.setText("InstaSave for Instagram");
                iv_type.setText("Instagram photo&video downloader");
                name_ktv.setText("Singing Lessons For Beginner");
                type_ktv.setText("Learn how to singing");
//                name_mine.setText("Minecraft Box");
//                type_mine.setText("Guides and skins for minecraft");
//                name_qr.setText("QR Code Reader");
//                type_qr.setText("Best QR/Bar Code  Scanner");
//                name_music.setText("iMini Music Player");
//                type_music.setText("A minimalist music player");
            }else{
                iv_name.setText("Instagram圖片&視頻下載");
                iv_type.setText("下載Instagram應用程式上的圖片&視頻");
                name_ktv.setText("KTV唱歌技巧");
                type_ktv.setText("教程學習如何發聲唱歌");
//                name_mine.setText("當個創世神攻略");
//                type_mine.setText("合成攻略視頻攻略皮膚下載");
//                name_qr.setText("QR碼掃描器");
//                type_qr.setText("QR碼條形碼讀取神器");
//                name_music.setText("iMini音樂播放器");
//                type_music.setText("完美的極簡音樂播放器");
            }
        }else if(language.equals("ja")){
            iv_name.setText("Instagram保存");
            iv_type.setText("インスタグラムの画像&動画保存");
            name_ktv.setText("Singing Lessons For Beginner");
            type_ktv.setText("Learn how to singing");
//            name_mine.setText("マイクラ攻略");
//            type_mine.setText("攻略forマイクラPE");
//            name_qr.setText("QRコードReader");
//            type_qr.setText("キューアールコードReader無料");
//
//            name_music.setText("iMini Music Player");
//            type_music.setText("A minimalist music player");

        }else{
            iv_name.setText("InstaSave for Instagram");
            iv_type.setText("Instagram photo&video downloader");
            name_ktv.setText("Singing Lessons For Beginner");
            type_ktv.setText("Learn how to singing");
//            name_mine.setText("Minecraft Box");
//            type_mine.setText("Guides and skins for minecraft");
//            name_qr.setText("QR Code Reader");
//            type_qr.setText("Best QR/Bar Code  Scanner");
//            name_music.setText("iMini Music Player");
//            type_music.setText("A minimalist music player");
        }

        RelativeLayout rl_one=(RelativeLayout)v.findViewById(R.id.rl_one);
        rl_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* webView.setVisibility(View.VISIBLE);
                webView.loadUrl("https://play.google.com/store/apps/details?id=" + "com.RekindleA.IVCalculator");
                //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
                webView.setWebViewClient(new WebViewClient(){
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        // TODO Auto-generated method stub
                        //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                        view.loadUrl(url);
                        return true;
                    }
                });
                //启用支持javascript
                WebSettings settings = webView.getSettings();
                settings.setJavaScriptEnabled(true);*/

                final String appPackageName = "com.tools.secondinssave"; // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.tools.secondinssave")));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.tools.secondinssave")));
                }

            }
        });
        RelativeLayout rl_ktv=(RelativeLayout)v.findViewById(R.id.rl_ktv);
        rl_ktv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.music.singinglessons")));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.music.singinglessons")));
                }

            }
        });

        //// TODO: 2017/5/19
     /*   RelativeLayout rl_threee=(RelativeLayout)v.findViewById(R.id.rl_threee);
        rl_threee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.gameguide.minebox")));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.gameguide.minebox")));
                }

            }
        });

        //// TODO: 2017/5/19
        RelativeLayout rl_four=(RelativeLayout)v.findViewById(R.id.rl_four);
        rl_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.tools.QRCodeReader")));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.tools.QRCodeReader")));
                }

            }
        });
        //// TODO: 2017/5/19
        RelativeLayout rl_five=(RelativeLayout)v.findViewById(R.id.rl_five);
        rl_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.tools.iMinMusicPlayerFree")));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.tools.iMinMusicPlayerFree")));
                }

            }
        });
*/
    }



    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getFragmentName());

    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getFragmentName());
    }


    @Override
    protected void initVariables(Bundle savedInstanceState) {

    }

    @Override
    protected void handleUIEvent() {

    }
}
