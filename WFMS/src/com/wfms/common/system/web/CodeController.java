/**
 *******************************************************************************
 * 文件名：LxdmglAction.java
 *
 * 描述：
 * 
 * 创建日期：Jan 26, 2010 1:37:24 PM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.common.system.web;


import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wfms.common.system.entity.Code;
import com.wfms.common.web.BaseController;



@Controller
@Lazy(true)
@RequestMapping("/system/code_manage/*")
public class CodeController extends BaseController<Code>{
	
}
