package com.wfms.common.system.entity;
// default package

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import com.wfms.common.orm.BaseEntity;


/**
 * Company entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="COMPANY"
, uniqueConstraints = {  }
)
@JsonIgnoreProperties(value={"departs","roles"})
public class Company  extends BaseEntity {


    // Fields    

     private String comid;
     private String name;
     private String managerid;
     private String siblingorder;
     private List<DepartGenInfo> departs = new ArrayList<DepartGenInfo>(0); 
     private List<RoleGenInfo> roles = new ArrayList<RoleGenInfo>(0);

    // Constructors

    /** default constructor */
    public Company() {
    }
    
    public Company(String comid)
    {
    	this.comid = comid;
    }

	/** minimal constructor */
    public Company(String comid, String name) {
        this.comid = comid;
        this.name = name;
    }
    
    /** full constructor */
    public Company(String comid, String name, String managerid, String siblingorder) {
        this.comid = comid;
        this.name = name;
        this.managerid = managerid;
        this.siblingorder = siblingorder;
    }

   
    // Property accessors
    @Id
    @GeneratedValue(generator = "pk")
	@GenericGenerator(name = "pk", strategy = "xuner.web.orm.IdGenerator")
    @Column(name="COMID", unique=true, nullable=false, insertable=true, updatable=true, length=30)

    public String getComid() {
        return this.comid;
    }
    
    public void setComid(String comid) {
        this.comid = comid;
    }
    
    @Column(name="NAME", unique=false, nullable=false, insertable=true, updatable=true, length=250)

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="MANAGERID", unique=false, nullable=true, insertable=true, updatable=true, length=30)

    public String getManagerid() {
        return this.managerid;
    }
    
    public void setManagerid(String managerid) {
        this.managerid = managerid;
    }
    
    @Column(name="SIBLINGORDER", unique=false, nullable=true, insertable=true, updatable=true, length=30)

    public String getSiblingorder() {
        return this.siblingorder;
    }
    
    public void setSiblingorder(String siblingorder) {
        this.siblingorder = siblingorder;
    }
    
    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="company")
    @OrderBy("name")
	public List<DepartGenInfo> getDeparts() {
		return departs;
	}

	public void setDeparts(List<DepartGenInfo> departs) {
		this.departs = departs;
	}

	@OneToMany(fetch=FetchType.LAZY,mappedBy="depart")
	public List<RoleGenInfo> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleGenInfo> roles) {
		this.roles = roles;
	}

}