package com.casey.smartbutter.service;
/*
* 项目名： SmartButter
* 包名：   com.casey.smartbutter.service
* 文件名： SmsService
* 创建者： Casey
* 创建时间：2017/9/26 9:36
* 描述：   短信服务
*/

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.SmsMessage;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.casey.smartbutter.R;
import com.casey.smartbutter.utils.StaticClass;
import com.casey.smartbutter.view.DispatchLinearLayout;

public class SmsService extends Service implements View.OnClickListener {

    private SmsReceiver smsReceiver;
    //发件人号码
    private String smsPhone;
    //短信内容
    private String smsContent;

    //窗口管理
    private WindowManager wm;
    //布局参数
    private WindowManager.LayoutParams layoutParams;
    private DispatchLinearLayout mView;

    private TextView tv_phone;
    private TextView tv_content;
    private Button btn_send_sms;

    private HomeWatchReceiver mHomeWatchReceiver;

    public static final String SYSTEM_DIALOGS_RESON_KEY = "reason";
    public static final String SYSTEM_DIALOGS_HOME_KEY = "homekey";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        init();
    }

    private void init() {

        //动态注册
        smsReceiver = new SmsReceiver();
        IntentFilter intent = new IntentFilter();
        //添加Action
        intent.addAction(StaticClass.SMS_ACTION);
        //设置权限
        intent.setPriority(Integer.MAX_VALUE);
        //注册
        registerReceiver(smsReceiver, intent);

        mHomeWatchReceiver = new HomeWatchReceiver();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        registerReceiver(mHomeWatchReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销
        unregisterReceiver(smsReceiver);
        unregisterReceiver(mHomeWatchReceiver);

    }

    //短信广播
    public class SmsReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (StaticClass.SMS_ACTION.equals(action)) {
                //获取短信内容返回的是一个Object数组
                Object[] objs = (Object[]) intent.getExtras().get("pdus");
                //遍历数组得到相关数据
                for (Object obj : objs) {
                    //把数组元素转换成短信对象
                    SmsMessage sms = SmsMessage.createFromPdu((byte[]) obj);
                    //发件人
                    smsPhone = sms.getOriginatingAddress();
                    //内容
                    smsContent = sms.getMessageBody();
                    showWindow();
                }
            }
        }

    }
    //窗口提示
    private void showWindow() {
        //获取系统服务
        wm = (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);
        //获取布局参数
        layoutParams = new WindowManager.LayoutParams();
        //定义宽高
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        //定义标记
        layoutParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        //定义格式
        layoutParams.format = PixelFormat.TRANSLUCENT;
        //定义类型
        layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        //加载布局
        mView = (DispatchLinearLayout) View.inflate(getApplicationContext(), R.layout.sms_item, null);

        tv_phone = mView.findViewById(R.id.tv_phone);
        tv_content = mView.findViewById(R.id.tv_content);
        btn_send_sms = mView.findViewById(R.id.btn_send_sms);
        btn_send_sms.setOnClickListener(this);

        tv_phone.setText("发件人：" + smsPhone);
        tv_content.setText(smsContent);
        //添加View到窗口
        wm.addView(mView,layoutParams);

        mView.setDispatchKeyEventListener(mDispatchKeyEventListener);
    }

    private DispatchLinearLayout.DispatchKeyEventListener mDispatchKeyEventListener =
            new DispatchLinearLayout.DispatchKeyEventListener() {
        @Override
        public boolean dispatchKeyEvent(KeyEvent event) {
            //判断是否是按了返回键
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                if (mView.getParent() != null) {
                    wm.removeView(mView);
                }
                return true;
            }
            return false;
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send_sms:
                sendSms();
                //消失窗口
                if (mView.getParent() != null) {
                    wm.removeView(mView);
                }
        }
    }

    //回复短信
    private void sendSms() {
        Uri uri = Uri.parse("smsto:" + smsPhone);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        //设置启动模式
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("sms_body", "");
        startActivity(intent);
    }

    //监听Home键的广播
    class HomeWatchReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_DIALOGS_RESON_KEY);
                if (SYSTEM_DIALOGS_HOME_KEY.equals(reason)) {
                    if (mView.getParent() != null) {
                        wm.removeView(mView);
                    }
                }
            }
        }
    }
}
