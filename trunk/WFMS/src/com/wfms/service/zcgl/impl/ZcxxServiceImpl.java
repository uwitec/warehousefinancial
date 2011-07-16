package com.wfms.service.zcgl.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfms.common.entity.Page;
import com.wfms.dao.zcgl.IZcxxDao;
import com.wfms.model.zcgl.ZcxxEntity;
import com.wfms.service.zcgl.IZcxxService;

@Service
public class ZcxxServiceImpl implements IZcxxService {
	
	@Autowired
	private IZcxxDao zcxxDao;

	public int addZcxx(ZcxxEntity zcxx) {
		return zcxxDao.addZcxx(zcxx);
	}

	public int deleteZcxx(int id) {
		return zcxxDao.deleteZcxx(id);
	}

	public Page getZcxxByPage(Page page) {
		if (page == null) {
			page = new Page();
		}
		page.setDataList(zcxxDao.getZcxxList(page));
		page.setTotalCount(zcxxDao.getZcxxTotalCount(page));
		return page;
	}

	public int updateZcxx(List<ZcxxEntity> zcxxList) {
		return zcxxDao.updateZcxx(zcxxList);
	}

	public List<ZcxxEntity> getZcxxList(String column, String zclx, String type) {
		if(type.equals("0")){//出库
			return zcxxDao.getZcxxListBysjdyo(column, zclx);
		}else{
			List<ZcxxEntity> p = zcxxDao.getZcxxList( column,  zclx);
			List<ZcxxEntity> r = new ArrayList<ZcxxEntity>();
			for(ZcxxEntity ze:p){
				ZcxxEntity z = new ZcxxEntity();
				z.setId(ze.getId());
				z.setZcdm(ze.getZcdm());
				z.setZcmc(ze.getZcmc()+"|"+ze.getZcgg());
				z.setZclx(ze.getZclx());
				z.setZcgg(ze.getZcgg());
				z.setZcdw(ze.getZcdw());
				z.setBz(ze.getBz());
				r.add(z);
			}
			return r;
		}
	}

	public List<ZcxxEntity> getZcxxByZclx(String column, String zclx) {
		return zcxxDao.getZcxxByZclx(column, zclx);
	}

}
