package com.winderinfo.wechat.view;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.winderinfo.wechat.R;
import com.winderinfo.wechat.api.OnClickDialogBottom2Interface;
import com.winderinfo.wechat.api.OnClickDialogBottomInterface;

/**
 * lovely_melon
 * 2019/11/21
 */
public class TrendsBottomDialog extends DialogFragment {


    TextView tvTrends1;
    TextView tvTrends2;
    EditText etInput;
    TextView tvCancel;

    OnClickDialogBottom2Interface bottomInterface;

    boolean isShowTrend1;
    boolean isShowTrend2;

    public void setShowTrend1(boolean showTrend1) {
        isShowTrend1 = showTrend1;
    }

    public void setShowTrend2(boolean showTrend2) {
        isShowTrend2 = showTrend2;
    }

    public void setBottomInterface(OnClickDialogBottom2Interface bottomInterface) {
        this.bottomInterface = bottomInterface;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_find_lay, null);
        tvTrends1 = view.findViewById(R.id.dialog_dt_tv1);
        tvTrends2 = view.findViewById(R.id.dialog_dt_tv2);
        etInput = view.findViewById(R.id.dialog_et);

        tvCancel = view.findViewById(R.id.dialog_dt_cancel);

        if (isShowTrend1) {
            tvTrends1.setText("动态1标记已读");
        } else {
            tvTrends1.setText("动态1标记未读");
        }
        if (isShowTrend2) {
            tvTrends2.setText("动态2标记已读");
        } else {
            tvTrends2.setText("动态2标记未读");
        }

        tvTrends1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isShowTrend1 = !isShowTrend1;
                if (isShowTrend1) {
                    tvTrends1.setText("动态1标记已读");
                } else {
                    tvTrends1.setText("动态1标记未读");
                }
                String num = etInput.getText().toString();


                if (bottomInterface != null) {
                    bottomInterface.onClickTrendOne(isShowTrend1,num);
                }
            }
        });
        tvTrends2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isShowTrend2 = !isShowTrend2;
                if (isShowTrend2) {
                    tvTrends2.setText("动态2标记未读");
                } else {
                    tvTrends2.setText("动态2标记已读");
                }
                if (bottomInterface != null) {
                    bottomInterface.onClickTrendTwo(isShowTrend2,"");
                }
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomInterface != null) {
                    bottomInterface.onCancel();
                }
            }
        });


        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        // 如果不设置这句代码, 那么弹框就会与四边都有一定的距离
        window.setBackgroundDrawableResource(android.R.color.transparent);
        // 设置动画
        window.setWindowAnimations(R.style.sound_bottom_dialog);
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        // 如果不设置宽度,那么即使你在布局中设置宽度为 match_parent 也不会起作用
        params.width = getResources().getDisplayMetrics().widthPixels;
        window.setAttributes(params);
    }

}
