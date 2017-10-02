package com.casey.smartbutter.fragment;
/*
* 项目名： SmartButter
* 包名：   com.casey.smartbutter.fragment
* 文件名： ButlerFragment
* 创建者： Casey
* 创建时间：2017/9/18 18:47
* 描述：   微信精选
*/

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.casey.smartbutter.R;
import com.casey.smartbutter.adapter.WeChatAdapter;
import com.casey.smartbutter.entity.WeChatData;
import com.casey.smartbutter.ui.WebViewActivity;
import com.casey.smartbutter.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WechatFragment extends Fragment {

    private ListView mListView;
    private List<WeChatData> mList = new ArrayList<>();
    //标题
    private List<String> mListTitle = new ArrayList<>();
    //地址
    private List<String> mListUrl = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wechat, null);
        initView(view);
        return view;
    }

    //初始化View
    private void initView(View view) {
        mListView = view.findViewById(R.id.mListView);

        //解析接口
        String url = "http://v.juhe.cn/weixin/query?key=" + StaticClass.WECHAT_KEY;
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                parsingJson(t);
            }
        });

        //点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("title", mListTitle.get(i));
                intent.putExtra("url", mListUrl.get(i));
                startActivity(intent);
            }
        });
    }

    //解析Json
    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            JSONArray jsonList = jsonResult.getJSONArray("list");
            for (int i = 0; i < jsonList.length(); i++) {
                JSONObject json = (JSONObject) jsonList.get(i);
                WeChatData data = new WeChatData();
                String title = json.getString("title");
                String url = json.getString("url");
                data.setTitle(title);
                data.setSource(json.getString("source"));
                data.setImgUrl(json.getString("firstImg"));
                mList.add(data);

                mListTitle.add(title);
                mListUrl.add(url);
            }
            WeChatAdapter adapter = new WeChatAdapter(getActivity(), mList);
            mListView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
