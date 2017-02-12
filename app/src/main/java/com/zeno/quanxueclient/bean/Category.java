package com.zeno.quanxueclient.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2016/12/7.
 *
 * 首页分类 item bean
 */

@Entity
public class Category implements Parcelable {

    private String name;
    private String desc;
    private String picUrl;
    private String webBaseUrl;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getWebBaseUrl() {
        return webBaseUrl;
    }

    public void setWebBaseUrl(String webBaseUrl) {
        this.webBaseUrl = webBaseUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.desc);
        dest.writeString(this.picUrl);
        dest.writeString(this.webBaseUrl);
        dest.writeString(this.url);
    }

    public Category() {
    }

    protected Category(Parcel in) {
        this.name = in.readString();
        this.desc = in.readString();
        this.picUrl = in.readString();
        this.webBaseUrl = in.readString();
        this.url = in.readString();
    }

    @Generated(hash = 1160807505)
    public Category(String name, String desc, String picUrl, String webBaseUrl, String url) {
        this.name = name;
        this.desc = desc;
        this.picUrl = picUrl;
        this.webBaseUrl = webBaseUrl;
        this.url = url;
    }

    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", webBaseUrl='" + webBaseUrl + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
