package com.casey.smartbutter.view;
/*
* 项目名： SmartButter
* 包名：   com.casey.smartbutter.view
* 文件名： DispatchLinearLayout
* 创建者： Casey
* 创建时间：2017/9/26 14:57
* 描述：   事件分发
*/

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.LinearLayout;

public class DispatchLinearLayout extends LinearLayout {

    public DispatchKeyEventListener getDispatchKeyEventListener() {
        return dispatchKeyEventListener;
    }

    public void setDispatchKeyEventListener(DispatchKeyEventListener dispatchKeyEventListener) {
        this.dispatchKeyEventListener = dispatchKeyEventListener;
    }

    private DispatchKeyEventListener dispatchKeyEventListener;

    public DispatchLinearLayout(Context context) {
        super(context);
    }

    public DispatchLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DispatchLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //接口
    public static interface DispatchKeyEventListener{
        boolean dispatchKeyEvent(KeyEvent event);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        //如果不为空 说明调用了去获取事件
        if (dispatchKeyEventListener != null) {
            return dispatchKeyEventListener.dispatchKeyEvent(event);
        }
        return super.dispatchKeyEvent(event);
    }
}
