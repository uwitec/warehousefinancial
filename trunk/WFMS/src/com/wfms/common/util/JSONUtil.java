package com.wfms.common.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.PropertyFilter;

import com.wfms.common.orm.Page;

public class JSONUtil {

	private static String dateFormat = "yyyy-M-d";

	/**
	 * 必须的
	 * 
	 * @param fromObject
	 * @param required
	 * @return
	 */
	public static JSONObject requiredObject(Object fromObject,
			Class<?> listType, String format, final String... required) {
		JsonConfig config = requiredConfig(format, listType, required);
		return JSONObject.fromObject(fromObject, config);
	}

	public static JSONObject requiredObject(Object fromObject,
			Class<?> listType, final String... required) {
		JsonConfig config = requiredConfig(dateFormat, listType, required);
		return JSONObject.fromObject(fromObject, config);
	}

	public static JSONObject requiredObject(Object fromObject,
			final String... required) {
		return requiredObject(fromObject, null, null, required);
	}

	public static JSONArray requiredArray(Object fromArray, String format,
			Class<?> listType, final String... required) {
		JsonConfig config = requiredConfig(format, listType, required);
		return JSONArray.fromObject(fromArray, config);
	}

	public static JSONArray requiredArray(Object fromArray,
			final String... required) {
		return requiredArray(fromArray, null, null, required);
	}

	/**
	 * 过滤的
	 * 
	 * @param fromObject
	 * @param required
	 * @return
	 */

	public static JSONObject filterObject(Object fromObject, String format,
			final String... filter) {
		JsonConfig config = filterConfig(format, filter);
		return JSONObject.fromObject(fromObject, config);
	}

	public static JSONObject filterObject(Object fromObject,
			final String... filter) {
		return filterObject(fromObject, null, filter);
	}

	public static JSONArray filterArray(Object fromArray, String format,
			final String... filter) {
		JsonConfig config = filterConfig(format, filter);
		return JSONArray.fromObject(fromArray, config);
	}

	public static JSONArray filterArray(Object fromArray,
			final String... filter) {
		return filterArray(fromArray, null, filter);
	}

	/**
	 * 格式化Page指定属性值
	 * 
	 * @param page
	 * @param required
	 * @return
	 */
	public static JSONObject requiredPage(Page page, Class<?> listType,
			String... required) {
		int orginalLength = required != null ? required.length : 0;
		String[] destRequired = null;
		if (orginalLength != 0) {
			int destLength = orginalLength + 4;
			destRequired = new String[destLength];
			destRequired[destLength - 1] = "results";
			destRequired[destLength - 2] = "total";
			destRequired[destLength - 3] = "limit";
			destRequired[destLength - 4] = "start";
			System.arraycopy(required, 0, destRequired, 0, orginalLength);
			required = destRequired;
		}
		return requiredObject(page, listType, required);
	}

	/**
	 * 过滤Page指定属性
	 * 
	 * @param page
	 * @param required
	 * @return
	 */
	public static JSONObject filterPage(Page page, String... required) {
		return requiredObject(page, required);
	}

	private static JsonConfig requiredConfig(String format,
			final Class<?> listType, String... required) {
		if(required == null)
		{
			required = new String[0];
		}
		final Map<String, String> requiredMap = new HashMap<String, String>();
			for (String req : required) {
				String[] reqArray = req.split("\\.");
				int reqLen = reqArray.length;
				if (reqArray.length > 1) {
					requiredMap.put(reqArray[reqLen - 2], reqArray[reqLen - 1]);
				}
			}
		JsonConfig config = baseFilterConfig();
		final List<String> requiredList = Arrays.asList(required);
		if (required != null && required.length != 0) {
			config.setJsonPropertyFilter(new PropertyFilter() {
				public boolean apply(Object source, String name, Object value) {
					String className = source.getClass().getSimpleName()
							.toLowerCase();
					String fullName = className + "." + name;
					boolean ignore = true;
					if (ReflectionUtils.getFieldValue(source, name) == null
							|| (!ReflectionUtils.getFieldValue(source, name)
									.getClass().isPrimitive() && requiredMap
									.containsKey(ReflectionUtils
											.getFieldValue(source, name)
											.getClass().getSimpleName()
											.toLowerCase()))) {
						return false;
					}
					for (int i = 0; i < requiredList.size(); i++) {
						String req = requiredList.get(i);
						if (req == null) {
							continue;
						}
						String[] parry = req.split("\\.");
						if (parry.length == 1) {
							if (req.equals(name)) {
								ignore = false;
								if (source.getClass() != listType) {
									requiredList.set(i, null);
								}
								break;
							}
						} else {
							if (parry.length > 2) {
								req = parry[parry.length - 2] + "."
										+ parry[parry.length - 1];
							}
							if (req.equals(fullName)) {
								ignore = false;
								if (source.getClass() != listType) {
									requiredList.set(i, null);
								}
								break;
							}
						}
					}
					return ignore;
				}
			});
		}
		return config;
	}

	private static JsonConfig filterConfig(String format, String... filter) {
		JsonConfig config = new JsonConfig();
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		config.setExcludes(new String[] { "handler", "hibernateLazyInitializer" });
		if (format != null && !"".equals(format)) {
			dateFormat = format;
		}
		config.registerJsonValueProcessor(java.util.Date.class,
				new JsonValueProcessorImpl(dateFormat));
		config.registerJsonValueProcessor(java.sql.Date.class,
				new JsonValueProcessorImpl(dateFormat));
		if (filter != null && filter.length != 0) {
			config.setExcludes(filter);
		}
		return config;
	}

	public static JsonConfig baseFilterConfig(String... format) {
		JsonConfig config = new JsonConfig();
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		config.setExcludes(new String[] { "handler", "hibernateLazyInitializer" });
		if (format != null && format.length != 0 && !"".equals(format[0])) {
			config.registerJsonValueProcessor(java.util.Date.class,
					new JsonValueProcessorImpl(format[0]));
			config.registerJsonValueProcessor(java.sql.Date.class,
					new JsonValueProcessorImpl(format[0]));
		}
		else
		{
			config.registerJsonValueProcessor(java.util.Date.class,
					new JsonValueProcessorImpl(dateFormat));
			config.registerJsonValueProcessor(java.sql.Date.class,
					new JsonValueProcessorImpl(dateFormat));
		}
		
		return config;
	}
}
