package com.wfms.model.zcgl;
// default package

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * RkdEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="ZCGL_RKD",schema="ECM")

public class RkdEntity  implements java.io.Serializable {


    // Fields    

     private int id;
     private String rkdbh;
     private CkxxEntity ckxx;
     private String rksj;
     private String rkqfzrxm;
     private String rkqfzrid;
     private String lylx;
     private String lydh;
     private String czrid;
     private String czrxm;
     private String czsj;
     private String bz;
     private String zt;
     private List<RkdmxEntity> rkdmxs = new ArrayList<RkdmxEntity>();


   
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

    @Column(name="RKDBH", unique=false, nullable=false, insertable=true, updatable=true, length=100)

    public String getRkdbh() {
        return this.rkdbh;
    }
    
    public void setRkdbh(String rkdbh) {
        this.rkdbh = rkdbh;
    }

    @Column(name="RKSJ", unique=false, nullable=false, insertable=true, updatable=true, length=20)

    public String getRksj() {
        return this.rksj;
    }
    
    public void setRksj(String rksj) {
        this.rksj = rksj;
    }

    @Column(name="RKQFZRXM", unique=false, nullable=false, insertable=true, updatable=true, length=100)

    public String getRkqfzrxm() {
        return this.rkqfzrxm;
    }
    
    public void setRkqfzrxm(String rkqfzrxm) {
        this.rkqfzrxm = rkqfzrxm;
    }

    @Column(name="RKQFZRID", unique=false, nullable=false, insertable=true, updatable=true, length=30)

    public String getRkqfzrid() {
        return this.rkqfzrid;
    }
    
    public void setRkqfzrid(String rkqfzrid) {
        this.rkqfzrid = rkqfzrid;
    }

    @Column(name="LYLX", unique=false, nullable=false, insertable=true, updatable=true, length=20)

    public String getLylx() {
        return this.lylx;
    }
    
    public void setLylx(String lylx) {
        this.lylx = lylx;
    }

    @Column(name="LYDH", unique=false, nullable=true, insertable=true, updatable=true, length=100)

    public String getLydh() {
        return this.lydh;
    }
    
    public void setLydh(String lydh) {
        this.lydh = lydh;
    }

    @Column(name="CZRID", unique=false, nullable=false, insertable=true, updatable=true, length=30)

    public String getCzrid() {
        return this.czrid;
    }
    
    public void setCzrid(String czrid) {
        this.czrid = czrid;
    }

    @Column(name="CZRXM", unique=false, nullable=false, insertable=true, updatable=true, length=20)

    public String getCzrxm() {
        return this.czrxm;
    }
    
    public void setCzrxm(String czrxm) {
        this.czrxm = czrxm;
    }

    @Column(name="CZSJ", unique=false, nullable=false, insertable=true, updatable=true, length=20)
    public String getCzsj() {
        return this.czsj;
    }
    
    public void setCzsj(String czsj) {
        this.czsj = czsj;
    }

    @Column(name="BZ", unique=false, nullable=true, insertable=true, updatable=true, length=500)

    public String getBz() {
        return this.bz;
    }
    
    public void setBz(String bz) {
        this.bz = bz;
    }

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)  
    @JoinColumn(name = "CKID" ,referencedColumnName="ID")    
	public CkxxEntity getCkxx() {
		return ckxx;
	}

	public void setCkxx(CkxxEntity ckxx) {
		this.ckxx = ckxx;
	}

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "rkd")
	public List<RkdmxEntity> getRkdmxs() {
		return rkdmxs;
	}

	public void setRkdmxs(List<RkdmxEntity> rkdmxs) {
		this.rkdmxs = rkdmxs;
	}

	public void addRkdmx(RkdmxEntity rkdmx) {
		rkdmx.setRkd(this);
		this.rkdmxs.add(rkdmx);
	}

    @Column(name="ZT", unique=false, nullable=false, insertable=true, updatable=true, length=20)
    public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}
}