package com.wfms.common.function.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.wfms.common.function.attribute.ActionReport;
import com.wfms.common.function.attribute.ImportField;
import com.wfms.common.function.attribute.LxdmType;
import com.wfms.common.function.command.DataImportForm;
import com.wfms.common.function.constant.DataImportConstant;
import com.wfms.common.function.dao.IImportDao;
import com.wfms.common.function.entity.Column;
import com.wfms.common.function.entity.ImportRule;
import com.wfms.common.function.service.DataImportHandler;
import com.wfms.common.function.util.DataImportValidater;
import com.wfms.common.system.dao.ICodeDao;
import com.wfms.common.system.entity.Code;

/**
 * 
 * <dl>
 * Description
 * <dd> 项目名称：数字化校园信息平台ECMS
 * <dd> 类名称：CommonDataImportDAO
 * <dd> 类描述：
 * <dd> 创建人：Administrator
 * <dd> 创建时间：Mar 4, 2010 1:57:25 PM
 * <dd> 修改人：无
 * <dd> 修改时间：无
 * <dd> 修改备注：无
 * </dl>
 * 
 * @author CYC
 * @see DefaultDataImportHandler
 * @version 1.0
 * 
 */
@Component("dataImportHandler")
@Scope("prototype")
public class DefaultDataImportHandler extends DataImportHandler {

	@Autowired
	private IImportDao importDao = null;
	@Autowired
	private ICodeDao lxdmDao = null;


	public void setImportDao(IImportDao importDao) {
		this.importDao = importDao;
	}

	public DefaultDataImportHandler() {
		super();
	}

	public DefaultDataImportHandler(InputStream is, DataImportForm form)
			throws Exception {
		super(is, form);
	}

	@Override
	public ActionReport dataImport(boolean insertPermit, boolean defaultInsert) {
		this.insertPermit = insertPermit;
		this.defaultInsert = defaultInsert;
		return commonImport(tableName, columns, pkColumns, insertPermit);
	}

