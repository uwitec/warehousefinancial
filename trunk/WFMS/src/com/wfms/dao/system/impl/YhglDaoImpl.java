package com.wfms.dao.system.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.entity.Page;
import com.wfms.constant.SystemConstant;
import com.wfms.dao.system.IYhglDao;
import com.wfms.model.system.MacEntity;
import com.wfms.model.system.MemberGenInfo;
import com.wfms.model.system.MemberRight;

@Repository
public class YhglDaoImpl extends BaseDao implements IYhglDao {
	
	/**
	 * 根据用户登录帐号得到用户信息
	 * @param dlzh
	 * @return 用户信息
	 */
	public MemberGenInfo getYhByDlzh(String dlzh) {
		String hql = "FROM MemberGenInfo where loginid=?";
		Object obj = super.getEntityByConditions(hql,dlzh);
		return  obj == null ? null :(MemberGenInfo)obj;
	}
	public List<MacEntity> readLocalMac() {
		return super.findAll(MacEntity.class);
	}
	public int updateMac(MacEntity me){
		return super.update(me);
	}
	public int addMac(MacEntity me){
//		return super.add(me);
		String kk = String.valueOf(super.insert(me));
		if(kk != null && !kk.equals("null") && !kk.equals("")){
			return 1;
		}else{
			return 0;
		}
	}
	/**
	 * 根据用户编号得到用户信息
	 * @param id
	 * @return 用户信息
	 */
	public MemberGenInfo getYhById(String id) {
		Object obj = super.getByPk(MemberGenInfo.class, id);
		return obj == null ? null :(MemberGenInfo)obj;
	}
	/**
	 * 修改用户信息
	 * @param yh
	 * @return 影响行数 
	 */
	public int updateYh(MemberGenInfo yh) {
		int row = super.update(yh);
		return row;
	}

	public int getCount(Page page){
		return super.getTotalCount(MemberGenInfo.class, page.getConditionList());
	}
	
	public List<MemberGenInfo> getAllYh(Page page){
		return super.executeQuery(MemberGenInfo.class, page);
	}
	
	public String addYh(MemberGenInfo yh){
		Serializable strId = super.insert(yh);
		return (String)strId;
	}
	
	
	public int delYh(String id){
		return super.delete(MemberGenInfo.class, id);
	}
	
	public List<MemberGenInfo> getYhForZw(String zwId) {
		String hql="select members from RoleGenInfo as role right join role.members as members where role.rolid=?";
		List<MemberGenInfo> memberList = new ArrayList<MemberGenInfo>(0);
		memberList = super.executeQuery(hql,zwId);
		return memberList;
	}
	public List<MemberGenInfo> getYhForZwNot(String zwId) {
		String hql = "from MemberGenInfo where role.rolid<>?";
		//String hql="select yh from MemberGenInfo yh where yh not in(select yh from MemberGenInfo yh,RoleMember yhzw where yh.memid=yhzw.member.memid and yhzw.role.rolid="+zwId+")";
		return super.executeQuery(hql,zwId);
	}
	
	public MemberGenInfo getYhByRyId(int ryId){
		String hql = "FROM MemberGenInfo where ry.id="+ryId;
		Object obj = super.getEntityByConditions(hql);
		return obj == null ? null : (MemberGenInfo)obj;
	}
	public List<Integer[]> getYhQxs(String yhId) {
		String qxSQL = "select rightId,rightType from" +
				" (select rightId,rightType from part_right" +
				" inner join role_part on role_part.partid = part_right.partid" +
				" inner join rol_mem on role_part.rolid = rol_mem.rolid" +
				" where rol_mem.memid=? union all" +
				" select rightId,rightType  from member_right" +
				" where rightStatus=? and memid=?" +
				" minus select rightId,rightType from member_right" +
				" where rightStatus=? and memid=?)group by rightId,rightType" +
				" having rightType = max(rightType)";
		String[] queryParams = new String[5];
		queryParams[0]=String.valueOf(yhId);
		queryParams[1]=SystemConstant.QXZT_QY;
		queryParams[2]=yhId;
		queryParams[3]=SystemConstant.QXZT_JY;
		queryParams[4]=yhId;
		List<Object[]> objsList=super.executeSQLQuery(qxSQL,queryParams);
		List<Integer[]> qxList = new ArrayList<Integer[]>(objsList.size());
		for(Object[] objs:objsList)
		{
			Integer[] qxLx = new Integer[2];
			qxLx[0]=Integer.valueOf(String.valueOf(objs[0]));
			qxLx[1]=Integer.valueOf(String.valueOf(objs[1]));
			qxList.add(qxLx);
		}
		return qxList;
	}
	
	/**
	 * 查询所有用户
	 */
	public List<MemberGenInfo> getAllYh() {
		Object obj = super.getList("FROM MemberGenInfo");
		return obj == null ? null : (List<MemberGenInfo>)obj;
	}
	
