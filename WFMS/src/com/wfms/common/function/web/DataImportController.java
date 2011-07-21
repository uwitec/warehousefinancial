package com.wfms.common.function.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import xuner.web.mvc.MvcUtil;

import com.wfms.common.attribute.AjaxMsg;
import com.wfms.common.function.attribute.ActionReport;
import com.wfms.common.function.attribute.DataImportReport;
import com.wfms.common.function.attribute.ImportField;
import com.wfms.common.function.command.DataImportForm;
import com.wfms.common.function.dao.IImportDao;
import com.wfms.common.function.entity.Column;
import com.wfms.common.function.service.DataImportHandler;
import com.wfms.common.function.util.ExcelUtil;
import com.wfms.common.util.BeanConvort;

/**
 * @author CYC
 * @see DataImportController
 * @version 1.0
 * 
 */
@Controller
@RequestMapping("/common/dataImportAction.do")
public class DataImportController {

	// 唯一标示列，由Spring进行注入(已失效，使用数据库unique替代)
	private Map<String, String[]> pkColumnsMap = null;
	private Map processorMap = null;

	private IImportDao importDao = null;

	@Autowired
	public void setImportDao(IImportDao importDao) {
		this.importDao = importDao;
	}

	@RequestMapping(params = "method=initDataImport")
	public String initDataImport(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String tableName = request.getParameter("tableName");
		request.setAttribute("tableName", tableName);
		return "common/function/dataImport";
	}

	@RequestMapping(params = "method=getTableColumns")
	public ModelAndView getTableColumns(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String tableName = request.getParameter("tableName");
		if ("".equals(tableName) || tableName == null) {
			return null;
		}
		List<Column> list = importDao.getColDetailByTabName(tableName);
		JSONArray obj = JSONArray.fromObject(list);
		response.setCharacterEncoding("utf-8");
		response.getWriter().print(obj);
		return null;
	}

	@RequestMapping(params = "method=loadColumnHeader")
	public ModelAndView loadColumnHeader(HttpServletRequest request,
			HttpServletResponse response, DataImportForm dataImportForm)
			throws IOException {

		// 得到文件
		MultipartFile multiFile = dataImportForm.getMultiFile();
		InputStream is = multiFile.getInputStream();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		Map<Object, Object> rs = new HashMap<Object, Object>();

		Workbook book = null;

		try {
			book = Workbook.getWorkbook(is);
		} catch (BiffException e) {
			e.printStackTrace();
			rs.put("success", false);
			rs.put("msg", "导入文件不存在或者文件中数据格式有误！");
			response.getWriter().print(JSONObject.fromObject(rs));
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			rs.put("success", false);
			rs.put("msg", "导入文件无法读取");
			response.getWriter().print(JSONObject.fromObject(rs));
			return null;
		}
		Sheet sheet = book.getSheet(0);
		int row = sheet.getRows();

		// 获取表头
		String[] headerList = null;
		if (row > 1) {
			headerList = ExcelUtil.getDataByRowIndex(sheet, 1);
		}
		if (headerList == null) {
			headerList = new String[] {};
		}

		// 获取数据
		StringBuffer rsData = new StringBuffer("[");
		if (dataImportForm.isPreview()) {

			for (int i = 2; i < row; i++) {
				String[] data = ExcelUtil.getDataByRowIndex(sheet, i);
				rsData.append("{");
				for (int j = 0; j < data.length; j++) {
					if (j == data.length - 1) {
						rsData.append("column" + j + ":'" + data[j] + "'");
					} else {
						rsData.append("column" + j + ":'" + data[j] + "',");
					}
				}
				if (i == row - 1) {
					rsData.append("}");
				} else {
					rsData.append("},");
				}
			}

		}
		rsData.append("]");
		rs.put("success", true);
		rs.put("title", multiFile.getOriginalFilename());
		rs.put("header", headerList);
		rs.put("data", rsData.toString());
		return MvcUtil.jsonObjectModelAndView(JSONObject.fromObject(rs));
	}

