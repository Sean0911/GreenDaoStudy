package com.liujc.greendao.Bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 类名称：LTest
 * 创建者：Create by liujc
 * 创建时间：Create on 2016/12/5 13:04
 * 描述：TODO
 * 最近修改时间：2016/12/5 13:04
 * 修改人：Modify by liujc
 */
@Entity
public class LTest {
    @Id(autoincrement = true)
    private Long id;
    private String name;
    private String remark;
    private String remark2;
    private String remark3;
    private String remark4;
    @Generated(hash = 974057480)
    public LTest(Long id, String name, String remark, String remark2,
            String remark3, String remark4) {
        this.id = id;
        this.name = name;
        this.remark = remark;
        this.remark2 = remark2;
        this.remark3 = remark3;
        this.remark4 = remark4;
    }
    @Generated(hash = 135633512)
    public LTest() {
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
    public String getRemark() {
        return this.remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getRemark2() {
        return this.remark2;
    }
    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }
    public String getRemark3() {
        return this.remark3;
    }
    public void setRemark3(String remark3) {
        this.remark3 = remark3;
    }
    public String getRemark4() {
        return this.remark4;
    }
    public void setRemark4(String remark4) {
        this.remark4 = remark4;
    }
}
