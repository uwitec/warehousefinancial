/**
 *******************************************************************************
 * 文件名：RyydServiceImpl.java
 *
 * 描述�?
 * 
 * 创建日期：Apr 16, 2010 1:59:12 PM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.service.rsgl.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfms.common.entity.Page;
import com.wfms.dao.rsgl.IRyglDao;
import com.wfms.dao.rsgl.IRyydDao;
import com.wfms.model.rsgl.BmEntity;
import com.wfms.model.rsgl.RyEntity;
import com.wfms.model.rsgl.RyydEntity;
import com.wfms.service.rsgl.IRyydService;

/**
 * <dl>
 * Description
 * <dd> 项目名称：迅尔数字化校园信息平台ECMS
 * <dd> 类名称：RyydServiceImpl
 * <dd> 类描述：
 * <dd> 创建人：Administrator
 * <dd> 创建时间：Apr 16, 2010 1:59:12 PM
 * <dd> 修改人：�?
 * <dd> 修改时间：无
 * <dd> 修改备注：无
 * </dl>
 * 
 * @author ECMS
 * @see RyydServiceImpl
 * @version 1.0
 * 
 */
@Service
public class RyydServiceImpl implements IRyydService {
	@Autowired
	private IRyydDao ryydDao;
	@Autowired
	private IRyglDao ryglDao;

	public void setRyglDao(IRyglDao ryglDao) {
		this.ryglDao = ryglDao;
	}

	public void setRyydDao(IRyydDao ryydDao) {
		this.ryydDao = ryydDao;
	}

	/**
	 * <dl>
	 * <b>方法�?:addRyyd</b>
	 * <dd>方法作用�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??)
	 * <dd>
	 * 
	 * @param ryyd
	 *            <dd>
	 * @return
	 *            <dd>
	 * @see com.is.rsgl.service.IRyydService#addRyyd(com.is.rsgl.entity.RyydEntity)
	 *      </dl>
	 */
	public int addRyyd(RyydEntity ryyd) {

		Map<Object, Object> codes = new HashMap<Object, Object>();
		Map<Object, Object> cons = new HashMap<Object, Object>();

		RyEntity ydqRy = ryglDao.getRyById(ryyd.getRy().getId());

		if (ryyd.getYdhBm() != null && ryyd.getYdhBm().getId() != 0) {
			codes.put("bm.id", ryyd.getYdhBm().getId());
			//ryyd.setYdqBm(ydqRy.getBm());
			ryyd.setYdhBm(ryyd.getYdhBm());
		}

		codes.put("zt", "1".equals(ryyd.getSflz()) ? "0" : "1");
		cons.put("id", ydqRy.getId());
		ryglDao.updateRyByCodesAndConditions(codes, cons);

		if (ryyd.getYdhBm() == null || ryyd.getYdhBm().getId() == 0) {
			ryyd.setYdhBm(new BmEntity(-1));
		}
		if (ryyd.getYdqBm() == null || ryyd.getYdqBm().getId() == 0) {
			ryyd.setYdqBm(new BmEntity(-1));
		}
		return ryydDao.addRyyd(ryyd);
	}

	/**
	 * <dl>
	 * <b>方法�?:deleteRyydById</b>
	 * <dd>方法作用�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??)
	 * <dd>
	 * 
	 * @param id
	 *            <dd>
	 * @return
	 *            <dd>
	 * @see com.is.rsgl.service.IRyydService#deleteRyydById(int)
	 *      </dl>
	 */
	public int deleteRyydById(int id) {
		return ryydDao.deleteRyydById(id);
	}

	/**
	 * <dl>
	 * <b>方法�?:getRyydByPage</b>
	 * <dd>方法作用�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??)
	 * <dd>
	 * 
	 * @param page
	 *            <dd>
	 * @return
	 *            <dd>
	 * @see com.is.rsgl.service.IRyydService#getRyydByPage(com.is.common.entity.Page)
	 *      </dl>
	 */
	public Page getRyydByPage(Page page) {
		if (page == null)
			page = new Page();
		page.setTotalCount(ryydDao.getRyydCount(page.getConditionList()));
		page.setDataList(ryydDao.getRyydByPage(page));
		return page;
	}

}
