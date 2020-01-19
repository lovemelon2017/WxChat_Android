package com.winderinfo.wechat.util;

import android.content.Context;
import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import com.winderinfo.wechat.bean.UserBean;

public class UserManager {

    private static UserManager instance = null;
    private UserBean mUser = null;

    private UserManager(Context context) {

    }

    public static UserManager getInstance(Context context) {
        if (instance == null) {
            synchronized (UserManager.class) {
                if (instance == null) {
                    instance = new UserManager(context.getApplicationContext());
                }
            }
        }
        return instance;
    }


    /**
     * 获取用户信息
     */
    public UserBean getUserInfo() {
        String user_bean = SPUtils.getInstance().getString("wx_user");
        if (TextUtils.isEmpty(user_bean)) {
            return null;
        }
        mUser = JsonUtils.parse(user_bean, UserBean.class);
        return mUser;
    }


    /**
     * 保存用户信息
     *
     * @param user 用户对象
     */
    public void saveUser(UserBean user) {
        SPUtils.getInstance().put("wx_user", new Gson().toJson(user));
        mUser = user;
    }

    /**
     * 清除登录信息
     */
    public void clearLoginState() {
        SPUtils.getInstance().clear();
        mUser = null;
    }



}
