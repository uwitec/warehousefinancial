package com.wfms.webapp.jxc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wfms.common.util.*;
import com.wfms.service.jxc.query.IQueryService;


@Controller
@Lazy(true)
@RequestMapping("/jxc/QueryAction.do")
public class QueryAction {
	@Autowired
	private IQueryService queryService;

	/**
	 * 供应商或经销商统计
	 */
	@RequestMapping(params = "method=getInitCustom")
	public String getInitCustom(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String ftime = request.getParameter("ftime");
		String etime = request.getParameter("etime");
		String type = request.getParameter("tp");
		String khid = "";
		if(request.getParameter("kh") != null && !request.getParameter("kh").equals("")){
			khid = new String(request.getParameter("kh").getBytes("iso-8859-1"), "UTF-8");
		}
		if(type == null){
			type = "0";
		}

		ftime = this.getDate(ftime, etime)[0];
		etime = this.getDate(ftime, etime)[1];
		
		request.setAttribute("stime", ftime);
		request.setAttribute("etime", etime);
		request.setAttribute("type", type);
		request.setAttribute("khid", khid==null?"":khid);
		return "/jxc/query/query_initcustom";
	}
	
	/**
	 * 供应商或经销商统计
	 */
	@RequestMapping(params = "method=getCustomers")
	public String getCustomers(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String type = request.getParameter("type");
		String ftime = request.getParameter("ftime");
		String etime = request.getParameter("etime");
		String custmc = "";
		if(request.getParameter("kh") != null && !request.getParameter("kh").equals("")){
			custmc = new String(request.getParameter("kh").getBytes("iso-8859-1"), "UTF-8");
		}
		
		if(type == null){
			type = "0";
		}
		ftime = this.getDate(ftime, etime)[0];
		etime = this.getDate(ftime, etime)[1];
		
		List list = queryService.getCustomers(type, custmc, ftime, etime);
		
		if(type.equals("0")){
			String[] title = new String[]{"产品ID","商品供应商","赠品供应商","产品类型","类型代码","产品名称","产品代码","产品规格","产品单位","生产厂家","赠品入库数量","赠品现存","商品入库数量","商品入库价格","商品库存","现存商品价格"};
			request.setAttribute("title", title);
		}else{
			String[] title = new String[]{"产品ID","商品经销商","赠品经销商","产品类型","类型代码","产品名称","产品代码","产品规格","产品单位","生产厂家","赠品出库数量","赠品现存","商品出库数量","商品出库价格","商品库存","现存商品价格"};
			request.setAttribute("title", title);
		}
		request.setAttribute("list", list);
		request.setAttribute("stime", ftime);
		request.setAttribute("etime", etime);
		request.setAttribute("type", type.equals("0") ? "进货" : "销售");
		request.setAttribute("khid", custmc==null?"":custmc);
		return "/jxc/query/query_custom";
	}

	/**
	 * 产品统计查询
	 */
	@RequestMapping(params = "method=getInitProducts")
	public String getInitProducts(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String ftime = request.getParameter("ftime");
		String etime = request.getParameter("etime");
		String cpid = request.getParameter("cpid");

		ftime = this.getDate(ftime, etime)[0];
		etime = this.getDate(ftime, etime)[1];
		
		request.setAttribute("stime", ftime);
		request.setAttribute("etime", etime);
		request.setAttribute("cpid", cpid == null ? "" : cpid);
		return "/jxc/query/query_initprod";
	}

	/**
	 * 产品统计查询
	 */
	@RequestMapping(params = "method=getProducts")
	public String getProducts(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String cpid = request.getParameter("cpid");
		String ftime = request.getParameter("ftime");
		String etime = request.getParameter("etime");

		ftime = this.getDate(ftime, etime)[0];
		etime = this.getDate(ftime, etime)[1];
		List list = queryService.getProducts(cpid, ftime, etime);
		
		String[] title = new String[]{"产品ID","产品类型","类型代码","产品名称","产品代码","产品规格","产品单位","生产厂家","赠品入库数量","赠品出库数量","赠品现存","商品入库数量","商品出库数量","商品库存"};
		request.setAttribute("list", list);
		request.setAttribute("stime", ftime);
		request.setAttribute("etime", etime);
		request.setAttribute("title", title);
		return "/jxc/query/query_prod";
	}

	/**
	 * 月表报
	 */
	@RequestMapping(params = "method=getInitMon")
	public String getInitMon(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String ftime = request.getParameter("ftime");
		String etime = request.getParameter("etime");

		ftime = this.getDate(ftime, etime)[0];
		etime = this.getDate(ftime, etime)[1];
		
		request.setAttribute("stime", ftime);
		request.setAttribute("etime", etime);
		return "/jxc/query/query_initmonth";
	}

	/**
	 * 月表报
	 */
	@RequestMapping(params = "method=getMon")
	public String getMon(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String ftime = request.getParameter("ftime");
		String etime = request.getParameter("etime");

		ftime = this.getDate(ftime, etime)[0];
		etime = this.getDate(ftime, etime)[1];
		
		List list = queryService.getMonth(ftime, etime);
		
		request.setAttribute("list", list);
		request.setAttribute("stime", ftime);
		request.setAttribute("etime", etime);
		return "/jxc/query/query_month";
	}

	/**
	 * 员工销售报表
	 */
	@RequestMapping(params = "method=getInitXs")
	public String getInitXs(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String ftime = request.getParameter("ftime");
		String etime = request.getParameter("etime");
		String xs = "";
		if(request.getParameter("xs") != null && !request.getParameter("xs").equals("")){
			xs = new String(request.getParameter("xs").getBytes("iso-8859-1"), "UTF-8");
		}

		ftime = this.getDate(ftime, etime)[0];
		etime = this.getDate(ftime, etime)[1];
		
		request.setAttribute("stime", ftime);
		request.setAttribute("etime", etime);
		request.setAttribute("xs", xs);
		return "/jxc/query/query_initxs";
	}

	/**
	 * 员工销售报表
	 */
	@RequestMapping(params = "method=getXs")
	public String getXs(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String ftime = request.getParameter("ftime");
		String etime = request.getParameter("etime");
		String xs = "";
		if(request.getParameter("xs") != null && !request.getParameter("xs").equals("")){
			xs = new String(request.getParameter("xs").getBytes("iso-8859-1"), "UTF-8");
		}

		ftime = this.getDate(ftime, etime)[0];
		etime = this.getDate(ftime, etime)[1];
		
		List list = queryService.getXs(ftime, etime, xs);
		
		request.setAttribute("list", list);
		request.setAttribute("stime", ftime);
		request.setAttribute("etime", etime);
		request.setAttribute("xs", xs);
		return "/jxc/query/query_xs";
	}
	
	private String[] getDate(String stime, String etime){
		String[] d = new String[]{"2011-01-01","2011-01-31"};
		try{
			if((stime == null || stime.equals("")) && (etime == null || etime.equals(""))){
				d[0] = DateUtil.getCurrentDateString().substring(0,7)+"-01";
				d[1] = DateUtil.getCurrentDateString().substring(0,7)+"-31";
			}else if(stime == null || stime.equals("")){
				d[0] = DateUtil.getCurrentDateString().substring(0,7)+"-01";
			}else if(etime == null || etime.equals("")){
				d[1] = DateUtil.getCurrentDateString().substring(0,7)+"-31";
			}else{
				d[0] = stime;
				d[1] = etime;
			}
		}catch(Exception ex){}
		return d;
	}
}
