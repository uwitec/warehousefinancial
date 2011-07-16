package com.wfms.dao.system.impl;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.util.BeanConvort;
import com.wfms.dao.system.IGnmkDao;
import com.wfms.model.system.ModuleGenInfo;
import com.wfms.model.system.PartGenInfo;
import com.wfms.model.system.PartRight;
import com.wfms.model.system.RightGenInfo;

@Repository
public class GnmkDaoImpl extends BaseDao implements IGnmkDao{

	public ModuleGenInfo getGnmk(int GnmkId) {
		Object obj = super.getByPk(ModuleGenInfo.class, GnmkId);
		return obj == null ? null : (ModuleGenInfo)obj;
	}
	public List<ModuleGenInfo> getAllGnmk(){
		Object obj = super.getList("FROM ModuleGenInfo");
		return obj == null ? null : (List<ModuleGenInfo>)obj;
	}
	
	public List<ModuleGenInfo> getSonGnmk(int parentId){
		Object obj = super.executeQuery("FROM ModuleGenInfo g where g.parentId=?",parentId);
		return obj == null ? null : (List<ModuleGenInfo>)obj;
	}
	
	public int addGnmk(ModuleGenInfo gnmk){
		Serializable strId = super.insert(gnmk);
		int id =-1;
		try {
			id = Integer.parseInt(strId.toString());
		} catch (Exception e) {
			id =-1;
		}
		return id;
	}

	public int delGnmk(int id){
		return super.delete(ModuleGenInfo.class, id);
	}
	
	public int updGnmk(ModuleGenInfo gnmk){
		return super.update(gnmk);
	}
	
	public int addQx(RightGenInfo qx){
		Serializable strId = super.insert(qx);
		int id =-1;
		try {
			id = Integer.parseInt(strId.toString());
		} catch (Exception e) {
			id =-1;
		}
		return id;
	}
	
	public int delQx(RightGenInfo qx){
		return super.delete(qx);
	}
	
	public List<ModuleGenInfo> getJsyyGnmk(int jsId){
		String sql="select module.* from module_geninf module" +
				" inner join right_geninf right" +
				" on module.moduleid = right.moduleid" +
				" inner join part_right partright" +
				" on right.rightid = partright.rightid" +
				" where partright.partid = ? and haschild='0'";
		List<ModuleGenInfo> moduleList = super.executeSQLQuery(sql,ModuleGenInfo.class, jsId);
		return moduleList;
	}
	
	public List<ModuleGenInfo> getJswyGnmk(int jsId){
		String sql ="select * from module_geninf where haschild='0' minus"
				+" (select module.* from module_geninf module"
				+" inner join right_geninf right"
				+" on module.moduleid = right.moduleid"
				+" inner join part_right partright"
				+" on right.rightid = partright.rightid"
				+" where partright.partid =? and haschild='0')";
		List<ModuleGenInfo> moduleList = super.executeSQLQuery(sql,ModuleGenInfo.class, jsId);
		return moduleList;
	}
	
