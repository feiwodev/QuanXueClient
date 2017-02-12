package com.zeno.quanxueclient.bean;
// +----------------------------------------------------------------------
// | QuanXue
// +----------------------------------------------------------------------
// | CreateDate : 2016/12/13
// +----------------------------------------------------------------------
// | Author: Zeno <zhuyongit@gmail.com>
// +----------------------------------------------------------------------

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 书籍列表bean
 */

@Entity
public class BookBean implements Parcelable {

    @Id(autoincrement = true)
    private Long id = null;
    private String author;
    private String name;
    private String baseUrl;
    private String categoryUrl;
    private String url;
    private String desc;
    private String coverRgb;
    private short isCollection;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getCategoryUrl() {
        return categoryUrl;
    }

    public void setCategoryUrl(String categoryUrl) {
        this.categoryUrl = categoryUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCoverRgb() {
        return coverRgb;
    }

    public void setCoverRgb(String coverRgb) {
        this.coverRgb = coverRgb;
    }

    public short getIsCollection() {
        return isCollection;
    }

    public void setIsCollection(short isCollection) {
        this.isCollection = isCollection;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.author);
        dest.writeString(this.name);
        dest.writeString(this.baseUrl);
        dest.writeString(this.categoryUrl);
        dest.writeString(this.url);
        dest.writeString(this.desc);
        dest.writeString(this.coverRgb);
        dest.writeInt(this.isCollection);
    }

    public BookBean() {
    }

    protected BookBean(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.author = in.readString();
        this.name = in.readString();
        this.baseUrl = in.readString();
        this.categoryUrl = in.readString();
        this.url = in.readString();
        this.desc = in.readString();
        this.coverRgb = in.readString();
        this.isCollection = (short) in.readInt();
    }

    @Generated(hash = 743637948)
    public BookBean(Long id, String author, String name, String baseUrl,
            String categoryUrl, String url, String desc, String coverRgb,
            short isCollection) {
        this.id = id;
        this.author = author;
        this.name = name;
        this.baseUrl = baseUrl;
        this.categoryUrl = categoryUrl;
        this.url = url;
        this.desc = desc;
        this.coverRgb = coverRgb;
        this.isCollection = isCollection;
    }

    public static final Creator<BookBean> CREATOR = new Creator<BookBean>() {
        @Override
        public BookBean createFromParcel(Parcel source) {
            return new BookBean(source);
        }

        @Override
        public BookBean[] newArray(int size) {
            return new BookBean[size];
        }
    };


    @Override
    public String toString() {
        return "BookBean{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", name='" + name + '\'' +
                ", baseUrl='" + baseUrl + '\'' +
                ", categoryUrl='" + categoryUrl + '\'' +
                ", url='" + url + '\'' +
                ", desc='" + desc + '\'' +
                ", coverRgb='" + coverRgb + '\'' +
                ", isCollection=" + isCollection +
                '}';
    }
}
