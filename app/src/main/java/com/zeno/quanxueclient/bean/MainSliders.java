package com.zeno.quanxueclient.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2016/12/7.
 */

public class MainSliders {


    /**
     * rs_status : 1
     * rs_msg : 查询数据成功
     * data : [{"id":7,"intro":"周振甫（1911年\u20142000年），原名麟瑞，笔名振甫，后以笔名行，浙江平湖人。中华书局编审,著名学者，古典诗词，文论专家，资深编辑家。","url":"http://quanxue.cn/QT_MingXiang/ZhouYiQJIndex.html","image":"http://120.77.65.80/quanxue/public/uploads/20161203\\28a192e2470f7f3b036902b3e06dc5b0.jpg","create_time":1480737183,"update_time":1480758782}]
     */

    private int rs_status;
    private String rs_msg;
    private List<DataBean> data;

    public int getRs_status() {
        return rs_status;
    }

    public void setRs_status(int rs_status) {
        this.rs_status = rs_status;
    }

    public String getRs_msg() {
        return rs_msg;
    }

    public void setRs_msg(String rs_msg) {
        this.rs_msg = rs_msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        /**
         * id : 7
         * intro : 周振甫（1911年—2000年），原名麟瑞，笔名振甫，后以笔名行，浙江平湖人。中华书局编审,著名学者，古典诗词，文论专家，资深编辑家。
         * url : http://quanxue.cn/QT_MingXiang/ZhouYiQJIndex.html
         * image : http://120.77.65.80/quanxue/public/uploads/20161203\28a192e2470f7f3b036902b3e06dc5b0.jpg
         * create_time : 1480737183
         * update_time : 1480758782
         */

        private int id;
        private String intro;
        private String url;
        private String image;
        private int create_time;
        private int update_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(int update_time) {
            this.update_time = update_time;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.intro);
            dest.writeString(this.url);
            dest.writeString(this.image);
            dest.writeInt(this.create_time);
            dest.writeInt(this.update_time);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.id = in.readInt();
            this.intro = in.readString();
            this.url = in.readString();
            this.image = in.readString();
            this.create_time = in.readInt();
            this.update_time = in.readInt();
        }

        public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", intro='" + intro + '\'' +
                    ", url='" + url + '\'' +
                    ", image='" + image + '\'' +
                    ", create_time=" + create_time +
                    ", update_time=" + update_time +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MainSliders{" +
                "rs_status=" + rs_status +
                ", rs_msg='" + rs_msg + '\'' +
                ", data=" + data +
                '}';
    }
}