	public ActionReport validateRows(Map<String, String> fieldValueMap,
			LxdmType lxdmType) {
		if (lxdmType != null) {
			this.lxdmType = lxdmType;
		}
		if (super.colEntList == null || colEntList.size() == 0) {
			return new ActionReport(ActionReport.SUCCESS, "获取系统数据列元数据失败");
		}
		int errorCount = 0;
		StringBuffer errorMsgs = new StringBuffer();
		Map<String, ImportRule> drgzMap = new HashMap<String, ImportRule>(
				super.drgzList.size());
		List<String> wbsjzList = new ArrayList<String>();
		for (ImportRule drgz : super.drgzList) {
			drgzMap.put(drgz.getZdmc() + drgz.getWbsjz(), drgz);
			wbsjzList.add(drgz.getWbsjz());
		}
		for (Column colEntity : super.colEntList) {
			String colName = colEntity.getColumnName();
			String colVal = fieldValueMap.get(colName);
			String comment = colEntity.getComments();
			// 类型代码转换
			ImportRule drgz = drgzMap.get(colName);
			if (drgz == null) {
				for (String wbsjz : wbsjzList) {
					drgz = drgzMap.get(colName + wbsjz);
					if (drgz != null) {
						break;
					}
				}
			}
			// 判断强制非空
			if (!DataImportConstant.QZFK.equals(colEntity.getNullable())) {
				if (!DataImportValidater.required(colName, fieldValueMap)) {
					// 加入默认值
					if (defaultInsert) {
						fieldValueMap.put(colName, colEntity.getDataDefault());
						colVal = colEntity.getDataDefault();
					}
					if (!DataImportValidater.required(colName, fieldValueMap)) {
						errorCount++;
						errorMsgs.append(comment + "的值不能为空 ");
					}
				}
			}
			// 如果导入数据值为空且默认值不为空
			if (DataImportConstant.QZFK.equals(colEntity.getNullable())
					&& "".equals(colVal) && colEntity.getDataDefault() != null
					&& !"".equals(colEntity.getDataDefault())) {
				if (defaultInsert) {
					fieldValueMap.put(colName, colEntity.getDataDefault());
					colVal = colEntity.getDataDefault();
				}
			}
			// 判断是否为数字,忽略有类型代码转换的情况
			if (DataImportConstant.NUMBER.equals(colEntity.getDataType())) {
				if (drgz != null && drgz.getWbzdlx() != null
						&& DataImportConstant.NUMBER.equals(drgz.getWbzdlx())) {
					if (!DataImportValidater.isValidateNumber(colVal)) {
						errorCount++;
						errorMsgs.append(comment + "必须为数字 ");
					} else {
						if (!DataImportValidater.isValidatePrecision(colVal,
								colEntity.getDataPrecision())) {
							errorCount++;
							errorMsgs.append(comment + "数位超出范围,正确范围在"
									+ colEntity.getDataPrecision() + "位以内 ");
						}
						if (DataImportValidater.isDecimal(colVal)) {
							if (!DataImportValidater.isValidateScale(colVal,
									colEntity.getDataScale())) {
								errorCount++;
								errorMsgs.append(comment + "小数位超出范围,正确范围在"
										+ colEntity.getDataScale() + "位以内 ");
							}
						}
					}
				}
			}
			// 验证字符长度，忽略有类型代码转换的情况
			else if (DataImportConstant.VARCHAR2
					.equals(colEntity.getDataType())
					|| DataImportConstant.VARCHAR.equals(colEntity
							.getDataType())
					|| DataImportConstant.CHAR.equals(colEntity.getDataType())) {
				// 没有代码转换的情况
				if (drgz == null) {
					int dataLen = colEntity.getDataLength();
					if (!DataImportValidater.isValidateVarchar2Len(colVal,
							dataLen)) {
						errorCount++;
						errorMsgs.append(comment + "字符长度超出范围 ");
					}
				}
			}
			if (drgz != null) {
				String zdmc = drgz.getZdmc();
				// 直接转换成对应的值
				if (drgzMap.get(zdmc + colVal) == null && drgz.getCkb() == null) {
					errorCount++;
					errorMsgs.append(comment + "字段值必须录入系统所指定的值 ");
				} else if (drgzMap.get(zdmc + colVal) != null
						&& drgz.getCkb() == null) {
					fieldValueMap.put(zdmc, drgzMap.get(zdmc + colVal)
							.getNbsjz());
				}
				// 根据类型代码表的代码值进行转换
				else if (drgz.getCkb() != null
						&& !"".equals(drgz.getCkb())
						&& DataImportConstant.LXDMB.equalsIgnoreCase(drgz
								.getCkb())) {
					Code lxdm = lxdmDao.getLxdmBySuperDmAndDmmc(drgz
							.getFllxdm(), fieldValueMap.get(zdmc));
					if ((lxdm == null || "".equals(lxdm))
							&& DataImportConstant.FQZFK.equals(colEntity
									.getNullable())) {
						colEntity.getNullable();
						errorCount++;
						errorMsgs.append(comment + "字段值在系统类型库中不存在相应代码 ");
					} else {
						// 首先根据数据库定义的字段规则获取类型代码字段
						String nbsjckzd = drgz.getNbsjckzd();
						if (nbsjckzd != null && !"".equals(nbsjckzd)) {
							if (LxdmType.LXDM.equals(nbsjckzd)) {
								fieldValueMap.put(drgz.getZdmc(), lxdm
										.getCode());
							} else if (LxdmType.DMMC.equals(nbsjckzd)) {
								fieldValueMap.put(drgz.getZdmc(), lxdm
										.getName());
							} else if (LxdmType.DMJC.equals(nbsjckzd)) {
								fieldValueMap.put(drgz.getZdmc(), lxdm
										.getShortName());
							}
						} else if (LxdmType.LXDM.equals(lxdmType)) {
							fieldValueMap.put(drgz.getZdmc(), lxdm.getCode());
						} else if (LxdmType.DMMC.equals(lxdmType)) {
							fieldValueMap.put(drgz.getZdmc(), lxdm.getName());
						} else if (LxdmType.DMJC.equals(lxdmType)) {
							fieldValueMap.put(drgz.getZdmc(), lxdm.getShortName());
						}
					}
				}
				// 非类型代码表的代码值进行转换
				else if (drgz.getCkb() != null
						&& !"".equals(drgz.getCkb())
						&& !DataImportConstant.LXDMB.equalsIgnoreCase(drgz
								.getCkb())) {
					String lxdm = lxdmDao.getDmByCkbCkzd(drgz.getWbsjckzd(),
							colVal, drgz.getNbsjckzd(), drgz.getCkb());
					if (lxdm != null && !"".equals(lxdm)) {
						fieldValueMap.put(zdmc, lxdm);
					} else if (DataImportConstant.FQZFK.equals(colEntity
							.getNullable())) {
						errorCount++;
						errorMsgs.append(comment + "字段值在系统数据中不存在对应值 ");
					}
				} else if (DataImportConstant.FQZFK.equals(colEntity
						.getNullable())) {
					errorCount++;
					errorMsgs.append(comment + "字段值在系统数据中不合法 ");
				}
			}
		}
		if (errorCount == 0) {
			return new ActionReport(ActionReport.SUCCESS, "该数据合法");
		} else {
			return new ActionReport(ActionReport.ERROR, String
					.valueOf(errorMsgs));
		}
	}

