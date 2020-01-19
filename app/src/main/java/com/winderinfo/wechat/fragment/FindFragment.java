package com.winderinfo.wechat.fragment;


import com.jaeger.library.StatusBarUtil;
import com.winderinfo.wechat.R;
import com.winderinfo.wechat.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 发现
 */
public class FindFragment extends BaseFragment {


    @Override
    public int getViewId() {
        return R.layout.fragment_find;
    }

    @Override
    public void initView() {
        // StatusBarUtil.setColor(getActivity(), getResources().getColor(R.color.cl_background),1);
    }

}
