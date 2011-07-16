package com.wfms.dao.jxc.query.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.dao.jxc.query.IQueryDao;
@Repository
public class QueryDaoimpl extends BaseDao implements IQueryDao{
	
	/**
	 * 产品报表
	 * @param cpid		产品ID
	 * @param ftime		查询开始时间
	 * @param etime		查询结束时间
	 * @return
	 */
	public List getProducts(String cpid, String ftime, String etime){
		/**
		 * String[] title = [产品ID，产品类型，类型代码，产品名称，产品代码，产品规格，产品单位，生产厂家
		 * 					，赠品入库数量，赠品出库数量，赠品现存，商品入库数量，商品出库数量，商品库存];
		 */
		String sql = "select x.id,l.lxmc,l.lxdm,x.zcmc,x.zcdm,x.zcgg,x.zcdw,x.sccj" +
			",zprk.zprksl,zpck.zpcksl,zpkc.zpkc,sprk.sprksl,spck.spcksl,spkc.spkc" +
			
			//产品信息
			" from zcgl_zclx l" +
			" inner join zcgl_zcxx x on l.id = x.zclx" +
			
			//赠品数量
			" left join (select zx.id,sum(NVL(cx.rksl,0)) zprksl from zcgl_rkd c " +
			" inner join zcgl_rkdmx cx on cx.RKDID = c.id" +
			" inner join zcgl_zcxx zx on zx.id = cx.zcid" +
			" where c.CKID in(select c.id from zcgl_ckxx c where c.ckmc like '%赠%')";
		if(ftime != null && !ftime.equals("") && etime != null && !etime.equals("")){
			sql += " and c.rksj between '"+ftime+"' and '"+etime+"'";
		}//赠品入库　　　入库时间段
		sql += " group by zx.id) zprk on zprk.id = x.id" +
			" left join (select zx.id,sum(NVL(cx.cksl,0)) zpcksl from zcgl_ckd c " +
			" inner join zcgl_ckdmx cx on cx.ckdid = c.id" +
			" inner join zcgl_zcxx zx on zx.id = cx.zcid" +
			" where c.CKID in(select c.id from zcgl_ckxx c where c.ckmc like '%赠%')";
		if(ftime != null && !ftime.equals("") && etime != null && !etime.equals("")){
			sql += " and c.cksj between '"+ftime+"' and '"+etime+"'";
		}//赠品出库　　　出库时间段
		sql += " group by zx.id)zpck on zpck.id = x.id" +
			" left join (select k.zcxxid,sum(NVL(k.zccssl,0))zpkc from zcgl_ckzcb k " +
			" where k.ckxxid in(select c.id from zcgl_ckxx c where c.ckmc like '%赠%')" +
			" group by k.zcxxid) zpkc on zpkc.zcxxid = x.id" +
			
			//商品数量
			" left join (select zx.id,sum(NVL(cx.rksl,0)) sprksl from zcgl_rkd c " +
			" inner join zcgl_rkdmx cx on cx.RKDID = c.id" +//赠品现库存
			" inner join zcgl_zcxx zx on zx.id = cx.zcid" +
			" where c.CKID not in(select c.id from zcgl_ckxx c where c.ckmc like '%赠%')";
		if(ftime != null && !ftime.equals("") && etime != null && !etime.equals("")){
			sql += " and c.rksj between '"+ftime+"' and '"+etime+"'";
		}//商品入库　　　入库时间段
		sql += " group by zx.id) sprk on sprk.id = x.id" +
			" left join (select zx.id,sum(NVL(cx.cksl,0)) spcksl from zcgl_ckd c " +
			" inner join zcgl_ckdmx cx on cx.ckdid = c.id" +
			" inner join zcgl_zcxx zx on zx.id = cx.zcid" +
			" where c.CKID not in(select c.id from zcgl_ckxx c where c.ckmc like '%赠%')";
		if(ftime != null && !ftime.equals("") && etime != null && !etime.equals("")){
			sql += " and c.cksj between '"+ftime+"' and '"+etime+"'";
		}//商品出库　　　出库时间段
		sql += " group by zx.id)spck on spck.id = x.id" +
			" left join (select k.zcxxid,sum(NVL(k.zccssl,0))spkc from zcgl_ckzcb k " +
			" where k.ckxxid not in(select c.id from zcgl_ckxx c where c.ckmc like '%赠%')" +
			" group by k.zcxxid) spkc on spkc.zcxxid = x.id" +
			" where 1=1";
		
		if(cpid != null && !cpid.equals("")){
			sql += " and x.id = "+cpid;
		}
		//sql += " order by sprk.RKQFZRXM";
		
		return this.executeSQLQuery(sql);
//		return this.exeSQL(sql, 14);
	}
	
