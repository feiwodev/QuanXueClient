package com.zeno.quanxueclient.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/7.
 *
 * 首页数据bean , 组合提供
 */

public class MainBean {

    private List<MainSliders.DataBean> imageSliders;
    private List<String> bulletins ;
    private List<Category> categories;

    public List<MainSliders.DataBean> getImageSliders() {
        return imageSliders;
    }

    public void setImageSliders(List<MainSliders.DataBean> imageSliders) {
        this.imageSliders = imageSliders;
    }

    public List<String> getBulletins() {
        return bulletins;
    }

    public void setBulletins(List<String> bulletins) {
        this.bulletins = bulletins;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
