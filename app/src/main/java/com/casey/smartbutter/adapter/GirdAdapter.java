package com.casey.smartbutter.adapter;
/*
* 项目名： SmartButter
* 包名：   com.casey.smartbutter.adapter
* 文件名： GridAdapter
* 创建者： Casey
* 创建时间：2017/9/25 16:28
* 描述：   社区的适配器
*/

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.casey.smartbutter.R;
import com.casey.smartbutter.entity.GirlData;
import com.casey.smartbutter.utils.PicassoUtils;

import java.util.List;

public class GirdAdapter extends BaseAdapter{
    private Context mContext;
    private List<GirlData> mList;
    private LayoutInflater inflater;
    private GirlData data;
    private int width;
    private WindowManager wm;

    public GirdAdapter(Context mContext, List<GirlData> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.girl_item, null);
            viewHolder.imageView = view.findViewById(R.id.imageView);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        data = mList.get(i);
        //解析图片
        String url = data.getImgUrl();
        PicassoUtils.loadImageViewSize(mContext, url, width / 2, 500, viewHolder.imageView);

        return view;
    }

    class ViewHolder{
        private ImageView imageView;
    }
}
