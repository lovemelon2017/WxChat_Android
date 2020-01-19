package com.winderinfo.wechat.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * lovely_melon
 * 2020/1/15
 */
@Entity
public class GroupSendBean implements Serializable {
    public static final long serialVersionUID = 10002;
    @Id(autoincrement = true)
    Long id;
    long sendTime;
    int type;
    int sendNum;
    String sendName;
    String sendContent;
    String sendImage;

    @Generated(hash = 1836131637)
    public GroupSendBean(Long id, long sendTime, int type, int sendNum,
                         String sendName, String sendContent, String sendImage) {
        this.id = id;
        this.sendTime = sendTime;
        this.type = type;
        this.sendNum = sendNum;
        this.sendName = sendName;
        this.sendContent = sendContent;
        this.sendImage = sendImage;
    }

    @Generated(hash = 2070186230)
    public GroupSendBean() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSendNum() {
        return sendNum;
    }

    public void setSendNum(int sendNum) {
        this.sendNum = sendNum;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String getSendContent() {
        return sendContent;
    }

    public void setSendContent(String sendContent) {
        this.sendContent = sendContent;
    }

    public String getSendImage() {
        return sendImage;
    }

    public void setSendImage(String sendImage) {
        this.sendImage = sendImage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }
}
