package com.winderinfo.wechat.util;

import android.content.Context;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;

/**
 * lovely_melon_2019
 */
public class DialogUtil {
    public static BasePopupView basePopupView;

    public static void showLoading(Context context, String title) {

        basePopupView = new XPopup.Builder(context).asLoading(title).show();
    }

    public static void hindLoading() {
        if (basePopupView != null) {
            basePopupView.dismiss();
        }
    }

}
