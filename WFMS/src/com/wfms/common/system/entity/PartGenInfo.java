package com.wfms.common.system.entity;
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
import javax.persistence.TableGenerator;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.wfms.common.orm.BaseEntity;


/**
 * PartGenInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="PART_GENINF"
, uniqueConstraints = {  }
)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer","partRights","memberParts"})
public class PartGenInfo  extends BaseEntity {


    // Fields    

     private Integer partId;
     private Integer parentId;
     private String name;
     private String partType;
     private String description;
     private String deleteable;
     private String createTime;
     private List<PartRight> partRights = new ArrayList<PartRight>(0);
     private List<MemberPart> memberParts = new ArrayList<MemberPart>(0);

    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="part")
	public List<MemberPart> getMemberParts() {
		return memberParts;
	}

	public void setMemberParts(List<MemberPart> memberParts) {
		this.memberParts = memberParts;
	}

	/** default constructor */
    public PartGenInfo() {
    }
    
    public PartGenInfo(Integer partId){
    	this.partId = partId;
    }
	/** minimal constructor */
    public PartGenInfo(Integer partId, String name) {
        this.partId = partId;
        this.name = name;
    }
    
    /** full constructor */
    public PartGenInfo(Integer partId, Integer parentId, String name, String partType, String description, List<PartRight> partRights) {
        this.partId = partId;
        this.parentId = parentId;
        this.name = name;
        this.partType = partType;
        this.description = description;
        this.partRights = partRights;
    }

   
    // Property accessors
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE,generator="webdemo_gen")
	@TableGenerator(name="webdemo_gen",
			table="tb_generator",
			pkColumnName="gen_name",
			valueColumnName="gen_value",
			pkColumnValue="part_pk",
			allocationSize=1)
    @Column(name="PartID", unique=true, nullable=false, insertable=true, updatable=true, precision=10, scale=0)
    public Integer getPartId() {
        return this.partId;
    }
    
    public void setPartId(Integer partId) {
        this.partId = partId;
    }
    
    @Column(name="ParentID", unique=false, nullable=true, insertable=true, updatable=true, precision=10, scale=0)
    public Integer getParentId() {
        return this.parentId;
    }
    
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
    
    @Column(name="Name", unique=false, nullable=false, insertable=true, updatable=true, length=250)

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="PartType", unique=false, nullable=true, insertable=true, updatable=true, length=10)

    public String getPartType() {
        return this.partType;
    }
    
    public void setPartType(String partType) {
        this.partType = partType;
    }
    
    @Column(name="Description", unique=false, nullable=true, insertable=true, updatable=true, length=512)

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="part")
    public List<PartRight> getPartRights() {
        return this.partRights;
    }
    
    public void setPartRights(List<PartRight> partRights) {
        this.partRights = partRights;
    }

	@Column(name="deleteable", unique=false, nullable=false, insertable=true, updatable=true, length=1)
	public String getDeleteable() {
		return deleteable;
	}

	public void setDeleteable(String deleteable) {
		this.deleteable = deleteable;
	}

	@Column(name="createtime", unique=false, nullable=false, insertable=true, updatable=true, length=20)
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}