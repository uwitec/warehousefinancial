package com.wfms.model.zcgl;
// default package

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * RkdmxEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="ZCGL_RKDMX",schema="ECM")

public class RkdmxEntity  implements java.io.Serializable {


    // Fields    

     private int id;
     private RkdEntity rkd;
     private ZclxEntity zclx;
     private ZcxxEntity zcxx;
     private double rkjg;
     private int rksl;
     private String scrq;
     private String bzq;
     
     
     private String bz;
     private int kczl;
     
     
     private int scsx = 0;
     private int zclxId ; 
     private int zcxxId ; 
     private String zcgg;
     private String zcdw; 
     private String zje;
     private String bcje;

    @Transient
	public int getZclxId() {
		return zclxId;
	}

	public void setZclxId(int zclxId) {
		this.zclxId = zclxId;
	}

	@Transient
	public int getZcxxId() {
		return zcxxId;
	}

	public void setZcxxId(int zcxxId) {
		this.zcxxId = zcxxId;
	}

	@Transient
	public String getZcgg() {
		return zcgg;
	}

	public void setZcgg(String zcgg) {
		this.zcgg = zcgg;
	}

	@Transient
	public String getZcdw() {
		return zcdw;
	}

	public void setZcdw(String zcdw) {
		this.zcdw = zcdw;
	}

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

    @Column(name="RKJG", unique=false, nullable=false, insertable=true, updatable=true, precision=10)
    public double getRkjg() {
        return this.rkjg;
    }
    
    public void setRkjg(double rkjg) {
        this.rkjg = rkjg;
    }

    @Column(name="RKSL", unique=false, nullable=false, insertable=true, updatable=true, precision=10, scale=0)
    public int getRksl() {
        return this.rksl;
    }
    
    public void setRksl(int rksl) {
        this.rksl = rksl;
    }

    @Column(name="SCRQ", unique=false, nullable=true, insertable=true, updatable=true, length=20)
    public String getScrq() {
        return this.scrq;
    }
    
    public void setScrq(String scrq) {
        this.scrq = scrq;
    }

    @Column(name="BZQ", unique=false, nullable=true, insertable=true, updatable=true, length=20)
    public String getBzq() {
        return this.bzq;
    }
    
    public void setBzq(String bzq) {
        this.bzq = bzq;
    }

    @Column(name="BZ", unique=false, nullable=true, insertable=true, updatable=true, length=500)
    public String getBz() {
        return this.bz;
    }
    
    public void setBz(String bz) {
        this.bz = bz;
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

	@ManyToOne(optional = false, cascade = CascadeType.REFRESH)  
    @JoinColumn(name = "RKDID" ,referencedColumnName="ID")    
	public RkdEntity getRkd() {
		return rkd;
	}

	public void setRkd(RkdEntity rkd) {
		this.rkd = rkd;
	}

	@Transient 
	public int getScsx() {
			return scsx;
	}

	public void setScsx(int scsx) {
		this.scsx = scsx;
	}
	
	@Column(name="KCZL", unique=false, nullable=false, insertable=true, updatable=true, precision=10, scale=0)
	public int getKczl() {
		return kczl;
	}

	public void setKczl(int kczl) {
		this.kczl = kczl;
	}

    @Column(name="ZJE", unique=false, nullable=true, insertable=true, updatable=true, length=500)
    public String getZje() {
		return zje;
	}

	public void setZje(String zje) {
		this.zje = zje;
	}

    @Column(name="BCJE", unique=false, nullable=true, insertable=true, updatable=true, length=500)
    public String getBcje() {
		return bcje;
	}

	public void setBcje(String bcje) {
		this.bcje = bcje;
	}
}