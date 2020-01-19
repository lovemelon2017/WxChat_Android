package com.winderinfo.wechat;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.winderinfo.wechat.base.BaseActivity;
import com.winderinfo.wechat.bean.ContactBean;
import com.winderinfo.wechat.bean.GroupSendBean;
import com.winderinfo.wechat.db.DbUtil;
import com.winderinfo.wechat.event.GroupEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 群发
 */
public class SendGroupActivity extends BaseActivity {


    List<ContactBean> sendBeans;
    @BindView(R.id.send_num_tv)
    TextView tvNum;
    @BindView(R.id.send_names_tv)
    TextView tvNames;
    StringBuffer sb = new StringBuffer();

    @BindView(R.id.chat_detail_et)
    EditText etInput;
    @BindView(R.id.chat_detail_add_iv)
    ImageView ivAddImage;
    @BindView(R.id.chat_detail_send_tv)
    TextView tvSend;

    @BindView(R.id.send_sv)
    NestedScrollView scrollView;


    @Override
    public int getLayoutId() {
        return R.layout.activity_send_group;
    }

    @Override
    public void initView() {
        sendBeans = (List<ContactBean>) getIntent().getSerializableExtra("list");
        if (sendBeans != null) {
            for (int i = 0; i < sendBeans.size(); i++) {
                ContactBean bean = sendBeans.get(i);
                String name = bean.getName();
                if (i == sendBeans.size() - 1) {
                    sb.append(name);
                } else {
                    sb.append(name + ", ");
                }
            }
            tvNames.setText(sb.toString());
            tvNum.setText("你将发消息给" + sendBeans.size() + "位朋友");
        }

        etInput.addTextChangedListener(new TextWatcher() {
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
                    ivAddImage.setVisibility(View.VISIBLE);
                    tvSend.setVisibility(View.GONE);
                } else {
                    ivAddImage.setVisibility(View.GONE);
                    tvSend.setVisibility(View.VISIBLE);
                }

            }
        });


    }


    @OnClick({R.id.back_iv, R.id.chat_detail_send_tv, R.id.chat_detail_add_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.chat_detail_send_tv:
                //发送
                hideSoftKeyboard();
                sendMessage();
                break;
            case R.id.chat_detail_add_iv:
                openImage();
                break;
        }
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

    private void sendMessage() {
        String input = etInput.getText().toString();
        long timeMillis = System.currentTimeMillis();
        if (!TextUtils.isEmpty(input)) {
            List<GroupSendBean> groupSendBeans = DbUtil.queryAllGroupMessage();
            if (groupSendBeans != null && groupSendBeans.size() > 0) {
                GroupSendBean sendBean = new GroupSendBean();
                sendBean.setId((groupSendBeans.size() + 1l));
                sendBean.setSendTime(timeMillis);
                sendBean.setSendNum(sendBeans.size());
                sendBean.setSendName(tvNames.getText().toString());
                sendBean.setType(0);
                sendBean.setSendContent(input);
                DbUtil.insertGroupBean(sendBean);
            } else {
                GroupSendBean sendBean = new GroupSendBean();
                sendBean.setId(1l);
                sendBean.setSendTime(timeMillis);
                sendBean.setSendNum(sendBeans.size());
                sendBean.setSendName(tvNames.getText().toString());
                sendBean.setType(0);
                sendBean.setSendContent(input);
                DbUtil.insertGroupBean(sendBean);
            }


        }

        GroupEvent event = new GroupEvent(true);
        event.setText(input);
        EventBus.getDefault().post(event);

        Intent intent = new Intent(SendGroupActivity.this, HelpSendActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

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
                    List<GroupSendBean> groupSendBeans = DbUtil.queryAllGroupMessage();
                    if (groupSendBeans != null && groupSendBeans.size() > 0) {

                        GroupSendBean sendBean = new GroupSendBean();
                        sendBean.setId((groupSendBeans.size() + 1l));
                        sendBean.setSendTime(timeMillis);
                        sendBean.setSendNum(sendBeans.size());
                        sendBean.setSendName(tvNames.getText().toString());
                        sendBean.setType(1);
                        sendBean.setSendImage(headUrl);
                        DbUtil.insertGroupBean(sendBean);

                    } else {

                        GroupSendBean sendBean = new GroupSendBean();
                        sendBean.setId(1l);
                        sendBean.setSendTime(timeMillis);
                        sendBean.setSendNum(sendBeans.size());
                        sendBean.setSendName(tvNames.getText().toString());
                        sendBean.setType(1);
                        sendBean.setSendImage(headUrl);
                        DbUtil.insertGroupBean(sendBean);
                    }

                    EventBus.getDefault().post(new GroupEvent(false));

                    Intent intent = new Intent(SendGroupActivity.this, HelpSendActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(0, 0);


                    break;
            }
        }
    }

}
