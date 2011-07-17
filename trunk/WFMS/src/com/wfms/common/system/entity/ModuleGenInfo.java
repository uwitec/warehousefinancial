package com.wfms.common.system.entity;

// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.wfms.common.orm.BaseEntity;

/**
 * ModuleGenInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "MODULE_GENINF", uniqueConstraints = {})
public class ModuleGenInfo extends BaseEntity {

	// Fields

	private int moduleId;
	private int parentId;
	private String name;
	private String forwardPage;
	private String hasChild;
	private String honeticize;
	private String description;
	private RightGenInfo right;
	private int rightType;

	public ModuleGenInfo(){}
	
	public ModuleGenInfo(int moduleId)
	{
		this.moduleId = moduleId;
	}
	
	@Transient
	public int getRightType() {
		return rightType;
	}

	public void setRightType(int rightType) {
		this.rightType = rightType;
	}

	@OneToOne(mappedBy="module",fetch=FetchType.EAGER)
	@NotFound(action=NotFoundAction.IGNORE)
	public RightGenInfo getRight() {
		return right;
	}

	public void setRight(RightGenInfo right) {
		this.right = right;
	}

	/*@Transient
	@ManyToOne(optional = true, targetEntity = ModuleGenInfo.class,fetch=FetchType.EAGER,cascade=CascadeType.PERSIST)
	@JoinColumn(name = "parentId")
	//@NotFound(action = NotFoundAction.IGNORE)
	public ModuleGenInfo getParent() {
		return parent;
	}

	public void setParent(ModuleGenInfo parent) {
		this.parent = parent;
	}*/

	/** minimal constructor */
	public ModuleGenInfo(int moduleId, String name, String hasChild,
			String honeticize) {
		this.moduleId = moduleId;
		this.name = name;
		this.hasChild = hasChild;
		this.honeticize = honeticize;
	}

	/** full constructor */
	public ModuleGenInfo(int moduleId, int parentId, String name,
			String hasChild, String honeticize, String description) {
		this.moduleId = moduleId;
		this.parentId = parentId;
		this.name = name;
		this.hasChild = hasChild;
		this.honeticize = honeticize;
		this.description = description;
	}

	// Property accessors

	@Column(name = "ParentID", unique = false, nullable = true, insertable = true, updatable = true, precision = 10, scale = 0)
	public int getParentId() {
		return this.parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	@Column(name = "Name", unique = false, nullable = false, insertable = true, updatable = true, length = 250)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "hasChild", unique = false, nullable = false, insertable = true, updatable = true, length = 1)
	public String getHasChild() {
		return this.hasChild;
	}

	public void setHasChild(String hasChild) {
		this.hasChild = hasChild;
	}

	@Column(name = "Honeticize", unique = false, nullable = false, insertable = true, updatable = true, length = 20)
	public String getHoneticize() {
		return this.honeticize;
	}

	public void setHoneticize(String honeticize) {
		this.honeticize = honeticize;
	}

	@Column(name = "Description", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="forwardPage",length=256,nullable=false)
	public String getForwardPage() {
		return forwardPage;
	}

	public void setForwardPage(String forwardPage) {
		this.forwardPage = forwardPage;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="webdemo_gen")
	@TableGenerator(name="webdemo_gen",
			table="tb_generator",
			pkColumnName="gen_name",
			valueColumnName="gen_value",
			pkColumnValue="module_pk",
			allocationSize=1)
	@Column(name = "ModuleID", unique = true, nullable = false, insertable = true, updatable = true, precision = 10, scale = 0)
	public int getModuleId() {
		return moduleId;
	}

}