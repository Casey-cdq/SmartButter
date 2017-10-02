package com.casey.smartbutter.adapter;
/*
* 项目名： SmartButter
* 包名：   com.casey.smartbutter.adapter
* 文件名： CourierAdapter
* 创建者： Casey
* 创建时间：2017/9/22 19:03
* 描述：   快递查询
*/

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.casey.smartbutter.R;
import com.casey.smartbutter.entity.CourierData;

import java.util.List;

public class CourierAdapter extends BaseAdapter{
    private Context mContext;
    private List<CourierData> mList;
    //布局加载器
    private LayoutInflater inflater;
    private CourierData data;

    public CourierAdapter(Context mContext, List<CourierData> mList) {
        this.mContext = mContext;
        this.mList = mList;
        //获取系统服务
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        ViewHodler viewHodler = null;
        if (view == null) {
            viewHodler = new ViewHodler();
            view = inflater.inflate(R.layout.layout_courier_item, null);
            viewHodler.tv_remark = view.findViewById(R.id.tv_remark);
            viewHodler.tv_zone = view.findViewById(R.id.tv_zone);
            viewHodler.tv_datetime = view.findViewById(R.id.tv_datetime);
            //设置缓存
            view.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) view.getTag();
        }
        //设置数据
        data = mList.get(i);
        viewHodler.tv_remark.setText(data.getRemark());
        viewHodler.tv_zone.setText(data.getZone());
        viewHodler.tv_datetime.setText(data.getDatatime());
        return view;
    }

    class ViewHodler{
        private TextView tv_remark;
        private TextView tv_zone;
        private TextView tv_datetime;

    }
}
