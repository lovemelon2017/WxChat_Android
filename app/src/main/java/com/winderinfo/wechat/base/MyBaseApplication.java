package com.winderinfo.wechat.base;

import android.database.sqlite.SQLiteDatabase;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.greendao.gen.DaoMaster;
import com.greendao.gen.DaoSession;

/**
 * lovely_melon
 * 2019/11/28
 */
public class MyBaseApplication extends MultiDexApplication {

    private static MyBaseApplication application;
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;


    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        application = this;
        setDatabase();
    }

    public static MyBaseApplication getApplication() {
        if (application == null) {
           application=new MyBaseApplication();
        }
        return application;
    }

    /**
     * 设置greenDAO
     */
    private void setDatabase() {
        mHelper = new DaoMaster.DevOpenHelper(this, "myWxDb", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }
    public DaoSession getDaoSession() {
        return mDaoSession;
    }
    public SQLiteDatabase getDb() {
        return db;
    }


}
