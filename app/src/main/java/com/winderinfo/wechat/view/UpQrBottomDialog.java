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
import com.winderinfo.wechat.api.OnClickDialogInterface;

/**
 * lovely_melon
 * 2019/11/21
 */
public class UpQrBottomDialog extends DialogFragment {


    TextView tvUp;
    TextView tvCancel;


    OnClickDialogInterface bottomInterface;

    public void setBottomInterface(OnClickDialogInterface bottomInterface) {
        this.bottomInterface = bottomInterface;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_up_qr_lay, null);
        tvUp = view.findViewById(R.id.item_qr_up_tv);
        tvCancel = view.findViewById(R.id.item_qr_cancel_tv);


        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomInterface != null) {
                    bottomInterface.onCancel();
                }
            }
        });
        tvUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomInterface != null) {
                    bottomInterface.onSure("");
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
