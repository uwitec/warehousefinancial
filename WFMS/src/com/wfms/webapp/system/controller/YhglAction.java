package com.wfms.webapp.system.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import xuner.web.mvc.MvcUtil;

import com.wfms.common.entity.AjaxMsg;
import com.wfms.common.entity.OtherTreeEntity;
import com.wfms.common.entity.Page;
import com.wfms.common.entity.TreeEntity;
import com.wfms.common.util.JSONUtil;
import com.wfms.common.util.QxUtil;
import com.wfms.common.util.StringUtil;
import com.wfms.common.util.TreeUtil;
import com.wfms.constant.SystemConstant;
import com.wfms.dto.system.GnmkDto;
import com.wfms.dto.system.YhDto;
import com.wfms.model.system.MemberGenInfo;
import com.wfms.model.system.MemberPart;
import com.wfms.model.system.RoleMember;
import com.wfms.model.system.XtszEntity;
import com.wfms.service.system.IGnmkService;
import com.wfms.service.system.IXtszglService;
import com.wfms.service.system.IYhglService;

@Controller
@Lazy(true)
@RequestMapping("/system/yhglAction.do")
public class YhglAction{
	
	@Autowired
	private IYhglService yhglService;
	@Autowired
	private IGnmkService gnmkService;
	@Autowired
	private IXtszglService xtszglService;
	
	@RequestMapping(params="method=yhdl")
	public ModelAndView yhdl(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String yhm = request.getParameter("yhm");
		String mm = request.getParameter("mm");
		String yhz = request.getParameter("yhz");
		String id = yhglService.yhdl(yhm, mm,yhz);
		AjaxMsg msg = new AjaxMsg();
		if("-2".equals(id)){
			msg.setSuccess(false);
			msg.setMessage("登录失败,请您为系统注册！联系电话：15310825488");
		}else if("-1".equals(id)){
			msg.setSuccess(false);
			msg.setMessage("登录失败,用户名或者密码错误!");
		}else if("0".equals(id)){
			msg.setSuccess(false);
			msg.setMessage("登录失败,该用户已被禁用,请联系系统管理员!");
		}else{
			request.getSession().setAttribute(SystemConstant.YHDL_USERID, id);
			request.getSession().setAttribute(SystemConstant.YHDL_YHM, yhm);
			request.getSession().setAttribute(SystemConstant.YHDL_MM, mm);
			request.getSession().setAttribute(SystemConstant.YHDL_USERGROUP, yhz);
			msg.setSuccess(true);
		}
		response.setCharacterEncoding("UTF-8");
		JSONObject obj = JSONUtil.formatObject(msg);
		return MvcUtil.jsonObjectModelAndView(obj);
	}
	
	@RequestMapping(params="method=yhInit")
	public ModelAndView yhInit(HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		Object obj = request.getSession().getAttribute(SystemConstant.YHDL_USERID);
		Object yhz = request.getSession().getAttribute(SystemConstant.YHDL_USERGROUP);
		AjaxMsg msg = new AjaxMsg();
		if(obj == null || "".equals(obj)){
			msg.setSuccess(false);
			msg.setMessage("系统初始化失败,请重新登录!");
		}else{
			String yhId = String.valueOf(obj);
			//获取功能模块
			GnmkDto gnmkDto= yhglService.updYhdl(yhId,String.valueOf(yhz));
			if(gnmkDto == null){
				msg.setSuccess(false);
				msg.setMessage("系统初始化失败,请重新登录!");	
			}else{
				//设置当前学年
				XtszEntity x  = xtszglService.getXtszByKey("DQXN");
				request.getSession().setAttribute("DQXN", x.getValue());
				//设置当前学期
				XtszEntity xs  = xtszglService.getXtszByKey("DQXQ");
				request.getSession().setAttribute("DQXQ", xs.getValue());
				
				//老师用户组登录设置用户信息
				if(SystemConstant.YHZ_TEACHER.equals(yhz))
				{
					YhDto dto = yhglService.getYh(yhId);
					request.getSession().setAttribute("yh",dto.getYh());
					//设置用户姓名
					request.getSession().setAttribute(SystemConstant.YHDL_YHXM, dto.getYh().getUsername());
					//设置人员姓名
					if(dto.getYh().getRy() != null && dto.getYh().getRy().getId() != 0){
						request.getSession().setAttribute("ryId", dto.getYh().getRy().getId());
					}else{
						request.getSession().setAttribute("ryId", -1);
					}
					//学生用户登录
				}
				//功能模块列表放入session
				request.getSession().setAttribute("gnmkList", gnmkDto.getGnmkList());
				msg.setSuccess(true);
			}
		}
		
		response.setCharacterEncoding("UTF-8");
		JSONObject js = JSONUtil.formatObject(msg);
		return MvcUtil.jsonObjectModelAndView(js);
	}

