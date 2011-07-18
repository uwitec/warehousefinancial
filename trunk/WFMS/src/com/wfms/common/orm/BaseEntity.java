package com.wfms.common.orm;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

@SuppressWarnings("serial")
@MappedSuperclass
public class BaseEntity implements Serializable{

	protected String id;

	@Id
	@GeneratedValue(generator = "pk")
	@Column(name = "ID")
	@GenericGenerator(name = "pk", strategy = "com.wfms.common.orm.IdGenerator")
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	/*
	public boolean isNew() {
		return this.id == null || "".equals(this.id);
	}*/
}
