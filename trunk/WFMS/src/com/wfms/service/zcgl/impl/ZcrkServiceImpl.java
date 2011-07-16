package com.wfms.service.zcgl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfms.common.entity.Page;
import com.wfms.dao.zcgl.ICkxxDao;
import com.wfms.dao.zcgl.IZclxDao;
import com.wfms.dao.zcgl.IZcrkDao;
import com.wfms.dao.zcgl.IZcxxDao;
import com.wfms.model.zcgl.CkzcEntity;
import com.wfms.model.zcgl.RkdEntity;
import com.wfms.model.zcgl.RkdmxEntity;
import com.wfms.service.zcgl.IZcrkService;

@Service
public class ZcrkServiceImpl implements IZcrkService {
	
	@Autowired
	private IZcrkDao zcrkDao;
	
	@Autowired
	private IZcxxDao zcxxDao;
	
	@Autowired
	private IZclxDao zclxDao;
	
	@Autowired
	private ICkxxDao ckxxDao;

	public Page getRkdmxByPage(Page page) {
		if (page == null) {
			page = new Page();
		}
		page.setDataList(zcrkDao.getRkdmxList(page));
		page.setTotalCount(zcrkDao.getRkdmxTotalCount(page));
		return page;
	}

	public Page getZcrkdByPage(Page page) {
		if (page == null) {
			page = new Page();
		}
		page.setDataList(zcrkDao.getZcrkdxList(page));
		page.setTotalCount(zcrkDao.getZcrkdTotalCount(page));
		return page;
	}

	public int addRkd(RkdEntity entity , List<RkdmxEntity> list) {
		entity.setZt("1");
		if (list != null && list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				RkdmxEntity rkdmx = list.get(i);
				String scrq = rkdmx.getScrq();
				if (scrq != null && !"".equals(scrq)) {
					rkdmx.setScrq(scrq.substring(0, 10));
				}
				int zcId = rkdmx.getZcxx().getId();
				int zclxId = rkdmx.getZclx().getId();
				int ckId = entity.getCkxx().getId();
				CkzcEntity ckzc = zcrkDao.getCkzc(ckId, zclxId, zcId);
				if (ckzc != null) {
					int zccssl = ckzc.getZccssl();
					ckzc.setZccssl(zccssl+rkdmx.getRksl());
					ckzc.setZcrkjg(String.valueOf(rkdmx.getRkjg()));
					zcrkDao.updateCkzc(ckzc);
				}else{
					ckzc = new CkzcEntity();
					ckzc.setCkxx(ckxxDao.getCkxxById(ckId));
					ckzc.setZclx(zclxDao.getZclxById(zclxId));
					ckzc.setZcxx(zcxxDao.getZcxxById(zcId));
					ckzc.setZccssl(rkdmx.getRksl());
					ckzc.setZcrkjg(String.valueOf(rkdmx.getRkjg()));
					zcrkDao.addCkzc(ckzc);
				}
				double zje = rkdmx.getRkjg()*rkdmx.getRksl();
				rkdmx.setZje(String.valueOf(zje));
				if(Integer.parseInt(rkdmx.getBcje()) < zje){//已收金额小于总金额，收费没有完成
					entity.setZt("0");
				}
				entity.addRkdmx(rkdmx);
			}
		}
		return zcrkDao.addRkd(entity);
	}

	public int deleteRkd(int rkdId) {
		List<RkdmxEntity> rkdmxList = zcrkDao.getRkdmxListByRkdId(rkdId);
		if (rkdmxList != null && !"".equals(rkdmxList)) {
			for (int i = 0; i < rkdmxList.size(); i++) {
				RkdmxEntity rkdmx = rkdmxList.get(i);
				int zcId = rkdmx.getZcxx().getId();
				int zclxId = rkdmx.getZclx().getId();
				int ckId = rkdmx.getRkd().getCkxx().getId();
				CkzcEntity ckzc = zcrkDao.getCkzc(ckId, zclxId, zcId);
				if (ckzc != null && !"".equals(ckzc)) {
					int zccssl = ckzc.getZccssl();
					ckzc.setZccssl(zccssl-rkdmx.getRksl());
					zcrkDao.updateCkzc(ckzc);
				}
				zcrkDao.deleteRkdmx(rkdmx);
			}
		}
		return zcrkDao.deleteRkd(rkdId);
	}


	public List<RkdmxEntity> getRkdmxByZclx(String column, String zclx) {
		return zcrkDao.getRkdmxByZclx(column,zclx);
	}

	public Page getCkzcByPage(Page page) {
		if (page == null ) {
			page = new Page();
		}
		page.setDataList(zcrkDao.getCkzcList(page));
		page.setTotalCount(zcrkDao.getCkzcTotalCount(page));
		return page;
	}
}
