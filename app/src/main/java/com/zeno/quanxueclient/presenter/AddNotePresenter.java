package com.zeno.quanxueclient.presenter;
// +----------------------------------------------------------------------
// | QuanXue
// +----------------------------------------------------------------------
// | CreateDate : 2016/12/19
// +----------------------------------------------------------------------
// | Author: Zeno <zhuyongit@gmail.com>
// +----------------------------------------------------------------------

import com.zeno.quanxueclient.bean.NoteBean;
import com.zeno.quanxueclient.model.AddNoteModelImp;
import com.zeno.quanxueclient.model.minterface.IAddNoteModel;
import com.zeno.quanxueclient.view.vinterface.IAddNoteView;

public class AddNotePresenter extends BasePresenter<IAddNoteView> {

    private IAddNoteView iAddNoteView;
    private IAddNoteModel iAddNoteModel = new AddNoteModelImp();

    public AddNotePresenter(IAddNoteView iAddNoteView) {
        this.iAddNoteView = iAddNoteView;
    }

    public void addNote(NoteBean noteBean) {
        if (iAddNoteModel != null) {
            iAddNoteModel.saveNote(noteBean, new IAddNoteModel.SaveListener() {
                @Override
                public void onSuccess() {
                    if (iAddNoteView != null)
                        iAddNoteView.showAddSuccess();
                }

                @Override
                public void onFailure() {
                    if (iAddNoteView != null)
                        iAddNoteView.showAddFailure();
                }
            });
        }
    }
}
