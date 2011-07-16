package com.wfms.dao.system.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.dao.system.IJsglDao;
import com.wfms.model.system.MemberGenInfo;
import com.wfms.model.system.MemberPart;
import com.wfms.model.system.PartGenInfo;
import com.wfms.model.system.PartRight;

@Repository
public class JsglDaoImpl extends BaseDao implements IJsglDao {

	public int addJs(PartGenInfo js) {
		Serializable strId = super.insert(js);
		int id = -1;
		try {
			id = Integer.parseInt(strId.toString());
		} catch (Exception e) {
			id = -1;
		}
		return id;
	}

	public int delJs(int id) {
		return super.delete(PartGenInfo.class, id);
	}

	public List<PartGenInfo> getAllJs() {
		Object obj = super.getList("FROM PartGenInfo");
		return obj == null ? null : (List<PartGenInfo>)obj;
	}

	public PartGenInfo getJs(int id) {
		Object obj = super.getByPk(PartGenInfo.class, id);
		return obj == null ? null : (PartGenInfo)obj;
	}

	public int updJs(PartGenInfo js) {
		return super.update(js);
	}
	
	public int addJsqx(PartRight jsqx){
		
		Serializable strId = super.insert(jsqx);
		int id = -1;
		try {
			id = Integer.parseInt(strId.toString());
		} catch (Exception e) {
			id = -1;
		}
		return id;
	}
	
	public int delJsqx(PartRight jsqx){
		return super.delete(jsqx);
	}
	
	public int updJsqx(PartRight jsqx){
		return super.update(jsqx);
	}
	public PartRight getJsqx(int id,int qxid){
		String hql = "FROM PartRight jq where jq.part.partId="+id+" and jq.right.rightId="+qxid;
		Object obj = super.getEntityByConditions(hql);
		return obj == null ? null : (PartRight)obj;
	}
	
	public List<PartRight> getAllJsqx(int id){
		Object obj = super.getList("FROM PartRight jq where jq.part.partId="+id);
		return obj == null ? null : (List<PartRight>)obj; 
	}
	
	public List<PartGenInfo> getYhwyJs(String yhId){
		String hql = "select js from PartGenInfo js where js.partId not in (select j.partId from PartGenInfo j,MemberPart yj,MemberGenInfo y where y.memid=yj.member.memid and j.partId=yj.part.partId and y.memid='"+yhId+"')";
		Object obj = super.getList(hql);
		return obj == null ? null : (List<PartGenInfo>)obj;
	}
	
	public int addYhjs(String yhId,int jsId){
		MemberPart yhjs = new MemberPart();
		Object yh  = super.getByPk(MemberGenInfo.class, yhId);
		Object js  = super.getByPk(PartGenInfo.class, jsId);
		
		if(yh == null || js == null){
			return 0;
		}
		try {
			yhjs.setPart((PartGenInfo)js);
			yhjs.setMember((MemberGenInfo)yh);
			super.insert(yhjs);
		} catch (Exception e) {
			return 0;
		}
		return 1;
	}
	
	public int delYhjs(String yhId,int jsId){
		Map<Object,Object> cons = new HashMap<Object, Object>();
		cons.put("member.partId", yhId);
		cons.put("part.partId", jsId);
		return super.deleteByConditions(MemberPart.class,cons);
	}
	
	public List<PartGenInfo> getJsForZw(String zwId) {
		String hql="select js from PartGenInfo js,RolePart zwjs where js.partId=zwjs.part.partId and zwjs.role.rolid=?";
		List<PartGenInfo> jsList=super.executeQuery(hql,zwId);
		return jsList;
	}

	public List<PartGenInfo> getJsForZwNot(String zwId) {
		String hql="select js from PartGenInfo js where js not in(select js from PartGenInfo js,RolePart zwjs where js.partId=zwjs.part.partId and zwjs.role.rolid=?)";
		List<PartGenInfo> jsList=super.executeQuery(hql,zwId);
		return jsList;
	}

	public PartGenInfo getJsByJsmc(String jsmc) {
		String queryHql="FROM PartGenInfo where name=?";
		return (PartGenInfo)super.executeScalar(queryHql, jsmc);
	}

	public int batchAddJsqx(List<PartRight> partRightList) {
		return super.batchAdd(partRightList, partRightList.size());
	}
}
