package com.casey.smartbutter.ui;
/*
* 项目名： SmartButter
* 包名：   com.casey.smartbutter.ui
* 文件名： SettingActivity
* 创建者： Casey
* 创建时间：2017/9/18 19:18
* 描述：   设置
*/

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.casey.smartbutter.R;
import com.casey.smartbutter.service.SmsService;
import com.casey.smartbutter.utils.ShareUtils;
import com.xys.libzxing.zxing.activity.CaptureActivity;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    //语音播报
    private Switch sw_speak;
    //智能短信
    private Switch sw_sms;

    //扫一扫
    private LinearLayout ll_scan;
    //扫描结果
    private TextView tv_scan_result;
    //生成二维码
    private LinearLayout ll_qr_code;
    //我的位置
    private LinearLayout ll_my_location;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    //初始化View
    private void initView() {
        sw_speak = (Switch) findViewById(R.id.sw_speak);
        sw_speak.setOnClickListener(this);
        boolean isSpeak = ShareUtils.getBoolean(this, "isSpeak", false);
        sw_speak.setChecked(isSpeak);

        sw_sms = (Switch) findViewById(R.id.sw_sms);
        sw_sms.setOnClickListener(this);
        boolean isSms = ShareUtils.getBoolean(this, "isSms", false);
        sw_sms.setChecked(isSms);

        ll_scan = (LinearLayout) findViewById(R.id.ll_scan);
        ll_scan.setOnClickListener(this);
        tv_scan_result = (TextView) findViewById(R.id.tv_scan_result);
        ll_qr_code = (LinearLayout) findViewById(R.id.ll_qr_code);
        ll_qr_code.setOnClickListener(this);
        ll_my_location = (LinearLayout) findViewById(R.id.ll_my_location);
        ll_my_location.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sw_speak:
                //切换相反
                sw_speak.setSelected(!sw_speak.isSelected());
                //保存状态
                ShareUtils.putBoolean(this, "isSpeak", sw_speak.isChecked());
                break;
            case R.id.sw_sms:
                //切换相反
                sw_sms.setSelected(!sw_sms.isChecked());
                //保存状态
                ShareUtils.putBoolean(this, "isSms", sw_sms.isChecked());
                if (sw_sms.isChecked()) {
                    startService(new Intent(SettingActivity.this, SmsService.class));
                } else {
                    stopService(new Intent(SettingActivity.this, SmsService.class));
                }
                break;
            case R.id.ll_scan:
                //打开二维码界面扫描条形码或二维码
                Intent openCameraIntent = new Intent(SettingActivity.this, CaptureActivity.class);
                startActivityForResult(openCameraIntent,0);
                break;
            case R.id.ll_qr_code:
                startActivity(new Intent(SettingActivity.this,QrCodeActivity.class));
                break;
            case R.id.ll_my_location:
                startActivity(new Intent(this,LocationActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            tv_scan_result.setText(scanResult);
        }
    }
}
