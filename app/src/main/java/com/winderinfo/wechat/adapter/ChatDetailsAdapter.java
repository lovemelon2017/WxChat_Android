package com.winderinfo.wechat.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.winderinfo.wechat.R;
import com.winderinfo.wechat.bean.WxBean;
import com.winderinfo.wechat.util.ChatTimeUtil;

import java.util.List;

/**
 * lovely_melon
 * 2020/1/12
 */
public class ChatDetailsAdapter extends BaseQuickAdapter<WxBean, BaseViewHolder> {

    String headUrl;

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public ChatDetailsAdapter(int layoutResId, @Nullable List<WxBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WxBean item) {
        if (item.isHideTime()) {
            helper.getView(R.id.chat_time_tv).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.chat_time_tv).setVisibility(View.VISIBLE);
        }
        long chatTime = item.getChatTime();
        int chatType = item.getChatType();
        if (chatType == 1) {
            ImageView ivChat = helper.getView(R.id.chat_content_iv);
            ivChat.setVisibility(View.GONE);
            helper.getView(R.id.chat_content_tv).setVisibility(View.VISIBLE);
            helper.setText(R.id.chat_content_tv, item.getChatContent());

        } else if (chatType == 2) {
            ImageView ivChat = helper.getView(R.id.chat_content_iv);
            ivChat.setVisibility(View.VISIBLE);
            helper.getView(R.id.chat_content_tv).setVisibility(View.GONE);
            Glide.with(mContext).load(item.getChatContent()).into(ivChat);
        }


        helper.setText(R.id.chat_time_tv, ChatTimeUtil.getTimeString(chatTime));


        RoundedImageView ivHead = helper.getView(R.id.chat_user_iv);
        if (!TextUtils.isEmpty(headUrl)) {
            Glide.with(mContext).load(headUrl).into(ivHead);
        }

    }
}
