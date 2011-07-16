package com.wfms.model.zcgl;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * CkdmxEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="ZCGL_CKDMX",schema="ECM")

public class CkdmxEntity  implements java.io.Serializable {


    // Fields    

     private int id;
     private CkdEntity ckd;
     private ZclxEntity zclx;
     private ZcxxEntity zcxx;
     
     private double ckjg;
     private int cksl;
     private String zczbh;
     private String scrq;
     private String zcbh;
     private String yjghsj;
     private String cklx;
     private String zje;
     private String bcje;


   
    // Property accessors
    @Id
    @Column(name="ID", unique=false, nullable=false, insertable=true, updatable=true, precision=10, scale=0)
    @GeneratedValue(strategy=GenerationType.AUTO)
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    @Column(name="CKJG", unique=false, nullable=false, insertable=true, updatable=true, precision=10)

    public double getCkjg() {
        return this.ckjg;
    }
    
    public void setCkjg(double ckjg) {
        this.ckjg = ckjg;
    }

    @Column(name="CKSL", unique=false, nullable=false, insertable=true, updatable=true, precision=10)

    public int getCksl() {
        return this.cksl;
    }
    
    public void setCksl(int cksl) {
        this.cksl = cksl;
    }

    @Column(name="ZCZBH", unique=false, nullable=true, insertable=true, updatable=true, length=100)

    public String getZczbh() {
        return this.zczbh;
    }
    
    public void setZczbh(String zczbh) {
        this.zczbh = zczbh;
    }

    @Column(name="SCRQ", unique=false, nullable=true, insertable=true, updatable=true, length=20)

    public String getScrq() {
        return this.scrq;
    }
    
    public void setScrq(String scrq) {
        this.scrq = scrq;
    }

    @Column(name="ZCBH", unique=false, nullable=true, insertable=true, updatable=true, length=100)

    public String getZcbh() {
        return this.zcbh;
    }
    
    public void setZcbh(String zcbh) {
        this.zcbh = zcbh;
    }

    @Column(name="YJGHSJ", unique=false, nullable=true, insertable=true, updatable=true, length=20)

    public String getYjghsj() {
        return this.yjghsj;
    }
    
    public void setYjghsj(String yjghsj) {
        this.yjghsj = yjghsj;
    }

    @Column(name="CKLX", unique=false, nullable=false, insertable=true, updatable=true, length=20)
    public String getCklx() {
        return this.cklx;
    }
    
    public void setCklx(String cklx) {
        this.cklx = cklx;
    }

    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CKDID", unique = false, nullable = false, insertable = true, updatable = true)
	public CkdEntity getCkd() {
		return ckd;
	}

	public void setCkd(CkdEntity ckd) {
		this.ckd = ckd;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ZCLBID", unique = false, nullable = false, insertable = true, updatable = true)
	public ZclxEntity getZclx() {
		return zclx;
	}

	public void setZclx(ZclxEntity zclx) {
		this.zclx = zclx;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ZCID", unique = false, nullable = false, insertable = true, updatable = true)
	public ZcxxEntity getZcxx() {
		return zcxx;
	}

	public void setZcxx(ZcxxEntity zcxx) {
		this.zcxx = zcxx;
	}

    @Column(name="ZJE", unique=false, nullable=false, insertable=true, updatable=true, length=20)
    public String getZje() {
		return zje;
	}

	public void setZje(String zje) {
		this.zje = zje;
	}

    @Column(name="BCJE", unique=false, nullable=false, insertable=true, updatable=true, length=20)
    public String getBcje() {
		return bcje;
	}

	public void setBcje(String bcje) {
		this.bcje = bcje;
	}


}