package com.wfms.service.zcgl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfms.common.entity.Page;
import com.wfms.dao.zcgl.IZcckDao;
import com.wfms.dao.zcgl.IZcrkDao;
import com.wfms.model.zcgl.CkdEntity;
import com.wfms.model.zcgl.CkdmxEntity;
import com.wfms.model.zcgl.CkzcEntity;
import com.wfms.service.zcgl.IZcckService;

@Service
public class ZcckServiceImpl implements IZcckService {
	
	@Autowired
	private IZcckDao zcckDao;
	
	@Autowired
	private IZcrkDao zcrkDao;
	
	public int addCkd(CkdEntity entity, List<CkdmxEntity> list) {
		int result = 0 ;
		if (list != null) {
			entity.setZt("1");
			for (int i = 0; i < list.size(); i++) {
				CkdmxEntity ckdmx = list.get(i);
				String scrq = ckdmx.getScrq();
				if (scrq != null && !"".equals(scrq)) {
					ckdmx.setScrq(scrq.substring(0, 10));
				}
				int zcId = ckdmx.getZcxx().getId();
				int zclxId = ckdmx.getZclx().getId();
				int ckId = entity.getCkxx().getId();
				
				CkzcEntity ckzc = zcrkDao.getCkzc(ckId, zclxId, zcId);
				if (ckzc != null) {
					int zccssl = ckzc.getZccssl();
					int sysl = zccssl-ckdmx.getCksl();
					if (sysl<=0) {
						result = 0;
						return result ;
					}
					ckzc.setZccssl(sysl);
					ckzc.setZcckjg(String.valueOf(ckdmx.getCkjg()));
					zcrkDao.updateCkzc(ckzc);
				}
				double zje = ckdmx.getCksl()*ckdmx.getCkjg();
				if(Integer.parseInt(ckdmx.getBcje()) < zje){
					entity.setZt("0");
				}
				ckdmx.setZje(String.valueOf(zje));
				entity.addCkdmx(ckdmx);
			}
			String xm = entity.getLyrxm();
			entity.setLyrid(xm.split("[|]")[0]);
			entity.setLyrxm(xm.split("[|]")[1]);
			result = zcckDao.addCkd(entity);
		}
		return result;
	}

	public int deleteCkd(int ckdId) {
		List<CkdmxEntity> ckdmxList = zcckDao.getCkdmxListByCkdId(ckdId);
		if (ckdmxList != null && !"".equals(ckdmxList)) {
			for (int i = 0; i < ckdmxList.size(); i++) {
				CkdmxEntity ckdmx = ckdmxList.get(i);
				int zcId = ckdmx.getZcxx().getId();
				int zclxId = ckdmx.getZclx().getId();
				int ckId = ckdmx.getCkd().getCkxx().getId();
				CkzcEntity ckzc = zcrkDao.getCkzc(ckId, zclxId, zcId);
				if (ckzc != null && !"".equals(ckzc)) {
					int zccssl = ckzc.getZccssl();
					ckzc.setZccssl(zccssl+ckdmx.getCksl());
					zcrkDao.updateCkzc(ckzc);
				}
				zcckDao.deleteCkdmx(ckdmx);
			}
		}
		return zcckDao.deleteCkd(ckdId);
	}

	public Page getCkdmxByPage(Page page) {
		if (page == null ) {
			page = new Page();
		}
		page.setDataList(zcckDao.getCkdmxList(page));
		page.setTotalCount(zcckDao.getCkdmxTotalCount(page));
		return page;
	}

	public Page getZcckdByPage(Page page) {
		if (page == null ) {
			page = new Page();
		}
		page.setDataList(zcckDao.getZcckdxList(page));
		page.setTotalCount(zcckDao.getZcckdTotalCount(page));
		return page;
	}
}
