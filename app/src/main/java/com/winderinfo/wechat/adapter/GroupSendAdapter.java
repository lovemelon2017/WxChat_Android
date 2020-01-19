package com.winderinfo.wechat.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.winderinfo.wechat.R;
import com.winderinfo.wechat.bean.GroupSendBean;
import com.winderinfo.wechat.util.ChatTimeUtil;

import java.util.List;

/**
 * lovely_melon
 * 2020/1/15
 * type 0文字
 */
public class GroupSendAdapter extends BaseQuickAdapter<GroupSendBean, BaseViewHolder> {

    boolean isShow = true;

    public GroupSendAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, GroupSendBean item) {
        int type = item.getType();

        List<GroupSendBean> data = getData();
        int adapterPosition = helper.getAdapterPosition();


        if (type == 0) {
            TextView tvContent = helper.getView(R.id.item_content_tv);
            helper.getView(R.id.item_content_iv).setVisibility(View.GONE);
            int sendNum = item.getSendNum();
            helper.setText(R.id.item_num_tv, sendNum + "位收件人:");
            helper.setText(R.id.item_names_tv, item.getSendName());
            tvContent.setVisibility(View.VISIBLE);
            tvContent.setText(item.getSendContent());

            TextView tvTime = helper.getView(R.id.item_time_tv);
            tvTime.setText(ChatTimeUtil.getTimeString(item.getSendTime()));


            if (adapterPosition + 1 < data.size() && adapterPosition > 0) {
                GroupSendBean sendBean = data.get(adapterPosition + 1);
                long two = sendBean.getSendTime();
                long one = item.getSendTime();
                if ((two - one) < 10 * 1000 * 60) {
                    tvTime.setVisibility(View.GONE);
                } else {
                    tvTime.setVisibility(View.VISIBLE);
                }

            }
            if (adapterPosition == data.size() - 1) {
                if (data.size() > 1) {
                    GroupSendBean sendBean = data.get(adapterPosition - 1);
                    long two = sendBean.getSendTime();
                    long one = item.getSendTime();
                    if ((one - two) < 10 * 1000 * 60) {
                        tvTime.setVisibility(View.GONE);
                    } else {
                        tvTime.setVisibility(View.VISIBLE);
                    }
                }
            }


        } else if (type == 1) {

            int sendNum = item.getSendNum();
            helper.setText(R.id.item_num_tv, sendNum + "位收件人:");
            helper.setText(R.id.item_names_tv, item.getSendName());
            TextView tvTime = helper.getView(R.id.item_time_tv);
            tvTime.setText(ChatTimeUtil.getTimeString(item.getSendTime()));
            helper.getView(R.id.item_content_tv).setVisibility(View.GONE);
            ImageView ivImage = helper.getView(R.id.item_content_iv);
            ivImage.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(item.getSendImage()).into(ivImage);
            if (adapterPosition + 1 < data.size() && adapterPosition > 0) {
                GroupSendBean sendBean = data.get(adapterPosition + 1);
                long two = sendBean.getSendTime();
                long one = item.getSendTime();
                if ((two - one) < 10 * 1000 * 60) {
                    tvTime.setVisibility(View.GONE);
                } else {
                    tvTime.setVisibility(View.VISIBLE);
                }
            }

            if (adapterPosition == data.size() - 1) {
                if (data.size() > 1) {
                    GroupSendBean sendBean = data.get(adapterPosition - 1);
                    long two = sendBean.getSendTime();
                    long one = item.getSendTime();
                    if ((one - two) < 10 * 1000 * 60) {
                        tvTime.setVisibility(View.GONE);
                    } else {
                        tvTime.setVisibility(View.VISIBLE);
                    }
                }
            }

        }

        helper.addOnClickListener(R.id.item_more_tv);
        helper.addOnClickListener(R.id.item_content_iv);
        helper.addOnClickListener(R.id.item_num_tv);

    }
}
