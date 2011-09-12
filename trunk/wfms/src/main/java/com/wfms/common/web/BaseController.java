package com.wfms.common.web;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.dao.DataAccessException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.wfms.common.dao.BaseService;
import com.wfms.common.orm.BaseEntity;
import com.wfms.common.orm.Page;
import com.wfms.common.util.MvcUtil;
import com.wfms.common.util.ReflectionUtils;
import com.wfms.common.util.WebUtils;

/**
 * 
 * @author CYC
 * 
 * @param <T>
 */
public abstract class BaseController<T extends BaseEntity> {
	// @Resource
	private Validator validator;

	protected static final Logger logger = LoggerFactory
			.getLogger(BaseController.class);

	// 模块名称
	protected String moduleName;

	protected String format = "yyyy-MM-dd";

	protected Class<?> entityClass;

	protected String className;

	protected String entityName;

	protected T entity;

	protected String listView = null;

	protected String formView = null;

	protected String detailView = null;

	public static final String successView = "/common/success.jsp";

	protected BaseService<T> baseService;

	/**
	 * 注入Service
	 */
	public void setBaseService(BaseService<T> baseService) {
		this.baseService = baseService;
	}

	public BaseController() {
		className = ReflectionUtils.getSuperClassGenricType(getClass())
				.getName();
		entityName = StringUtils.substringAfterLast(className, ".");
		entityName = entityName.substring(0, 1).toLowerCase()
				+ entityName.substring(1, entityName.length());
		entityClass = ReflectionUtils.getSuperClassGenricType(super.getClass());
		listView = "list.jsp";
		formView = "form.jsp";
		detailView = "detail.jsp";
	}

	/**
	 * 获取实体服务类的实例
	 * 
	 * @return
	 */
	protected BaseService<T> getEntityService() {
		return baseService;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
		binder.registerCustomEditor(Integer.class, new CustomNumberEditor(
				Integer.class, true));
		binder.registerCustomEditor(Double.class, new CustomNumberEditor(
				Double.class, true));
		binder.registerCustomEditor(MultipartFile.class,
				new ByteArrayMultipartFileEditor());
	}

	/**
	 * 跳转到主页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("list.do")
	public ModelAndView doList(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView(listView);
		return mav;
	}

	/**
	 * 分页查询
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 */
	@RequestMapping("loadPage.do")
	public ModelAndView loadPage(HttpServletRequest request,
			HttpServletResponse response) {
		Page page = new Page();
		beforeLoadPage(request, page);
		WebUtils.setPageParameter(request, page);
		// 多级属性
		boolean multiLevel = isMultiLevelParam(request);
		if (multiLevel) {
			page = getEntityService().find(page);
		} else {
			page = getEntityService().fillPage(page);
		}
		ModelAndView mav = afterLoadPage(request, response, page);
		return mav;
	}

	protected String[] beforeLoadPage(HttpServletRequest request, Page page) {
		return null;
	};

	protected ModelAndView afterLoadPage(HttpServletRequest request,
			HttpServletResponse response, Page page) {
		ModelAndView mav = MvcUtil.jsonObjectModelAndView(page);
		return mav;
	};

