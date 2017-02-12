package com.zeno.quanxueclient.bean;
// +----------------------------------------------------------------------
// | QuanXue
// +----------------------------------------------------------------------
// | CreateDate : 2016/12/15
// +----------------------------------------------------------------------
// | Author: Zeno <zhuyongit@gmail.com>
// +----------------------------------------------------------------------

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

/**
 * 书籍内容
 */

@Entity
public class BookContentsBean implements Parcelable {

    
    private String bookUrl;
    private String sectionName;
    private String sectionUrl;


    public String getBookUrl() {
        return bookUrl;
    }

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }


    public String getSectionUrl() {
        return sectionUrl;
    }

    public void setSectionUrl(String sectionUrl) {
        this.sectionUrl = sectionUrl;
    }

    @Override
    public String toString() {
        return "BookContentsBean{" +
                "bookUrl='" + bookUrl + '\'' +
                ", sectionName='" + sectionName + '\'' +
                ", sectionUrl='" + sectionUrl + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.bookUrl);
        dest.writeString(this.sectionName);
        dest.writeString(this.sectionUrl);
    }

    public BookContentsBean() {
    }

    protected BookContentsBean(Parcel in) {
        this.bookUrl = in.readString();
        this.sectionName = in.readString();
        this.sectionUrl = in.readString();
    }

    @Generated(hash = 1345619931)
    public BookContentsBean(String bookUrl, String sectionName, String sectionUrl) {
        this.bookUrl = bookUrl;
        this.sectionName = sectionName;
        this.sectionUrl = sectionUrl;
    }

    public static final Creator<BookContentsBean> CREATOR = new Creator<BookContentsBean>() {
        @Override
        public BookContentsBean createFromParcel(Parcel source) {
            return new BookContentsBean(source);
        }

        @Override
        public BookContentsBean[] newArray(int size) {
            return new BookContentsBean[size];
        }
    };
}
