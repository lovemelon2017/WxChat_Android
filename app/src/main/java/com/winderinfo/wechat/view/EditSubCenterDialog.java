package com.winderinfo.wechat.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.winderinfo.wechat.R;
import com.winderinfo.wechat.api.OnClickDialogInterface;

/**
 * lovely_melon
 * 2019/11/25
 */
public class EditSubCenterDialog extends DialogFragment {
    TextView tvCancel;
    TextView tvCommit;
    EditText etInput;


    OnClickDialogInterface dialogInterface;

    public void setDialogInterface(OnClickDialogInterface dialogInterface) {
        this.dialogInterface = dialogInterface;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_edit_sub_lay, null);

        tvCancel = view.findViewById(R.id.cancel_tv);
        tvCommit = view.findViewById(R.id.commit_tv);
        etInput = view.findViewById(R.id.dialog_et);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialogInterface != null) {
                    dialogInterface.onCancel();
                }
            }
        });
        tvCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toString = etInput.getText().toString();
                if (TextUtils.isEmpty(toString)) {
                    Toast.makeText(getContext(), "请输入内容", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (dialogInterface != null) {
                    dialogInterface.onSure(toString);
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
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        // 如果不设置宽度,那么即使你在布局中设置宽度为 match_parent 也不会起作用
        params.width = getResources().getDisplayMetrics().widthPixels;
        window.setAttributes(params);
    }
}
