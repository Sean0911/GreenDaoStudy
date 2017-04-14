package com.liujc.greendao.manager;

import android.support.annotation.NonNull;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * 类名称：IDatabase
 * 创建者：Create by sean
 * 创建时间：Create on 2016/12/21 09:11
 * 描述：TODO
 * 最近修改时间：2016/12/21 09:11
 * 修改人：Modify by sean
 */
public interface IOperateDatabase<T, K> {
    //增加
    boolean insert(T entity);
    boolean insertOrReplace(@NonNull T entity);
    boolean insertList(List<T> mList);
    boolean insertOrReplaceList(List<T> mList);

    //删除
    boolean delete(T entity);
    boolean deleteByKey(K key);
    boolean deleteList(List<T> mList);
    boolean deleteByKeyInTx(K... key);
    boolean deleteAll();

    //修改
    boolean update(T entity);
    boolean updateInTx(T... entities);
    boolean updateList(List<T> mList);

    //查询
    T selectByPrimaryKey(K key);
    List<T> loadAll();
    boolean refresh(T entity);

    /**
     * 清理缓存
     */
    void clearDaoSession();

    /**
     * Delete all tables and content from our database
     */
    boolean dropDatabase();

    /**
     * 事务
     */
    void runInTx(Runnable runnable);

    /**
     * 自定义查询
     * @return
     */
    QueryBuilder<T> getQueryBuilder();

    /**
     * @param where
     * @param selectionArg
     * @return
     */
    List<T> queryRaw(String where, String... selectionArg);
}
