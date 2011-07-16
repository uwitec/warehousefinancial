package com.wfms.service.system.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfms.constant.SystemConstant;
import com.wfms.dao.system.IGnmkDao;
import com.wfms.dao.system.IJsglDao;
import com.wfms.dto.system.JsDto;
import com.wfms.model.system.ModuleGenInfo;
import com.wfms.model.system.PartGenInfo;
import com.wfms.model.system.PartRight;
import com.wfms.service.system.IJsglService;

@Service
public class JsglServiceImpl implements IJsglService {
	@Autowired
	private IJsglDao jsglDao;
	@Autowired
	private IGnmkDao gnmkDao;

	public void setGnmkDao(IGnmkDao gnmkDao) {
		this.gnmkDao = gnmkDao;
	}

	public void setJsglDao(IJsglDao jsglDao) {
		this.jsglDao = jsglDao;
	}

	public int addJs(PartGenInfo js) {
		return jsglDao.addJs(js);
	}

	public int delJs(int id) {
		PartGenInfo js = jsglDao.getJs(id);
		if (js.getPartRights() != null && js.getPartRights().size() != 0) {
			return -1;
		}else if (js != null
				&& SystemConstant.JS_DELETEABLE_CANNOT.equals(js
						.getDeleteable())) {
			return -3;
		} else {
			return jsglDao.delJs(id);
		}
	}

	public JsDto getAllJs() {
		JsDto dto = new JsDto();
		List<PartGenInfo> list = jsglDao.getAllJs();
		dto.setJsList(list);
		return dto;
	}

	public JsDto getJs(int id) {
		JsDto dto = new JsDto();
		PartGenInfo js = jsglDao.getJs(id);
		dto.setJs(js);
		return dto;
	}

	public int updJs(List<PartGenInfo> list) {
		int i = 0;
		for (; i < list.size(); i++) {
			jsglDao.updJs(list.get(i));
		}
		return i;
	}

	public int addJsqx(int partId, String gnmkIds) {
		List<PartRight> partRightList = new ArrayList<PartRight>();
		String[] gnmkIdAry = gnmkIds.split(";");
		for (String gnmkId : gnmkIdAry) {
			ModuleGenInfo gnmk = gnmkDao.getGnmk(Integer.valueOf(gnmkId));
			PartRight jsqx = new PartRight();
			PartGenInfo js = new PartGenInfo();
			js.setPartId(partId);
			jsqx.setPart(js);
			jsqx.setRight(gnmk.getRight());
			jsqx.setRightStatus("1");
			jsqx.setRightType(2);
			partRightList.add(jsqx);
		}
		return jsglDao.batchAddJsqx(partRightList);
	}

	public int delJsqx(int id, String gnmkIds) {
		String[] gnmkAry = gnmkIds.split(";");
		int res = 0;
		for(String gnmkId:gnmkAry)
		{
			ModuleGenInfo gnmk = gnmkDao.getGnmk(Integer.valueOf(gnmkId));
			PartRight jsqx = jsglDao.getJsqx(id, gnmk.getRight().getRightId());
			if (jsqx != null) {
				res += jsglDao.delJsqx(jsqx);
			}
		}
		return res;
	}

	public int updJsqx(List<PartRight> list) {
		int i = 0;
		for (; i < list.size(); i++) {
			jsglDao.updJsqx(list.get(i));
		}
		return i;
	}

	public JsDto getAllJsqx(int partId) {
		List<PartRight> list = jsglDao.getAllJsqx(partId);
		JsDto dto = new JsDto();
		dto.setJsqxList(list);
		return dto;
	}

	public JsDto getYhwyJs(String yhId) {
		JsDto dto = new JsDto();
		List<PartGenInfo> list = jsglDao.getYhwyJs(yhId);
		dto.setJsList(list);
		return dto;
	}

	public int addYhjs(String yhId, int jsId) {
		return jsglDao.addYhjs(yhId, jsId);
	}

	public int delYhjs(String yhId, int jsId) {
		return jsglDao.delYhjs(yhId, jsId);
	}

	public JsDto getJsForZw(String zwId) {
		JsDto dto = new JsDto();
		dto.setJsList(jsglDao.getJsForZw(zwId));
		return dto;
	}

	public JsDto getJsForZwNot(String zwId) {
		JsDto dto = new JsDto();
		dto.setJsList(jsglDao.getJsForZwNot(zwId));
		return dto;
	}

}
