package com.zeno.quanxueclient.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/7.
 */

public class Bulletins {


    /**
     * rs_status : 1
     * rs_msg : 查询数据成功
     * data : [{"id":16,"type":0,"content":"发生范德萨发大水的发生飞洒","create_time":1480756033,"update_time":1480756033}]
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

    public List<String> getBulletins() {
        List<String> arrays = new ArrayList<>();
        List<DataBean> data = getData();
        for (DataBean d: data) {
            arrays.add(d.getContent());
        }

        return arrays;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 16
         * type : 0
         * content : 发生范德萨发大水的发生飞洒
         * create_time : 1480756033
         * update_time : 1480756033
         */

        private int id;
        private int type;
        private String content;
        private int create_time;
        private int update_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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
    }
}
