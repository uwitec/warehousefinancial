package com.wfms.webapp.common.dataimport.attribute;

/**
 * 
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：LxdmType
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Mar 4, 2010 2:00:07 PM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see LxdmType
 * @version 1.0
 *
 */
public enum LxdmType{
	LXDM,DMMC,DMJC;

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.name();
	}
	
}
