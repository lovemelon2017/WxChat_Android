package com.winderinfo.wechat.fragment;


import android.content.Intent;
import android.text.TextUtils;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.winderinfo.wechat.R;
import com.winderinfo.wechat.UserCenterActivity;
import com.winderinfo.wechat.base.BaseFragment;
import com.winderinfo.wechat.bean.UserBean;
import com.winderinfo.wechat.util.UserManager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的
 */
public class MineFragment extends BaseFragment {

    @BindView(R.id.mine_head_iv)
    RoundedImageView rivHead;
    @BindView(R.id.mine_name_tv)
    TextView tvName;
    @BindView(R.id.mine_wx_num_tv)
    TextView tvWxNo;

    UserManager mManager;
    UserBean userInfo;

    @Override
    public int getViewId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView() {

    }

    @Override
    public void onResume() {
        super.onResume();
        mManager = UserManager.getInstance(getActivity());
        userInfo = mManager.getUserInfo();
        if (userInfo != null) {
            String userHead = userInfo.getUserHead();
            if (!TextUtils.isEmpty(userHead)) {
                Glide.with(getActivity()).load(userHead).into(rivHead);
            }
            String userName = userInfo.getUserName();
            if (!TextUtils.isEmpty(userName)) {
                tvName.setText(userName);
            }
            String userWxNo = userInfo.getUserWxNo();
            if (!TextUtils.isEmpty(userWxNo)) {
                tvWxNo.setText("微信号: " + userWxNo);
            }
        }
    }

    @OnClick(R.id.mine_user_cc)
    public void onClick() {

        Intent intent = new Intent(getContext(), UserCenterActivity.class);
        startActivity(intent);
    }
}
