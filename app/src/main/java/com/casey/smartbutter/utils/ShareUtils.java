package com.casey.smartbutter.utils;
/*
* 项目名： SmartButter
* 包名：   com.casey.smartbutter.utils
* 文件名： ShareUtils
* 创建者： Casey
* 创建时间：2017/9/18 20:16
* 描述：   SharedPreferences封装类
*/

import android.content.Context;
import android.content.SharedPreferences;

public class ShareUtils {
    private static final String NAME = "config";

    /**
     *
     * @param mContext 上下文
     * @param key 键
     * @param value 值
     */
    public static void putString(Context mContext,String key,String value){
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key,value).commit();
    }

    /**
     *
     * @param mContext 上下文
     * @param key 键
     * @param deValue 默认值
     * @return
     */
    public static String getString(Context mContext, String key, String deValue) {
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getString(key, deValue);
    }

    /**
     *
     * @param mContext 上下文
     * @param key 键
     * @param value 值
     */
    public static void putInt(Context mContext,String key,int value){
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putInt(key,value).commit();
    }

    /**
     *
     * @param mContext 上下文
     * @param key 键
     * @param deValue 默认值
     * @return
     */
    public static int getInt(Context mContext, String key, int deValue) {
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, deValue);
    }

    /**
     *
     * @param mContext 上下文
     * @param key 键
     * @param value 值
     */
    public static void putBoolean(Context mContext,String key,boolean value){
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();
    }

    /**
     *
     * @param mContext 上下文
     * @param key 键
     * @param deValue 默认值
     * @return
     */
    public static boolean getBoolean(Context mContext, String key, boolean deValue) {
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, deValue);
    }

    //删除 单个
    public static void deleShare(Context mContext, String key) {
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().remove(key);
    }

    //删除 全部
    public static void deleAll(Context mContext) {
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }
}
