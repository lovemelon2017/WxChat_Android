package com.winderinfo.wechat;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.makeramen.roundedimageview.RoundedImageView;
import com.winderinfo.wechat.api.OnClickDialogInterface;
import com.winderinfo.wechat.base.BaseActivity;
import com.winderinfo.wechat.bean.UserBean;
import com.winderinfo.wechat.util.DialogUtil;
import com.winderinfo.wechat.util.UserManager;
import com.winderinfo.wechat.view.UpQrBottomDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的二维码
 */
public class QrActivity extends BaseActivity {

    @BindView(R.id.qr_iv)
    ImageView ivQr;
    UserManager mManager;
    UserBean userInfo;

    @Override
    public int getLayoutId() {
        return R.layout.activity_qr;
    }

    @Override
    public void initView() {
        mManager = UserManager.getInstance(this);
        userInfo = mManager.getUserInfo();

        DialogUtil.showLoading(this, "正在生成二维码");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DialogUtil.hindLoading();
                if (userInfo != null) {
                    String qrUrl = userInfo.getUserQrUrl();
                    if (!TextUtils.isEmpty(qrUrl)) {
                        Glide.with(QrActivity.this).load(qrUrl).into(ivQr);
                    }
                }
            }
        }, 400);


    }

    @OnClick({R.id.back_iv, R.id.qr_up_pv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.qr_up_pv:
                showUpDialog();
                break;
        }

    }

    private void showUpDialog() {
        UpQrBottomDialog dialog = new UpQrBottomDialog();
        dialog.setBottomInterface(new OnClickDialogInterface() {
            @Override
            public void onCancel() {
                dialog.dismiss();
            }

            @Override
            public void onSure(String content) {
                openImage();
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
                    Glide.with(this).load(headUrl).into(ivQr);
                    if (userInfo != null) {
                        userInfo.setUserQrUrl(headUrl);
                        mManager.saveUser(userInfo);
                    } else {
                        UserBean bean = new UserBean();
                        bean.setUserQrUrl(headUrl);
                        mManager.saveUser(bean);
                    }


                    break;
            }
        }
    }
}
