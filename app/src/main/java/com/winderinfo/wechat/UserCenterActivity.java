package com.winderinfo.wechat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.makeramen.roundedimageview.RoundedImageView;
import com.winderinfo.wechat.api.OnClickDialogInterface;
import com.winderinfo.wechat.base.BaseActivity;
import com.winderinfo.wechat.bean.UserBean;
import com.winderinfo.wechat.util.MyFileUtil;
import com.winderinfo.wechat.util.UserManager;
import com.winderinfo.wechat.view.EditUserNameCenterDialog;
import com.winderinfo.wechat.view.EditUserWxCenterDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 个人中心
 */
public class UserCenterActivity extends BaseActivity {

    @BindView(R.id.center_head_iv)
    RoundedImageView ivHead;
    @BindView(R.id.center_user_name)
    TextView tvName;
    @BindView(R.id.center_user_wx_num)
    TextView tvWx;
    UserManager mManager;
    UserBean userInfo;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_center;
    }

    @Override
    public void initView() {
        mManager = UserManager.getInstance(this);
        userInfo = mManager.getUserInfo();
        if (userInfo != null) {

            String userHead = userInfo.getUserHead();
            if (!TextUtils.isEmpty(userHead)) {
                Glide.with(this).load(userHead).into(ivHead);
            }
            String userName = userInfo.getUserName();
            if (!TextUtils.isEmpty(userName)) {
                tvName.setText(userName);
            }

            String userWxNo = userInfo.getUserWxNo();
            if (!TextUtils.isEmpty(userWxNo)) {
                tvWx.setText(userWxNo);
            }

        }

    }

    @OnClick({R.id.back_iv, R.id.qr_fl, R.id.user_fl, R.id.fl_nick_name, R.id.fl_nick_wx})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.qr_fl:
                Intent intent = new Intent(this, QrActivity.class);
                startActivity(intent);
                break;
            case R.id.user_fl:
                //选择头像
                openImage();
                break;
            case R.id.fl_nick_name:
                showNickNameDialog();
                break;
            case R.id.fl_nick_wx:
                showNickWxDialog();
                break;
        }
    }

    private void showNickNameDialog() {
        EditUserNameCenterDialog dialog1 = new EditUserNameCenterDialog();
        dialog1.setDialogInterface(new OnClickDialogInterface() {
            @Override
            public void onCancel() {
                dialog1.dismiss();
            }

            @Override
            public void onSure(String content) {
                tvName.setText(content);
                dialog1.dismiss();
                if (userInfo != null) {
                    userInfo.setUserName(content);
                    mManager.saveUser(userInfo);
                } else {
                    UserBean bean = new UserBean();
                    bean.setUserName(content);
                    mManager.saveUser(bean);
                }


            }
        });
        dialog1.show(getSupportFragmentManager(), "dialog1");
    }

    private void showNickWxDialog() {
        EditUserWxCenterDialog dialog2 = new EditUserWxCenterDialog();
        dialog2.setDialogInterface(new OnClickDialogInterface() {
            @Override
            public void onCancel() {
                dialog2.dismiss();
            }

            @Override
            public void onSure(String content) {
                tvWx.setText(content);
                dialog2.dismiss();
                if (userInfo != null) {
                    userInfo.setUserWxNo(content);
                    mManager.saveUser(userInfo);
                } else {
                    UserBean bean = new UserBean();
                    bean.setUserWxNo(content);
                    mManager.saveUser(bean);
                }


            }
        });
        dialog2.show(getSupportFragmentManager(), "dialog2");
    }

    private void openImage() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .enableCrop(true)
                .compress(true)
                .selectionMode(PictureConfig.SINGLE)
                .cropCompressQuality(60)// 裁剪压缩质量 默认90 int
                .minimumCompressSize(200)// 小于100kb的图片不压缩
                .withAspectRatio(1, 1)
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
                    Glide.with(UserCenterActivity.this).load(headUrl).into((RoundedImageView) ivHead);
                    if (userInfo != null) {
                        userInfo.setUserHead(headUrl);
                        mManager.saveUser(userInfo);
                    } else {
                        UserBean userBean=new UserBean();
                        userBean.setUserHead(headUrl);
                        mManager.saveUser(userBean);
                    }


                    break;
            }
        }
    }
}
