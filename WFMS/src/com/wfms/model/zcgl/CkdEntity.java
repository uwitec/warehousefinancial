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
 * CkdEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="ZCGL_CKD",schema="ECM")

public class CkdEntity  implements java.io.Serializable {


    // Fields    

     private int id;
     private String ckdbh;
     private CkxxEntity ckxx;
     private String cksj;
     private String lybmid;
     private String lyrid;
     private String lyrxm;
     private String lybmmc;
     private String czrid;
     private String czrmc;
     private String czsj;
     private String bz;
     private String zt;

     private List<CkdmxEntity> ckdmxs = new ArrayList<CkdmxEntity>();
   
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

    @Column(name="CKDBH", unique=false, nullable=false, insertable=true, updatable=true, length=100)

    public String getCkdbh() {
        return this.ckdbh;
    }
    
    public void setCkdbh(String ckdbh) {
        this.ckdbh = ckdbh;
    }

    @Column(name="CKSJ", unique=false, nullable=false, insertable=true, updatable=true, length=20)

    public String getCksj() {
        return this.cksj;
    }
    
    public void setCksj(String cksj) {
        this.cksj = cksj;
    }

    @Column(name="LYBMID", unique=false, nullable=false, insertable=true, updatable=true, length=30)

    public String getLybmid() {
        return this.lybmid;
    }
    
    public void setLybmid(String lybmid) {
        this.lybmid = lybmid;
    }

    @Column(name="LYRID", unique=false, nullable=false, insertable=true, updatable=true, length=30)

    public String getLyrid() {
        return this.lyrid;
    }
    
    public void setLyrid(String lyrid) {
        this.lyrid = lyrid;
    }

    @Column(name="LYRXM", unique=false, nullable=false, insertable=true, updatable=true, length=20)

    public String getLyrxm() {
        return this.lyrxm;
    }
    
    public void setLyrxm(String lyrxm) {
        this.lyrxm = lyrxm;
    }

    @Column(name="LYBMMC", unique=false, nullable=false, insertable=true, updatable=true, length=30)

    public String getLybmmc() {
        return this.lybmmc;
    }
    
    public void setLybmmc(String lybmmc) {
        this.lybmmc = lybmmc;
    }

    @Column(name="CZRID", unique=false, nullable=false, insertable=true, updatable=true, length=30)

    public String getCzrid() {
        return this.czrid;
    }
    
    public void setCzrid(String czrid) {
        this.czrid = czrid;
    }

    @Column(name="CZRMC", unique=false, nullable=false, insertable=true, updatable=true, length=20)

    public String getCzrmc() {
        return this.czrmc;
    }
    
    public void setCzrmc(String czrmc) {
        this.czrmc = czrmc;
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

    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CKID", unique = false, nullable = false, insertable = true, updatable = true)
	public CkxxEntity getCkxx() {
		return ckxx;
	}

	public void setCkxx(CkxxEntity ckxx) {
		this.ckxx = ckxx;
	}
   
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "ckd")
    public List<CkdmxEntity> getCkdmxs() {
		return ckdmxs;
	}

	public void setCkdmxs(List<CkdmxEntity> ckdmxs) {
		this.ckdmxs = ckdmxs;
	}
	
	public void addCkdmx(CkdmxEntity ckdmx) {
		ckdmx.setCkd(this);
		this.ckdmxs.add(ckdmx);
	}

    @Column(name="ZT", unique=false, nullable=true, insertable=true, updatable=true, length=500)
    public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}
}