package com.wfms.common.dao;

import java.io.Serializable;

import org.springframework.transaction.annotation.Transactional;

import com.wfms.common.orm.BaseEntity;
import com.wfms.common.orm.Page;


@Transactional
public abstract class BaseService<T extends BaseEntity> {
	protected BaseDao<T> baseDao;

	public BaseDao<T> getBaseDao() {
		return this.baseDao;
	}

	public abstract void setBaseDao(BaseDao<T> paramBaseDao);

	public Serializable save(T entity) {
		return getBaseDao().save(entity);
	}

	public void update(T entity) {
		getBaseDao().update(entity);
	}

	public void saveOrUpdate(T entity) {
		if (entity.getId()==null
				|| "".equals(entity.getId())) {
			getBaseDao().save(entity);
		} else {
			getBaseDao().update(entity);
		}
	}

	public void delete(T entity) {
		getBaseDao().delete(entity);
	}
	
	public void delete(String id) {
		getBaseDao().delete(id);
	}
	
	public int delete(String[] ids)
	{
		int successCount = 0;
		try{
			for(String id:ids)
			{
				getBaseDao().delete(id);
				successCount ++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return successCount;
		
	}
	
	@Transactional(readOnly = true)
	public T find(String id) {
		return getBaseDao().fetch(id);
	}

	@Transactional(readOnly = true)
	public Page find(Page page) {
		return getBaseDao().fetch(page);
	}
	
	@Transactional(readOnly = true)
	public Page fillPage(Page page) {
		return getBaseDao().find(page);
	}

}
