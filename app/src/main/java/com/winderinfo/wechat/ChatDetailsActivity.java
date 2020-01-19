package com.winderinfo.wechat;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.winderinfo.wechat.adapter.ChatDetailsAdapter;
import com.winderinfo.wechat.api.OnClickDialogInterface;
import com.winderinfo.wechat.base.BaseActivity;
import com.winderinfo.wechat.bean.GroupSendBean;
import com.winderinfo.wechat.bean.UserBean;
import com.winderinfo.wechat.bean.WxBean;
import com.winderinfo.wechat.db.DbUtil;
import com.winderinfo.wechat.event.GroupEvent;
import com.winderinfo.wechat.event.MyEvent;
import com.winderinfo.wechat.util.ChatTimeUtil;
import com.winderinfo.wechat.util.UserManager;
import com.winderinfo.wechat.view.EditUserNameCenterDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ChatDetailsActivity extends BaseActivity {

    @BindView(R.id.chat_details_name_tv)
    TextView tvName;
    @BindView(R.id.chat_rv)
    RecyclerView rv;
    @BindView(R.id.chat_detail_add_iv)
    ImageView ivAdd;
    @BindView(R.id.chat_detail_send_tv)
    TextView tvSend;
    @BindView(R.id.chat_detail_et)
    EditText etChat;
    ChatDetailsAdapter mAdapter;
    List<WxBean> list = new ArrayList<>();

    WxBean wxBean;

    UserManager mManager;
    UserBean userInfo;

    @Override
    public int getLayoutId() {
        return R.layout.activity_chat_details;
    }

    @Override
    public void initView() {
        mManager = UserManager.getInstance(this);
        userInfo = mManager.getUserInfo();
        wxBean = (WxBean) getIntent().getSerializableExtra("bean");
        tvName.setText(wxBean.getChatName());

        etChat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String toString = editable.toString();
                if (TextUtils.isEmpty(toString)) {
                    ivAdd.setVisibility(View.VISIBLE);
                    tvSend.setVisibility(View.GONE);
                } else {
                    ivAdd.setVisibility(View.GONE);
                    tvSend.setVisibility(View.VISIBLE);
                }

            }
        });
        initRv();

    }

    private void initRv() {

        rv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ChatDetailsAdapter(R.layout.chat_details_item_lay, list);
        if (userInfo != null) {
            String userHead = userInfo.getUserHead();
            if (!TextUtils.isEmpty(userHead)) {
                mAdapter.setHeadUrl(userHead);
            }
        }
        rv.setAdapter(mAdapter);
    }

    @OnClick({R.id.back_iv, R.id.chat_detail_send_tv, R.id.chat_detail_add_iv, R.id.more_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.chat_detail_send_tv:
                sendMessage();
                resetView();
                break;
            case R.id.chat_detail_add_iv:
                openImage();
                break;
            case R.id.more_iv:
                //修改昵称
                showEditNickName();
                break;
        }
    }

    private void showEditNickName() {
        EditUserNameCenterDialog dialog = new EditUserNameCenterDialog();
        dialog.setDialogInterface(new OnClickDialogInterface() {
            @Override
            public void onCancel() {
                dialog.dismiss();
            }

            @Override
            public void onSure(String content) {
                wxBean.setChatName(content);
                DbUtil.insert(wxBean);
                tvName.setText(content);
                EventBus.getDefault().post(new MyEvent(1));
                dialog.dismiss();
            }
        });
        dialog.show(getSupportFragmentManager(), "dialog");
    }

    private void openImage() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .enableCrop(true)
                .compress(true)
                .selectionMode(PictureConfig.SINGLE)
                .cropCompressQuality(60)// 裁剪压缩质量 默认90 int
                .minimumCompressSize(200)// 小于100kb的图片不压缩
                .forResult(PictureConfig.CHOOSE_REQUEST);

    }

    private void resetView() {
        hideSoftKeyboard();
        etChat.setText("");
        ivAdd.setVisibility(View.VISIBLE);
        tvSend.setVisibility(View.GONE);
    }

    private void sendMessage() {

        String chatContent = etChat.getText().toString();
        WxBean mItemBean = new WxBean();
        long timeMillis = System.currentTimeMillis();

        if (list.size() > 0) {
            mItemBean.setHideTime(true);
        } else {
            mItemBean.setHideTime(false);
        }
        mItemBean.setChatType(1);
        mItemBean.setChatTime(timeMillis);
        mItemBean.setChatContent(chatContent);
        list.add(mItemBean);
        wxBean.setChatTime(timeMillis);
        wxBean.setChatContent(chatContent);
        //TODO 纯文字
        wxBean.setChatType(1);
        wxBean.setChatNum("1");
        wxBean.setRead(false);

        DbUtil.insert(wxBean);

        mAdapter.notifyDataSetChanged();
        EventBus.getDefault().post(new MyEvent(1));

    }

    /**
     * 头像回调
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    String headUrl = selectList.get(0).getCutPath();
                    long timeMillis = System.currentTimeMillis();

                    WxBean mItemBean = new WxBean();

                    if (list.size() > 0) {
                        mItemBean.setHideTime(true);
                    } else {
                        mItemBean.setHideTime(false);
                    }


                    mItemBean.setChatType(2);
                    mItemBean.setChatTime(timeMillis);
                    mItemBean.setChatContent(headUrl);
                    list.add(mItemBean);
                    wxBean.setChatTime(timeMillis);
                    wxBean.setChatContent(headUrl);
                    //TODO 1纯文字 2图片
                    wxBean.setChatType(2);
                    wxBean.setChatNum("1");

                    DbUtil.insert(wxBean);

                    mAdapter.notifyDataSetChanged();
                    EventBus.getDefault().post(new MyEvent(1));


                    break;
            }
        }
    }
}
