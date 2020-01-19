package com.winderinfo.wechat.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.winderinfo.wechat.R;
import com.winderinfo.wechat.bean.ContactBean;
import com.winderinfo.wechat.util.GlideHeadUtil;

/**
 * lovely_melon
 * 2020/1/15
 */
public class SelectTopAdapter extends BaseQuickAdapter<ContactBean, BaseViewHolder> {
    public SelectTopAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ContactBean item) {
        int head = item.getHead();
        ImageView view = helper.getView(R.id.top_iv);
        GlideHeadUtil.glideContactHead(mContext, view, head);
    }
}
