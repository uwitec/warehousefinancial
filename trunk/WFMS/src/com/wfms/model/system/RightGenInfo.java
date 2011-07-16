package com.wfms.model.system;

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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.wfms.common.entity.Invisible;

/**
 * RightGenInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "RIGHT_GENINF", schema = "ECM", uniqueConstraints = {})
@JsonIgnoreProperties(value = {"hibernateLazyInitializer","module","partRights","memberRights"})
public class RightGenInfo implements java.io.Serializable {

	// Fields

	private Integer rightId;
	private Integer parentId;
	private String systemId;
	private String name;
	private String description;
	private String rightStatus;
	private ModuleGenInfo module;
	private List<PartRight> partRights = new ArrayList<PartRight>(0);
	private List<MemberRight> memberRights = new ArrayList<MemberRight>(0);


	/** default constructor */
	public RightGenInfo() {
	}
	
	public RightGenInfo(Integer rightId)
	{
		this.rightId = rightId;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="webdemo_gen")
	@TableGenerator(name="webdemo_gen",
			table="tb_generator",
			pkColumnName="gen_name",
			valueColumnName="gen_value",
			pkColumnValue="right_pk",
			allocationSize=1)
	@Column(name = "RightID", unique = true, nullable = false, insertable = true, updatable = true, precision = 10, scale = 0)
	public Integer getRightId() {
		return this.rightId;
	}

	public void setRightId(Integer rightId) {
		this.rightId = rightId;
	}

	@Column(name = "ParentID", unique = false, nullable = true, insertable = true, updatable = true, precision = 10, scale = 0)
	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Column(name = "SystemID", unique = false, nullable = false, insertable = true, updatable = true, length = 30)
	public String getSystemId() {
		return this.systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	@Column(name = "Name", unique = false, nullable = false, insertable = true, updatable = true, length = 250)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "Description", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "RightStatus", unique = false, nullable = false, insertable = true, updatable = true, length = 1)
	public String getRightStatus() {
		return this.rightStatus;
	}

	public void setRightStatus(String rightStatus) {
		this.rightStatus = rightStatus;
	}

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "right")
	@Invisible
	public List<PartRight> getPartRights() {
		return this.partRights;
	}

	public void setPartRights(List<PartRight> partRights) {
		this.partRights = partRights;
	}

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "right")
	@Invisible
	public List<MemberRight> getMemberRights() {
		return this.memberRights;
	}

	public void setMemberRights(List<MemberRight> memberRights) {
		this.memberRights = memberRights;
	}

	@OneToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="ModuleID",updatable=false)  
	public ModuleGenInfo getModule() {
		return module;
	}

	public void setModule(ModuleGenInfo module) {
		this.module = module;
	}

}