	/**
	 * 供应商供货统计
	 * @param supid		供应商名称
	 * @param ftime		入库查询开始时间
	 * @param etime		入库查询结束时间
	 * @return
	 */
	public List getSuppliers(String supid, String ftime, String etime){
		/**
		 * String[] title = [产品ID，商品供应商，赠品供应商，产品类型，类型代码，产品名称，产品代码，产品规格，产品单位，生产厂家
		 * 					，赠品入库数量，赠品现存，商品入库数量，商品入库价格，商品库存，现存商品价格];
		 */
		String sql = "select x.id,sprk.RKQFZRXM,zprk.RKQFZRXM,l.lxmc,l.lxdm,x.zcmc,x.zcdm,x.zcgg,x.zcdw,x.sccj" +
				",zprk.zprksl,zpkc.zpkc,sprk.sprksl,sprk.sprkjg,spkc.spkc,spkc.spjg" +
				" from zcgl_zclx l" +
				" inner join zcgl_zcxx x on l.id = x.zclx" +
				" left join (select zx.id,c.RKQFZRXM,sum(NVL(cx.rksl,0)) zprksl from zcgl_rkd c" +
				" inner join zcgl_rkdmx cx on cx.RKDID = c.id" +
				" inner join zcgl_zcxx zx on zx.id = cx.zcid" +
				" where c.CKID in(select c.id from zcgl_ckxx c where c.ckmc like '%赠%')";
		if(ftime != null && !ftime.equals("") && etime != null && !etime.equals("")){
			sql += " and c.RKSJ between '"+ftime+"' and '"+etime+"'";
		}//赠品入库   时间对比
		sql += " group by c.RKQFZRXM,zx.id) zprk on zprk.id = x.id" +
				" left join (select k.zcxxid,sum(NVL(k.zccssl,0))zpkc from zcgl_ckzcb k" +
				" where k.ckxxid in(select c.id from zcgl_ckxx c where c.ckmc like '%赠%')" +
				" group by k.zcxxid) zpkc on zpkc.zcxxid = x.id" +
				" left join (select zx.id,c.RKQFZRXM,sum(NVL(cx.rksl,0)) sprksl,sum(NVL(cx.rksl,0)*NVL(cx.rkjg,0))sprkjg" +
				" from zcgl_rkd c" +
				" inner join zcgl_rkdmx cx on cx.RKDID = c.id" +
				" inner join zcgl_zcxx zx on zx.id = cx.zcid" +
				" where c.CKID not in(select c.id from zcgl_ckxx c where c.ckmc like '%赠%')";
		if(ftime != null && !ftime.equals("") && etime != null && !etime.equals("")){
			sql += " and c.RKSJ between '"+ftime+"' and '"+etime+"'";
		}//正常入库   时间对比
		sql += " group by c.RKQFZRXM,zx.id) sprk on sprk.id = x.id" +
				" left join (select k.zcxxid,sum(NVL(k.zccssl,0))spkc,sum(NVL(k.zccssl,0)*NVL(k.zcrkjg,0))spjg from zcgl_ckzcb k" +
				" where k.ckxxid not in(select c.id from zcgl_ckxx c where c.ckmc like '%赠%')" +
				" group by k.zcxxid) spkc on spkc.zcxxid = x.id" +
				" where 1=1";
		
		if(supid != null && !supid.equals("")){//供应商名称
			sql += " and sprk.RKQFZRXM like '%"+supid+"%'";
		}
		sql += " order by sprk.RKQFZRXM,zprk.RKQFZRXM";
		return this.executeSQLQuery(sql);
//		return this.exeSQL(sql, 16);
	}
	
