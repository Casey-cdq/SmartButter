package com.casey.smartbutter.entity;
/*
* 项目名： SmartButter
* 包名：   com.casey.smartbutter.entity
* 文件名： ChatListData
* 创建者： Casey
* 创建时间：2017/9/25 9:36
* 描述：   对话列表实体类
*/

public class ChatListData {

    //type
    private int type;
    //文本
    private String text;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
