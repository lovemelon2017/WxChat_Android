package com.winderinfo.wechat.api;

import com.winderinfo.wechat.bean.WxBean;

/**
 * lovely_melon
 * 2019/11/25
 */
public interface OnClickDialogEditInterface {
    public void onCancel();

    public void onStateRead(boolean state);

    public void onEditNum(WxBean wxBean);

    public void onEditTime(WxBean wxBean);

    public void onDelete(WxBean wxBean);

    public void onEditContent(WxBean wxBean);
}