	/**
	 * 经销商统计
	 * @param deaid		经销商名称
	 * @param ftime		统计开始时间
	 * @param etime		统计结束时间
	 * @return
	 */
	public List getDealer(String deaid, String ftime, String etime){
		/**
		 * String[] title = [产品ID，商品供应商，赠品供应商，产品类型，类型代码，产品名称，产品代码，产品规格，产品单位，生产厂家
		 * 					，赠品出库数量，赠品现存，商品出库数量，商品出库价格，商品库存，现存商品价格];
		 */
		String sql = "select x.id,spck.lybmmc,zpck.lybmmc,l.lxmc,l.lxdm,x.zcmc,x.zcdm,x.zcgg,x.zcdw,x.sccj" +
			",zpck.zpcksl,zpkc.zpkc,spck.spcksl,spck.spckjg,spkc.spkc,spkc.spjg" +
			" from zcgl_zclx l" +
			" inner join zcgl_zcxx x on l.id = x.zclx" +
			" left join (select zx.id,c.lybmmc,sum(NVL(cx.cksl,0)) zpcksl from zcgl_ckd c" +
			" inner join zcgl_ckdmx cx on cx.cKDID = c.id" +
			" inner join zcgl_zcxx zx on zx.id = cx.zcid" +
			" where c.CKID in(select c.id from zcgl_ckxx c where c.ckmc like '%赠%')";
		if(ftime != null && !ftime.equals("") && etime != null && !etime.equals("")){
			sql += " and c.cksj between '"+ftime+"' and '"+etime+"'";
		}//赠品入库   时间对比
		sql += " group by c.lybmmc,zx.id) zpck on zpck.id = x.id" +
			" left join (select k.zcxxid,sum(NVL(k.zccssl,0))zpkc from zcgl_ckzcb k" +
			" where k.ckxxid in(select c.id from zcgl_ckxx c where c.ckmc like '%赠%')" +
			" group by k.zcxxid) zpkc on zpkc.zcxxid = x.id" +
			" left join (select zx.id,c.lybmmc,sum(NVL(cx.cksl,0)) spcksl,sum(NVL(cx.cksl,0)*NVL(cx.ckjg,0))spckjg" +
			" from zcgl_ckd c" +
			" inner join zcgl_ckdmx cx on cx.cKDID = c.id" +
			" inner join zcgl_zcxx zx on zx.id = cx.zcid" +
			" where c.CKID not in(select c.id from zcgl_ckxx c where c.ckmc like '%赠%')";
		if(ftime != null && !ftime.equals("") && etime != null && !etime.equals("")){
			sql += " and c.cksj between '"+ftime+"' and '"+etime+"'";
		}//商品入库   时间对比
		sql += " group by c.lybmmc,zx.id) spck on spck.id = x.id" +
			" left join (select k.zcxxid,sum(NVL(k.zccssl,0))spkc,sum(NVL(k.zccssl,0)*NVL(k.zcckjg,0))spjg from zcgl_ckzcb k" +
			" where k.ckxxid not in(select c.id from zcgl_ckxx c where c.ckmc like '%赠%')" +
			" group by k.zcxxid) spkc on spkc.zcxxid = x.id";
		if(deaid != null && !deaid.equals("")){
			sql += " and spck.lybmmc like '"+deaid+"'";
		}
		sql += " order by spck.lybmmc,zpck.lybmmc";
		
		return this.executeSQLQuery(sql);
//		return this.exeSQL(sql, 16);
	}
	
	/**
	 * 收入金额
	 * @param ftime	开始时间
	 * @param etime	结束时间
	 * @return
	 */
	public List getMonsr(String ftime, String etime){
		String sql = "select sum(NVL(cx.cksl,0)*NVL(cx.ckjg,0)) from zcgl_ckdmx cx" +
				" inner join zcgl_ckd c on c.id = cx.ckdid" +
				" where c.cksj between '"+ftime+"' and '"+etime+"'";
		
		return this.executeSQLQuery(sql);
//		return this.exeSQL(sql, 1);
	}
	
