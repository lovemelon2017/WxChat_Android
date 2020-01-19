package com.winderinfo.wechat.event;

/**
 * lovely_melon
 * 2020/1/15
 */
public class GroupEvent {
    String text;
    boolean isText;

    public GroupEvent(boolean isText) {
        this.isText = isText;
    }

    public boolean isText() {
        return isText;
    }

    public void setText(boolean text) {
        isText = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
