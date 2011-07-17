package com.wfms.common.system.entity;
// default package

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonMethod;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.wfms.common.attribute.BaseTree;
import com.wfms.common.orm.BaseEntity;


/**
 * DepGenInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="DEP_GENINF"
    ,schema="ECM"
, uniqueConstraints = {  }
)
@JsonAutoDetect(value=JsonMethod.GETTER,getterVisibility=JsonAutoDetect.Visibility.PUBLIC_ONLY)
@JsonIgnoreProperties(value={"chargeRole","roles","parent","children","objects","childNodes"})
public class DepartGenInfo extends BaseEntity{

     private String depid;
     private String name;
     private String customId;
     private String synopsis;
     private String respon;
     private String siblingorder;
     private DepartGenInfo parent;
     private List<DepartGenInfo> children = new ArrayList<DepartGenInfo>(0);
     private Company company;
     private RoleGenInfo  chargeRole;
     private List<RoleGenInfo> roles=new ArrayList<RoleGenInfo>(0);

    // Constructors

    /** default constructor */
    public DepartGenInfo() {
    }
    
	/** minimal constructor */
    public DepartGenInfo(String depid) {
        this.depid = depid;
    }
    
    // Property accessors
    @Id
    @GeneratedValue(generator = "pk")
	@GenericGenerator(name = "pk", strategy = "xuner.web.orm.IdGenerator")
    @Column(name="DEPID", unique=true, nullable=false, insertable=true, updatable=true, length=30)

    public String getDepid() {
        return this.depid;
    }
    
    public void setDepid(String depid) {
        this.depid = depid;
    }
	
    @Column(name="ID", unique=false, nullable=true, insertable=true, updatable=true, length=30)
    public String getCustomId() {
		return customId;
	}

	public void setCustomId(String customId) {
		this.customId = customId;
	}
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="MANAGERID")
	@NotFound(action=NotFoundAction.IGNORE)
   	public RoleGenInfo getChargeRole() {
		return chargeRole;
	}

	public void setChargeRole(RoleGenInfo chargeRole) {
		this.chargeRole = chargeRole;
	}

	@Column(name="NAME", unique=false, nullable=true, insertable=true, updatable=true, length=100)

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="SYNOPSIS", unique=false, nullable=true, insertable=true, updatable=true, length=4000)
    @Basic(fetch=FetchType.LAZY)
    public String getSynopsis() {
        return this.synopsis;
    }
    
    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
    
    @Column(name="RESPON", unique=false, nullable=true, insertable=true, updatable=true, length=4000)
    @Basic(fetch=FetchType.LAZY)
    public String getRespon() {
        return this.respon;
    }
    
    public void setRespon(String respon) {
        this.respon = respon;
    }
    
    @Column(name="SIBLINGORDER", unique=false, nullable=true, insertable=true, updatable=true, length=30)

    public String getSiblingorder() {
        return this.siblingorder;
    }
    
    public void setSiblingorder(String siblingorder) {
        this.siblingorder = siblingorder;
    }
    
    @OneToMany(cascade=CascadeType.PERSIST,mappedBy="depart",fetch=FetchType.LAZY)
	@OrderBy("name ASC")
	public List<RoleGenInfo> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleGenInfo> rolList) {
		this.roles = rolList;
	}
	
	@ManyToOne(fetch=FetchType.LAZY,optional=false)
	@JoinColumn(name = "PARENTID")
	@NotFound(action = NotFoundAction.IGNORE)
	public DepartGenInfo getParent() {
		return parent;
	}

	public void setParent(DepartGenInfo parent) {
		this.parent = parent;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="parentid",referencedColumnName="COMID",insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public Company getCompany() {
		if(company == null)
		{
			return new Company("0");
		}
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@OneToMany(fetch=FetchType.LAZY,mappedBy="parent")
	public List<DepartGenInfo> getChildren() {
		return children;
	}

	public void setChildren(List children) {
		this.children = children;
	}


}