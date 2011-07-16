package com.wfms.model.system;

// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * PartRight entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "PART_RIGHT", schema = "ECM", uniqueConstraints = {})
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class PartRight implements java.io.Serializable {

	// Fields

	private Integer id;
	private PartGenInfo part;
	private RightGenInfo right;
	private int rightType;
	private String rightStatus;

	// Constructors

	/** default constructor */
	public PartRight() {
	}

	/** full constructor */
	public PartRight(Integer id, PartGenInfo partGeninfo,
			RightGenInfo rightGeninfo, int rightType, String rightStatus) {
		this.id = id;
		this.part = partGeninfo;
		this.right = rightGeninfo;
		this.rightType = rightType;
		this.rightStatus = rightStatus;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="webdemo_gen")
	@TableGenerator(name="webdemo_gen",
			table="tb_generator",
			pkColumnName="gen_name",
			valueColumnName="gen_value",
			pkColumnValue="partright_pk",
			allocationSize=1)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PartID", unique = false, nullable = false, insertable = true, updatable = true)
	public PartGenInfo getPart() {
		return part;
	}

	public void setPart(PartGenInfo part) {
		this.part = part;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RightID", unique = false, nullable = false, insertable = true, updatable = true)
	public RightGenInfo getRight() {
		return right;
	}

	public void setRight(RightGenInfo right) {
		this.right = right;
	}

	@Column(name = "RightType", unique = false, nullable = false, insertable = true, updatable = true, precision = 4, scale = 0)
	public int getRightType() {
		return this.rightType;
	}

	public void setRightType(int rightType) {
		this.rightType = rightType;
	}

	@Column(name = "RightStatus", unique = false, nullable = false, insertable = true, updatable = true, length = 1)
	public String getRightStatus() {
		return this.rightStatus;
	}

	public void setRightStatus(String rightStatus) {
		this.rightStatus = rightStatus;
	}

}