package com.wfms.dao.jxc.fywh.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.entity.Page;
import com.wfms.common.util.ConditionBean;
import com.wfms.dao.jxc.fywh.IFywhDao;
import com.wfms.model.jxc.fygl.FywhEntity;
@Repository
public class FywhDaoimpl extends BaseDao implements IFywhDao{

	public int addFywh(FywhEntity FywhEntity) {
		return super.add(FywhEntity);
	}

	public int deleteFywh(int id) {
		return super.delete(FywhEntity.class, id);
	}

	public List<FywhEntity> getFywhEntity() {
		return super.findAll(FywhEntity.class);
	}

	public List<FywhEntity> getFywhEntity(Page page) {
		return super.executeQuery(FywhEntity.class, page);
	}

	public int getFywhEntityCount(List<ConditionBean> list) {
		return super.getTotalCount(FywhEntity.class, list);
	}

	public int updateFywh(List<FywhEntity> FywhEntity) {
		return super.batchUpdate(FywhEntity,15);
	}

	public List<FywhEntity> getAllXfz(String type) {
		List<FywhEntity> re = new ArrayList<FywhEntity>();
		String sql = "";
		if(!type.equals("")){
			if(type.trim().equals("0")){
//				sql = "SELECT MEMID,USERNAME FROM MEM_GENINF";
				sql = "select id,ygmc from jxc_ygxxb where zt='在职'";
			}else{
				sql = "SELECT ID,QCPZ FROM JXC_QCGL";
			}
		}
		List l = super.executeSQLQuery(sql);
		for(int i=0;i<l.size();i++){
			FywhEntity fe = new FywhEntity();
			Object[] o = (Object[])l.get(i);
			fe.setXfid(String.valueOf(o[0])+"|"+String.valueOf(o[1]));
			fe.setXfmc(String.valueOf(o[1]));
			re.add(fe);
		}
		return re;
	}
}
