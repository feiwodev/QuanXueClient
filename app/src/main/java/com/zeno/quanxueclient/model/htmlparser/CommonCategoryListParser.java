package com.zeno.quanxueclient.model.htmlparser;
// +----------------------------------------------------------------------
// | QuanXue
// +----------------------------------------------------------------------
// | CreateDate : 2016/12/13
// +----------------------------------------------------------------------
// | Author: Zeno <zhuyongit@gmail.com>
// +----------------------------------------------------------------------

/**
 * 书籍列表html解析
 */

import com.elvishew.xlog.XLog;
import com.zeno.quanxueclient.App;
import com.zeno.quanxueclient.bean.BookBean;
import com.zeno.quanxueclient.db.gen.BookBeanDao;
import com.zeno.quanxueclient.db.gen.DaoSession;
import com.zeno.quanxueclient.net.API;
import com.zeno.quanxueclient.utils.StringUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class CommonCategoryListParser {

    private static List<BookBean> books = new ArrayList<>();

    public static List<BookBean> parser(String content,String categoryUrl) {

        DaoSession daoSession = App.getInstance().getDaoSession();
        BookBeanDao bookBeanDao = daoSession.getBookBeanDao();

        try{

            books.clear();
            Document document = Jsoup.parse(content);
            String baseUri = API.WEB_HOST;
            Element body = document.body();
            Elements tableElements = body.getElementsByTag("tr");
            int tableSize = tableElements.size();
            XLog.e("category ---- "+categoryUrl);
            if (categoryUrl.contains("QT_MingXiang")) {
                for (int i = 6; i < tableSize; i++)
                {
                    htmlParser(baseUri, tableElements, i,categoryUrl,bookBeanDao);
                }
            }else{
                for (int i = 1; i < tableSize; i++)
                {
                    htmlParser(baseUri, tableElements, i,categoryUrl,bookBeanDao);
                }
            }


        }catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }

    /**
     * html解析
     * @param baseUri
     * @param tableElements
     * @param i
     * @param categoryUrl
     * @param bookBeanDao
     */
    private static void htmlParser(String baseUri, Elements tableElements, int i, String categoryUrl, BookBeanDao bookBeanDao)
    {

        Element element = tableElements.get(i);
        Elements tdTag = element.getElementsByTag("td");
        if (tdTag.size() > 0)
        {
            BookBean bookBean = new BookBean();
            bookBean.setCategoryUrl(categoryUrl);

            /*产生随机颜色封面*/
            int r = (int) (Math.random() * 255);
            int g = (int) (Math.random() * 255);
            int b = (int) (Math.random() * 255);
            StringBuilder rgbBuilder = new StringBuilder();
            rgbBuilder.append(r);
            rgbBuilder.append(",");
            rgbBuilder.append(g);
            rgbBuilder.append(",");
            rgbBuilder.append(b);
            bookBean.setCoverRgb(rgbBuilder.toString());

            bookBean.setBaseUrl(baseUri);
            bookBean.setAuthor(tdTag.get(0).text());

            Element authorTag = tdTag.get(1);
            Elements aTag = authorTag.getElementsByTag("a");
            bookBean.setName(aTag.text());

            String bookUrl = StringUtils.filterHtmlUrl(categoryUrl)+aTag.attr("href");
            bookBean.setUrl(bookUrl);

            Element bookDescTag = tdTag.get(2);
            bookBean.setDesc(bookDescTag.text());

            books.add(bookBean);
            bookBeanDao.insert(bookBean);
        }

    }
}
