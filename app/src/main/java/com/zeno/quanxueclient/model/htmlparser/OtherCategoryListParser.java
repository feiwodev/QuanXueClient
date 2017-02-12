package com.zeno.quanxueclient.model.htmlparser;
// +----------------------------------------------------------------------
// | QuanXue
// +----------------------------------------------------------------------
// | CreateDate : 2016/12/14
// +----------------------------------------------------------------------
// | Author: Zeno <zhuyongit@gmail.com>
// +----------------------------------------------------------------------

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

public class OtherCategoryListParser {

    private static List<BookBean> books = new ArrayList<>();

    public static List<BookBean> parser(String content,String categoryUrl) {
        try{

            books.clear();
            Document document = Jsoup.parse(content);
            String baseUri = API.WEB_HOST;
            Element body = document.body();
            Elements trTag = body.getElementsByTag("tr");
            for(Element element : trTag) {
                Elements tdTag = element.getElementsByTag("td");
                if (tdTag.size() > 0)
                {
                    BookBean bookBean = new BookBean();
                    bookBean.setCategoryUrl(categoryUrl);
                    bookBean.setBaseUrl(baseUri);
                    bookBean.setAuthor(tdTag.get(0).text());
                    Element bookNameTag = tdTag.get(1);
                    Elements aTag = bookNameTag.getElementsByTag("a");
                    String bookUrl = StringUtils.filterHtmlUrl(categoryUrl) + aTag.attr("href");
                    bookBean.setUrl(bookUrl);
                    bookBean.setName(bookNameTag.text());

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

                    BookBean bookBean1 = new BookBean();
                    bookBean1.setBaseUrl(baseUri);
                    bookBean1.setCategoryUrl(categoryUrl);
                    if (tdTag.get(2).text().isEmpty() && !tdTag.get(3).text().isEmpty())
                    {

                        bookBean1.setAuthor(tdTag.get(3).text());
                        bookNameTag = tdTag.get(4);
                        aTag = bookNameTag.getElementsByTag("a");
                        bookUrl = StringUtils.filterHtmlUrl(categoryUrl) + aTag.attr("href");
                        bookBean1.setUrl(bookUrl);
                        bookBean1.setName(bookNameTag.text());
                    }else{
                        if (tdTag.get(2) != null && !tdTag.text().isEmpty())
                        {
                            bookBean1.setAuthor(tdTag.get(2).text());
                            bookNameTag = tdTag.get(3);
                            aTag = bookNameTag.getElementsByTag("a");
                            bookUrl = StringUtils.filterHtmlUrl(categoryUrl) + aTag.attr("href");
                            bookBean1.setUrl(bookUrl);
                            bookBean1.setName(bookNameTag.text());
                        }

                    }

                    /*产生随机颜色封面*/
                    int r1 = (int) (Math.random() * 255);
                    int g1 = (int) (Math.random() * 255);
                    int b1 = (int) (Math.random() * 255);
                    StringBuilder rgbBuilder1 = new StringBuilder();
                    rgbBuilder1.append(r1);
                    rgbBuilder1.append(",");
                    rgbBuilder1.append(g1);
                    rgbBuilder1.append(",");
                    rgbBuilder1.append(b1);
                    bookBean1.setCoverRgb(rgbBuilder1.toString());

                    books.add(bookBean);
                    books.add(bookBean1);

                }

            }

        }catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }
}