	//用户界面初始化
	@RequestMapping(params="method=initYhgl")
	public String initYhgl(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		Object obj = request.getSession().getAttribute("gnmkList");
		if(obj == null){
			return "error";
		}
		int qxlx = QxUtil.getQx(id, obj);
		request.setAttribute("qxlx", qxlx);
		request.setAttribute("mkid", id);
		return "/system/yhgl_list";
	}
	
	//查询所有用户
	@RequestMapping(params="method=getAllYh")
	public ModelAndView getAllYh(HttpServletRequest request, 
			HttpServletResponse response,Page page)
			throws Exception {
		page.formatCondList();
		YhDto dto = yhglService.getAllYh(page);
		JSONObject obj = JSONUtil.formatObject(dto.getPage(), new String[]{
			"roleParts","roleRights","parts","company","parent","parts",
			"memberRights","members","memberParts","roleMembers","ry","roles","chargeDepart"});
		response.setCharacterEncoding("utf-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}
	
	//添加用户
	@RequestMapping(params="method=addYh")
	public ModelAndView addYh(HttpServletRequest request,
			HttpServletResponse response,
			MemberGenInfo yh)
	throws Exception {
		//默认用户状态启用
		yh.setDeniedlogin("false");
		String row = yhglService.addYh(yh);
		AjaxMsg msg = new AjaxMsg();
		if(!StringUtil.isNullOrEmpty(row) ){
			msg.setSuccess(true);
		}else{
			msg.setSuccess(false);
			msg.setMessage("添加用户失败,服务器繁忙,请稍后重试!");
		}
		response.setCharacterEncoding("utf-8");
		JSONObject obj = JSONUtil.formatObject(msg);
		return MvcUtil.jsonObjectModelAndView(obj);
	}
	
	//删除用户
	@RequestMapping(params="method=delYh")
	public ModelAndView delYh(HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String strId = request.getParameter("id");
		AjaxMsg msg = new AjaxMsg();
		if(StringUtil.isNullOrEmpty(strId)){
			msg.setSuccess(false);
			msg.setMessage("删除失败,数据有误!");
		}else{
			int row = yhglService.delYh(strId);
			if(row == -1 ){
				msg.setSuccess(false);
				msg.setMessage("删除失败,该用户下还拥有权限控制,请先删除权限控制!");
			}else if(row == -2){
				msg.setSuccess(false);
				msg.setMessage("删除失败,该用户还对应有角色,请先删除用户角色!");
			}else if(row == -3){
				msg.setSuccess(false);
				msg.setMessage("删除失败,该用户还有职务信息,请先删除用户职务!");
			}else if(row == 0){
				msg.setSuccess(false);
				msg.setMessage("删除失败,服务器繁忙,请稍后重试!");
			}else{
				msg.setSuccess(true);
			}
		}
		response.setCharacterEncoding("utf-8");
		JSONObject obj = JSONUtil.formatObject(msg);
		return MvcUtil.jsonObjectModelAndView(obj);
	}
	
	//批量修改用户
	@RequestMapping(params="method=updYh")
	public ModelAndView updYh(HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String data = request.getParameter("data");
		data = java.net.URLDecoder.decode(data,"UTF-8"); 
		JSONArray ja = JSONArray.fromObject(data);
		List<MemberGenInfo> list = (List<MemberGenInfo>) JSONArray.toCollection(ja,MemberGenInfo.class);
		//List<MemberGenInfo> list = BeanConvort.boundToArray(MemberGenInfo.class, ja);
		int row = yhglService.updYh(list);
		AjaxMsg msg = new AjaxMsg();
		
		if(row < 0){
			msg.setSuccess(false);
			msg.setMessage("更新失败,服务器繁忙,请稍后重试!");
		}else{
			msg.setSuccess(true);
		}
		response.setCharacterEncoding("utf-8");
		JSONObject obj = JSONUtil.formatObject(msg);
		return MvcUtil.jsonObjectModelAndView(obj);
	}
	
	//跳转用户授权页面
	@RequestMapping(params="method=yhsq")
	public String yhsq(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String strId = request.getParameter("id");
		YhDto dto = yhglService.getYh(strId);
		request.setAttribute("yh", dto.getYh());
		return "/system/yhgl_sq";
	}
	
	//读取用户所有角色信息
	@RequestMapping(params="method=getYhjs")
	@ResponseBody
	public List<MemberPart> getYhjs(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="id")String memid)
			throws Exception {
		YhDto dto = yhglService.getYhjs(memid);
		return dto.getYhjsList();
	}
	
	//读取用户所有职务信息
	@RequestMapping(params="method=getYhzw")
	@ResponseBody
	public List<RoleMember> getYhzw(HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String strId = request.getParameter("id");
		YhDto dto = yhglService.getYhzw(strId);
		return dto.getYhzwList();
	}
	
	//读取用户所有模块权限
	@RequestMapping(params="method=getYhGnmk")
	public ModelAndView getYhGnmk(HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String strId = request.getParameter("id");
		GnmkDto dto = yhglService.getYhGnmk(strId);
		JSONArray jay = JSONUtil.formatArray(dto.getGnmkList(), new String[]{"qx"});
		response.setCharacterEncoding("utf-8");
		return MvcUtil.jsonArrayModelAndView(jay);
	}
	
	//读取用户已有权限
	@RequestMapping(params="method=getYhyyGnmk")
	public ModelAndView getYhyyGnmk(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String contextPath = request.getContextPath();
		String strId = request.getParameter("id");
		GnmkDto dto = yhglService.getYhGnmk(strId);
		GnmkDto rsDto = gnmkService.getAllGnmk(dto.getGnmkList());
		List<TreeEntity> treeList = TreeUtil.initTree(contextPath,rsDto.getGnmkList());
		JSONArray jay = JSONUtil.formatArray(treeList);
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonArrayModelAndView(jay);
	}
	
	//读取用户未有权限
	@RequestMapping(params="method=getYhwyGnmk")
	public ModelAndView getYhwyGnmk(HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String contextPath = request.getContextPath();
		String strId = request.getParameter("id");
		GnmkDto dto = yhglService.getYhGnmk(strId);
		GnmkDto rsDto = gnmkService.getYhwyGnmk(dto.getGnmkList());
		List<TreeEntity> treeList = TreeUtil.initTree(contextPath,rsDto.getGnmkList());
		JSONArray jay = JSONUtil.formatArray(treeList);
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonArrayModelAndView(jay);
	}
	
	//读取职务已有用户
	@RequestMapping(params="method=getZwyyYh")
	public ModelAndView getZwyyYh(@RequestParam(value="id")String rolid)
			throws Exception {
		if(StringUtil.isNullOrEmpty(rolid)){
			return null;
		}
		YhDto dto = yhglService.getYhForZw(rolid);
		JSONArray jay = JSONUtil.formatArray(dto.getYhList(),new String[]{"roleParts","roleRights","parts","company","parent","parts",
			"memberRights","members","memberParts","roleMembers","ry","roles","yhzwList","yhjsList","yh","chargeDepart"});
		return MvcUtil.jsonArrayModelAndView(jay);
	}
	
	//读取职务未有用户
	@RequestMapping(params="method=getZwwyYh")
	public ModelAndView getZwwyYh(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String strId = request.getParameter("id");
		if(StringUtil.isNullOrEmpty(strId)){
			return null;
		}
		YhDto dto = yhglService.getYhForZwNot(strId);
		JSONArray jay = JSONUtil.formatArray(dto.getYhList(),new String[]{"roleParts","roleRights","parts","company","parent","parts",
			"memberRights","members","memberParts","roleMembers","ry","role","yhzwList","yhjsList","yh","chargeDepart"});
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonArrayModelAndView(jay);
	}
	
	@RequestMapping(params="method=initDesktop")
	public String initDesktop(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String userId = (String)request.getSession().getAttribute("userId");
		if(StringUtil.isNullOrEmpty(userId)){
			return "error";
		}else{
			YhDto dto = yhglService.getYh(userId);
			request.setAttribute("yh", dto.getYh());
		}
		return "desktop";
	}
	
	@RequestMapping(params="method=userLoginOut")
	public ModelAndView userLoginOut(HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		request.getSession().invalidate();
		//response.getWriter().print("s");
		//response.sendRedirect("../login.html");
		//response.getOutputStream().flush();
		//response.getOutputStream().close();
		return new ModelAndView("redirect:/login.html");
	}
	
	@RequestMapping(params="method=getYhObjectInSession")
	public ModelAndView getYhObjectInSession(HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		Map<Object, Object> obj = new HashMap<Object, Object>();
		HttpSession session = request.getSession();
		obj.put("yhm", session.getAttribute(SystemConstant.YHDL_YHM) == null ? "" : session.getAttribute(SystemConstant.YHDL_YHM));
		obj.put("mm", session.getAttribute(SystemConstant.YHDL_MM) == null ? "" : session.getAttribute(SystemConstant.YHDL_MM));
		obj.put("yhz", session.getAttribute(SystemConstant.YHDL_USERGROUP) == null ? "" : session.getAttribute(SystemConstant.YHDL_USERGROUP));
		JSONObject jo = JSONUtil.formatObject(obj);
		response.setCharacterEncoding("utf-8");
		return MvcUtil.jsonObjectModelAndView(jo);
	}
	
	/**
	 * 用户树
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params="method=getYhTree")
	public ModelAndView getYhTree(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("node");
		JSONArray obj = null;
		if("0".equals(id) || "".equals(id)){
			YhDto dto = yhglService.getAllYh();
			List<OtherTreeEntity> list  = TreeUtil.getTreeByYh(dto.getYhList());
			obj = JSONUtil.formatArray(list);
		}
		response.setCharacterEncoding("utf-8");
		return MvcUtil.jsonArrayModelAndView(obj);
	}
	
	@RequestMapping(params="method=getYhsTree")
	public ModelAndView getYhsTree(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("node");
		JSONArray obj = null;
		if("0".equals(id) || "".equals(id)){
			YhDto dto = yhglService.getAllYh();
			List<OtherTreeEntity> list  = TreeUtil.getTreeByYhs(dto.getYhList());
			obj = JSONUtil.formatArray(list);
		}
		response.setCharacterEncoding("utf-8");
		return MvcUtil.jsonArrayModelAndView(obj);
	}

	/**
	 * 教师个人密码维护
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params="method=initYhmmwh")
	public String initYhmmwh(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		Object obj = request.getSession().getAttribute("gnmkList");
		if(obj == null){
			return "error";
		}
		int qxlx = QxUtil.getQx(id, obj);
		request.setAttribute("qxlx", qxlx);
		request.setAttribute("mkid", id);
		return "/system/tystpt_xtgl_mmwh";
	}

	/**
	 * 学生密码维护
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params="method=initXsmmwh")
	public String initXsmmwh(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		Object obj = request.getSession().getAttribute("gnmkList");
		if(obj == null){
			return "error";
		}
		int qxlx = QxUtil.getQx(id, obj);
		request.setAttribute("qxlx", qxlx);
		request.setAttribute("mkid", id);
		return "/xsfwck/grxx/xsfwck_grxx_mmwh";
	}
	
	/**
	 * 学生密码修改
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params="method=batchInserUpdateXsmm")
	public ModelAndView batchInserUpdateXsmm(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)throws Exception {
		AjaxMsg msg = new AjaxMsg();
		
		String oldmm = request.getParameter("oldmm");
		String newmm = request.getParameter("newmm");
		String lastmm = request.getParameter("lastmm");
		String yhz = (String)session.getAttribute(SystemConstant.YHDL_USERGROUP);
		String dlm = (String)session.getAttribute(SystemConstant.YHDL_YHM);
		
		if(!newmm.equals(lastmm)){
			msg.setSuccess(false);
			msg.setMessage("新密码和确认密码必须一致!");
		}else{
			msg.setSuccess(true);
			if(yhz.equals(SystemConstant.YHZ_TEACHER)){
				msg.setMessage(yhglService.updateYhmm(dlm, oldmm, newmm));
			}
		}
		
		response.setCharacterEncoding("utf-8");
		JSONObject obj = JSONUtil.formatObject(msg);
		return MvcUtil.jsonObjectModelAndView(obj);
	}
	
	@RequestMapping(params="method=addYhqx")
	@ResponseBody
	public AjaxMsg addMemberRight(@RequestParam(value="id",required=true,defaultValue="")String strId
			,@RequestParam(value="gnmkId",required=true,defaultValue="")String strGnmkId)
	throws Exception {
		AjaxMsg msg = new AjaxMsg();
		if (StringUtil.isNullOrEmpty(strId) || StringUtil.isNullOrEmpty(strGnmkId)) {
			msg.setSuccess(false);
			msg.setMessage("添加失败,数据有误!");
		} else {
			if(yhglService.addYhqx(strId, strGnmkId))
			{
				msg.setSuccess(true);
			}
			else {
				msg.setSuccess(false);
				msg.setMessage("添加失败,服务器繁忙,请稍后重试!");
			}
		}
		return msg;
	}
	
	@RequestMapping(params="method=delYhqx")
	@ResponseBody
	public AjaxMsg delMemberRight(@RequestParam(value="id",required=true,defaultValue="")String memid,
			@RequestParam(value="gnmkId")String gnmkIds)
	throws Exception {
		AjaxMsg msg = new AjaxMsg();
		if (StringUtil.isNullOrEmpty(memid) || StringUtil.isNullOrEmpty(gnmkIds)) {
			msg.setSuccess(false);
			msg.setMessage("删除失败,数据有误!");
		} else {
			if(yhglService.delYhqx(memid, gnmkIds)){
				msg.setSuccess(true);
			}
			else {
				msg.setSuccess(false);
				msg.setMessage("删除失败,服务器繁忙,请稍后重试!");
			}
		}
		return msg;
	}

	/**
	 * 学生密码维护
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params="method=getDlyhxx")
	public String getDlyhxx(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception{
		
		String userid = "";
		String path = request.getContextPath();
		String yhz = (String)session.getAttribute(SystemConstant.YHDL_USERGROUP);
		String[] ps = new String[6];
		boolean flag = false;
		
		File file = new File("userInfo.htm");
		path = file.getCanonicalPath();
		path = path.substring(0,path.length()-16);
		path += "webapps"+request.getContextPath()+"/imgs/";
		if(SystemConstant.YHZ_TEACHER.equalsIgnoreCase(yhz)){
			userid = (String)session.getAttribute(SystemConstant.YHDL_USERID);
			flag = yhglService.getYhzp(userid, path, "0");
			MemberGenInfo yh = (MemberGenInfo)session.getAttribute("yh");
			ps[1] = yh.getUsername();
			ps[2] = yh.getLoginid();
			ps[3] = yh.getEmail() == null ? "" : yh.getEmail();
			ps[4] = yh.getOfficephone() == null ? "" : yh.getOfficephone();
			ps[5] = yh.getPhone() == null ? "" : yh.getPhone();
		}else if(SystemConstant.YHZ_STUDENT.equals(yhz)){
			userid = (String)session.getAttribute(SystemConstant.YHDL_YHM);
			flag = yhglService.getYhzp(userid, path, "1");
			
			ps[1] = (String)session.getAttribute("userName");
			ps[2] = userid;
			ps[3] = "";
			ps[4] = "";
			ps[5] = (String)session.getAttribute("dhhm");
		}
		
		if(flag){
			ps[0] = "imgs/"+userid+".jpg";
		}else{
			ps[0] = "imgs/default-zp.jpg";
		}
		
		request.setAttribute("dlyh", ps);
		return "/userInfo";
	}
	
	@RequestMapping(params="method=updateJxcmac")
	public String updateJxcmac(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception{
		String name=request.getParameter("name");
		if(name == null || name.equals("")){
			request.setAttribute("restr", "注册失败！");
		}else{
			if(yhglService.updateJxcmac(name)){
				request.setAttribute("restr", "注册成功！");
			}else{
				request.setAttribute("restr", "注册失败！");
			}
		}
		return "/test";
	}
}
