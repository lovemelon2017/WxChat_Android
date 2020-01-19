package com.winderinfo.wechat.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.winderinfo.wechat.R;

/**
 * lovely_melon
 * 2020/1/12
 */
public class ChatPopWindow extends PopupWindow {
    public ChatPopWindow(Context context) {
        super(context);
        initView(context);
    }

    public ChatPopWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ChatPopWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_pop_lay, null);

    }
}
