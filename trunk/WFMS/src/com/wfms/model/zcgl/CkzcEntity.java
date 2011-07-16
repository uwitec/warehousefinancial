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
@Table(name="ZCGL_CKZCB"
    ,schema="ECM"
)

public class CkzcEntity  implements java.io.Serializable {


    // Fields    

     private int id;
     private CkxxEntity ckxx;
     private ZclxEntity zclx;
     private ZcxxEntity zcxx;
     private int zccssl;
     private String bz;
     private String zcrkjg;
     private String zcckjg;
     
     public CkzcEntity(){}
     



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

  

    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CKXXID", unique = false, nullable = false, insertable = true, updatable = true)
	public CkxxEntity getCkxx() {
		return ckxx;
	}

	public void setCkxx(CkxxEntity ckxx) {
		this.ckxx = ckxx;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ZCLXID", unique = false, nullable = false, insertable = true, updatable = true)
	public ZclxEntity getZclx() {
		return zclx;
	}

	public void setZclx(ZclxEntity zclx) {
		this.zclx = zclx;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ZCXXID", unique = false, nullable = false, insertable = true, updatable = true)
	public ZcxxEntity getZcxx() {
		return zcxx;
	}

	public void setZcxx(ZcxxEntity zcxx) {
		this.zcxx = zcxx;
	}


    @Column(name="ZCCSSL", unique=false, nullable=true, insertable=true, updatable=true, precision=10)
    public int getZccssl() {
		return zccssl;
	}

	public void setZccssl(int zccssl) {
		this.zccssl = zccssl;
	}

	@Column(name="BZ", unique=false, nullable=true, insertable=true, updatable=true, length=520)
	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	@Column(name="ZCRKJG", unique=false, nullable=true, insertable=true, updatable=true, length=20)
	public String getZcrkjg() {
		return zcrkjg;
	}

	public void setZcrkjg(String zcrkjg) {
		this.zcrkjg = zcrkjg;
	}

	@Column(name="ZCCKJG", unique=false, nullable=true, insertable=true, updatable=true, length=20)
	public String getZcckjg() {
		return zcckjg;
	}

	public void setZcckjg(String zcckjg) {
		this.zcckjg = zcckjg;
	}

}