	/**
	 * 入库支出金额
	 * @param ftime	开始时间
	 * @param etime	结束时间
	 * @return
	 */
	public List getMonrkzc(String ftime, String etime){
		String sql = "select sum(NVL(rx.rkjg,0)*NVL(rx.rksl,0)) from zcgl_rkdmx rx" +
				" inner join zcgl_rkd r on r.id = rx.rkdid" +
				" where r.rksj between '"+ftime+"' and '"+etime+"'";
		
		return this.executeSQLQuery(sql);
//		return this.exeSQL(sql, 1);
	}
	
	/**
	 * 费用支出金额
	 * @param ftime	开始时间
	 * @param etime	结束时间
	 * @return
	 */
	public List getMonfyzc(String ftime, String etime){
		String sql = "select f.fylx,sum(NVL(f.fy,0)) from jxc_fywh f" +
				" where f.xfsj between '"+ftime+"' and '"+etime+"'" +
				" group by f.fylx";
		
		return this.executeSQLQuery(sql);
//		return this.exeSQL(sql, 2);
	}
	
	public List getMonckhzBylx(String ftime, String etime){
		/**
		 * String[] title = [出库类型，资产名称，资产规格，资产单位，数量，金额]
		 */
		String sql = "select cx.cklx,z.zcmc,z.zcgg,z.zcdw,sum(NVL(cx.cksl,0)),sum(NVL(cx.cksl,0)*NVL(cx.ckjg,0))" +
				" from zcgl_ckdmx cx" +
				" inner join zcgl_ckd c on c.id = cx.ckdid" +
				" inner join zcgl_zcxx z on z.id = cx.zcid" +
				" where c.cksj between '"+ftime+"' and '"+etime+"'" +
				" group by cx.cklx,z.zcmc,z.zcgg,z.zcdw";

		return this.executeSQLQuery(sql);
//		return this.exeSQL(sql, 6);
	}
	
	public List getMonrkhzBylx(String ftime, String etime){
		/**
		 * String[] title = [入库类型，资产名称，资产规格，资产单位，入库数量，金额]
		 */
		String sql = "select r.lylx,z.zcmc,z.zcgg,z.zcdw,sum(NVL(rx.rksl,0)),sum(NVL(rx.rksl,0)*NVL(rx.rkjg,0))" +
				" from zcgl_rkdmx rx" +
				" inner join zcgl_rkd r on r.id = rx.rkdid" +
				" inner join zcgl_zcxx z on z.id = rx.zcid" +
				" where r.rksj between '"+ftime+"' and '"+etime+"'" +
				" group by r.lylx,z.zcmc,z.zcgg,z.zcdw";

		return this.executeSQLQuery(sql);
//		return this.exeSQL(sql, 6);
	}
	
	public List getXs(String ftime, String etime, String xs) {
		String sql = "select k.sxgsmc,sum(cx.ckjg*cx.cksl) xsjg from zcgl_ckdmx cx" +
				" inner join zcgl_ckd c on c.id = cx.ckdid" +
				" inner join jxc_kyxx k on k.jdmc = c.lybmmc" +
				" where c.cksj between '"+ftime+"' and '"+etime+"'" ;
		if(xs != null && !xs.equals("")){
			sql += " and k.sxgsmc = '"+xs+"'";
		}
		sql += " group by k.sxgsmc";
		return this.executeSQLQuery(sql);
//		return this.exeSQL(sql, 2);
	}
	
	private List exeSQL(String sql, int count){
		List list = new ArrayList();
		Connection con = null;
		Statement stmt = null;
		//PreparedStatement ps = null;
		ResultSet rs = null;

		try{
			Session s = this.getSession();
			con = s.connection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				Object[] obj = new Object[count];
				for(int i=0;i<count;i++){
					obj[i] = rs.getString(i+1);
				}
				list.add(obj);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				if(con != null ){
					con.close();
				}
				if(stmt != null ){
					stmt.close();
				}
				if(rs != null ){
					rs.close();
				}
			}catch(Exception ex){}
		}
		return list;
	}
}
