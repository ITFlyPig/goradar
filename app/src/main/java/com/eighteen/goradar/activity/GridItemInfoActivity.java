package com.eighteen.goradar.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.eighteen.goradar.util.StatusBarUtil;
import com.eighteen.goradar.util.TxtReader;
import com.eighteen.goradar.R;
import com.umeng.analytics.MobclickAgent;

import java.io.InputStream;

public class GridItemInfoActivity extends Activity {

    private TextView mTitle_text,mNum,mType_one,mType_two;
    private Bundle bundle;
    private String  name,num;
    private ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_item_info);
        bundle = this.getIntent().getExtras();
        StatusBarUtil.setColor(this, getResources().getColor(R.color.appcolor));
        initfindById();
    }

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

    public void initfindById() {
        mTitle_text = (TextView) findViewById(R.id.title_text);
        mNum = (TextView) findViewById(R.id.num_text);
        mImageView = (ImageView) findViewById(R.id.imageView);
        mType_one = (TextView) findViewById(R.id.type_one);
        mType_two = (TextView) findViewById(R.id.type_two);
        mTitle_text.setText(bundle.getString("title"));
        mNum.setText("#" + bundle.getString("num"));
        mImageView.setBackgroundResource(bundle.getInt("image"));
        InputStream inputStream = getResources().openRawResource(R.raw.attack);
        String string = TxtReader.getString(inputStream);
//        Log.i("tag", "attack--：" + string.toString());
        //1 先获取到全部数据
        //2 获取点击过来对应的那个条数据

        String[] temp = null;
        if (string != null && !string.equals("")) {
            temp = string.split(";");
            for (int i = 0; i < temp.length; i++) {
//                Log.i("tag", "第" + i + "行" + temp[i]);
                //这是每行001
                String horizontal = temp[i];
                //每行根据：拆分
                String[] horizon = horizontal.split(":");
                //如果每行数据》1，获取第1个位置的（001后面的）
//                if (horizon.length > 1) {
                //判断是否是对应那条数据然后获取对应的id的数据，
//                    Log.i("tag", "strNum[0]现在获取的--："+ horizon[0]);
//                    Log.i("tag", "原来的--：" + bundle.getString("num"));
                String a=bundle.getString("num");
                String b=horizon[0];
                if (b.contains(a)){
                    //一般技能
                    String general_skill = horizon[1];
                    String[] general = general_skill.split(",");
                    if (general.length > 0) {
                        //获取逗号之前的，第0个位置
                        if (!general[0].equals("") && general[0] != null) {
                            mType_one.setText(general[0]);
                            if (!general[2].equals("")&&general[2]!=null) {
                                mType_one.setText("");
                                mType_one.setText(general[0]+"\n"+general[2]);
                                Log.i("tag", "第" + i + "你好-->" + general[0]+"\n"+general[2]);
                            }
                        }
                    }

                    //能量技能
                    String energy_skills = horizon[2];
                    Log.i("tag", "哈哈哈：" + energy_skills);
                    String[] energy = energy_skills.split(",");
                    if(energy.length>0){
                        if (!energy[0].equals("") || energy[0] != null) {
                            mType_two.setText(energy[0]);
                            Log.i("tag", "第" + i + "嘿嘿1" + general[0]);
                            if (!energy[2].equals("") || energy[2] != null) {
                                mType_two.setText("");
                                mType_two.setText(energy[0] + "\n" + energy[2]);
                                Log.i("tag", "第" + i + "嘿嘿2" + energy[0] + "\n" + energy[2]);
                                if (!energy[4].equals("") || energy[4] != null) {
                                    mType_two.setText("");
                                    mType_two.setText(energy[0] + "\n" + energy[2] + "\n" + energy[4]);
                                    Log.i("tag", "第" + i + "嘿嘿3" +energy[0] + "\n" + energy[2] + "\n" + energy[4]);
                                }
                            }
                        }

                    }



                }else{
                    Toast.makeText(this,"111",Toast.LENGTH_LONG).show();
                }







//                    String energy_skills = horizon[2];
//                    String[] energy = energy_skills.split(",");
//                    Log.i("tag", "第" + i + "行-->能量技能" + energy.length);
//                    if (!energy[0].equals("") || energy[0] != null) {
//                        Log.i("tag", "第" + i + "行能量技能" + energy[0]);
//                        mType_two.setText(energy[0]);
//                    }
//                    if (!energy[2].equals("") || energy[2] != null) {
//                        Log.i("tag", "第" + i + "行能量技能" + energy[2]);
//                        mType_two.setText(energy[0] + "\n" + energy[2]);
//                    }
//                    if (!energy[4].equals("") || energy[4] != null) {
//                        Log.i("tag", "第" + i + "行能量技能" + energy[2]);
//                        mType_two.setText(energy[0] + "\n" + energy[2] + "\n" + energy[4]);
//                    }
                // }
            }
        }
    }




    public void onBackBtnClick(View v){
        finish();
    }

}
