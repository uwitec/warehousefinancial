package com.wfms.common.dao;

import java.io.Serializable;

import com.wfms.common.orm.BaseEntity;
import com.wfms.common.orm.Page;

public interface GeneralDao<T extends BaseEntity> {
	public Serializable save(T entity);

	public void update(T entity);

	public void delete(T entity);

	public void delete(String id);

	public T fetch(String id);

	public Page fetch(Page page);
	
	/**
	 * Creteria复合条件查询
	 * @param page
	 * @return
	 */
	public Page find(Page page);
	
	public int executeHQLUpdate(String hql, Object param);
	
}
