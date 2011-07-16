package com.wfms.model.jxc.fygl;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * FywhEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="jxc_fywh",schema="ECM", uniqueConstraints = {  })

public class FywhEntity  implements java.io.Serializable {
	
    // Fields    
     private int id;
     private String fylx;
     private String xfzid;
     private String fy;
     private String jlsj;
     private String jlrid;
     private String jlrmc;
     private String xfzmc;
     private String xfsj;
     private String memo;
     
     private String xfid;
     private String xfmc;

    // Constructors

 	@Transient 
    public String getXfid() {
		return xfid;
	}

	public void setXfid(String xfid) {
		this.xfid = xfid;
	}

	@Transient 
	public String getXfmc() {
		return xfmc;
	}

	public void setXfmc(String xfmc) {
		this.xfmc = xfmc;
	}

	/** default constructor */
    public FywhEntity() {
    }
    
    /** full constructor */
    public FywhEntity(int id, String fylx, String xfzid, String fy, String memo, String jlsj, String jlrid, String jlrmc, String xfzmc, String xfsj) {
        this.id = id;
        this.fylx = fylx;
        this.xfzid = xfzid;
        this.fy = fy;
        this.memo = memo;
        this.jlsj = jlsj;
        this.jlrid = jlrid;
        this.jlrmc = jlrmc;
        this.xfzmc = xfzmc;
        this.xfsj = xfsj;
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
    
    @Column(name="FYLX", unique=false, nullable=false, insertable=true, updatable=true, length=50)
    public String getFylx() {
        return this.fylx;
    }
    
    public void setFylx(String fylx) {
        this.fylx = fylx;
    }
    
    @Column(name="JLSJ", unique=false, nullable=false, insertable=true, updatable=true, length=50)
    public String getJlsj() {
        return this.jlsj;
    }
    
    public void setJlsj(String jlsj) {
        this.jlsj = jlsj;
    }
    
    @Column(name="JLRID", unique=false, nullable=false, insertable=true, updatable=true, length=200)
    public String getJlrid() {
        return this.jlrid;
    }
    
    public void setJlrid(String jlrid) {
        this.jlrid = jlrid;
    }
    
    @Column(name="MEMO", unique=false, nullable=false, insertable=true, updatable=true, length=100)
    public String getMemo() {
        return this.memo;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
    }
    
    @Column(name="XFZID", unique=false, nullable=false, insertable=true, updatable=true, length=50)
    public String getXfzid() {
        return this.xfzid;
    }
    
    public void setXfzid(String xfzid) {
        this.xfzid = xfzid;
    }
    
    @Column(name="FY", unique=false, nullable=false, insertable=true, updatable=true, length=200)
    public String getFy() {
        return this.fy;
    }
    
    public void setFy(String fy) {
        this.fy = fy;
    }
    
    @Column(name="JLRMC", unique=false, nullable=false, insertable=true, updatable=true, length=100)
    public String getJlrmc() {
        return this.jlrmc;
    }
    
    public void setJlrmc(String jlrmc) {
        this.jlrmc = jlrmc;
    }
    
    @Column(name="XFZMC", unique=false, nullable=false, insertable=true, updatable=true, length=100)
    public String getXfzmc() {
        return this.xfzmc;
    }
    
    public void setXfzmc(String xfzmc) {
        this.xfzmc = xfzmc;
    }
    
    @Column(name="XFSJ", unique=false, nullable=false, insertable=true, updatable=true, length=100)
    public String getXfsj() {
        return this.xfsj;
    }
    
    public void setXfsj(String xfsj) {
        this.xfsj = xfsj;
    }
}