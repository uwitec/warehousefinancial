package com.wfms.dao.zcgl.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.entity.Page;
import com.wfms.dao.zcgl.IZcxxDao;
import com.wfms.model.zcgl.ZcxxEntity;

@Repository
public class ZcxxDaoImpl extends BaseDao implements IZcxxDao{

	public int addZcxx(ZcxxEntity zcxx) {
		return super.add(zcxx);
	}

	public int deleteZcxx(int id) {
		return super.delete(ZcxxEntity.class, id);
	}

	public List<ZcxxEntity> getZcxxList(Page page) {
		return super.executeQuery(ZcxxEntity.class, page);
	}

	public int getZcxxTotalCount(Page page) {
		return super.getTotalCount(ZcxxEntity.class, page.getConditionList());
	}

	public int updateZcxx(List<ZcxxEntity> zcxxList) {
		return super.batchUpdate(zcxxList, 15);
	}

	public List<ZcxxEntity> getZcxxList(String column, String zclx) {
		String hql = "FROM ZcxxEntity where 1=1";
		if (column != null && !"".equals(column) && zclx != null && !"".equals(zclx)) {
			hql += " and  "+column+" ='"+zclx+"' ";
		}
		return (List<ZcxxEntity>)super.getList(hql);
	}

	public List<ZcxxEntity> getZcxxByZclx(String column, String zclx) {
		String hql = "FROM ZcxxEntity where "+column+" ='"+zclx+"'";
		return (List<ZcxxEntity>) super.getList(hql);
	}

	public int updateZcxx(ZcxxEntity zcxx) {
		return super.update(zcxx);
	}

	public ZcxxEntity getZcxxById(int zcxxId) {
		return (ZcxxEntity)super.getByPk(ZcxxEntity.class, zcxxId);
	}
	public List<ZcxxEntity> getZcxxListBysjdyo(String column, String zclx) {
		List<ZcxxEntity> relist = new ArrayList<ZcxxEntity>();
		String hql = "select z.id,z.zcdm,z.zcmc,z.zclx,z.zcgg,z.zcdw,z.bz" +//,z.scrq
				" from ZCGL_ZCXX z " +
				" inner join zcgl_ckzcb c on c.ZCXXID = z.id" +
				" where c.ZCCSSL > 0";
		if (column != null && !"".equals(column) && zclx != null && !"".equals(zclx)) {
			hql += " and  "+column+" ='"+zclx+"' ";
		}
		List list = super.executeSQLQuery(hql);
		for(int i=0;i<list.size();i++){
			Object[] p = (Object[])list.get(i);
			ZcxxEntity z = new ZcxxEntity();
			z.setId(Integer.parseInt(String.valueOf(p[0])));
			z.setZcdm(String.valueOf(p[1]));
			z.setZcmc(String.valueOf(p[2])+"|"+String.valueOf(p[4]));
			z.setZclx(String.valueOf(p[3]));
			z.setZcgg(String.valueOf(p[4]));
			z.setZcdw(String.valueOf(p[5]));
			z.setBz(String.valueOf(p[6]));
			relist.add(z);
		}
		return relist;
	}
}
