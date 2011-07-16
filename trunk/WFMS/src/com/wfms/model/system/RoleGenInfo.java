package com.wfms.model.system;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.wfms.common.entity.BaseTree;
import com.wfms.common.entity.Invisible;
import com.wfms.common.entity.Tree;


/**
 * RolGenInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="ROL_GENINF"
    ,schema="ECM"
, uniqueConstraints = {  }
)
@JsonIgnoreProperties(value={"parent","childNodes","children","parts","members","roleMembers","roleParts","company","objects"})
public class RoleGenInfo  extends BaseTree implements java.io.Serializable {


    // Fields    

     private String rolid;
     private String name;
     private String customId;
     private String synopsis;
     private String siblingorder;
     private String createTime;
     private Company company;
     private DepartGenInfo depart;
     private List<PartGenInfo> parts = new ArrayList<PartGenInfo>(0);
     private List<MemberGenInfo> members = new ArrayList<MemberGenInfo>(0);
     //private List<MemberGenInfo> mainMembers = new ArrayList<MemberGenInfo>(0);
     private List<RolePart> roleParts = new ArrayList<RolePart>(0); 
     private String roleType;
     
	/** default constructor */
     public RoleGenInfo() {
     }

 	/** minimal constructor */
     public RoleGenInfo(String rolid) {
         this.rolid = rolid;
     }
     
     /** full constructor */
     public RoleGenInfo(String rolid, String name, DepartGenInfo depart, String id, String synopsis, String siblingorder) {
         this.rolid = rolid;
         this.name = name;
         this.depart = depart;
         this.id = id;
         this.synopsis = synopsis;
         this.siblingorder = siblingorder;
     }

    //@ManyToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
    @Invisible
    @OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
    @JoinTable(name="ROLE_PART",joinColumns={@JoinColumn(name="ROLID",referencedColumnName="ROLID")},
    		inverseJoinColumns={@JoinColumn(name="PARTID",referencedColumnName="PARTID")})
    public List<PartGenInfo> getParts() {
		return parts;
	}
    
	public void setPartGenInfo(List<PartGenInfo> parts) {
		this.parts = parts;
	}

	@Invisible
	//@OneToMany(mappedBy="role",cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    //@JoinTable(name="ROL_MEM",joinColumns={@JoinColumn(name="ROLID")},
    	//inverseJoinColumns={@JoinColumn(name="MEMID")})
	@ManyToMany
	@JoinTable(name="ROL_MEM",joinColumns={@JoinColumn(name="ROLID")},
	    	inverseJoinColumns={@JoinColumn(name="MEMID")})
	@NotFound(action=NotFoundAction.IGNORE)
	public List<MemberGenInfo> getMembers() {
		return members;
	}

	public void setMembers(List<MemberGenInfo> members) {
		this.members = members;
	}
   
    @Id
    @GeneratedValue(generator = "pk")
	@GenericGenerator(name = "pk", strategy = "xuner.web.orm.IdGenerator")
    @Column(name="ROLID", unique=true, nullable=false, insertable=true, updatable=true, length=10)

    public String getRolid() {
        return this.rolid;
    }
    
    public void setRolid(String rolid) {
        this.rolid = rolid;
    }
    
    @Column(name="ID", unique=false, nullable=true, insertable=true, updatable=true, length=30)
	public String getCustomId() {
		return customId;
	}

	public void setCustomId(String customId) {
		this.customId = customId;
	}
    
    @Column(name="NAME", unique=false, nullable=true, insertable=true, updatable=true, length=250)
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
    
    @Column(name="SIBLINGORDER", unique=false, nullable=true, insertable=true, updatable=true, length=30)
    public String getSiblingorder() {
        return this.siblingorder;
    }
    
    public void setSiblingorder(String siblingorder) {
        this.siblingorder = siblingorder;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="DEPID",referencedColumnName="COMID",insertable=false,updatable=false)
    @NotFound(action=NotFoundAction.IGNORE)
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

	 @ManyToOne(fetch=FetchType.EAGER,cascade={CascadeType.ALL})
     @JoinColumn(name = "DEPID")  
     @NotFound(action=NotFoundAction.IGNORE)
	public DepartGenInfo getDepart() {
		 return depart;
	}

	public void setDepart(DepartGenInfo depart) {
		this.depart = depart;
	}

	public void setParts(List<PartGenInfo> parts) {
		this.parts = parts;
	}

	/*@Invisible
	//@Transient
	@OneToMany(mappedBy="role",cascade=CascadeType.PERSIST,fetch=FetchType.LAZY)
	//@JoinTable(name="ROL_MEM",joinColumns={@JoinColumn(name="ROLID")},
	//    	inverseJoinColumns={@JoinColumn(name="MEMID")})
	@NotFound(action=NotFoundAction.IGNORE)
	public List<RoleMember> getRoleMembers() {
		return roleMembers;
	}

	public void setRoleMembers(List<RoleMember> roleMembers) {
		this.roleMembers = roleMembers;
	}*/

	@Invisible
	@OneToMany(cascade=CascadeType.PERSIST,fetch=FetchType.LAZY)
	@JoinColumn(name="ROLID")
	public List<RolePart> getRoleParts() {
		return roleParts;
	}

	public void setRoleParts(List<RolePart> roleParts) {
		this.roleParts = roleParts;
	}

	@Column(name="CREATETIME",insertable=true,updatable=true,length=20)
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

    @Transient
    public String getId() {
        return this.getRolid();
    }
    
	@Transient
	@Override
	public List getChildNodes() {
		return null;
	}

	@Transient
	@Override
	public List getObjects() {
		return this.getMembers();
	}

	@Transient
	@Override
	public Tree getParent() {
		return this.getDepart();
	}

	@Transient
	@Override
	public String getQtip() {
		return this.getSynopsis();
	}

	@Transient
	@Override
	public String getText() {
		return this.getName();
	}
	
	@Transient
	@Override
	public String getIcon(){
		return "";
	}
	
	@Transient
    public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	/*@OneToMany(mappedBy="role")
	public List<MemberGenInfo> getMainMembers() {
		return mainMembers;
	}

	public void setMainMembers(List<MemberGenInfo> mainMembers) {
		this.mainMembers = mainMembers;
	}*/
	
	
}