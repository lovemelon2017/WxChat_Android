package com.winderinfo.wechat.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import com.winderinfo.wechat.R;
import com.winderinfo.wechat.api.OnClickDialogEditInterface;
import com.winderinfo.wechat.bean.WxBean;

/**
 * lovely_melon
 * 2020/1/14
 */
public class HomEditDialog extends DialogFragment {
    CardView mCv;

    TextView tvStateRead;
    TextView tvNumEdit;
    TextView tvTimeEdit;
    TextView tvDelete;

    boolean isRead;
    WxBean wxBean;

    private DialogInterface.OnDismissListener mOnClickListener;

    public void setmOnClickListener(DialogInterface.OnDismissListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    public void setWxBean(WxBean wxBean) {
        this.wxBean = wxBean;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    OnClickDialogEditInterface click;

    public void setClick(OnClickDialogEditInterface click) {
        this.click = click;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.home_pop_lay, null);
        mCv = view.findViewById(R.id.dialog_cardView);
        tvStateRead = view.findViewById(R.id.dialog_read_state_tv);
        tvNumEdit = view.findViewById(R.id.dialog_edit_message_tv);
        tvTimeEdit = view.findViewById(R.id.dialog_edit_time_tv);
        tvDelete = view.findViewById(R.id.dialog_delete_tv);

        if (isRead) {
            tvStateRead.setText("标记未读");
        } else {
            tvStateRead.setText("标记已读");
        }

        tvStateRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (click != null) {
                    isRead = !isRead;
                    click.onStateRead(isRead);
                }

            }
        });

        tvNumEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (click != null) {
                    click.onEditNum(wxBean);
                }
            }
        });

        tvTimeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (click != null) {
                    click.onEditTime(wxBean);
                }
            }
        });

        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (click != null) {
                    click.onDelete(wxBean);
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

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mOnClickListener != null) {
            mOnClickListener.onDismiss(dialog);
        }
    }
}
