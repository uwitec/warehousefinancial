package com.wfms.model.system;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * Rolepart entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ROLE_PART", schema = "ECM", uniqueConstraints = {})
public class RolePart implements java.io.Serializable {

	// Fields

	private Integer id;
	private PartGenInfo part;
	private RoleGenInfo role;


	/** default constructor */
	public RolePart() {
	}

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="webdemo_gen")
	@TableGenerator(name="webdemo_gen",
			table="tb_generator",
			pkColumnName="gen_name",
			valueColumnName="gen_value",
			pkColumnValue="rolepart_pk",
			allocationSize=1)	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARTID", unique = false, nullable = true, insertable = true, updatable = true)
	public PartGenInfo getPart() {
		return part;
	}

	public void setPart(PartGenInfo part) {
		this.part = part;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLID", unique = false, nullable = false, insertable = true, updatable = true)
	public RoleGenInfo getRole() {
		return role;
	}

	public void setRole(RoleGenInfo role) {
		this.role = role;
	}

}