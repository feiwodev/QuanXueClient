package com.zeno.quanxueclient.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zeno.quanxueclient.R;
import com.zeno.quanxueclient.presenter.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class PersonalCenterActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    /**
     * 显示个人中心
     *
     * @param context
     */
    public static void startPersonalCenterView(Context context) {
        Intent intent = new Intent(context, PersonalCenterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_personal_center);

        setToolBar(toolbar,this);
    }

    @OnClick({R.id.tv_ll_all_note_list,R.id.tv_all_book_collection})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_ll_all_note_list: /*全部笔记*/
                NoteListActivity.showNoteListView(this,"","");
                break;
            case R.id.tv_all_book_collection: /*全部收藏*/
                CollectionListActivity.showCollectionLisView(this);
                break;
        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
