/**
 *******************************************************************************
 * 文件名：DataExportAction.java
 *
 * 描述：
 * 
 * 创建日期：Mar 12, 2010 3:56:53 PM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.common.function.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import xuner.web.mvc.bean.ExportBean;

import com.wfms.common.function.dao.IDataExportDao;


@Controller
@Lazy(true)
@RequestMapping("/common/dataExportAction.do")
public class DataExportAction{

	private IDataExportDao dataExportDao = null;
	
	@Autowired
	public void setDataExportDao(IDataExportDao dataExportDao) {
		this.dataExportDao = dataExportDao;
	}

	//新生上报信息
	@RequestMapping(params="method=xssbxxExp")
	public ModelAndView xssbxxExp(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		dataExportDao.exportData("五一技校报名学生名单", "", "view_zsjygl_wbmxsxx",
				new String[] { "XH", "XSBH", "SFZH", "XM", "DQSZJ", "CYM",
						"XB", "CSRQ", "XZ", "MZ", "JG", "ZZMM", "HKXZ", "BYXX",
						"JTXXDZ", "JTYZBM", "SFDB","SRLY","XZTXDZ", "DZYX", "DHHM",
						"YHKH", "BZ", "LZHZLX", "LZHZXXJGDM", "SFXSGJZXJ",
						"GJZXJYFFBZ", "SFGATQB", "ZSLX", "XSLBDM", "RXNY",
						"ZYMC","ZYJB","BZ" }, response);
		return null;
	}

	//新生报名信息
	@RequestMapping(params="method=xsbmxxExp")
	public ModelAndView xsbmxxExp(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		dataExportDao.exportXsbdData("新生报名统计表", "", "view_zsjygl_ZSGL_XSJBXX",
				new String[] { "XH", "XM", "SFZH", "XB", "CSRQ",
						"MZ", "DHHM", "ZZMM", "HKSZD", "HKXZ", "ZYMC", "AHTC", "BYXX",
						"RXNY", "BJID", "YHKH","ZYJBMC","ZYLBDM", "WHCD", "XZ",
						"JTLXR", "JTLXDH", "JTNZSR", "JTRJSR", "SFDB",
						"SRLY", "JTXXDZ", "JTYZBM", "LXR", "SX", "LXRDH","BMSJ" }, response);
		return null;
	}
	

	@RequestMapping(params="method=initDataExport")
	public String initDataExport()
	{
		return "/common/function/dataExport";
	}
	
	@RequestMapping(params="method=commonExp",method=RequestMethod.POST)
	public ModelAndView commonExp(@RequestParam("fileType") String fileType,@RequestParam("fileName") String fileName,HttpServletResponse response)
			throws Exception {
		String sql="select xh,xsbh,sfzh,xm,dqszj,cym,decode(xb,'0','女','1','男')" +
				" xb,csrq,xsjbxxb.xz,decode(sfswyxmz,'0','否','1','是')" +
				" sfswyxmz,mz,jg,decode(sfdty,'0','否','1','是')" +
				" sfdty,zzmm,hkszd,hkxz,byxx,whcd, cbdxx,ywj,bysj,dyzy,dezy," +
				"jtxxdz,jtyzbm,jtrjsr,jtnzsr,srly," +
				"decode(sfdb,'0','否','1','是') sfdb," +
				"xztxdz,xzyzbm,dzyx,dhhm,sjhm,zp,yhkh," +
				"xsjbxxb.bz,lzhzlx,lzhzxxjgdm,decode(sfxsgjzxj," +
				"'0','否','1','是') sfxsgjzxj,gjzxjyffbz,xxlx," +
				"sjdzzcxjh, decode(sfgatqb,'0','否','1','是')" +
				" sfgatqb,zslx, bmzt,rxfsdm,jdfsdm,xslbdm,xslydm," +
				"syd,rxny,zydmb.zymc,(select dmmc from t_lxdmb where" +
				" sjlxdm='1016' and lxdm=zyjbdm) zyjb from xsjbxxb," +
				"bjdmb,zydmb where xsjbxxb.bjid=bjdmb.id and bjdmb.zyid=zydmb.id" +
				" and xsjbxxb.bmzt='0'";
		Map<String,String> matchMap = new HashMap<String, String>(1);
		matchMap.put("ZYJB", "专业级别");
		ExportBean exportBean = dataExportDao.getExportBean("五一技校报名学生名单",sql,new String[]{"XSJBXXB","BJDMB","ZYDMB"},matchMap);
		List<ExportBean> exportBeanList = new ArrayList<ExportBean>(1);
		exportBeanList.add(exportBean);
		/*if("text".equals(fileType))
		{
			return MvcUtil.textExtModelAndView(exportBeanList);
		}
		else if("excel".equals(fileType))
		{
			return MvcUtil.excelModelAndView(exportBeanList);
		}
		else if("pdf".equals(fileType))
		{
			return MvcUtil.pdfModelAndView(exportBeanList);
		}*/
		return null;
	}
	
	//新生上报信息
	@RequestMapping(params="method=xssbxxExp")
	public void xssbxxExp(HttpServletResponse response)
			throws Exception {
		String sql="select xh,xsbh,sfzh,xm,dqszj,cym,decode(xb,'0','女','1','男')" +
				" xb,csrq,xsjbxxb.xz,decode(sfswyxmz,'0','否','1','是')" +
				" sfswyxmz,mz,jg,decode(sfdty,'0','否','1','是')" +
				" sfdty,zzmm,hkszd,hkxz,byxx,whcd, cbdxx,ywj,bysj,dyzy,dezy," +
				"jtxxdz,jtyzbm,jtrjsr,jtnzsr,srly," +
				"decode(sfdb,'0','否','1','是') sfdb," +
				"xztxdz,xzyzbm,dzyx,dhhm,sjhm,zp,yhkh," +
				"xsjbxxb.bz,lzhzlx,lzhzxxjgdm,decode(sfxsgjzxj," +
				"'0','否','1','是') sfxsgjzxj,gjzxjyffbz,xxlx," +
				"sjdzzcxjh, decode(sfgatqb,'0','否','1','是')" +
				" sfgatqb,zslx, bmzt,rxfsdm,jdfsdm,xslbdm,xslydm," +
				"syd,rxny,zydmb.zymc,(select dmmc from lxdmb where" +
				" sjlxdm='1016' and lxdm=zyjbdm) zyjb from xsjbxxb," +
				"bjdmb,zydmb where xsjbxxb.bjid=bjdmb.id and bjdmb.zyid=zydmb.id" +
				" and xsjbxxb.bmzt='0'";
		Map<String,String> matchMap = new HashMap<String,String>();
		matchMap.put("ZYJB", "专业级别");
		//dataExportDao.exportData("五一技校报名学生名单",sql,new String[]{"XSJBXXB","BJDMB","ZYDMB"},matchMap,response);
	}
}
