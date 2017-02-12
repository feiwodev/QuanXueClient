package com.zeno.quanxueclient.model.htmlparser;
// +----------------------------------------------------------------------
// | QuanXue
// +----------------------------------------------------------------------
// | CreateDate : 2016/12/12
// +----------------------------------------------------------------------
// | Author: Zeno <zhuyongit@gmail.com>
// +----------------------------------------------------------------------

import android.util.Log;

import com.zeno.quanxueclient.App;
import com.zeno.quanxueclient.bean.Category;
import com.zeno.quanxueclient.db.gen.CategoryDao;
import com.zeno.quanxueclient.db.gen.DaoSession;
import com.zeno.quanxueclient.net.API;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


/**
 * 首页类别html解析
 */
public class MainCategoryParser {
    private static final String TAG = MainCategoryParser.class.getSimpleName();

    private static List<Category> categories = new ArrayList<>();

    public static List<Category> paser(String content) {
        DaoSession daoSession = App.getInstance().getDaoSession();
        CategoryDao categoryDao = daoSession.getCategoryDao();

        Document document = Jsoup.parse(content);
        // 得到html->body的内容
        Element body = document.body();
        // 得到<div class='half1'>...</div> 模块内容
        Elements elementsByClass = body.getElementsByClass("half1");
        int size = elementsByClass.size();
        for(int i=0 ; i < size-4 ; i++) {
            // 过滤前面模块内容， 如群号，通知之类
            if (size > 3 && i > 3)
            {
                // 得到每个模块里面的内容
                Element element = elementsByClass.get(i);
                // 域名
                final String baseUri = API.WEB_HOST;
                // 获取<a></a>标签内容
                Elements aTag = element.getElementsByTag("a");
                // 获取<a href='xxx'></a>标签href属性内容
                String ahref = aTag.attr("href");
                // 拼接模块指向的url地址
                final String sectionUrl = baseUri+ahref;
                // 获取<a><h></h></a>中的h标签
                Element hTag = aTag.get(0);
                // 得到h标签的内容
                final String sectionName = hTag.text();
                // 获取img标签
                Elements tagImg = element.getElementsByTag("img");
                // 得到img src属性
                String imgTagSrc = tagImg.attr("src");
                // 拼接模块图片地址
                final String sectionPicUrl = baseUri + imgTagSrc;
                // 获取p标签元素
                Elements pTag = element.getElementsByTag("p");
                // 得到p标签元素里面的内容
                final String sectionDesc = pTag.text();

                Category category = new Category();
                category.setName(sectionName.replace("■ ", ""));
                category.setDesc(sectionDesc);
                category.setPicUrl(sectionPicUrl);
                category.setWebBaseUrl(baseUri);
                category.setUrl(sectionUrl);
                categoryDao.insert(category);
                categories.add(category);

            }
        }
        return categories;
    }
}
