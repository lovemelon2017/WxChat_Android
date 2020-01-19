package com.winderinfo.wechat.event;

/**
 * lovely_melon
 * 2020/1/13
 * type 1 刷新首页
 */
public class MyEvent {
    int type;

    public MyEvent(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
