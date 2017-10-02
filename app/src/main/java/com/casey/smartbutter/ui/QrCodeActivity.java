package com.casey.smartbutter.ui;
/*
* 项目名： SmartButter
* 包名：   com.casey.smartbutter.ui
* 文件名： QrCodeActivity
* 创建者： Casey
* 创建时间：2017/9/27 9:54
* 描述：   二维码分享
*/

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.casey.smartbutter.R;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

public class QrCodeActivity extends BaseActivity{

    private ImageView iv_qr_code;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);

        initView();
    }

    private void initView() {
        iv_qr_code = (ImageView) findViewById(R.id.iv_qr_code);

        int width = getResources().getDisplayMetrics().widthPixels;

        Bitmap qrCodeBitmap = EncodingUtils.createQRCode("智能管家", width / 2, width / 2,
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        iv_qr_code.setImageBitmap(qrCodeBitmap);
    }
}