	public int getDepMemsCount(String depid){
		return super.getTotalCountByHql("select count(*) FROM MemberGenInfo where role.depart.depid='"+depid+"'");
	}
	public List<MemberGenInfo> getMemberByDep(Page page,String depid) {
		String hql ="from MemberGenInfo where role.depart.depid='"+depid+"'";
		return super.executeQuery(hql, page);
	}
	public List<MemberGenInfo> getMemberByRol(Page page, String rolid) {
		String hql ="from MemberGenInfo where role.rolid='"+rolid+"'";
		return super.executeQuery(hql, page);
	}
	public int getRolMemsCount(String rolid) {
		return super.getTotalCountByHql("select count(*) FROM MemberGenInfo where role.rolid='"+rolid+"'");
	}
	
	public int batchAddYhqx(List<MemberRight> memberRightList) {
		return super.batchAdd(memberRightList, memberRightList.size());
	}
	
	public int existsMemberRight(MemberRight memberRight) {
		String sql = "select id from member_right where memid='"+memberRight.getMember().getMemid()+"'" +
				" and rightId="+memberRight.getRight().getRightId();
		Object id = super.executeSQLScalar(sql);
		int memberRightId = 0;
		if(id!=null)
		{
			memberRightId = ((BigDecimal)id).intValue();
		}
		return memberRightId;
	}
	
	public int batchUpdateMemberRight(List<MemberRight> memberRightList) {
		return super.batchUpdate(memberRightList,memberRightList.size());
	}
	
	public MemberRight getYhqx(String memid, int rightId) {
		//String hql ="from MemberRight where member.memid=? and right.rightId=?";
		//return (MemberRight)super.executeScalar(hql, new String[]{memid,String.valueOf(rightId)});
		String sql="select * from member_right where memid=? and rightid=?";
		return (MemberRight)super.executeSQLScalar(sql, MemberRight.class, new String[]{memid,String.valueOf(rightId)});
	}
	
	public int delMemberRight(MemberRight memberRight){
		return super.delete(memberRight);
	}
	
	public int getRightAndType(String memid, String gnmkid) {
		String sql="select partright.rightType from part_right partright" +
				" inner join (select part.partId from part_geninf part" +
				" inner join role_part rolepart on part.partid = rolepart.partid" +
				" inner join (select rolid from rol_mem where memid=?) rolmem" +
				" on rolepart.rolid=rolmem.rolid) memberpart" +
				" on partright.partid = memberpart.partid" +
				" inner join right_geninf right on partright.rightid = right.rightid" +
				" where right.moduleid = ?";
		return ((BigDecimal)super.executeSQLScalar(sql,new String[]{memid,gnmkid})).intValue();
	}
	
	public boolean getYhzp(String userid, String path, String type) {
		boolean flag = false;
		String sql = "";
		Connection conn = null;
		ResultSet rs = null;
		Statement stmt = null;
		try{
			if(type.equals("0")){
				sql = "SELECT yhzp FROM ryb WHERE MEMID='"+userid+"'";
			}else if(type.equals("1")){
				sql = "SELECT xszp FROM xsjbxxb WHERE xh='"+userid+"'";
			}
			
			String fileurl = path+userid+".jpg";
			Session s = this.getSession();
			conn = s.connection();
			
			boolean defaultCommit = conn.getAutoCommit();
		    conn.setAutoCommit(false);
		    stmt = conn.createStatement();
		    rs = stmt.executeQuery(sql);
		    while (rs.next()) {
				/* 取出此BLOB对象 */
		    	oracle.sql.BLOB blob = null;
		    	if(type.equals("0")){
		    		blob = (oracle.sql.BLOB) rs.getBlob("yhzp");
				}else if(type.equals("1")){
					blob = (oracle.sql.BLOB) rs.getBlob("xszp");
				}
				
				/* 以二进制形式输出 */
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(fileurl));
				BufferedInputStream in = new BufferedInputStream(blob.getBinaryStream());
				int c;
				while ((c = in.read()) != -1) {
					out.write(c);
				}
				in.close();
				out.close();
				flag = true;
			}
			/* 正式提交 */
			conn.commit();
			conn.setAutoCommit(defaultCommit);
		    
			/*List list = super.executeSQLQuery(sql);
			for(int i=0;i<list.size();i++){
				Object obj = (Object)list.get(i);
				String bb = (String)obj;
				oracle.sql.BLOB blob = (oracle.sql.BLOB)list.get(i);
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(path+"//"+userid+".jpg"));
				BufferedInputStream in = new BufferedInputStream(bb.getBinaryStream());
				int c;
				while ((c = in.read()) != -1) {
					out.write(c);
				}
				in.close();
				out.close();
				flag = true;
			}*/
		} catch (Exception ex){
			ex.printStackTrace();
		}
		return flag;
	}
}
