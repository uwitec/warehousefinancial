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
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * CkxxEntityId entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="ZCGL_CKXX"
    ,schema="ECM"
)
public class CkxxEntity  implements java.io.Serializable {


    // Fields    

     private int id;
     private String ckdm;
     private String ckmc;
     private String ckdd;
     private String glyid;
     private String glyxm;
     private String bz;
     
     private List<CkzcEntity> ckzcList = new ArrayList<CkzcEntity>();
     
     public CkxxEntity(){
    	 
     }
     public CkxxEntity(int ckId){
    	 
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

    @Column(name="CKDM", unique=false, nullable=false, insertable=true, updatable=true, length=20)

    public String getCkdm() {
        return this.ckdm;
    }
    
    public void setCkdm(String ckdm) {
        this.ckdm = ckdm;
    }

    @Column(name="CKMC", unique=false, nullable=false, insertable=true, updatable=true, length=40)

    public String getCkmc() {
        return this.ckmc;
    }
    
    public void setCkmc(String ckmc) {
        this.ckmc = ckmc;
    }

    @Column(name="CKDD", unique=false, nullable=false, insertable=true, updatable=true, length=60)

    public String getCkdd() {
        return this.ckdd;
    }
    
    public void setCkdd(String ckdd) {
        this.ckdd = ckdd;
    }

    @Column(name="GLYID", unique=false, nullable=false, insertable=true, updatable=true, length=30)

    public String getGlyid() {
        return this.glyid;
    }
    
    public void setGlyid(String glyid) {
        this.glyid = glyid;
    }

    @Column(name="GLYXM", unique=false, nullable=false, insertable=true, updatable=true, length=20)

    public String getGlyxm() {
        return this.glyxm;
    }
    
    public void setGlyxm(String glyxm) {
        this.glyxm = glyxm;
    }

    @Column(name="BZ", unique=false, nullable=true, insertable=true, updatable=true, length=500)

    public String getBz() {
        return this.bz;
    }
    
    public void setBz(String bz) {
        this.bz = bz;
    }

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "ckxx")
	public List<CkzcEntity> getCkzcList() {
		return ckzcList;
	}

	public void setCkzcList(List<CkzcEntity> ckzcList) {
		this.ckzcList = ckzcList;
	}
   
	public void addCkzc(CkzcEntity ckzc){
		ckzc.setCkxx(this);
		ckzcList.add(ckzc);
	}



   
}