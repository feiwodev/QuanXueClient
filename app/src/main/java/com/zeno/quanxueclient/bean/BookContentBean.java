package com.zeno.quanxueclient.bean;
// +----------------------------------------------------------------------
// | QuanXue
// +----------------------------------------------------------------------
// | CreateDate : 2016/12/19
// +----------------------------------------------------------------------
// | Author: Zeno <zhuyongit@gmail.com>
// +----------------------------------------------------------------------

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class BookContentBean {
    private String sectionUrl;
    private String content;

    @Generated(hash = 1354644983)
    public BookContentBean(String sectionUrl, String content) {
        this.sectionUrl = sectionUrl;
        this.content = content;
    }

    @Generated(hash = 225088435)
    public BookContentBean() {
    }

    public String getSectionUrl() {
        return sectionUrl;
    }

    public void setSectionUrl(String sectionUrl) {
        this.sectionUrl = sectionUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
