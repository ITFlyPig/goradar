package com.eighteen.goradar.base;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.eighteen.goradar.util.StatusBarUtil;
import com.eighteen.goradar.R;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by ${张志珍} on 2016/12/2217:00.
 * Super Mario RunDemo
 * com.superguide.supermariorunsecond.base
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        setStatusBar();
    }


    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.appcolor));
    }

    // 使用Intent实现activity跳转
    public void goActivity(Class<?> activity) {
        Intent intent = new Intent();
        intent.setClass(this, activity);
        startActivity(intent);
    }

    /**
     * 跳转页面
     *
     * @param activity
     *            你想要跳转的activity
     * @param paName
     *            值的名称
     * @param param
     *            值
     * @param pName
     *            值的名称
     * @param pname
     *            值
     * @param paaName
     *            值的名称
     * @param panaame
     *         值
     */
    public void goActivity(Class<?> activity, String paName, String param,
                           String pName, String pname, String paaName, String panaame) {
        Intent intent = new Intent(this, activity);
        intent.putExtra(paName, param);
        intent.putExtra(pName, pname);
        intent.putExtra(paaName, panaame);
        startActivity(intent);
    }

    /**
     * 弹出消息提示层。
     *
     * @param message
     */
    public void AlertToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
                .show();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


}
