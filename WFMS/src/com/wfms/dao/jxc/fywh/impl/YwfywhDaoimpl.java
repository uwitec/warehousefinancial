package com.wfms.dao.jxc.fywh.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.entity.Page;
import com.wfms.common.util.ConditionBean;
import com.wfms.dao.jxc.fywh.IYwfywhDao;
import com.wfms.model.jxc.fygl.YwfywhEntity;
@Repository
public class YwfywhDaoimpl extends BaseDao implements IYwfywhDao{

	public int addFywh(YwfywhEntity YwfywhEntity) {
		return super.add(YwfywhEntity);
	}

	public int deleteFywh(int id) {
		return super.delete(YwfywhEntity.class, id);
	}

	public List<YwfywhEntity> getYwfywhEntity() {
		return super.findAll(YwfywhEntity.class);
	}

	public List<YwfywhEntity> getYwfywhEntity(Page page) {
		return super.executeQuery(YwfywhEntity.class, page);
	}

	public int getYwfywhEntityCount(List<ConditionBean> list) {
		return super.getTotalCount(YwfywhEntity.class, list);
	}

	public int updateFywh(List<YwfywhEntity> YwfywhEntity) {
		return super.batchUpdate(YwfywhEntity,15);
	}

	public List<YwfywhEntity> getAllXfz(String ywd, String stime, String etime) {
		List<YwfywhEntity> re = new ArrayList<YwfywhEntity>();
		//出库单ID，出库金额，出库已收金额，出库未收金额，出库时间，出库单编号，经销商
		String sql = "select C.ID YWDID,CK.CZJE ZJE,CK.CBCJE SJE,CK.CJE CJE,C.CKSJ JYSJ,C.CKDBH DJH,C.LYBMMC KHMC,'1' ZT" +
				" from zcgl_ckd C" +
				" INNER JOIN (" +
				"SELECT SUM(ZJE) CZJE,SUM(BCJE) CBCJE,SUM(ZJE)-SUM(BCJE) CJE,CKDID " +
				" FROM zcgl_ckdmx GROUP BY CKDID) CK ON CK.CKDID = C.ID" +
				" WHERE C.ZT = '0'" ;
				if(ywd != null  && !ywd.equals("")){
					sql += " and C.CKDBH = '"+ywd+"'";
				}
				if(stime != null && !stime.equals("") && etime != null && !etime.equals("")){
					sql += " and C.CKSJ between '"+stime+"' and '"+etime+"'";
				}
				sql += " union all" +
				" select C.ID YWDID,CK.CZJE ZJE,CK.CBCJE SJE,CK.CJE CJE,C.RKSJ JYSJ,C.RKDBH DJH,C.RKQFZRXM KHMC,'0' ZT" + 
				" from zcgl_rkd C" +
				" INNER JOIN (SELECT SUM(ZJE) CZJE,SUM(BCJE) CBCJE,SUM(ZJE)-SUM(BCJE) CJE" +
				" ,RKDID " +
				" FROM zcgl_rkdmx GROUP BY RKDID) CK ON CK.RKDID = C.ID" +
				" WHERE C.ZT = '0'";
				if(ywd != null  && !ywd.equals("")){
					sql += " and C.RKDBH = '"+ywd+"'";
				}
				if(stime != null && !stime.equals("") && etime != null && !etime.equals("")){
					sql += " and C.RKSJ between '"+stime+"' and '"+etime+"'";
				}
		List l = super.executeSQLQuery(sql);
		for(int i=0;i<l.size();i++){
			YwfywhEntity fe = new YwfywhEntity();
			Object[] o = (Object[])l.get(i);
			fe.setId(Integer.parseInt(o[0].toString()));
			fe.setYwdid(o[0].toString());//业务单ID
			fe.setJe(o[1].toString());//总金额
			fe.setJsrid(o[2].toString());//已付金额
			fe.setJsrmc(o[3].toString());//差额
			fe.setJysj(o[4].toString());//交易时间
			fe.setKhid(o[5].toString());//业务单号
			fe.setKhmc(o[6].toString());//客户名称
			fe.setZt(o[7].toString());//0入库(支出)；1出库(收入)
			re.add(fe);
		}
		return re;
	}
	
	/**
	 * 多次缴费金额
	 * @return
	 */
	public List<YwfywhEntity> getDcjf() {
		List<YwfywhEntity> re = new ArrayList<YwfywhEntity>();
		String sql = "select ywdid,SUM(je) je from jxc_ywfy group by ywdid";
		List l = super.executeSQLQuery(sql);
		for(int i=0;i<l.size();i++){
			YwfywhEntity fe = new YwfywhEntity();
			Object[] o = (Object[])l.get(i);
			fe.setYwdid(o[0].toString());//业务单ID
			fe.setJe(o[0].toString());
			re.add(fe);
		}
		return re;
	}
}
