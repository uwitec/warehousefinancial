package com.wfms.dto.system;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.wfms.common.entity.Invisible;
import com.wfms.model.system.PartGenInfo;
import com.wfms.model.system.PartRight;

@JsonIgnoreProperties("jsList")
public class JsDto {
	private PartGenInfo js = new PartGenInfo();
	private List<PartGenInfo> jsList = new ArrayList<PartGenInfo>();
	private List<PartRight> jsqxList = new ArrayList<PartRight>();
	
	public List<PartRight> getJsqxList() {
		return jsqxList;
	}
	public void setJsqxList(List<PartRight> jsqxList) {
		this.jsqxList = jsqxList;
	}
	
	@Invisible
	public PartGenInfo getJs() {
		return js;
	}
	public void setJs(PartGenInfo js) {
		this.js = js;
	}
	
	@Invisible
	public List<PartGenInfo> getJsList() {
		return jsList;
	}
	public void setJsList(List<PartGenInfo> jsList) {
		this.jsList = jsList;
	};
}
