package com.casey.smartbutter.fragment;
/*
* 项目名： SmartButter
* 包名：   com.casey.smartbutter.fragment
* 文件名： ButlerFragment
* 创建者： Casey
* 创建时间：2017/9/18 18:47
* 描述：   美女社区
*/

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.casey.smartbutter.R;
import com.casey.smartbutter.adapter.GirdAdapter;
import com.casey.smartbutter.entity.GirlData;
import com.casey.smartbutter.utils.PicassoUtils;
import com.casey.smartbutter.view.CustomDialog;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

public class GirlFragment extends Fragment {

    private GridView mGridView;
    //数据
    private List<GirlData> mList = new ArrayList<>();
    //适配器
    private GirdAdapter mAdapter;
    //提示框
    private CustomDialog dialog;
    //预览图片
    private ImageView iv_img;
    //图片地址的数据
    private List<String> mListUrl = new ArrayList<>();
    //PhotoView
    private PhotoViewAttacher mAttacher;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_girl, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mGridView = view.findViewById(R.id.mGridView);

        //初始化提示框
        dialog = new CustomDialog(getActivity(), LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, R.layout.dialog_girl, R.style.Theme_dialog
                , Gravity.CENTER, R.style.pop_anim_style);
        iv_img = dialog.findViewById(R.id.iv_img);

        String welfare = null;

        try {
            welfare = URLEncoder.encode("福利", "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        RxVolley.get("http://gank.io/api/search/query/listview/category/" + welfare
                + "/count/50/page/1", new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                parsingJson(t);
            }
        });

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //解析图片
                PicassoUtils.loadImageView(getActivity(), mListUrl.get(i), iv_img);
                //缩放
                mAttacher = new PhotoViewAttacher(iv_img);
                //刷新
                mAttacher.update();
                dialog.show();
            }
        });
    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = (JSONObject) jsonArray.get(i);
                String url = json.getString("url");
                mListUrl.add(url);

                GirlData data = new GirlData();
                data.setImgUrl(url);
                mList.add(data);
            }
            mAdapter = new GirdAdapter(getActivity(), mList);
            mGridView.setAdapter(mAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
