package com.wfms.model.jxc.jcsz;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * XqwhEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="jxc_qcgl",schema="ECM", uniqueConstraints = {  })

public class YgxxEntity  implements java.io.Serializable {
	
    // Fields    
     private int id;
     private String ygmc;
     private String lxdh;
     private String xb;
     private String sfzh;
     private String rzsj;
     private String lzsj;
     private String deptid;
     private String deptmc;
     private String memo;

    // Constructors

    /** default constructor */
    public YgxxEntity() {
    }
    
    /** full constructor */
    public YgxxEntity(int id, String ygmc, String lxdh, String xb, String sfzh
    		, String memo, String rzsj, String lzsj, String deptid, String deptmc) {
        this.id = id;
        this.ygmc = ygmc;
        this.lxdh = lxdh;
        this.xb = xb;
        this.memo = memo;
        this.sfzh = sfzh;
        this.rzsj = rzsj;
        this.lzsj = lzsj;
        this.deptid = deptid;
        this.deptmc = deptmc;
    }

   
    // Property accessors
    @Id
	@Column(name = "ID", unique = true, nullable = false, insertable = true, updatable = true, precision = 10, scale = 0)
	@GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    @Column(name="YGMC", unique=false, nullable=false, insertable=true, updatable=true, length=20)
    public String getYgmc() {
        return this.ygmc;
    }
    
    public void setYgmc(String ygmc) {
        this.ygmc = ygmc;
    }
    
    @Column(name="LXDH", unique=false, nullable=false, insertable=true, updatable=true, length=15)
    public String getLxdh() {
        return this.lxdh;
    }
    
    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }
    
    @Column(name="XB", unique=false, nullable=false, insertable=true, updatable=true, length=2)
    public String getXb() {
        return this.xb;
    }
    
    public void setXb(String xb) {
        this.xb = xb;
    }
    
    @Column(name="MEMO", unique=false, nullable=false, insertable=true, updatable=true, length=500)
    public String getMemo() {
        return this.memo;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
    }
    
    @Column(name="SFZH", unique=false, nullable=false, insertable=true, updatable=true, length=20)
    public String getSfzh() {
        return this.sfzh;
    }
    
    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }
    
    @Column(name="RZSJ", unique=false, nullable=false, insertable=true, updatable=true, length=20)
    public String getRzsj() {
        return this.rzsj;
    }
    
    public void setRzsj(String rzsj) {
        this.rzsj = rzsj;
    }
    
    @Column(name="LZSJ", unique=false, nullable=false, insertable=true, updatable=true, length=20)
    public String getLzsj() {
        return this.lzsj;
    }
    
    public void setLzsj(String lzsj) {
        this.lzsj = lzsj;
    }
    
    @Column(name="DEPTID", unique=false, nullable=false, insertable=true, updatable=true, length=50)
    public String getDeptid() {
        return this.deptid;
    }
    
    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }
    
    @Column(name="DEPTMC", unique=false, nullable=false, insertable=true, updatable=true, length=20)
    public String getDeptmc() {
        return this.deptmc;
    }
    
    public void setDeptmc(String deptmc) {
        this.deptmc = deptmc;
    }
}