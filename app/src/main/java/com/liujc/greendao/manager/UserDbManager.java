package com.liujc.greendao.manager;

import com.liujc.greendao.Bean.User;

import org.greenrobot.greendao.AbstractDao;

/**
 * 类名称：UserDbManager
 * 创建者：Create by sean
 * 创建时间：Create on 2016/12/21 09:35
 * 描述：TODO
 * 最近修改时间：2016/12/21 09:35
 * 修改人：Modify by sean
 */
public class UserDbManager extends GreenDaoManager<User, Long> {
    @Override
    AbstractDao<User, Long> getAbstractDao() {
        return daoSession.getUserDao();
    }
}
