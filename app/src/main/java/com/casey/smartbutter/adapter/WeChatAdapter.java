package com.casey.smartbutter.adapter;
/*
* 项目名： SmartButter
* 包名：   com.casey.smartbutter.adapter
* 文件名： WeChatAdapter
* 创建者： Casey
* 创建时间：2017/9/25 11:10
* 描述：   微信精选
*/

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.casey.smartbutter.R;
import com.casey.smartbutter.entity.WeChatData;
import com.casey.smartbutter.utils.PicassoUtils;

import java.util.List;

public class WeChatAdapter extends BaseAdapter{
    private Context mContext;
    private LayoutInflater inflater;
    private List<WeChatData> mList;
    private WeChatData data;
    private int width;
    private int height;
    private WindowManager wm;

    public WeChatAdapter(Context mContext, List<WeChatData> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();

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
            view = inflater.inflate(R.layout.wechat_item, null);
            viewHolder.iv_img = view.findViewById(R.id.iv_imgs);
            viewHolder.tv_title = view.findViewById(R.id.tv_title);
            viewHolder.tv_source = view.findViewById(R.id.tv_source);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        data = mList.get(i);
        viewHolder.tv_title.setText(data.getTitle());
        viewHolder.tv_source.setText(data.getSource());
        //加载图片
        PicassoUtils.loadImageViewSize(mContext,data.getImgUrl(),width/3,200,viewHolder.iv_img);
        return view;
    }

    class ViewHolder{
        private ImageView iv_img;
        private TextView tv_title;
        private TextView tv_source;
    }
}
