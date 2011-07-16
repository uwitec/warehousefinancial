package com.wfms.model.common.datastat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
// default package

/**
 * StatRuleEntity entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="COMMON_STAT_DEFINE"
    ,schema="ECM"
, uniqueConstraints = {  }
)
public class StatDefineEntity implements java.io.Serializable {

	// Fields

	private int id;
	private String ssbmc;
	private String tjfl;
	private String tjdx;
	private String sql;
	private String ssb;
	
	@Id
	@Column(name = "ID", unique = true, nullable = false, insertable = true, updatable = true, precision = 10, scale = 0)
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="SSBMC",length=20,nullable=false)
	public String getSsbmc() {
		return ssbmc;
	}
	public void setSsbmc(String ssbmc) {
		this.ssbmc = ssbmc;
	}
	
	@Column(name="TJFL",length=200,nullable=false)
	public String getTjfl() {
		return tjfl;
	}
	public void setTjfl(String tjfl) {
		this.tjfl = tjfl;
	}
	
	@Column(name="TJDX",length=200,nullable=false)
	public String getTjdx() {
		return tjdx;
	}
	public void setTjdx(String tjdx) {
		this.tjdx = tjdx;
	}
	
	@Column(name="SQL",length=1024,nullable=true)
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}

	@Column(name="SSB",length=20,nullable=false)
	public String getSsb() {
		return ssb;
	}

	public void setSsb(String ssb) {
		this.ssb = ssb;
	}

}