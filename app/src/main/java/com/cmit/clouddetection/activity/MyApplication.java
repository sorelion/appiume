package com.cmit.clouddetection.activity;

import android.app.Application;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import com.cmit.clouddetection.dao.DaoMaster;
import com.cmit.clouddetection.dao.DaoSession;

/**
 * Created by pact on 2018/10/9.
 */

public class MyApplication extends Application {

    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        setupDatabase();
    }

    public static MyApplication getInstan() {
        return new MyApplication();
    }

    /**
     * 配置数据库
     */
    private void setupDatabase() {
        //创建数据库"
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "cloud_detection.db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    private int result;
    private Intent intent;
}
