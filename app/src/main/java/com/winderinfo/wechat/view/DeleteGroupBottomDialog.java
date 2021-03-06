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
public class DeleteGroupBottomDialog extends DialogFragment {


    TextView tvDelete;
    TextView tvCancel;

    OnClickDialogBottomInterface bottomInterface;

    public void setBottomInterface(OnClickDialogBottomInterface bottomInterface) {
        this.bottomInterface = bottomInterface;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_group_delete_lay, null);
        tvDelete = view.findViewById(R.id.dialog_delete_tv);

        tvCancel = view.findViewById(R.id.dialog_canel_tv);

        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomInterface != null) {
                    bottomInterface.onSure(0);
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
