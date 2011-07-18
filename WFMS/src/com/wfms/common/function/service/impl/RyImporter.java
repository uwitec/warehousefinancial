package com.wfms.common.function.service.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("ryImportHandler")
@Scope("prototype")
public class RyImporter extends DefaultDataImportHandler {

	/*@Autowired
	private IRoleDao zwglDao;

	@Autowired
	//private IRyglDao ryglDao;

	public RyImporter() {
		super.getImportFields().addField(new ImportField("人员编号", "rybh"));
		super.getImportFields().addField(new ImportField("人员姓名", "ryxm"));
		super.getImportFields().addField(new ImportField("性别", "xb"));
		super.getImportFields().addField(
				new ImportField("职务", "member.role.name"));
		super.getImportFields().addField(
				new ImportField("部门", "member.role.depart.name"));
		super.getImportFields().addField(new ImportField("在职状态", "zt"));
		super.getImportFields().addField(new ImportField("入职时间", "rzsj"));
		super.getImportFields().addField(
				new ImportField("登录帐号", "member.loginid"));
		super.getImportFields().addField(
				new ImportField("登录密码", "member.password"));
		super.getImportFields().addField(new ImportField("是否老师", "sfls"));
		super.getImportFields().addField(
				new ImportField("班主任带班班级", "bzr.bzrbj.bjdm.bjmc"));
	}

	public RyImporter(InputStream is, DataImportForm form) throws Exception {
		super(is, form);
	}

	@Override
	public ActionReport dataImport(boolean insertPermit, boolean defaultInsert) {
		this.insertPermit = insertPermit;
		this.defaultInsert = defaultInsert;
		return ryImport(tableName, columns, pkColumns, insertPermit);
	}

	@Override
	public ActionReport validateRows(Map<String, String> fieldValueMap,
			LxdmType lxdmType) {
		int errorCount = 0;
		StringBuffer errorMsgs = new StringBuffer();

		String xb = fieldValueMap.get("xb");
		String departName = fieldValueMap.get("member.role.depart.name");
		String roleName = fieldValueMap.get("member.role.name");

		if(!StringUtil.isNullOrEmpty(fieldValueMap.get("zt")))
		{
			if("在职".equals(fieldValueMap.get("zt")))
			{
				fieldValueMap.put("zt","1");
			}
			else if("离职".equals(fieldValueMap.get("zt")))
			{
				fieldValueMap.put("zt", "0");
			}
			else
			{
				errorCount += 1;
				errorMsgs.append("在职状态不合法：" + fieldValueMap.get("zt"));
			}
				
		}
		if(StringUtil.isNullOrEmpty(fieldValueMap.get("rzsj")))
		{
			fieldValueMap.put("rzsj", DateUtil.getCurrentDateString());
		}
		if(!"1".equals(xb.trim()) &&
				!"0".equals(xb.trim()))
		{
			if (!StringUtil.isNullOrEmpty(xb)
					&& (!"0".equals(xb) && !"1".equals(xb))){
				if ("男".equals(xb)) {
					fieldValueMap.put("xb", "1");
				} else if ("女".equals(xb)) {
					fieldValueMap.put("xb", "0");
				} else {
					errorCount += 1;
					errorMsgs.append("性别不合法：" + fieldValueMap.get("xb"));
				}
			}
			else {
				errorCount += 1;
				errorMsgs.append("性别信息不允许为空 ");
			}
		}

		if(StringUtil.isNullOrEmpty(fieldValueMap.get("member.loginid")))
		{
			fieldValueMap.put("member.loginid", fieldValueMap.get("rybh"));
			if(StringUtil.isNullOrEmpty(fieldValueMap.get("member.password")))
			{
				fieldValueMap.put("member.password","6868");
			}
			try {
				fieldValueMap.put("member.password", EncoderUtil.EncoderByMd5(fieldValueMap.get("rybh")));
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		else
		{
			if(StringUtil.isNullOrEmpty(fieldValueMap.get("member.password")))
			{
				try {
					fieldValueMap.put("member.password", EncoderUtil.EncoderByMd5("0"));
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			else
			{
				try {
					fieldValueMap.put("member.password", EncoderUtil.EncoderByMd5(fieldValueMap.get("member.password")));
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		RoleGenInfo role = zwglDao.getRoleByNameAndDepart(roleName, departName);
		if (role == null) {
			errorCount += 1;
			errorMsgs.append("部门或部门下的职务：[" + departName + "->" + roleName
					+ "]在系统中不存在 ");
		} else {
			fieldValueMap.put("member.role.rolid", role.getRolid());
		}

		if (!StringUtil.isNullOrEmpty(fieldValueMap.get("sfls"))
				&&"是".equals(fieldValueMap.get("sfls"))) {
			fieldValueMap.put("ls.lsxm", fieldValueMap.get("ryxm"));
		}
		if (errorCount == 0) {
			return new ActionReport(ActionReport.SUCCESS, "该数据合法");
		} else {
			return new ActionReport(ActionReport.ERROR, String
					.valueOf(errorMsgs));
		}
	}

	public ActionReport ryImport(String tableName, String[] columns,
			String[] pkColumns, boolean insertPermit) {
		ActionReport report = null;

		report = beforeDataImport();
		if (!report.isSuccess()) {
			dataImportReport.addError(-1, report.getMsg());
			return report;
		}
		int rowcount = sheet.getRows();
		List<RyEntity> addRyList = new ArrayList<RyEntity>();
		List<RyEntity> updateRyList = new ArrayList<RyEntity>();
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
			RyEntity ry = new RyEntity();
			try {
				BeanConvort.boundToObject(RyEntity.class, ry, fieldValueMap);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			if(ry.getMember()!=null)
			{
				ry.getMember().setRy(ry);
				ry.getMember().setDeniedlogin("false");
			}
			RyEntity existsRy = ryglDao.getRyByBh(ry.getRybh());
			if (existsRy == null) {
				addRyList.add(ry);
			} else {
				updateRyList.add(existsRy);
			}
		}
		ryglDao.addRyList(addRyList);
		ryglDao.updateRyList(updateRyList);
		dataImportReport.setRecordCount(rowcount - 2);
		dataImportReport.setMergeCount(updateRyList.size());
		dataImportReport.setSuccessCount(addRyList.size());
		report = afterDataImport();
		if (!report.isSuccess()) {
			dataImportReport.addError(-2, report.getMsg());
			return report;
		}
		return new ActionReport(ActionReport.SUCCESS, "");
	}*/
}
