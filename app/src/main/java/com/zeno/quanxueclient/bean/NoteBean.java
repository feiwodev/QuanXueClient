package com.zeno.quanxueclient.bean;
// +----------------------------------------------------------------------
// | QuanXue
// +----------------------------------------------------------------------
// | CreateDate : 2016/12/19
// +----------------------------------------------------------------------
// | Author: Zeno <zhuyongit@gmail.com>
// +----------------------------------------------------------------------

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

@Entity
public class NoteBean implements Parcelable {

    @Id(autoincrement = true)
    private Long id = null; // 如果仅仅是作为id使用，那么用long和Long都行，如果是自增长的，必须使用Long。
    private String bookUrl;
    private String bookContent;
    private String userContent;
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookUrl() {
        return bookUrl;
    }

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }

    public String getBookContent() {
        return bookContent;
    }

    public void setBookContent(String bookContent) {
        this.bookContent = bookContent;
    }

    public String getUserContent() {
        return userContent;
    }

    public void setUserContent(String userContent) {
        this.userContent = userContent;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.bookUrl);
        dest.writeString(this.bookContent);
        dest.writeString(this.userContent);
        dest.writeLong(this.createTime != null ? this.createTime.getTime() : -1);
    }

    public NoteBean() {
    }

    protected NoteBean(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.bookUrl = in.readString();
        this.bookContent = in.readString();
        this.userContent = in.readString();
        long tmpCreateTime = in.readLong();
        this.createTime = tmpCreateTime == -1 ? null : new Date(tmpCreateTime);
    }

    @Generated(hash = 1845553203)
    public NoteBean(Long id, String bookUrl, String bookContent, String userContent,
            Date createTime) {
        this.id = id;
        this.bookUrl = bookUrl;
        this.bookContent = bookContent;
        this.userContent = userContent;
        this.createTime = createTime;
    }

    public static final Creator<NoteBean> CREATOR = new Creator<NoteBean>() {
        @Override
        public NoteBean createFromParcel(Parcel source) {
            return new NoteBean(source);
        }

        @Override
        public NoteBean[] newArray(int size) {
            return new NoteBean[size];
        }
    };

    @Override
    public String toString() {
        return "NoteBean{" +
                "id=" + id +
                ", bookUrl='" + bookUrl + '\'' +
                ", bookContent='" + bookContent + '\'' +
                ", userContent='" + userContent + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
