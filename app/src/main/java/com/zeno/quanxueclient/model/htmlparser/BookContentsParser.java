package com.zeno.quanxueclient.model.htmlparser;
// +----------------------------------------------------------------------
// | QuanXue
// +----------------------------------------------------------------------
// | CreateDate : 2016/12/15
// +----------------------------------------------------------------------
// | Author: Zeno <zhuyongit@gmail.com>
// +----------------------------------------------------------------------

import com.zeno.quanxueclient.App;
import com.zeno.quanxueclient.bean.BookContentsBean;
import com.zeno.quanxueclient.db.gen.BookContentsBeanDao;
import com.zeno.quanxueclient.db.gen.DaoSession;
import com.zeno.quanxueclient.utils.StringUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 书籍目录解析
 */

public class BookContentsParser {

    public static List<BookContentsBean> parser(String categoryUrl, String bookUrl, String content){

        List<BookContentsBean> bookContentsBeanList = new ArrayList<>();
        DaoSession daoSession = App.getInstance().getDaoSession();
        BookContentsBeanDao contentBeanDao = daoSession.getBookContentsBeanDao();

        Document document = Jsoup.parse(content);
        Elements introClass = document.getElementsByClass("indextdintr");
        if (introClass.size()>0)
        {
            Element element = introClass.get(0);
            System.out.println(element.getElementsByTag("p").text());
        }

        Elements tdTags = document.getElementsByTag("td");
        for(Element td : tdTags) {
            Elements aTags = td.getElementsByTag("a");
            if (aTags != null && !aTags.text().isEmpty())
            {

                String href = aTags.attr("href");
                String target = aTags.attr("target");
                if (href != null && target.isEmpty())
                {
                    BookContentsBean bookContentsBean = new BookContentsBean();
                    bookContentsBean.setBookUrl(bookUrl);
                    bookContentsBean.setSectionName(aTags.text());
                    bookContentsBean.setSectionUrl(StringUtils.filterHtmlUrl(categoryUrl)+href);

                    bookContentsBeanList.add(bookContentsBean);
                    contentBeanDao.insert(bookContentsBean);
                }

            }
        }

        return bookContentsBeanList;
    }
}
