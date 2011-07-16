package com.wfms.model.jxc.fygl;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * ztwhEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="JXC_YWFY",schema="ECM", uniqueConstraints = {  })

public class YwfywhEntity  implements java.io.Serializable {
	
    // Fields    
     private int id;
     private String khid;
     private String khmc;
     private String zt;
     private String je;
     private String jysj;
     private String jsrid;
     private String jsrmc;
     private String ywdid;
     private String memo;
     private String czrid;
     private String czrmc;
     private String czsj;

    // Constructors

	/** default constructor */
    public YwfywhEntity() {
    }
    
    /** full constructor */
    public YwfywhEntity(int id, String khid, String khmc, String zt, String memo, String je, String jysj, String jsrid, String jsrmc, String ywdid) {
        this.id = id;
        this.khid = khid;
        this.khmc = khmc;
        this.zt = zt;
        this.memo = memo;
        this.je = je;
        this.jysj = jysj;
        this.jsrid = jsrid;
        this.jsrmc = jsrmc;
        this.ywdid = ywdid;
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
    
    @Column(name="KHID", unique=false, nullable=false, insertable=true, updatable=true, length=50)
    public String getKhid() {
        return this.khid;
    }
    
    public void setKhid(String khid) {
        this.khid = khid;
    }
    
    @Column(name="JE", unique=false, nullable=false, insertable=true, updatable=true, length=50)
    public String getJe() {
        return this.je;
    }
    
    public void setJe(String je) {
        this.je = je;
    }
    
    @Column(name="JYSJ", unique=false, nullable=false, insertable=true, updatable=true, length=200)
    public String getJysj() {
        return this.jysj;
    }
    
    public void setJysj(String jysj) {
        this.jysj = jysj;
    }
    
    @Column(name="MEMO", unique=false, nullable=false, insertable=true, updatable=true, length=100)
    public String getMemo() {
        return this.memo;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
    }
    
    @Column(name="KHMC", unique=false, nullable=false, insertable=true, updatable=true, length=50)
    public String getKhmc() {
        return this.khmc;
    }
    
    public void setKhmc(String khmc) {
        this.khmc = khmc;
    }
    
    @Column(name="ZT", unique=false, nullable=false, insertable=true, updatable=true, length=200)
    public String getZt() {
        return this.zt;
    }
    
    public void setZt(String zt) {
        this.zt = zt;
    }
    
    @Column(name="JSRID", unique=false, nullable=false, insertable=true, updatable=true, length=100)
    public String getJsrid() {
        return this.jsrid;
    }
    
    public void setJsrid(String jsrid) {
        this.jsrid = jsrid;
    }
    
    @Column(name="JSRMC", unique=false, nullable=false, insertable=true, updatable=true, length=100)
    public String getJsrmc() {
        return this.jsrmc;
    }
    
    public void setJsrmc(String jsrmc) {
        this.jsrmc = jsrmc;
    }
    
    @Column(name="YWDID", unique=false, nullable=false, insertable=true, updatable=true, length=100)
    public String getYwdid() {
        return this.ywdid;
    }

	public void setYwdid(String ywdid) {
        this.ywdid = ywdid;
    }
    
    @Column(name="CZRID", unique=false, nullable=false, insertable=true, updatable=true, length=50)
    public String getCzrid() {
		return czrid;
	}

	public void setCzrid(String czrid) {
		this.czrid = czrid;
	}
    
    @Column(name="CZRMC", unique=false, nullable=false, insertable=true, updatable=true, length=100)
    public String getCzrmc() {
		return czrmc;
	}

	public void setCzrmc(String czrmc) {
		this.czrmc = czrmc;
	}
    
    @Column(name="CZSJ", unique=false, nullable=false, insertable=true, updatable=true, length=100)
    public String getCzsj() {
		return czsj;
	}

	public void setCzsj(String czsj) {
		this.czsj = czsj;
	}
}