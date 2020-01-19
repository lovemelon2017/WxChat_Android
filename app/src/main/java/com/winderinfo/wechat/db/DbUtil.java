package com.winderinfo.wechat.db;

import com.greendao.gen.ContactBeanDao;
import com.greendao.gen.DaoSession;
import com.greendao.gen.GroupSendBeanDao;
import com.greendao.gen.WxBeanDao;
import com.winderinfo.wechat.base.MyBaseApplication;
import com.winderinfo.wechat.bean.ContactBean;
import com.winderinfo.wechat.bean.GroupSendBean;
import com.winderinfo.wechat.bean.WxBean;

import java.util.List;

/**
 * lovely_melon
 * 2020/1/13
 */
public class DbUtil {

    public static List<WxBean> queryAll() {
        DaoSession daoSession = MyBaseApplication.getApplication().getDaoSession();
        daoSession.clear(); //清除缓存，即时刷新
        WxBeanDao wxBeanDao = daoSession.getWxBeanDao();
        List<WxBean> wxBeans = wxBeanDao.loadAll();

        if (wxBeans != null && wxBeans.size() > 0) {
            return wxBeans;
        }
        return null;
    }

    public static void insert(WxBean bean) {
        DaoSession daoSession = MyBaseApplication.getApplication().getDaoSession();
        daoSession.clear(); //清除缓存，即时刷新
        WxBeanDao dao = daoSession.getWxBeanDao();
        dao.insertOrReplace(bean);

    }

    public static void insertWxList(List<WxBean> list) {
        DaoSession daoSession = MyBaseApplication.getApplication().getDaoSession();
        daoSession.clear(); //清除缓存，即时刷新
        WxBeanDao beanDao = daoSession.getWxBeanDao();
        beanDao.insertOrReplaceInTx(list);
    }

    public static void clear() {
        DaoSession daoSession = MyBaseApplication.getApplication().getDaoSession();
        daoSession.clear(); //清除缓存，即时刷新
        WxBeanDao wxBeanDao = daoSession.getWxBeanDao();
        if (wxBeanDao != null) {
            wxBeanDao.deleteAll();
        }
        ContactBeanDao contactBeanDao = daoSession.getContactBeanDao();
        if (contactBeanDao != null) {
            contactBeanDao.deleteAll();
        }
        GroupSendBeanDao sendBeanDao = daoSession.getGroupSendBeanDao();
        if (sendBeanDao != null) {
            sendBeanDao.deleteAll();
        }
    }

    public static void delete(WxBean bean) {
        DaoSession daoSession = MyBaseApplication.getApplication().getDaoSession();
        WxBeanDao dao = daoSession.getWxBeanDao();
        // long id = bean.getId();
        dao.delete(bean);

    }

    /**
     * 联系人
     *
     * @return
     */
    public static List<ContactBean> queryContactAll() {
        DaoSession daoSession = MyBaseApplication.getApplication().getDaoSession();
        daoSession.clear(); //清除缓存，即时刷新
        ContactBeanDao beanDao = daoSession.getContactBeanDao();
        List<ContactBean> list = beanDao.loadAll();
        return list;
    }

    public static void insertContact(List<ContactBean> list) {
        DaoSession daoSession = MyBaseApplication.getApplication().getDaoSession();
        daoSession.clear(); //清除缓存，即时刷新
        ContactBeanDao beanDao = daoSession.getContactBeanDao();
        beanDao.insertOrReplaceInTx(list);
    }

    public static void insertContact(ContactBean bean) {
        DaoSession daoSession = MyBaseApplication.getApplication().getDaoSession();
        daoSession.clear(); //清除缓存，即时刷新
        ContactBeanDao beanDao = daoSession.getContactBeanDao();
        beanDao.insertOrReplace(bean);
    }

    /**
     * 群发消息
     */
    public static List<GroupSendBean> queryAllGroupMessage() {
        DaoSession daoSession = MyBaseApplication.getApplication().getDaoSession();
        daoSession.clear(); //清除缓存，即时刷新
        GroupSendBeanDao dao = daoSession.getGroupSendBeanDao();
        List<GroupSendBean> beans = dao.loadAll();

        if (beans != null && beans.size() > 0) {
            return beans;
        }
        return null;
    }

    public static void deleteGroupBean(GroupSendBean bean) {
        DaoSession daoSession = MyBaseApplication.getApplication().getDaoSession();
        daoSession.clear(); //清除缓存，即时刷新
        GroupSendBeanDao dao = daoSession.getGroupSendBeanDao();
        dao.delete(bean);
    }

    public static void insertGroupBean(GroupSendBean bean) {
        DaoSession daoSession = MyBaseApplication.getApplication().getDaoSession();
        daoSession.clear(); //清除缓存，即时刷新
        GroupSendBeanDao dao = daoSession.getGroupSendBeanDao();
        dao.insertOrReplace(bean);
    }

}
