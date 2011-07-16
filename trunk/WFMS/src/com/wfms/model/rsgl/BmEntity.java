package com.wfms.model.rsgl;

// default package

import java.util.HashSet;
import java.util.Set;

/**
 * BmEntity entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class BmEntity implements java.io.Serializable {

	 // Fields    

    private int id;
    private int fjid;
    private String bmmc;
    private String cjsj;
    private String bmms;
    private Set rys = new HashSet(0);


   // Constructors

   /** default constructor */
   public BmEntity() {
   }
   public BmEntity(int id) {
	   this.id = id;
   }

	/** minimal constructor */
   public BmEntity(String bmmc) {
       this.bmmc = bmmc;
   }
   
   /** full constructor */
   public BmEntity(int fjid, String bmmc, String cjsj, String bmms, Set rybs) {
       this.fjid = fjid;
       this.bmmc = bmmc;
       this.cjsj = cjsj;
       this.bmms = bmms;
       this.rys = rybs;
   }

  
   // Property accessors

   public int getId() {
       return this.id;
   }
   
   public void setId(int id) {
       this.id = id;
   }

   public int getFjid() {
       return this.fjid;
   }
   
   public void setFjid(int fjid) {
       this.fjid = fjid;
   }

   public String getBmmc() {
       return this.bmmc;
   }
   
   public void setBmmc(String bmmc) {
       this.bmmc = bmmc;
   }

   public String getCjsj() {
       return this.cjsj;
   }
   
   public void setCjsj(String cjsj) {
       this.cjsj = cjsj;
   }

   public String getBmms() {
       return this.bmms;
   }
   
   public void setBmms(String bmms) {
       this.bmms = bmms;
   }

	public Set getRys() {
		return rys;
	}

	public void setRys(Set rys) {
		this.rys = rys;
	}

	

}