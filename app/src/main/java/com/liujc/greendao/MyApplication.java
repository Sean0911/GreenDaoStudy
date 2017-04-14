package com.liujc.greendao;

import android.app.Application;
import android.content.Context;
import com.liujc.greendao.manager.GreenDaoManager;

/**
 * 类名称：MyApplication
 * 创建者：Create by sean
 * 创建时间：Create on 2016/12/1 14:57
 * 描述：TODO
 * 最近修改时间：2016/12/1 14:57
 * 修改人：Modify by sean
 */
public class MyApplication extends Application{
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //greenDao全局配置,只希望有一个数据库操作对象
        GreenDaoManager.init();//初始化数据库
    }

    public static Context getContext(){
        return context;
    }

}
