package com.wfms.service.jxc.fywh.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfms.common.entity.Page;
import com.wfms.common.util.ConditionBean;
import com.wfms.dao.jxc.fywh.IYwfywhDao;
import com.wfms.model.jxc.fygl.YwfywhEntity;
import com.wfms.service.jxc.fywh.IYwfywhService;

@Service
public class YwfywhServiceimpl implements IYwfywhService {
	@Autowired 
	private IYwfywhDao ywfywhDao;
	
	public int addFywh(YwfywhEntity YwfywhEntity) {
		return ywfywhDao.addFywh(YwfywhEntity);
	}

	public int deleteFywh(int id) {
		return ywfywhDao.deleteFywh(id);
	}

	public List<YwfywhEntity> getYwfywhEntity() {
		return ywfywhDao.getYwfywhEntity();
	}

	public Page getYwfywhEntity(Page page) {
		if(page==null){
			page=new Page();
		}
		page.setDataList(ywfywhDao.getYwfywhEntity(page));
		page.setTotalCount(ywfywhDao.getYwfywhEntityCount(page.getConditionList()));
		return page;
	}

	public int getYwfywhEntityCount(List<ConditionBean> list) {
		return ywfywhDao.getYwfywhEntityCount(list);
	}

	public int updateFywh(List<YwfywhEntity> YwfywhEntity) {
		return ywfywhDao.updateFywh(YwfywhEntity);
	}
	
	public List<YwfywhEntity> getAllXfz(String ywd, String stime, String etime, String khmc, String fylx){
		List<YwfywhEntity> re = new ArrayList<YwfywhEntity>();
		List<YwfywhEntity> ls = new ArrayList<YwfywhEntity>();
		List<YwfywhEntity> r = ywfywhDao.getAllXfz(ywd, stime, etime);
		List<YwfywhEntity> d = ywfywhDao.getDcjf();
		
		for(YwfywhEntity yc : r){
			if(fylx != null && !fylx.equals("")){
				if(fylx.equals(yc.getZt())){
					for(YwfywhEntity dc : d){
						if(yc.getYwdid().equals(dc.getYwdid())){
							yc.setJsrmc((Integer.parseInt(yc.getJsrmc())-Integer.parseInt(dc.getJe()))+"");
						}
					}
					ls.add(yc);
				}
			}else{
				for(YwfywhEntity dc : d){
					if(yc.getYwdid().equals(dc.getYwdid())){
						yc.setJsrmc((Integer.parseInt(yc.getJsrmc())-Integer.parseInt(dc.getJe()))+"");
					}
				}
				ls.add(yc);
			}
		}

		if(khmc != null && !khmc.equals("") && ywd != null && !ywd.equals("")){
			for(YwfywhEntity yc : ls){
				if(ywd.equals(yc.getYwdid()) && khmc.equals(yc.getKhmc())){
					re.add(yc);
				}
			}
		}else if(khmc != null && !khmc.equals("")){
			for(YwfywhEntity yc : ls){
				if(khmc.equals(yc.getKhmc())){
					re.add(yc);
				}
			}
		}else{
			for(YwfywhEntity yc : ls){
				if(ywd.equals(yc.getYwdid())){
					re.add(yc);
				}
			}
		}
		return re;
	}
}
