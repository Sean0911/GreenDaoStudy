package com.liujc.greendao.Bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 类名称：Classes
 * 创建者：Create by liujc
 * 创建时间：Create on 2016/12/5 10:05
 * 描述：TODO
 * 最近修改时间：2016/12/5 10:05
 * 修改人：Modify by liujc
 */
@Entity
public class Profession {
    @Id(autoincrement = true)
    private Long id;
    private String major;
    private String college ;
    @Generated(hash = 442713316)
    public Profession(Long id, String major, String college) {
        this.id = id;
        this.major = major;
        this.college = college;
    }
    @Generated(hash = 900874100)
    public Profession() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMajor() {
        return this.major;
    }
    public void setMajor(String major) {
        this.major = major;
    }
    public String getCollege() {
        return this.college;
    }
    public void setCollege(String college) {
        this.college = college;
    }
}
