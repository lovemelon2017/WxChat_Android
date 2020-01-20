package com.winderinfo.wechat.adapter;

import android.util.Log;
import android.view.View;
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
 * 2020/1/10
 */
public class ContactAdapter extends BaseQuickAdapter<ContactBean, BaseViewHolder> {
    String lastLetter = "";

    public ContactAdapter(int layoutResId, @Nullable List<ContactBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, ContactBean item) {
        List<ContactBean> data = getData();
        TextView tvLetter = helper.getView(R.id.contact_letter_tv);
        int adapterPosition = helper.getAdapterPosition();
        Log.e("han", "适配器位置:" + adapterPosition);


        String strLetter = item.getLetter();
        tvLetter.setText(strLetter);
        if (item.isShowLetter()) {
            tvLetter.setVisibility(View.VISIBLE);
        } else {
            tvLetter.setVisibility(View.GONE);
        }


        if (adapterPosition == data.size()) {
            helper.getView(R.id.contact_item_line).setVisibility(View.GONE);
        } else {
            //查看下一个数据
            if (adapterPosition < data.size() + 1) {
                ContactBean bean = data.get(adapterPosition);
                boolean show = bean.getIsShowLetter();
                if (!show) {
                    helper.getView(R.id.contact_item_line).setVisibility(View.VISIBLE);
                } else {
                    helper.getView(R.id.contact_item_line).setVisibility(View.GONE);
                }
            }

        }
        ImageView head = helper.getView(R.id.contact_item_iv);

        GlideHeadUtil.glideContactHead(mContext, head, item.getHead());

        helper.setText(R.id.contact_item_tv, item.getName());

    }
}
