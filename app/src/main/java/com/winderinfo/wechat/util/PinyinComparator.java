package com.winderinfo.wechat.util;


import com.winderinfo.wechat.bean.ContactBean;

import java.util.Comparator;

public class PinyinComparator implements Comparator<ContactBean> {

    @Override
    public int compare(ContactBean o1, ContactBean o2) {
        if ("@".equals(o1.getLetter()) || "#".equals(o2.getLetter())) {
            return -1;
        } else if ("#".equals(o1.getLetter()) || "@".equals(o2.getLetter())) {
            return 1;
        } else {
            return o1.getLetter().compareTo(o2.getLetter());
        }
    }
}