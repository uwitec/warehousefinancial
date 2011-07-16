package com.wfms.webapp.common.attachfile;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import xuner.web.mvc.MvcUtil;

import com.wfms.common.entity.AjaxMsg;
import com.wfms.common.util.StringUtil;

@Controller
@Lazy
@RequestMapping("/common/attachFileAction.do")
public class AttachFileAction {
	
	@RequestMapping(params="method=delMultiFile")
	public AjaxMsg deleteMultiFile(HttpServletRequest request,
			@RequestParam(value="cate")String cate,
			@RequestParam(value="filenames")String filenames)
	{
		String customerFolder ="";
		if("mail".equals(cate))
		{
			customerFolder = "/mailfolder/";
		}
		AjaxMsg msg = new AjaxMsg();
		String appPath = request.getSession().getServletContext().getRealPath(
		"/");
		for(String filename:filenames.split(","))
		{
			String fullFilePath =appPath+customerFolder+filename;
			File removeFile = new File(fullFilePath);
			if(removeFile.exists())
			{
				removeFile.delete();
			}
		}
		return msg;
	}
	
	@RequestMapping(params = "method=downloadMultiFile")
	private ModelAndView downloadMultiFile(
			@RequestParam("path")String path) {
		path = StringUtil.unescape(path); 
		return MvcUtil.mimeModelAndView(path);
	}
}
