package com.wfms.service.system;

import java.util.List;

import com.wfms.dto.system.JsDto;
import com.wfms.model.system.PartGenInfo;
import com.wfms.model.system.PartRight;

public interface IJsglService {
	public JsDto getAllJs();
	
	public JsDto getJs(int id);
	
	public int addJs(PartGenInfo js);
	
	public int updJs(List<PartGenInfo> list);
	
	public int delJs(int id);
	
	public int addJsqx(int id,String gnmkIds);
	
	public int delJsqx(int id,String gnmkIds);
	
	public int updJsqx(List<PartRight> list);
	
	public JsDto getAllJsqx(int id);
	
	public JsDto getYhwyJs(String yhId);
	
	public int addYhjs(String yhId,int jsId);
	
	public int delYhjs(String yhId,int jsId);
	
	public JsDto getJsForZw(String zwId);

	public JsDto getJsForZwNot(String zwId);
}
