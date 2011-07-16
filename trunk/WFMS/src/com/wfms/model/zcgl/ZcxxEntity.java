package com.wfms.model.zcgl;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * ZcxxEntityId entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="ZCGL_ZCXX"
    ,schema="ECM"
)
public class ZcxxEntity  implements java.io.Serializable {


    // Fields    

     private int id;
     private String zcdm;
     private String zcmc;
     private String zclx;
     private String zcgg;
     private String zcdw;
     private String sccj;
     private String bz;

     private Integer kczl;

     public ZcxxEntity(){
    	 
     }
     public ZcxxEntity(int zcxxId){
    	 
     }

   
    // Property accessors
    @Id
    @Column(name="ID", unique=true, nullable=false, insertable=true, updatable=true, precision=10, scale=0)
    @GeneratedValue(strategy=GenerationType.AUTO)
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    @Column(name="ZCDM", unique=false, nullable=false, insertable=true, updatable=true, length=20)

    public String getZcdm() {
        return this.zcdm;
    }
    
    public void setZcdm(String zcdm) {
        this.zcdm = zcdm;
    }

    @Column(name="ZCMC", unique=false, nullable=false, insertable=true, updatable=true, length=40)

    public String getZcmc() {
        return this.zcmc;
    }
    
    public void setZcmc(String zcmc) {
        this.zcmc = zcmc;
    }

    @Column(name="ZCLX", unique=false, nullable=false, insertable=true, updatable=true, length=20)

    public String getZclx() {
        return this.zclx;
    }
    
    public void setZclx(String zclx) {
        this.zclx = zclx;
    }

    @Column(name="BZ", unique=false, nullable=true, insertable=true, updatable=true, length=500)

    public String getBz() {
        return this.bz;
    }
    
    public void setBz(String bz) {
        this.bz = bz;
    }

    @Column(name="ZCGG", unique=false, nullable=false, insertable=true, updatable=true, length=40)
	public String getZcgg() {
		return zcgg;
	}

	public void setZcgg(String zcgg) {
		this.zcgg = zcgg;
	}
	@Column(name="ZCDW", unique=false, nullable=false, insertable=true, updatable=true, length=40)
	public String getZcdw() {
		return zcdw;
	}

	public void setZcdw(String zcdw) {
		this.zcdw = zcdw;
	}

	@Column(name="SCCJ", unique=false, nullable=false, insertable=true, updatable=true, length=40)
	public String getSccj() {
		return sccj;
	}

	public void setSccj(String sccj) {
		this.sccj = sccj;
	}

	@Column(name="KCZL", unique=false, nullable=true, insertable=true, updatable=true, precision=10, scale=0)
	public Integer getKczl() {
		return kczl;
	}

	public void setKczl(Integer kczl) {
		this.kczl = kczl;
	}
   



}