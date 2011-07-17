package com.wfms.common.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "MEM_JXC_MAC", schema = "ECM", uniqueConstraints = {})
public class MacEntity implements java.io.Serializable {

	// Fields 客户维护

	private int id;
	private String mac;
	private String dlcs;
	private String zdcs;
	private String kssj;
	private String jssj;
	private String lmac;


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
    
	@Column(name = "ZDCS", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
	public String getZdcs() {
		return this.zdcs;
	}

	public void setZdcs(String zdcs) {
		this.zdcs = zdcs;
	}

	@Column(name = "DLCS", unique = false, nullable = false, insertable = true, updatable = true, length = 40)
	public String getDlcs() {
		return dlcs;
	}

	public void setDlcs(String dlcs) {
		this.dlcs = dlcs;
	}

	@Column(name = "MAC", unique = false, nullable = false, insertable = true, updatable = true, length = 100)
	public String getMac() {
		return this.mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	@Column(name = "LMAC", unique = false, nullable = false, insertable = true, updatable = true, length = 100)
	public String getLmac() {
		return this.lmac;
	}

	public void setLmac(String lmac) {
		this.lmac = lmac;
	}

	@Column(name = "KSSJ", unique = false, nullable = false, insertable = true, updatable = true, length = 20)
	public String getKssj() {
		return kssj;
	}

	public void setKssj(String kssj) {
		this.kssj = kssj;
	}

	@Column(name = "JSSJ", unique = false, nullable = false, insertable = true, updatable = true, length = 20)
	public String getJssj() {
		return this.jssj;
	}

	public void setJssj(String jssj) {
		this.jssj = jssj;
	}
}