	/**
	 * 
	 * <dl>
	 * <b>方法名:dataValidate</b>
	 * <dd>方法作用：验证所有导入数据
	 * <dd>重写备注：(这里描述重写原因、结果或备注)
	 * <dd>适用条件：(这里描述这个方法的适用条件 – 可选)
	 * <dd>执行流程：(这里描述这个方法的执行流程 – 可选)
	 * <dd>适用方法：(这里描述这个方法的使用方法 – 可选)
	 * <dd>注意事项：(这里描述这个方法的注意事项 – 可选)
	 * <dd>
	 * 
	 * @param mapping
	 *            <dd>
	 * @param form
	 *            <dd>
	 * @param request
	 *            <dd>
	 * @param response
	 *            <dd>
	 * @return
	 *            <dd>
	 * @throws IOException
	 *             </dl>
	 */
	@RequestMapping(params = "method=dataValidate")
	public ModelAndView dataValidate(HttpServletRequest request,
			HttpServletResponse response, DataImportForm dataImportForm)
			throws IOException {
		// 获取外部数据
		// List<Map<String, String>> dataList = (List<Map<String, String>>)
		// request
		// .getAttribute("");
		// 获取导入表名称
		String tableName = "";
		Map<String, String> matchMap = null;

		Object matchData = dataImportForm.getMatchData();
		JSONArray matchColumns = JSONArray.fromObject(matchData);
		List<Column> columns = null;
		AjaxMsg msg = new AjaxMsg();
		try {
			columns = (List<Column>) BeanConvort.boundToArray(
					Column.class, matchColumns);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (columns != null && columns.size() > 0) {
			Column col = columns.get(0);
			tableName = col.getTablename();
			matchMap = new HashMap<String, String>(columns.size());
			for (Column column : columns) {
				matchMap.put(column.getColumnName(), column.getMatchcontent());
			}
		}
		if (tableName == null || "".equals(tableName)) {
			msg.setSuccess(false);
			msg.setMessage("未获得系统数据库表名称");
			JSONObject jsonObject = JSONObject.fromObject(msg);
			response.setCharacterEncoding("UTF-8");
			return MvcUtil.jsonObjectModelAndView(jsonObject);
		}
		// 设置request对象
		dataImportForm.setRequest(request);
		DataImportHandler dataImportDAO = null;
		try {
			dataImportDAO = getImportBeanInstance(request, tableName, matchMap,
					columns, dataImportForm);
		} catch (Exception e) {
			e.printStackTrace();
			msg.setSuccess(false);
			msg.setMessage("初始化导入支持信息失败");
			JSONObject jsonObject = JSONObject.fromObject(msg);
			return MvcUtil.jsonObjectModelAndView(jsonObject);
		}
		if (dataImportDAO == null) {
			msg.setSuccess(false);
			msg.setMessage("未找到导入支持");
			JSONObject jsonObject = JSONObject.fromObject(msg);
			response.setCharacterEncoding("UTF-8");
			return MvcUtil.jsonObjectModelAndView(jsonObject);
		}
		dataImportDAO.dataImport(false, true);
		DataImportReport report = dataImportDAO.getDataImportReport();
		if (report.hasError()) {
			msg.setSuccess(false);
			msg.setMessage("数据验证未完全通过");
			msg.setOther("验证成功条数:" + report.getSuccessCount() + " 验证失败条数:"
					+ report.getFailedCount());
			msg.setErrorList(report.getErrorList());
		} else {
			ActionReport actionReport = dataImportDAO.dataImport(true, true);
			report = dataImportDAO.getDataImportReport();
			if (actionReport.isSuccess()) {
				msg.setSuccess(true);
				msg.setMessage("导入成功,共计" + report.getSuccessCount() + "条");
			}
		}
		JSONObject jsonObject = JSONObject.fromObject(msg);
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(jsonObject);
	}

	/**
	 * 
	 * <dl>
	 * <b>方法名:dataImport</b>
	 * <dd>方法作用：数据导入
	 * <dd>重写备注：(这里描述重写原因、结果或备注)
	 * <dd>适用条件：(这里描述这个方法的适用条件 – 可选)
	 * <dd>执行流程：(这里描述这个方法的执行流程 – 可选)
	 * <dd>适用方法：(这里描述这个方法的使用方法 – 可选)
	 * <dd>注意事项：(这里描述这个方法的注意事项 – 可选)
	 * <dd>
	 * 
	 * @param mapping
	 *            <dd>
	 * @param form
	 *            <dd>
	 * @param request
	 *            <dd>
	 * @param response
	 *            <dd>
	 * @return
	 *            <dd>
	 * @throws IOException
	 *             </dl>
	 */
	@RequestMapping(params = "method=dataImport")
	public String dataImport(HttpServletRequest request,
			HttpServletResponse response, DataImportForm dataImportForm)
			throws IOException {
		String tableName = dataImportForm.getTableName();
		AjaxMsg msg = new AjaxMsg();
		String redirectUrl = "";
		if("JWGL_JXRWB".equals(tableName))
		{
			redirectUrl = "redirect:/jwgl_new/jxjhgl/jxrwNewAction.do?method=jxrwImport";
		}
		else if("RYB".equals(tableName))
		{
			redirectUrl = "redirect:/rsgl/ryxx_import.html";
		}
		if ((tableName == null) || (tableName == "")) {
			msg.setSuccess(false);
			msg.setMessage("未获得系统数据库表名称");
			request.getSession().setAttribute("msg",msg);
			return redirectUrl;
		}

		// 设置request对象
		dataImportForm.setRequest(request);
		DataImportHandler dataImportDAO = null;
		try {
			dataImportDAO = getImportBeanInstance(request, tableName, null,
					null, dataImportForm);
		} catch (Exception e) {
			e.printStackTrace();
			msg.setSuccess(false);
			msg.setMessage("初始化导入支持信息失败");
			request.getSession().setAttribute("msg",msg);
			request.getSession().setAttribute("report",dataImportDAO.getDataImportReport());
			return redirectUrl;
		}
		if (dataImportDAO == null) {
			msg.setSuccess(false);
			msg.setMessage("未找到导入支持");
			request.getSession().setAttribute("msg",msg);
			request.getSession().setAttribute("report",dataImportDAO.getDataImportReport());
			return redirectUrl;
		}
		ActionReport actionReport;
		actionReport = dataImportDAO.dataImport(true, true);
		if (!actionReport.isSuccess()) {
			msg.setSuccess(false);
			msg.setMessage(actionReport.getMsg());
			request.setAttribute("msg",msg);
			request.setAttribute("report",dataImportDAO.getDataImportReport());
			response.getWriter().println(JSONObject.fromObject(msg));
			request.getSession().setAttribute("msg",msg);
			request.getSession().setAttribute("report",dataImportDAO.getDataImportReport());
			return redirectUrl;
		}
		DataImportReport report = dataImportDAO.getDataImportReport();
		if (report.hasError()) {
			msg.setSuccess(false);
			msg.setMessage("导入失败");
			request.getSession().setAttribute("msg",msg);
			request.getSession().setAttribute("report",dataImportDAO.getDataImportReport());
			return redirectUrl;
		}
		msg.setSuccess(true);
		msg.setMessage("共有" + report.getSuccessCount() + "条数据成功导入");
		request.getSession().setAttribute("msg",msg);
		request.getSession().setAttribute("report",dataImportDAO.getDataImportReport());
		return redirectUrl;
	}

	private DataImportHandler getImportBeanInstance(HttpServletRequest request,
			String tableName, Map<String, String> matchMap,
			List<Column> colEntList, DataImportForm dataImportForm) {
		
		DataImportHandler dataImportDao = null;
		ApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(request.getSession()
						.getServletContext());

		if (context != null) {
			// 得到通用DataImportDao
			dataImportDao = (DataImportHandler) context
					.getBean("dataImportHandler");
			if ("JWGL_JXRWB".equals(tableName)) {
				dataImportDao = (DataImportHandler) context
						.getBean("jxrwImportHandler");
			}
			if("RYB".equals(tableName))
			{
				dataImportDao = (DataImportHandler)context.getBean("ryImportHandler");
			}
			// 初始化外部数据文件
			dataImportDao.initDataImportForm(dataImportForm);
			// 初始化导入表名
			dataImportDao.setTableName(tableName);
			// 初始化验证规则基础信息
			if (colEntList != null) {
				dataImportDao.setColEntList(colEntList);
			} else {
				dataImportDao.setColEntList(importDao
						.getColDetailByTabName(tableName));
			}
			dataImportDao.setDrgzList(importDao.getDrgzByTableName(tableName));
			// 初始化导入列
			if (matchMap != null) {
			dataImportDao.setColumns(new String[matchMap.size()]);
			// 初始化字段匹配
			int i = 0;
			
				for (Map.Entry<String, String> field : matchMap.entrySet()) {
					dataImportDao.getImportFields().addField(
							new ImportField(field.getValue(), field.getKey()));
					dataImportDao.getColumns()[i] = field.getKey();
					i++;
				}
			}
			if (pkColumnsMap != null) {
				dataImportDao.setPkColumns(pkColumnsMap.get(tableName));
			}
			dataImportDao.setPkColsList(importDao
					.getUniqColsByTabName(tableName));
		}
		return dataImportDao;
	}

	public void setPkColumnsMap(Map<String, String[]> pkColumnsMap) {
		this.pkColumnsMap = pkColumnsMap;
	}

}
