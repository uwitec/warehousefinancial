package com.wfms.service.system.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfms.dao.system.IGnmkDao;
import com.wfms.dto.system.GnmkDto;
import com.wfms.model.system.ModuleGenInfo;
import com.wfms.model.system.RightGenInfo;
import com.wfms.service.system.IGnmkService;

@Service
public class GnmkServiceImpl implements IGnmkService {
	@Autowired
	private IGnmkDao gnmkDao;
	
	public void setGnmkDao(IGnmkDao gnmkDao) {
		this.gnmkDao = gnmkDao;
	}

	public GnmkDto getAllGnmk() {
		List<ModuleGenInfo> list = gnmkDao.getAllGnmk();
		GnmkDto dto = new GnmkDto();
		dto.setGnmkList(list);
		return dto;
	}
	
	
	public GnmkDto getAllGnmk(List<ModuleGenInfo> list) {
		List<ModuleGenInfo> parentList = new ArrayList<ModuleGenInfo>();
		List<ModuleGenInfo> rootList = gnmkDao.getAllRootGnmk();
		Map<Integer, ModuleGenInfo> gnmkMap=new HashMap<Integer, ModuleGenInfo>(rootList.size());
		for(ModuleGenInfo gnmk:rootList)
		{
			gnmkMap.put(gnmk.getModuleId(), gnmk);
		}
		for(int i=0;i<list.size();i++){
			//parentList = getParentGnmk(parentList,list.get(i));
			parentList = getParentGnmk(parentList,gnmkMap,list.get(i));
			if(!parentList.contains(list.get(i)))
			{
				parentList.add(list.get(i));
			}
		}
		
		//初始化返回的功能模块
		/*List<ModuleGenInfo> rsList  = new ArrayList<ModuleGenInfo>();
		
		//添加标记
		boolean isAdd;
		
		//遍历�?有用户对应功能模�?
		for(int i=0;i<parentList.size();i++){
			//标记为可添加
			isAdd = true;
			//遍历�?有返回的功能模块
			for(int j=0;j<rsList.size();j++){
				//如果有相同的则标记不可加
				if(parentList.get(i).getId() == rsList.get(j).getId()){
					//标记不可添加
					isAdd = false;
					break;
				}
			}
			//根据标记添加功能模块
			if(isAdd){
				rsList.add(parentList.get(i));
			}
		}*/
		GnmkDto dto = new GnmkDto();
		dto.setGnmkList(parentList);
		return dto;
	}
	
	/**
	 * 获取功能父节�?
	 * @param list
	 * @param gnmk
	 * @return List<ModuleGenInfo> 
	 */
	private List<ModuleGenInfo> getParentGnmk(List<ModuleGenInfo> list,ModuleGenInfo gnmk){
		ModuleGenInfo entity = gnmk;
		while (!"0".equals(entity.getParentId())) {
			entity = gnmkDao.getGnmk(entity.getParentId());
			list.add(entity);
		}
		return list;
	}
	
	public GnmkDto getSonGnmk(int parentId){
		List<ModuleGenInfo> list = gnmkDao.getSonGnmk(parentId);
		GnmkDto dto = new GnmkDto();
		dto.setGnmkList(list);
		return dto;
	}
	
	public int addGnmk(ModuleGenInfo gnmk){
		int parentId = gnmk.getParentId();
		ModuleGenInfo parentGnmk = gnmkDao.getGnmk(parentId);
		
		if(parentGnmk == null)
		{
			RightGenInfo qx = new RightGenInfo();
			qx.setModule(gnmk);
			qx.setParentId(0);
			qx.setRightStatus("1");
			qx.setName(gnmk.getName());
			qx.setDescription(gnmk.getDescription());
			gnmkDao.addGnmk(gnmk);
			return gnmkDao.addQx(qx);
		}
		if(parentGnmk !=null && parentGnmk.getRight() != null &&  parentGnmk.getRight().getPartRights()!= null && parentGnmk.getRight().getPartRights().size()!=0){
			return -1;
		}
		if( parentGnmk !=null  && parentGnmk.getRight()!=null){
			gnmkDao.delQx(parentGnmk.getRight());
		}
		if(parentGnmk !=null && "0".equals(parentGnmk.getHasChild())){
			parentGnmk.setHasChild("1");
			gnmkDao.updGnmk(parentGnmk);
		}
		RightGenInfo qx = new RightGenInfo();
		qx.setModule(gnmk);
		qx.setParentId(0);
		qx.setRightStatus("1");
		qx.setName(gnmk.getName());
		qx.setDescription(gnmk.getDescription());
		gnmkDao.addGnmk(gnmk);
		gnmkDao.addQx(qx);
		return 1;
	}

	public int delGnmk(int id){
		List<ModuleGenInfo> list = gnmkDao.getSonGnmk(id);
		if(list.size() != 0 ){
			return -1;
		}
		ModuleGenInfo gnmk = gnmkDao.getGnmk(id);
		RightGenInfo qx = gnmk.getRight();
		if(qx == null){
			return gnmkDao.delGnmk(id);
		}
		if(qx.getPartRights()== null || qx.getPartRights().size() == 0 ){
			gnmkDao.delQx(qx);
			gnmkDao.delGnmk(id);
			return 1;
		}
		return -2;
	}
	
	public int updGnmkList(List<ModuleGenInfo> list){
		int count=0;
		for(int i=0;i<list.size();i++){
			count += gnmkDao.updGnmk(list.get(i));
		}
		return count;
	}
	public GnmkDto getJsyyGnmk(int jsId){
		List<ModuleGenInfo> gnmkList = gnmkDao.getJsyyGnmk(jsId);
		return getAllGnmk(gnmkList);
	}
	
	public GnmkDto getJswyGnmk(int jsId){
		List<ModuleGenInfo> gnmkList = gnmkDao.getJswyGnmk(jsId);
		return getAllGnmk(gnmkList);
	}
	
	public GnmkDto getYhwyGnmk(List<ModuleGenInfo> list){
		List<ModuleGenInfo> gnmkList = gnmkDao.getAllLeafGnmk();
		for(int i=0;i<gnmkList.size();i++){
			for(int j=0;j<list.size();j++){
				if(gnmkList.get(i).getModuleId() == list.get(j).getModuleId()){
					gnmkList.remove(i);
					i--;
					break;
				}
			}
		}
		GnmkDto dto = getAllGnmk(gnmkList);
		return dto;
	}
	
	/**
	 * 获取功能父节�?
	 * @param list
	 * @param gnmk
	 * @return List<ModuleGenInfo> 
	 */
	private List<ModuleGenInfo> getParentGnmk(List<ModuleGenInfo> list,Map<Integer,ModuleGenInfo> gnmkMap,ModuleGenInfo gnmk){
		ModuleGenInfo entity = gnmk;
		while (entity.getParentId()!=0) {
			int sjmkId = entity.getParentId();
			entity = gnmkMap.get(sjmkId);
			if(entity == null)
			{
				entity = gnmkDao.getGnmk(sjmkId);
				gnmkMap.put(entity.getModuleId(), entity);
			}
			if(!list.contains(entity))
			{
				list.add(entity);
			}
		}
		return list;
	}
}