	public List<ModuleGenInfo> getAllLeafGnmk(){
		String hql ="FROM ModuleGenInfo where hasChild='0'";
		return super.executeQuery(hql);
		//Object obj = super.getList(hql);
		//return obj == null ? null : (List<ModuleGenInfo>)obj;
	}
	public List<ModuleGenInfo> getGnmkByQxIds(int[] qxIds) {
		String[] allFields = new String[14];

		allFields[0]="module.moduleId";
		allFields[1]="module.name";
		allFields[2]="module.honeticize";
		allFields[3]="module.forwardPage";
		allFields[4]="module.parentId";
		allFields[5]="module.hasChild";
		allFields[6]="module.description";
		allFields[7]="module.sortMark";
		allFields[8]="right.rightId";
		allFields[9]="right.parentId";
		allFields[10]="right.name";
		allFields[11]="right.moduleId";
		allFields[12]="right.description";
		allFields[13]="right.rightStatus";
		
		List allFieldList = Arrays.asList(allFields);
		List<Map> gnmkParamValsList = new ArrayList<Map>();
		
		String queryHql="select "+allFieldList.toString().replace("[","").replace("]","")+" from module_geninf module" +
				" inner join right_geninf right on right.moduleid=module.moduleid where module.hasChild='0'";
		List<Object[]> objsList = super.executeSQLQuery(queryHql,"right.rightId",qxIds);
		for(int i=0;i<objsList.size();i++)
		{
			Object[] objs= objsList.get(i);
			Map gnmkParamVals=new HashMap(13);
			for(int j=0;j<objs.length;j++)
			{
				if(j<7)
					gnmkParamVals.put(allFields[j].substring(allFields[j].indexOf(".")+1), objs[j]);
				else
					gnmkParamVals.put(allFields[j], objs[j]);
			}
			gnmkParamValsList.add(gnmkParamVals);
		}
		List<ModuleGenInfo> gnmkList = null;
		try {
			gnmkList = (List<ModuleGenInfo>)BeanConvort.boundToArray(ModuleGenInfo.class,gnmkParamValsList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gnmkList;
	}
	
	public List<ModuleGenInfo> getGnmkByIds(int[] gnmkIds) {
		String[] allFields = new String[13];

		allFields[0]="gnmk.id";
		allFields[1]="gnmk.mkmc";
		allFields[2]="gnmk.mkpy";
		allFields[3]="gnmk.dyym";
		allFields[4]="gnmk.sjmkid";
		allFields[5]="gnmk.sfyxj";
		allFields[6]="gnmk.mkms";
		allFields[7]="qx.id qxid";
		allFields[8]="qx.fjid";
		allFields[9]="qx.qxmc";
		allFields[10]="qx.gnmkid";
		allFields[11]="qx.qxms";
		allFields[12]="qx.zt";
		
		List allFieldList = Arrays.asList(allFields);
		List<Map> gnmkParamValsList = new ArrayList<Map>();
		
		String queryHql="select "+allFieldList.toString().replace("[","").replace("]","")+" from gnmkb gnmk" +
				" left join qxb qx on qx.gnmkid=gnmk.id";
		List<Object[]> objsList = super.executeSQLQuery(queryHql,"gnmk.id",gnmkIds);
		for(int i=0;i<objsList.size();i++)
		{
			Object[] objs= objsList.get(i);
			Map gnmkParamVals=new HashMap(13);
			for(int j=0;j<objs.length;j++)
			{
				if(objs[j]!=null)
				{
					if(j<7)
						gnmkParamVals.put(allFields[j].substring(allFields[j].indexOf(".")+1), objs[j]);
					else if(j==7)
						gnmkParamVals.put("qx.id", objs[j]);
					else
						gnmkParamVals.put(allFields[j], objs[j]);
				}
			}
			gnmkParamValsList.add(gnmkParamVals);
		}
		List<ModuleGenInfo> gnmkList = null;
		try {
			gnmkList = (List<ModuleGenInfo>)BeanConvort.boundToArray(ModuleGenInfo.class,gnmkParamValsList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gnmkList;
	}
	public List<ModuleGenInfo> getAllRootGnmk() {
		String[] allFields = new String[7];

		allFields[0]="moduleId";
		allFields[1]="name";
		allFields[2]="honeticize";
		allFields[3]="forwardPage";
		allFields[4]="parentId";
		allFields[5]="hasChild";
		allFields[6]="description";
		
		List allFieldList = Arrays.asList(allFields);
		List<Map> gnmkParamValsList = new ArrayList<Map>();
		
		String queryHql="select "+allFieldList.toString().replace("[","").replace("]","")+" from module_geninf module" +
				" where module.parentid=? or module.haschild=?";
		String[] queryParams=new String[2];
		queryParams[0]="0";
		queryParams[1]="1";
		List<Object[]> objsList = super.executeSQLQuery(queryHql,queryParams);
		for(int i=0;i<objsList.size();i++)
		{
			Object[] objs= objsList.get(i);
			Map gnmkParamVals=new HashMap(7);
			for(int j=0;j<objs.length;j++)
			{
				if(objs[j]!=null)
				{
					gnmkParamVals.put(allFields[j], objs[j]);
				}
			}
			gnmkParamValsList.add(gnmkParamVals);
		}
		List<ModuleGenInfo> gnmkList = null;
		try {
			gnmkList = (List<ModuleGenInfo>)BeanConvort.boundToArray(ModuleGenInfo.class,gnmkParamValsList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gnmkList;
	}
}
