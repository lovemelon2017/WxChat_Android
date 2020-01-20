package com.winderinfo.wechat.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.winderinfo.wechat.R;
import com.winderinfo.wechat.bean.WxBean;
import com.winderinfo.wechat.util.ChatTimeUtil;
import com.winderinfo.wechat.util.GlideHeadUtil;

import java.util.List;

/**
 * lovely_melon
 * 2020/1/9
 */
public class HomeRvAdapter extends BaseMultiItemQuickAdapter<WxBean, BaseViewHolder> {


    public HomeRvAdapter(List<WxBean> data) {
        super(data);
        addItemType(WxBean.TYPE_CHAT, R.layout.chat_item_user_lay);
        addItemType(WxBean.TYPE_GROUP, R.layout.chat_item_grop_lay);
        addItemType(WxBean.TYPE_SUB, R.layout.chat_item_sub_lay);
        addItemType(WxBean.TYPE_PAY, R.layout.chat_item_pay_lay);
        addItemType(WxBean.TYPE_CHAT_GROUP_SEND, R.layout.chat_item_grop_send_lay);
        addItemType(WxBean.TYPE_GZH, R.layout.chat_item_gzh_lay);
    }

    @Override
    protected void convert(BaseViewHolder helper, WxBean item) {

        int type = item.getType();

        switch (type) {
            case WxBean.TYPE_CHAT:
                //聊天
                boolean read = item.isRead();
                if (read) {
                    helper.getView(R.id.chat_message_tv).setVisibility(View.GONE);
                } else {
                    helper.getView(R.id.chat_message_tv).setVisibility(View.VISIBLE);
                }
                String chatNum = item.getChatNum();
                if (!TextUtils.isEmpty(chatNum)) {
                    int num = Integer.valueOf(chatNum);
                    if (num > 99) {
                        helper.setText(R.id.chat_message_tv, "99");
                    } else {
                        helper.setText(R.id.chat_message_tv, chatNum);
                    }

                }

                ImageView head = helper.getView(R.id.chat_user_head_iv);

                String chatName = item.getChatName();
                TextView tvName = helper.getView(R.id.chat_user_name_tv);

                if (!TextUtils.isEmpty(chatName)) {

                    if ("微信运动".equals(chatName)) {
                        GlideHeadUtil.glideGZHHead(mContext, head, item.getChatHead());
                        tvName.setTextColor(mContext.getResources().getColor(R.color.cl_text_blue));
                    } else {
                        GlideHeadUtil.glideContactHead(mContext, head, item.getChatHead());
                        tvName.setTextColor(mContext.getResources().getColor(R.color.cl_000));
                    }
                    tvName.setText(chatName);
                }
                long time = item.getChatTime();
                helper.setText(R.id.chat_user_time_tv, ChatTimeUtil.getTimeString(time));


                int chatType = item.getChatType();
                if (chatType == 1) {
                    String chatContent = item.getChatContent();
                    if (!TextUtils.isEmpty(chatContent)) {
                        helper.setText(R.id.chat_user_content_tv, chatContent);
                    }

                } else if (chatType == 2) {
                    helper.setText(R.id.chat_user_content_tv, "图片");

                } else if (chatType == 3) {

                }

                View vBg1 = helper.getView(R.id.item_parent_v);
                if (item.isLongCheck()) {
                    vBg1.setBackgroundColor(mContext.getResources().getColor(R.color.cl_background));
                } else {
                    vBg1.setBackgroundColor(mContext.getResources().getColor(R.color.cl_fff));
                }


                break;
            case WxBean.TYPE_GROUP:
                //群聊
                boolean read2 = item.isRead();
                if (read2) {
                    helper.getView(R.id.chat_message_tv).setVisibility(View.GONE);
                } else {
                    helper.getView(R.id.chat_message_tv).setVisibility(View.VISIBLE);
                }
                String chatNum2 = item.getChatNum();
                if (!TextUtils.isEmpty(chatNum2)) {
                    int num = Integer.valueOf(chatNum2);
                    if (num > 99) {
                        helper.setText(R.id.chat_message_tv, "99");
                    } else {
                        helper.setText(R.id.chat_message_tv, chatNum2);
                    }

                }
                ImageView head2 = helper.getView(R.id.chat_user_head_iv);
                GlideHeadUtil.glideGroupHead(mContext, head2, item.getChatHead());
                String chatName2 = item.getChatName();
                if (!TextUtils.isEmpty(chatName2)) {
                    helper.setText(R.id.chat_user_name_tv, chatName2);
                }
                long time2 = item.getChatTime();
                helper.setText(R.id.chat_user_time_tv, ChatTimeUtil.getTimeString(time2));


                int chatType2 = item.getChatType();
                if (chatType2 == 1) {
                    String chatContent = item.getChatContent();
                    if (!TextUtils.isEmpty(chatContent)) {
                        helper.setText(R.id.chat_user_content_tv, chatContent);
                    }

                } else if (chatType2 == 2) {
                    helper.setText(R.id.chat_user_content_tv, "图片");

                } else if (chatType2 == 3) {

                }


                View vBg2 = helper.getView(R.id.item_parent_v);
                if (item.isLongCheck()) {
                    vBg2.setBackgroundColor(mContext.getResources().getColor(R.color.cl_background));
                } else {
                    vBg2.setBackgroundColor(mContext.getResources().getColor(R.color.cl_fff));
                }


                break;
            case WxBean.TYPE_SUB:
                //订阅号

                boolean read3 = item.isRead();
                if (read3) {
                    helper.getView(R.id.chat_message_tv).setVisibility(View.GONE);
                } else {
                    helper.getView(R.id.chat_message_tv).setVisibility(View.VISIBLE);
                }

                String subContent = item.getChatContent();
                if (!TextUtils.isEmpty(subContent)) {
                    helper.setText(R.id.chat_user_content_tv, subContent);
                }


                View vBg3 = helper.getView(R.id.item_parent_v);
                if (item.isLongCheck()) {
                    vBg3.setBackgroundColor(mContext.getResources().getColor(R.color.cl_background));
                } else {
                    vBg3.setBackgroundColor(mContext.getResources().getColor(R.color.cl_fff));
                }
                long sub_time = item.getChatTime();
                helper.setText(R.id.chat_user_time_tv, ChatTimeUtil.getTimeString(sub_time));


                break;
            case WxBean.TYPE_PAY:
                //微信支付
                View vBg4 = helper.getView(R.id.item_parent_v);
                if (item.isLongCheck()) {
                    vBg4.setBackgroundColor(mContext.getResources().getColor(R.color.cl_background));
                } else {
                    vBg4.setBackgroundColor(mContext.getResources().getColor(R.color.cl_fff));
                }
                long pay_time = item.getChatTime();
                helper.setText(R.id.chat_user_time_tv, ChatTimeUtil.getTimeString(pay_time));


                break;
            case WxBean.TYPE_CHAT_GROUP_SEND:
                //群发
                long send_time = item.getChatTime();
                helper.setText(R.id.chat_user_time_tv, ChatTimeUtil.getTimeString(send_time));
                String chatContent = item.getChatContent();
                if (!TextUtils.isEmpty(chatContent)) {
                    helper.setText(R.id.chat_user_content_tv, chatContent);
                }

                break;

            case WxBean.TYPE_GZH:

                boolean read6 = item.isRead();
                if (read6) {
                    helper.getView(R.id.chat_message_tv).setVisibility(View.GONE);
                } else {
                    helper.getView(R.id.chat_message_tv).setVisibility(View.VISIBLE);
                }
                String num = item.getChatNum();
                if (!TextUtils.isEmpty(num)) {
                    helper.setText(R.id.chat_message_tv, num);
                }
                //公众号
                ImageView ivHeadGz = helper.getView(R.id.chat_user_head_iv);
                int chatHead = item.getChatHead();
                GlideHeadUtil.glideGZHHead(mContext, ivHeadGz, chatHead);
                String name = item.getChatName();
                if (!TextUtils.isEmpty(name)) {
                    helper.setText(R.id.chat_user_name_tv, name);
                }

                long sendTime = item.getChatTime();
                helper.setText(R.id.chat_user_time_tv, ChatTimeUtil.getTimeString(sendTime));
                String sendContent = item.getChatContent();
                if (!TextUtils.isEmpty(sendContent)) {
                    helper.setText(R.id.chat_user_content_tv, sendContent);
                }

                break;
        }

    }
}
