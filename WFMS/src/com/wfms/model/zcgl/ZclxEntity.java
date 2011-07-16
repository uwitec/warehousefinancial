package com.wfms.model.zcgl;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * ZclxEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="ZCGL_ZCLX"
    ,schema="ECM"
, uniqueConstraints = {  }
)
public class ZclxEntity  implements java.io.Serializable {


    // Fields    

     private String lxdm;
     private String lxmc;
     private int id;
     private String bz;
     
     public ZclxEntity(){
    	 
     }
     public ZclxEntity(int zclxId){
    	 
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
     
     
    @Column(name="LXDM", unique=true, nullable=false, insertable=true, updatable=true, length=20)
    public String getLxdm() {
        return this.lxdm;
    }
    
    public void setLxdm(String lxdm) {
        this.lxdm = lxdm;
    }
    
    @Column(name="lxmc", unique=false, nullable=false, insertable=true, updatable=true, length=50)
    public String getLxmc() {
		return lxmc;
	}

	public void setLxmc(String lxmc) {
		this.lxmc = lxmc;
	}

	
    @Column(name="BZ", unique=false, nullable=true, insertable=true, updatable=true, length=500)

    public String getBz() {
        return this.bz;
    }
    
    public void setBz(String bz) {
        this.bz = bz;
    }
}