	public ActionReport commonImport(String tableName, String[] columns,
			String[] pkColumns, boolean insertPermit) {
		ActionReport report = null;
		List<String> sqlList = new ArrayList<String>();
		report = beforeDataImport();
		if (!report.isSuccess()) {
			dataImportReport.addError(-1, report.getMsg());
			return report;
		}
		int rowcount = sheet.getRows();
		for (int i = 2; i < rowcount; i++) {
			Cell[] rows = sheet.getRow(i);
			fieldValueMap.clear();
			for (ImportField importField : importFields.getImportObjList()) {
				int index = importField.getIndex();
				String value;
				if (index < rows.length)
					value = rows[index].getContents().trim();
				else
					value = "";
				fieldValueMap.put(importField.getDBName(), value);
			}
			fieldValueMap.put(DataImportConstant.TABLE_NAME, tableName);
			report = validateRows(fieldValueMap, null);
			if (!report.isSuccess()) {
				dataImportReport.addError(i + 1, report.getMsg());
				continue;
			}
			try {
				boolean exsitsRs = false;
				String[] pkValues = null;
				if (pkColumns == null
						&& (pkColsList == null || pkColsList.size() == 0)) {
					exsitsRs = false;
				} else if (pkColumns != null && pkColumns.length > 0) {
					pkValues = getColumnValues(pkColumns, fieldValueMap);
					exsitsRs = importDao.exsitsRsByPks(tableName, pkColumns,
							pkValues);
				} else {
					if (!exsitsRs) { // 根据数据库唯一索引判断记录是否存在
						for (String[] pkCols : pkColsList) {
							String[] pkColValues = getColumnValues(pkCols,
									fieldValueMap);
							exsitsRs = importDao.exsitsRsByPks(tableName,
									pkCols, pkColValues);
							if (exsitsRs) {
								pkColumns = pkCols;
								pkValues = pkColValues;
								break;
							}
						}
					}
				}
				String[] values = getColumnValues(columns, fieldValueMap);
				if (!exsitsRs) {
					if (insertPermit) {
						// 插入记录
						sqlList
								.add(getAutoInsertSql(tableName, columns,
										values));
						// importDao.insertWithAutoID(tableName, columns,
						// values);
					} else {
						continue;
					}
				} else if (insertPermit) {
					if (form.isReserve()) {
						// // 覆盖原记录
						importDao.updateByPks(tableName, columns, values,
								pkColumns, pkValues);
					} else {
						// 保留原记录
						// 错误处理例程
						dataImportReport.addError(i + 1, "系统中已经存在该记录");
						continue;
					}
				} else {
					continue;
				}
			} catch (Exception e) {
				e.printStackTrace();
				dataImportReport.addError(i + 1, "数据格式错误，请检查后重新导入");
				continue;
			}
			dataImportReport.addSuccess();
		}
		dataImportReport.setRecordCount(rowcount - 2);
		int importCount = importDao.batchInsertWithAutoID(sqlList);
		dataImportReport.setSuccessCount(importCount);
		dataImportReport.setFailedCount(sqlList.size() - importCount);
		dataImportReport.setMergeCount(rowcount - 2 - importCount);
		report = afterDataImport();
		if (!report.isSuccess()) {
			dataImportReport.addError(-2, report.getMsg());
			return report;
		}
		return new ActionReport(ActionReport.SUCCESS, "");
	}

	public String getAutoInsertSql(String tableName, String columns[],
			String values[]) {
		StringBuffer sbSql = new StringBuffer("insert into ");
		sbSql.append(tableName);
		sbSql.append("( ID,");
		for (int i = 0; i < columns.length; i++) {
			sbSql.append(columns[i]);
			sbSql.append(i != columns.length - 1 ? "," : "");
		}
		sbSql.append(" ) values( HIBERNATE_SEQUENCE.nextval,");
		for (int i = 0; i < values.length; i++)
			if (i == values.length - 1)
				sbSql.append("'" + values[i] + "'");
			else
				sbSql.append("'" + values[i] + "',");
		sbSql.append(" )");
		return sbSql.toString();
	}
}
