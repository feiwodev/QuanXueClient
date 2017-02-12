package com.zeno.quanxueclient.adapter;
// +----------------------------------------------------------------------
// | QuanXue
// +----------------------------------------------------------------------
// | CreateDate : 2016/12/16
// +----------------------------------------------------------------------
// | Author: Zeno <zhuyongit@gmail.com>
// +----------------------------------------------------------------------

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.FrameLayout;

import com.elvishew.xlog.XLog;
import com.zeno.quanxueclient.App;
import com.zeno.quanxueclient.R;
import com.zeno.quanxueclient.bean.BookContentBean;
import com.zeno.quanxueclient.bean.BookContentsBean;
import com.zeno.quanxueclient.db.gen.BookContentBeanDao;
import com.zeno.quanxueclient.net.API;
import com.zeno.quanxueclient.net.HttpStringCallBack;
import com.zeno.quanxueclient.net.HttpUtils;
import com.zeno.quanxueclient.view.widget.X5WebView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

public class ReadBookContentAdapter extends BaseAdapter<BookContentsBean> {


    private final BookContentBeanDao mBookContentBeanDao;

    public ReadBookContentAdapter(@NonNull List<BookContentsBean> datas) {
        super(R.layout.item_read_book_content_layout, datas);
        mBookContentBeanDao = App.getInstance().getDaoSession().getBookContentBeanDao();
    }

    @Override
    protected void onBindView(final BaseViewHolder holder, final BookContentsBean bookContentsBean) {
        BookContentBean bookContent = isBookContent(bookContentsBean);
        if (bookContent == null) {
            HttpUtils.get(bookContentsBean.getSectionUrl(), new HttpStringCallBack() {
                @Override
                public void onSuccess(int what, String s) {

                    StringBuilder bodyString = getParserBookContent(s);

                    saveBookContent(bodyString, bookContentsBean);

                    showHtml(bodyString.toString(), holder);

                }

                @Override
                public void onFailed(int what, String s) {

                }
            });
        }else{
            showHtml(bookContent.getContent(), holder);
        }




    }

    private BookContentBean isBookContent(BookContentsBean bookContentsBean) {
        BookContentBean tempContentBean = null;
        List<BookContentBean> bookContentsBeanList = mBookContentBeanDao.queryBuilder()
                .where(BookContentBeanDao.Properties.SectionUrl
                        .eq(bookContentsBean.getSectionUrl())).list();
        if (bookContentsBeanList.size() >=1){
            BookContentBean contentBean = bookContentsBeanList.get(0);
            if (!TextUtils.isEmpty(contentBean.getContent())) {
                tempContentBean = contentBean;
            }
        }
        return tempContentBean;
    }

    /**
     * 显示html内容
     * @param bodyString
     * @param holder
     */
    private void showHtml(String bodyString, BaseViewHolder holder) {
        FrameLayout frameLayout = holder.getView(R.id.fl_book_content);
        X5WebView x5WebView =  new X5WebView(holder.itemView.getContext(),null);
        x5WebView.setFocusable(true);
        x5WebView.setFocusableInTouchMode(true);
        x5WebView.setClickable(true);

        x5WebView.loadDataWithBaseURL(API.WEB_HOST,bodyString.trim(),"text/html","utf-8","");
        frameLayout.addView(x5WebView, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 更新bookcontent表中的内容
     * @param bodyString
     * @param bookContentsBean
     */
    private void saveBookContent(StringBuilder bodyString, BookContentsBean bookContentsBean) {
         BookContentBean bookContentBean = new BookContentBean();
         bookContentBean.setSectionUrl(bookContentsBean.getSectionUrl());
         bookContentBean.setContent(bodyString.toString());
         mBookContentBeanDao.insert(bookContentBean);
    }

    /**
     * 将解析的html内容 ， 重新组装成新的html页面 ， 供webView解析。
     * @param s
     * @return
     */
    @NonNull
    private StringBuilder getParserBookContent(String s) {
        Document document = Jsoup.parse(s);
        StringBuilder bodyString = new StringBuilder();
        bodyString.append("<html>");
        bodyString.append("<head>");
        bodyString.append("<META http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
        bodyString.append("<style>");
        bodyString.append("p{font-size:20px;}");
        bodyString.append("</style>");
        bodyString.append("</head>");
        bodyString.append("<body>");
        bodyString.append("<h2>");
        bodyString.append(document.title());
        bodyString.append("</h2>");
        Element body = document.body();
        Elements p = body.getElementsByTag("p");
        for (Element element : p) {
            bodyString.append("<p>"+element.html()+"</p>");
        }
        bodyString.append("</body>");
        bodyString.append("</html>");
        return bodyString;
    }
}
