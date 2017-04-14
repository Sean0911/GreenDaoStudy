package com.liujc.greendao.manager;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.liujc.greendao.dao.DaoMaster;
import com.liujc.greendao.dao.DaoSession;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.Collection;
import java.util.List;

/**
 * 类名称：AbstractDatabaseManager
 * 创建者：Create by sean
 * 创建时间：Create on 2016/12/21 09:16
 * 描述：greendao 管理类扩展性比较好，解耦和，实现类只需要实现getAbstractDao方法
 * 最近修改时间：2016/12/21 09:16
 * 修改人：Modify by sean
 */
public abstract class GreenDaoManager<T, K> implements IOperateDatabase<T, K>{

    private static final String DEFAULT_DATABASE_NAME = "database_name.db";
    private static MySQLiteOpenHelper mHelper;
    protected static DaoSession daoSession;
    /**
     * 初始化OpenHelper
     */
    public static void init(Context context) {
        mHelper = new MySQLiteOpenHelper(new GreenDaoContext(context), DEFAULT_DATABASE_NAME, null);//GreenDaoContext为创建数据库路径使用
        daoSession = new DaoMaster(mHelper.getWritableDatabase()).newSession();
    }


    @Override
    public void clearDaoSession() {
        if (daoSession != null) {
            daoSession.clear();
            daoSession = null;
        }
    }

    @Override
    public boolean dropDatabase() {
        try {
//             DaoMaster.dropAllTables(database, true); // drops all tables
            // mHelper.onCreate(database); // creates the tables
//          mDaoSession.deleteAll(BankCardBean.class); // clear all elements
            // from
            // a table
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean insert(@NonNull T entity) {
        try {
            if (entity == null)
                return false;
            getAbstractDao().insert(entity);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean insertOrReplace(@NonNull T entity) {
        try {
            if (entity == null)
                return false;
            getAbstractDao().insertOrReplace(entity);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(@NonNull T entity) {
        try {
            if (entity == null)
                return false;
            getAbstractDao().delete(entity);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteByKey(K key) {
        try {
            if (TextUtils.isEmpty(key.toString()))
                return false;
            getAbstractDao().deleteByKey(key);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteByKeyInTx(K... key) {
        try {
            getAbstractDao().deleteByKeyInTx(key);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteList(List<T> mList) {
        try {
            if (mList == null || mList.size() == 0)
                return false;
            getAbstractDao().deleteInTx(mList);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteAll() {
        try {
            getAbstractDao().deleteAll();
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean update(@NonNull T entity) {
        try {
            if (entity == null)
                return false;
            getAbstractDao().update(entity);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean updateInTx(T... entities) {
        try {
            if (entities == null)
                return false;
            getAbstractDao().updateInTx(entities);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean updateList(List<T> mList) {
        try {
            if (mList == null || mList.size() == 0)
                return false;
            getAbstractDao().updateInTx(mList);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public T selectByPrimaryKey(@NonNull K key) {
        try {
            return getAbstractDao().load(key);
        } catch (SQLiteException e) {
            return null;
        }
    }

    @Override
    public List<T> loadAll() {
        return getAbstractDao().loadAll();
    }

    @Override
    public boolean refresh(@NonNull T entity) {
        try {
            if (entity == null)
                return false;
            getAbstractDao().refresh(entity);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public void runInTx(Runnable runnable) {
        try {
            daoSession.runInTx(runnable);
        } catch (SQLiteException e) {
        }
    }

    @Override
    public boolean insertList(@NonNull List<T> list) {
        try {
            if (list == null || list.size() == 0)
                return false;
            getAbstractDao().insertInTx(list);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    /**
     * @param list
     * @return
     */
    @Override
    public boolean insertOrReplaceList(@NonNull List<T> list) {
        try {
            if (list == null || list.size() == 0)
                return false;
            getAbstractDao().insertOrReplaceInTx(list);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public QueryBuilder<T> getQueryBuilder() {
        return getAbstractDao().queryBuilder();
    }

    /**
     * @param where
     * @param selectionArg
     * @return
     */
    @Override
    public List<T> queryRaw(String where, String... selectionArg) {
        return getAbstractDao().queryRaw(where, selectionArg);
    }

    public Query<T> queryRawCreate(String where, Object... selectionArg) {
        return getAbstractDao().queryRawCreate(where, selectionArg);
    }

    public Query<T> queryRawCreateListArgs(String where, Collection<Object> selectionArg) {
        return getAbstractDao().queryRawCreateListArgs(where, selectionArg);
    }
    /**
     * 获取Dao
     * @return
     */
    abstract AbstractDao<T, K> getAbstractDao();
}