	/**
	 * 跳转到新增页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("create.do")
	public ModelAndView doCreate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView(formView);
		onCreate(request, response, mav);
		return mav;
	}

	protected void onCreate(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mav) {
	};

	/**
	 * 跳转到修改页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("edit.do")
	public ModelAndView edit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView(formView);
		String id = request.getParameter("editId");
		if (StringUtils.isNotEmpty(id)) {
			entity = getEntityService().find(id);
			mav.addObject(entityName, entity);
		}

		onEdit(entity, mav, request);
		return mav;
	}

	protected void onEdit(T entity, ModelAndView mav, HttpServletRequest request) {
	};

	/**
	 * 跳转到详细信息查看页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("detail.do")
	public ModelAndView detail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView(detailView);
		String id = request.getParameter("detailId");
		if (StringUtils.isNotEmpty(id)) {
			entity = getEntityService().find(id);
			mav.addObject(entityName, entity);
		}

		onDetail(entity, mav, request);
		return mav;
	}

	protected void onDetail(T entity, ModelAndView mav,
			HttpServletRequest request) {
	}

	/**
	 * 新增或修改保存
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("save.do")
	public ModelAndView doSave(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject model = new JSONObject(2);
		DataAccessException e = null;
		String id = request.getParameter("id");
		if (StringUtils.isEmpty(id)) {
			entity = (T) ReflectionUtils.getSuperClassGenricType(getClass())
					.newInstance();
		} else {
			entity = getEntityService().find(id);
		}

		// 绑定前检查
		boolean check = beforeBindRequestEntity(request, entity, model);
		if (!check) {
			model.put("success", false);
			model.put("msg", "数据格式错误");
			return MvcUtil.jsonObjectModelAndView(model);
		}

		BindException errors = bindRequestEntity(request, entity);

		beforeSave(request, entity, errors, model);

		if (errors.hasErrors()) {
			logger.error(errors.getMessage());
			model.put("success", "false");
			model.put("msg", getMessageFromErrors(errors));
			return MvcUtil.jsonObjectModelAndView(model);
		}
		String operate = StringUtils.isEmpty(entity.getId()) ? "增加" : "修改";
		try {
			getEntityService().saveOrUpdate(entity);
			model.put("success", "true");
			model.put("msg", operate + moduleName + "成功!");
		} catch (DataAccessException dae) {
			model.put("msg", operate + moduleName + "失败!");
			e = dae;
		}
		afterSave(request, response, model, entity, e);
		return MvcUtil.jsonObjectModelAndView(model);
	}

	protected BindException bindRequestEntity(HttpServletRequest request,
			T entity) throws Exception {
		ServletRequestDataBinder binder = new ServletRequestDataBinder(entity);
		initBinder(binder);
		binder.bind(request);
		BindException errors = new BindException(binder.getBindingResult());
		if (validator != null) {
			validator.validate(entity, errors);
		}
		return errors;
	}

	protected boolean beforeBindRequestEntity(HttpServletRequest request,
			T entity, JSONObject model) {
		return true;
	}

	protected void beforeSave(HttpServletRequest request, T entity,
			BindException errors, JSONObject model) {
	}

	protected void afterSave(HttpServletRequest request,
			HttpServletResponse response, JSONObject model, T entity,
			DataAccessException dae) {
		// 此处可处理异常，并返回errorCode
	}

	/**
	 * 单条数据删除
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("remove.do")
	public ModelAndView doRemove(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject model = new JSONObject(2);
		DataAccessException ex = null;
		String id = request.getParameter("id");

		T t = getEntityService().find(id);

		beforeRemove(request, response, t);
		try {
			getEntityService().delete(t);
			model.put("success", "true");
			String msg = new StringBuilder().append("成功删除").append("1个")
					.append(moduleName).append(" id：").append(id).toString();
			model.put("msg", msg);
		} catch (DataAccessException dae) {
			ex = dae;
			ex.printStackTrace();
			model.put("msg", "删除" + moduleName + "失败");
		}
		afterRemove(request, response, model, ex);

		return MvcUtil.jsonObjectModelAndView(model);
	}

	/**
	 * 批量数据删除
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("batchRemove.do")
	public ModelAndView doBatchRemove(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject model = new JSONObject(2);
		DataAccessException ex = null;
		String[] ids = request.getParameterValues("ids");

		StringBuilder sb = null;
		int success = 0;
		if (ids != null) {
			try {
				success = getEntityService().delete(ids);
			} catch (DataAccessException dae) {
				ex = dae;
				ex.printStackTrace();
			}
			sb = new StringBuilder().append("成功删除").append(success).append("个")
					.append(moduleName).append(" ids：")
					.append(Arrays.toString(ids) + "<br/>")
					.append("其中失败数为:" + (ids.length - success));
			model.put("succes", "true");
			model.put("msg", sb.toString());
		} else {
			model.put("msg", "未选择" + moduleName);
		}

		afterRemove(request, response, model, ex);
		return MvcUtil.jsonObjectModelAndView(model);
	}

	protected void beforeRemove(HttpServletRequest request,
			HttpServletResponse response, T entity) {
	}

	protected void afterRemove(HttpServletRequest request,
			HttpServletResponse response, JSONObject model,
			DataAccessException dae) {
		// 此处可处理异常，并返回errorCode
	}

	/**
	 * 查询单条数据
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("load.do")
	public ModelAndView load(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "detailId", required = true) String id)
			throws Exception {
		entity = getEntityService().find(id);

		JSONObject model = new JSONObject(2);
		model.put("data", entity);
		model.put("success", true);
		ModelAndView mav = MvcUtil.jsonObjectModelAndView(model);
		onLoad(request, response, entity, mav);
		return mav;
	}

	protected void onLoad(HttpServletRequest request,
			HttpServletResponse response, T entity, ModelAndView mav) {
	}

	@SuppressWarnings("unchecked")
	private String getMessageFromErrors(BindException errors) {
		StringBuilder sb = new StringBuilder();
		sb.append("错误信息：");
		List<ObjectError> list = errors.getAllErrors();
		for (ObjectError error : list) {
			sb.append(error.getDefaultMessage()).append(";");
		}
		return sb.toString();
	}

	private boolean isMultiLevelParam(HttpServletRequest request) {
		boolean multiLevel = false;
		String paramData = request.getParameter("data");
		if (paramData != null && !"".equals(paramData)) {
			for (String param : paramData.split("&")) {
				if (param.indexOf(".") != -1) {
					multiLevel = true;
					break;
				}
			}
		}
		return multiLevel;
	}
}
