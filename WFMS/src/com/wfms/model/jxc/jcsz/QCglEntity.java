package com.wfms.model.jxc.jcsz;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * XqwhEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="jxc_qcgl",schema="ECM", uniqueConstraints = {  })

public class QCglEntity  implements java.io.Serializable {
	
    // Fields    
     private int id;
     private String qcpz;
     private String fdjh;
     private String gmrq;
     private String scrq;
     private String cz;
     private String gmje;
     private String memo;

    // Constructors

    /** default constructor */
    public QCglEntity() {
    }
    
    /** full constructor */
    public QCglEntity(int id, String qcpz, String fdjh, String gmrq, String memo, String scrq, String cz, String gmje) {
        this.id = id;
        this.qcpz = qcpz;
        this.fdjh = fdjh;
        this.gmrq = gmrq;
        this.memo = memo;
        this.scrq = scrq;
        this.cz = cz;
        this.gmje = gmje;
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
    
    @Column(name="QCPZ", unique=false, nullable=false, insertable=true, updatable=true, length=50)
    public String getQcpz() {
        return this.qcpz;
    }
    
    public void setQcpz(String qcpz) {
        this.qcpz = qcpz;
    }
    
    @Column(name="SCRQ", unique=false, nullable=false, insertable=true, updatable=true, length=50)
    public String getScrq() {
        return this.scrq;
    }
    
    public void setScrq(String scrq) {
        this.scrq = scrq;
    }
    
    @Column(name="CZ", unique=false, nullable=false, insertable=true, updatable=true, length=200)
    public String getCz() {
        return this.cz;
    }
    
    public void setCz(String cz) {
        this.cz = cz;
    }
    
    @Column(name="MEMO", unique=false, nullable=false, insertable=true, updatable=true, length=100)
    public String getMemo() {
        return this.memo;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
    }
    
    @Column(name="FDJH", unique=false, nullable=false, insertable=true, updatable=true, length=50)
    public String getFdjh() {
        return this.fdjh;
    }
    
    public void setFdjh(String fdjh) {
        this.fdjh = fdjh;
    }
    
    @Column(name="GMRQ", unique=false, nullable=false, insertable=true, updatable=true, length=200)
    public String getGmrq() {
        return this.gmrq;
    }
    
    public void setGmrq(String gmrq) {
        this.gmrq = gmrq;
    }
    
    @Column(name="GMJE", unique=false, nullable=false, insertable=true, updatable=true, length=100)
    public String getGmje() {
        return this.gmje;
    }
    
    public void setGmje(String gmje) {
        this.gmje = gmje;
    }
}