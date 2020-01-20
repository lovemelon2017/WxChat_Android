package com.winderinfo.wechat.adapter;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.winderinfo.wechat.R;
import com.winderinfo.wechat.bean.ContactBean;
import com.winderinfo.wechat.util.GlideHeadUtil;

import java.util.List;

/**
 * lovely_melon
 * 2020/1/15
 */
public class SelectContactAdapter extends BaseQuickAdapter<ContactBean, BaseViewHolder> {
    String lastLetter = "";

    public SelectContactAdapter(int layoutResId, @Nullable List<ContactBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, ContactBean item) {

        List<ContactBean> data = getData();

        TextView tvLetter = helper.getView(R.id.contact_letter_tv);
        int adapterPosition = helper.getAdapterPosition();
        String strLetter = item.getLetter();
        tvLetter.setText(strLetter);


        if (item.isShowLetter()) {
            tvLetter.setVisibility(View.VISIBLE);
        } else {
            tvLetter.setVisibility(View.GONE);
        }


        if (adapterPosition == data.size() - 1) {
            helper.getView(R.id.contact_item_line).setVisibility(View.GONE);
        } else {
            //查看下一个数据
            if (adapterPosition < data.size() - 1) {
                ContactBean bean = data.get(adapterPosition + 1);
                boolean show = bean.getIsShowLetter();
                if (!show) {
                    helper.getView(R.id.contact_item_line).setVisibility(View.VISIBLE);
                } else {
                    helper.getView(R.id.contact_item_line).setVisibility(View.GONE);
                }
            }

        }


        if (item.isCheck()) {
            CheckBox cb = helper.getView(R.id.select_cb);
            cb.setChecked(true);
        } else {
            CheckBox cb = helper.getView(R.id.select_cb);
            cb.setChecked(false);
        }
        String name = item.getName();
        helper.setText(R.id.contact_item_tv, name);
        int head = item.getHead();
        ImageView ivHead = helper.getView(R.id.contact_item_iv);
        GlideHeadUtil.glideContactHead(mContext, ivHead, head);

    }
}
