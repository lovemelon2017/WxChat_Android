package com.winderinfo.wechat.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * lovely_melon
 * 2020/1/10
 */
@Entity
public class ContactBean implements Serializable {
    public static final long serialVersionUID = 10001;
    @Id(autoincrement = true)
    long id;
    int head;
    String name;
    boolean isCheck;
    String letter;

    @Generated(hash = 1067278338)
    public ContactBean(long id, int head, String name, boolean isCheck,
            String letter) {
        this.id = id;
        this.head = head;
        this.name = name;
        this.isCheck = isCheck;
        this.letter = letter;
    }

    @Generated(hash = 1283900925)
    public ContactBean() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getHead() {
        return head;
    }

    public void setHead(int head) {
        this.head = head;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public boolean getIsCheck() {
        return this.isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }
}
