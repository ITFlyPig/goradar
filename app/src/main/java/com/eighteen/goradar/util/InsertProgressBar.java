package com.eighteen.goradar.util;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

/**
 * Created by 作者：${zhangzhizhen} on 2017/6/19.
 * Email: 190042477@qq.com
 */
public class InsertProgressBar {

    private RelativeLayout relative ;
    private ProgressBar bar;
    private Context context;
    private int rate = 0;

    protected static final int STOP = 0;
    protected static final int NEXT = 1;

    public InsertProgressBar(Context c) {
        super();
        context = (Context) c;
//        relative = (RelativeLayout)context.findViewById(R.id.relative);
        bar = new ProgressBar(context);
//设置ProgressBar的高宽和显示位置
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        params.getRules()[RelativeLayout.CENTER_IN_PARENT] = RelativeLayout.TRUE;
        bar.setLayoutParams(params);
    }

    public void insertBar() {
        bar.setProgress(0);
//当ProgressBar正在运行时就不再创建ProgressBar
        if(!bar.isShown()){
            bar.setVisibility(View.VISIBLE);
            relative.addView(bar);
            new MyThread().start();
        }

    }

    class MyThread extends Thread{

        @Override
        public void run() {
            super.run();

            try {
                for(int i=0;i<25;i++){
                    rate = (i+1)*4;
                    if(i!=24){
                        Message msg = new Message();
                        msg.what = NEXT;
                        myhandler.sendMessage(msg);
                    }else{
                        Message msg = new Message();
                        msg.what = STOP;
                        myhandler.sendMessage(msg);
                    }
                    MyThread.sleep(200);
                }
            } catch (Exception e) {
            }

        }

    }
    //创建一个Handle 接收消息
    private Handler myhandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case STOP:
                    bar.setVisibility(View.GONE);
                    relative.removeView(bar);
                    Thread.currentThread().interrupt();
                    break;
                case NEXT:
                    bar.setProgress(rate);
                    break;
            }
        }
    };
}
