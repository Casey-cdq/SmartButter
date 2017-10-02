package com.casey.smartbutter.adapter;
/*
* 项目名： SmartButter
* 包名：   com.casey.smartbutter.adapter
* 文件名： ChatListAdapter
* 创建者： Casey
* 创建时间：2017/9/25 9:37
* 描述：   对话列表适配器
*/

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.casey.smartbutter.R;
import com.casey.smartbutter.entity.ChatListData;

import java.util.List;

public class ChatListAdapter extends BaseAdapter {

    //左边的type
    public static final int VALUE_LEFT_TEXT = 1;
    //右边的type
    public static final int VALUE_RIGHT_TEXT = 2;

    private Context mContext;
    private LayoutInflater inflater;
    private ChatListData data;
    private List<ChatListData> mList;

    public ChatListAdapter(Context mContext, List<ChatListData> mList) {
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
        ViewHolderLeftText viewHolderLeftText = null;
        ViewHolderRightText viewHolderRightText = null;
        //获取当前要显示的type 根据这个type来区分数据的加载
        int type = getItemViewType(i);
        switch (type) {
            case VALUE_LEFT_TEXT:
                if (view == null) {
                    viewHolderLeftText = new ViewHolderLeftText();
                    view = inflater.inflate(R.layout.left_item, null);
                    viewHolderLeftText.tv_left_text = view.findViewById(R.id.tv_left_text);
                    view.setTag(viewHolderLeftText);
                } else {
                    viewHolderLeftText = (ViewHolderLeftText) view.getTag();
                }
                break;
            case VALUE_RIGHT_TEXT:
                if (view == null) {
                    viewHolderRightText = new ViewHolderRightText();
                    view = inflater.inflate(R.layout.right_item, null);
                    viewHolderRightText.tv_right_text = view.findViewById(R.id.tv_right_text);
                    view.setTag(viewHolderRightText);
                } else {
                    viewHolderRightText = (ViewHolderRightText) view.getTag();
                }
                break;
        }

        //赋值
        ChatListData data = mList.get(i);
        switch (type) {
            case VALUE_LEFT_TEXT:
                viewHolderLeftText.tv_left_text.setText(data.getText());
                break;
            case VALUE_RIGHT_TEXT:
                viewHolderRightText.tv_right_text.setText(data.getText());
                break;
        }

        return view;
    }

    //根据数据源的position来返回要显示的item
    @Override
    public int getItemViewType(int position) {
        ChatListData data = mList.get(position);
        int type = data.getType();
        return type;
    }

    //返回所有的Layout数据
    @Override
    public int getViewTypeCount() {
        return 3;
    }

    //左边的文本
    class ViewHolderLeftText {
        private TextView tv_left_text;
    }

    //右边的文本
    class ViewHolderRightText {
        private TextView tv_right_text;
    }
}
