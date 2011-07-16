package com.wfms.service.system;

import java.util.List;

import com.wfms.dto.system.XtszDto;
import com.wfms.model.system.XtszEntity;

public interface IXtszglService {
	
	public int insertXtsz(XtszEntity xtsz);
	
	public int updateXtsz(List<XtszEntity> xtszList);
	
	public int deleteXtszByPk(int xtszId);
	
	public XtszEntity getXtszByKey(String key);
	
	public List<XtszEntity> getAllXtsz();
	
	public XtszDto getKdXtsz();
	
	public XtszEntity getXtszByKeyName(String keyName);
	
}
