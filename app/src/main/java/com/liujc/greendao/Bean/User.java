package com.liujc.greendao.Bean;

import com.liujc.greendao.dao.DaoSession;
import com.liujc.greendao.dao.ProfessionDao;
import com.liujc.greendao.dao.UserDao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * 类名称：User
 * 创建者：Create by liujc
 * 创建时间：Create on 2016/12/1 14:55
 * 描述：TODO
 * 最近修改时间：2016/12/1 14:55
 * 修改人：Modify by liujc
 */
@Entity
public class User {
    @Id(autoincrement = true)
    private Long id;
    private String name;
    private int age;
    private boolean isBoy;
    private long professionId;
    @ToOne(joinProperty = "professionId")
    private Profession profession;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1507654846)
    private transient UserDao myDao;
    @Generated(hash = 1303769356)
    public User(Long id, String name, int age, boolean isBoy, long professionId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.isBoy = isBoy;
        this.professionId = professionId;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public boolean getIsBoy() {
        return this.isBoy;
    }
    public void setIsBoy(boolean isBoy) {
        this.isBoy = isBoy;
    }
    public long getProfessionId() {
        return this.professionId;
    }
    public void setProfessionId(long professionId) {
        this.professionId = professionId;
    }
    @Generated(hash = 2049070167)
    private transient Long profession__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1918544418)
    public Profession getProfession() {
        long __key = this.professionId;
        if (profession__resolvedKey == null
                || !profession__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ProfessionDao targetDao = daoSession.getProfessionDao();
            Profession professionNew = targetDao.load(__key);
            synchronized (this) {
                profession = professionNew;
                profession__resolvedKey = __key;
            }
        }
        return profession;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 372447209)
    public void setProfession(@NotNull Profession profession) {
        if (profession == null) {
            throw new DaoException(
                    "To-one property 'professionId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.profession = profession;
            professionId = profession.getId();
            profession__resolvedKey = professionId;
        }
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2059241980)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserDao() : null;
    }
}
