package com.wfms.dao.system.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.dao.system.IQtdmglDao;
import com.wfms.model.system.QtdmEntity;

@Repository
public class QtdmglDaoImpl extends BaseDao implements IQtdmglDao {
	/**
	 * 
	 * <dl>
	 * <b>方法�?:addQtdm</b>
	 * <dd>方法作用�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param qtdm
	 * <dd>@return
	 * <dd>@see com.is.system.dao.IQtdmDao#addQtdm(com.is.system.entity.QtdmEntity)
	 * </dl>
	 */
	public int addQtdm(QtdmEntity qtdm) {
		Serializable strId = super.insert(qtdm);
		int id = -1;
		try {
			id = Integer.parseInt(strId.toString());
		} catch (Exception e) {
			id = -1;
		}
		return id;
	}
	/**
	 * 
	 * <dl>
	 * <b>方法�?:addQtdm</b>
	 * <dd>方法作用�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param qtdm
	 * <dd>@return
	 * <dd>@see com.is.system.dao.IQtdmDao#addQtdm(com.is.system.entity.QtdmEntity)
	 * </dl>
	 */
	public int delQtdm(int id) {
		return super.delete(QtdmEntity.class, id);
	}
	/**
	 * 
	 * <dl>
	 * <b>方法�?:addQtdm</b>
	 * <dd>方法作用�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param qtdm
	 * <dd>@return
	 * <dd>@see com.is.system.dao.IQtdmDao#addQtdm(com.is.system.entity.QtdmEntity)
	 * </dl>
	 */
	public List<QtdmEntity> getQtdm(String dmlb) {
		Object obj = super.getList("FROM QtdmEntity q where q.dmlb='"+dmlb+"' order by q.dmjc" );
		return obj == null ? null : (List<QtdmEntity>)obj;
	}
	/**
	 * 
	 * <dl>
	 * <b>方法�?:addQtdm</b>
	 * <dd>方法作用�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param qtdm
	 * <dd>@return
	 * <dd>@see com.is.system.dao.IQtdmDao#addQtdm(com.is.system.entity.QtdmEntity)
	 * </dl>
	 */
	public int updQtdm(QtdmEntity qtdm) {
		return super.update(qtdm);
	}

}
