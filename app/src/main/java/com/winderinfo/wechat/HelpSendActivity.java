package com.winderinfo.wechat;

import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.winderinfo.wechat.adapter.GroupSendAdapter;
import com.winderinfo.wechat.api.OnClickDialogBottomInterface;
import com.winderinfo.wechat.api.OnClickDialogInterface;
import com.winderinfo.wechat.base.BaseActivity;
import com.winderinfo.wechat.bean.GroupSendBean;
import com.winderinfo.wechat.db.DbUtil;
import com.winderinfo.wechat.view.DeleteGroupBottomDialog;
import com.winderinfo.wechat.view.EditPersonCenterDialog;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

/**
 * 群发助手
 */
public class HelpSendActivity extends BaseActivity {

    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(R.id.rv_send)
    RecyclerView rvSend;
    @BindView(R.id.fl_send_new)
    FrameLayout flAddNew;

    GroupSendAdapter mAdapter;
    List<GroupSendBean> sendBeans;

    @Override
    public int getLayoutId() {
        return R.layout.activity_help_send;
    }

    @Override
    public void initView() {
        initRv();

    }

    @Override
    protected void onResume() {
        super.onResume();
        sendBeans = DbUtil.queryAllGroupMessage();
        if (sendBeans != null && sendBeans.size() > 0) {
            llEmpty.setVisibility(View.GONE);
            rvSend.setVisibility(View.VISIBLE);
            mAdapter.setNewData(sendBeans);
            flAddNew.setVisibility(View.VISIBLE);
        } else {
            llEmpty.setVisibility(View.VISIBLE);
            rvSend.setVisibility(View.GONE);
            flAddNew.setVisibility(View.GONE);
        }
    }

    private void initRv() {
        mAdapter = new GroupSendAdapter(R.layout.item_send_group_lay);
        rvSend.setLayoutManager(new LinearLayoutManager(this));
        rvSend.setAdapter(mAdapter);
        OverScrollDecoratorHelper.setUpOverScroll(rvSend, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<GroupSendBean> data = adapter.getData();
                GroupSendBean sendBean = data.get(position);
                switch (view.getId()) {
                    case R.id.item_more_tv:
                        Intent intent = new Intent(HelpSendActivity.this, SendGroupMoreActivity.class);
                        intent.putExtra("bean", sendBean);
                        startActivity(intent);
                        break;
                    case R.id.item_content_iv:
                        //大图
                        String imageUrl = sendBean.getSendImage();
                        Intent photo = new Intent(HelpSendActivity.this, ImageActivity.class);
                        photo.putExtra("photo", imageUrl);
                        startActivity(photo);
                        break;
                    case R.id.item_num_tv:
                        //修改收件人
                        showEditPersonDialog(sendBean);

                        break;
                }

            }
        });

        mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                List<GroupSendBean> data = adapter.getData();
                GroupSendBean bean = data.get(position);
                showDeleteDialog(bean);
                return true;
            }
        });

    }

    /**
     * 收件人修改
     *
     * @param sendBean
     */
    private void showEditPersonDialog(GroupSendBean sendBean) {
        EditPersonCenterDialog personCenterDialog = new EditPersonCenterDialog();
        personCenterDialog.setDialogInterface(new OnClickDialogInterface() {
            @Override
            public void onCancel() {
                personCenterDialog.dismiss();
            }

            @Override
            public void onSure(String content) {
                sendBean.setSendNum(Integer.valueOf(content));
                personCenterDialog.dismiss();
                DbUtil.insertGroupBean(sendBean);
                mAdapter.notifyDataSetChanged();
            }
        });
        personCenterDialog.show(getSupportFragmentManager(),"personCenterDialog");

    }

    private void showDeleteDialog(GroupSendBean bean) {
        DeleteGroupBottomDialog dialog = new DeleteGroupBottomDialog();
        dialog.setBottomInterface(new OnClickDialogBottomInterface() {
            @Override
            public void onCancel() {
                dialog.dismiss();
            }

            @Override
            public void onSure(int num) {
                DbUtil.deleteGroupBean(bean);
                dialog.dismiss();
                sendBeans = DbUtil.queryAllGroupMessage();
                if (sendBeans != null && sendBeans.size() > 0) {
                    llEmpty.setVisibility(View.GONE);
                    rvSend.setVisibility(View.VISIBLE);
                    mAdapter.setNewData(sendBeans);
                    flAddNew.setVisibility(View.VISIBLE);

                } else {
                    llEmpty.setVisibility(View.VISIBLE);
                    rvSend.setVisibility(View.GONE);
                    flAddNew.setVisibility(View.GONE);
                }
            }
        });
        dialog.show(getSupportFragmentManager(), "dialog");
    }

    @OnClick({R.id.back_iv, R.id.help_add_tv, R.id.add_new_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.help_add_tv:
                startActivity(new Intent(this, SelectActivity.class));
                break;
            case R.id.add_new_tv:
                startActivity(new Intent(this, SelectActivity.class));
                break;
        }
    }
}
