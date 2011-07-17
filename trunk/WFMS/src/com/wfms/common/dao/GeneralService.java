package com.wfms.common.dao;


import java.io.Serializable;

import org.springframework.transaction.annotation.Transactional;

import com.wfms.common.orm.BaseEntity;
import com.wfms.common.orm.Page;

public interface GeneralService<T extends BaseEntity> {

	public abstract void setBaseDao(BaseDao<T> paramBaseDao);

	public Serializable save(T entity);

	public void update(T entity);

	public void saveOrUpdate(T entity);

	public void delete(T entity);
	
	public void delete(String id);
	
	public int delete(String[] ids);
	
	@Transactional(readOnly = true)
	public T find(String id);

	@Transactional(readOnly = true)
	public Page find(Page page);
	
	@Transactional(readOnly = true)
	public Page fillPage(Page page);
}
