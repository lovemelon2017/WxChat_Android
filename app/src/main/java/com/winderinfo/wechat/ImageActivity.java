package com.winderinfo.wechat;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.winderinfo.wechat.base.BaseActivity;



import butterknife.BindView;
import butterknife.OnClick;

/**
 * 大图查看
 */
public class ImageActivity extends BaseActivity {

    @BindView(R.id.photo_v)
    PhotoView photoView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_image;
    }

    @Override
    public void initView() {
        String photo = getIntent().getStringExtra("photo");
        Glide.with(this).load(photo).into(photoView);

    }
    @OnClick(R.id.back_iv)
    public void onClick(){
        finish();
    }
}
