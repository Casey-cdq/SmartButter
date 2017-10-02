package com.casey.smartbutter.entity;
/*
* 项目名： SmartButter
* 包名：   com.casey.smartbutter.entity
* 文件名： CourierData
* 创建者： Casey
* 创建时间：2017/9/22 19:00
* 描述：   快递查询实体
*/

public class CourierData {
    //时间
    private String datatime;
    //状态
    private String remark;
    //城市
    private String zone;

    public String getDatatime() {
        return datatime;
    }

    public void setDatatime(String datatime) {
        this.datatime = datatime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    @Override
    public String toString() {
        return "CourierData{" +
                "datatime='" + datatime + '\'' +
                ", remark='" + remark + '\'' +
                ", zone='" + zone + '\'' +
                '}';
    }
}
