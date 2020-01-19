package com.winderinfo.wechat.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import java.util.Comparator;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Keep;

/**
 * lovely_melon
 * 2020/1/9
 * type 1好友 2 群组 3 订阅号 4微信支付 5群发助手 6 公众号
 * chatHead 好友头像
 * chatName 好友姓名
 * chatContent 聊天内容
 * chatTime 聊天时间
 * chatNum 聊天消息数
 * isRead 标记 已读与未读
 */
@Entity
public class WxBean implements MultiItemEntity, Serializable, Comparator<WxBean> {
    public static final long serialVersionUID = 10000;
    public static final int TYPE_CHAT = 1;
    public static final int TYPE_GROUP = 2;
    public static final int TYPE_SUB = 3;
    public static final int TYPE_PAY = 4;
    public static final int TYPE_CHAT_GROUP_SEND = 5;
    public static final int TYPE_GZH = 6;

    @Id(autoincrement = true)
    Long id;
    int type;
    int chatHead;
    String chatName;
    String chatContent;
    int chatType;//文字 图片 表情
    long chatTime;
    String chatNum;
    boolean isRead;
    boolean isLongCheck;//是否长按
    boolean isHideTime;

    @Keep
    public WxBean(Long id, int type, int chatHead, String chatName,
                  String chatContent, int chatType, long chatTime, String chatNum,
                  boolean isRead, boolean isLongCheck, boolean isHideTime) {
        this.id = id;
        this.type = type;
        this.chatHead = chatHead;
        this.chatName = chatName;
        this.chatContent = chatContent;
        this.chatType = chatType;
        this.chatTime = chatTime;
        this.chatNum = chatNum;
        this.isRead = isRead;
        this.isLongCheck = isLongCheck;
        this.isHideTime = isHideTime;
    }

    @Keep
    public WxBean() {
    }

    public int getChatType() {
        return chatType;
    }

    public void setChatType(int chatType) {
        this.chatType = chatType;
    }

    public static int getTypeChat() {
        return TYPE_CHAT;
    }

    public static int getTypeGroup() {
        return TYPE_GROUP;
    }

    public static int getTypeSub() {
        return TYPE_SUB;
    }

    public static int getTypePay() {
        return TYPE_PAY;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getChatHead() {
        return chatHead;
    }

    public void setChatHead(int chatHead) {
        this.chatHead = chatHead;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public String getChatContent() {
        return chatContent;
    }

    public void setChatContent(String chatContent) {
        this.chatContent = chatContent;
    }

    public long getChatTime() {
        return chatTime;
    }

    public void setChatTime(long chatTime) {
        this.chatTime = chatTime;
    }

    public String getChatNum() {
        return chatNum;
    }

    public void setChatNum(String chatNum) {
        this.chatNum = chatNum;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public boolean isLongCheck() {
        return isLongCheck;
    }

    public void setLongCheck(boolean longCheck) {
        isLongCheck = longCheck;
    }

    public boolean isHideTime() {
        return isHideTime;
    }

    public void setHideTime(boolean hideTime) {
        isHideTime = hideTime;
    }

    @Override
    public int getItemType() {
        return type;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getIsRead() {
        return this.isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public boolean getIsLongCheck() {
        return this.isLongCheck;
    }

    public void setIsLongCheck(boolean isLongCheck) {
        this.isLongCheck = isLongCheck;
    }

    public boolean getIsHideTime() {
        return this.isHideTime;
    }

    public void setIsHideTime(boolean isHideTime) {
        this.isHideTime = isHideTime;
    }



    @Override
    public int compare(WxBean oneBean, WxBean twoBean) {
        long time = twoBean.getChatTime() - oneBean.getChatTime();
        if (time>0){
            return 1;
        }else if (time==0){
            return 0;
        }else {
            return -1;
        }
    }
}
