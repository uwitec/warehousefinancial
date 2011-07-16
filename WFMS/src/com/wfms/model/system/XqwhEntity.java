package com.wfms.model.system;
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
@Table(name="JCSZ_XQWH"
    ,schema="ECM"
, uniqueConstraints = {  }
)

public class XqwhEntity  implements java.io.Serializable {


    // Fields    

     private int id;
     private String xqdm;
     private String xqdz;


    // Constructors

    /** default constructor */
    public XqwhEntity() {
    }

    
    /** full constructor */
    public XqwhEntity(int id, String xqdm, String xqdz) {
        this.id = id;
        this.xqdm = xqdm;
        this.xqdz = xqdz;
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
    
    @Column(name="XQDM", unique=false, nullable=false, insertable=true, updatable=true, length=20)

    public String getXqdm() {
        return this.xqdm;
    }
    
    public void setXqdm(String xqdm) {
        this.xqdm = xqdm;
    }
    
    @Column(name="XQDZ", unique=false, nullable=false, insertable=true, updatable=true, length=100)

    public String getXqdz() {
        return this.xqdz;
    }
    
    public void setXqdz(String xqdz) {
        this.xqdz = xqdz;
    }
   








}