package com.wfms.common.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import com.wfms.common.entity.Invisible;
import com.wfms.common.entity.InvisibleFilter;
import com.wfms.common.entity.SimpleFieldProcessor;

public class JSONUtil {

	private static String DEFAULT_IGNORE_ANNOTATION = "ignore";

	/*private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(JSONUtil.class);
*/
	public static JSONObject formatObject(Object obj) {
		JSONObject jsObj = JSONObject.fromObject(obj);
		return jsObj;
	}

	public static JSONObject formatObject(Object obj, String fiterColumn) {
		JSONObject jsObj = JSONObject.fromObject(obj,
				columnsFilter(new String[] { fiterColumn }));
		return jsObj;
	}

	public static JSONObject formatObject(Object obj, String[] fiterColumns) {
		JSONObject jsObj = JSONObject.fromObject(obj,
				columnsFilter(fiterColumns));
		return jsObj;
	}

	public static JSONArray formatArray(Object list) {
		JSONArray jsAry = JSONArray.fromObject(list);
		return jsAry;
	}

	public static JSONArray formatArray(Object list, String fiterColumn) {
		JSONArray jsAry = JSONArray.fromObject(list,
				columnsFilter(new String[] { fiterColumn }));
		return jsAry;
	}

	public static JSONArray formatArray(Object list, String[] fiterColumns) {
		JSONArray jsAry = JSONArray.fromObject(list,
				columnsFilter(fiterColumns));
		return jsAry;
	}

	public static JSONObject formatObject(Object obj, boolean annotationFilter,
			String... filterAnnotations) {
		if (annotationFilter) {
			return JSONObject.fromObject(obj,
					annotationFilter(filterAnnotations));

		}
		return formatObject(obj,filterAnnotations);
	}

	public static JSONArray formatArray(Object list, boolean annotationFilter,
			String... filterAnnotaions) {
		if (annotationFilter) {
			return JSONArray.fromObject(list,
					annotationFilter(filterAnnotaions));

		}
		return formatArray(list);
	}

	public static JSONObject formatSimpleObject(Object obj,
			String... filterAttributes) {
		return JSONObject.fromObject(obj, simpleFilter(filterAttributes));
	}

	public static JSONArray formatSimpleArray(Object list,
			String... filterAttributes) {
		return JSONArray.fromObject(list, simpleFilter(filterAttributes));
	}

	private static JsonConfig columnsFilter(String[] params) {
		JsonConfig config = new JsonConfig();
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		if (params.length == 0) {
			return config;
		}
		config.setExcludes(params);
		return config;
	}

	private static JsonConfig propertyFilter(boolean ignorCollection,
			String[] filterProperties) {
		JsonConfig config = new JsonConfig();
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		if (ignorCollection && !StringUtil.isNullOrSizeZero(filterProperties)) {
			config.setJsonPropertyFilter(new SimpleFieldProcessor(true,
					filterProperties));
		} else if (ignorCollection
				&& StringUtil.isNullOrSizeZero(filterProperties)) {
			config.setJsonPropertyFilter(new SimpleFieldProcessor(true));
		} else if (!ignorCollection
				&& !StringUtil.isNullOrSizeZero(filterProperties)) {
			config.setJsonPropertyFilter(new SimpleFieldProcessor(false,
					filterProperties));
		}
		return config;
	}

	private static JsonConfig annotationFilter(String... filterValues) {
		JsonConfig config = new JsonConfig();
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		config.setExcludes(filterValues);
		config.addIgnoreFieldAnnotation(Invisible.class);
		if (StringUtil.isNullOrSizeZero(filterValues)) {
			config.setJsonPropertyFilter(new InvisibleFilter(
					DEFAULT_IGNORE_ANNOTATION));
		} else {
			config.setJsonPropertyFilter(new InvisibleFilter(filterValues));
		}
		return config;
	}

	private static JsonConfig simpleFilter(String... filterAttributes) {
		JsonConfig config = new JsonConfig();
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		config.setJsonPropertyFilter(new SimpleFieldProcessor(true,
				filterAttributes));
		return config;
	}

	public static List<ConditionBean> formatConditionList(JSONObject obj) {
		if (obj == null) {
			return new ArrayList<ConditionBean>();
		}
		List<ConditionBean> list = new ArrayList<ConditionBean>();
		for (Object key : obj.keySet()) {
			if (key != null && !"".equals(String.valueOf(key))) {
				Object value = obj.get(key);
				if (value != null && !"".equals(String.valueOf(value))) {
					list.add(new ConditionBean(String.valueOf(key), String
							.valueOf(value), Rule.ALIKE));
				}
			}
		}
		return list;
	}

	public static List<ConditionBean> formatConditionList(JSONObject obj,
			Map<String, Rule> RuleMap) {
		if (obj == null) {
			return new ArrayList<ConditionBean>();
		}
		List<ConditionBean> list = new ArrayList<ConditionBean>();
		for (Object key : obj.keySet()) {
			if (key != null && !"".equals(String.valueOf(key))) {
				Object value = obj.get(key);
				if (value != null && !"".equals(String.valueOf(value))) {
					Rule rule = RuleMap.get(key);
					if (rule != null) {
						list.add(new ConditionBean(String.valueOf(key), String
								.valueOf(value), rule));
					} else {
						list.add(new ConditionBean(String.valueOf(key), String
								.valueOf(value), Rule.ALIKE));
					}
				}
			}
		}
		return list;
	}

	public static String object2json(Object obj) {
		StringBuilder json = new StringBuilder();
		if (obj == null) {
			json.append("\"\"");
		} else if (obj instanceof String || obj instanceof Integer
				|| obj instanceof Float || obj instanceof Boolean
				|| obj instanceof Short || obj instanceof Double
				|| obj instanceof Long || obj instanceof BigDecimal
				|| obj instanceof BigInteger || obj instanceof Byte) {
			json.append("\"").append(string2json(obj.toString())).append("\"");
		} else if (obj instanceof Object[]) {
			json.append(array2json((Object[]) obj));
		} else if (obj instanceof List) {
			json.append(list2json((List<?>) obj));
		} else if (obj instanceof Map) {
			json.append(map2json((Map<?, ?>) obj));
		} else if (obj instanceof Set) {
			json.append(set2json((Set<?>) obj));
		} else {
			json.append(bean2json(obj));
		}
		return json.toString();
	}

	public static String bean2json(Object bean) {
		StringBuilder json = new StringBuilder();
		json.append("{");
		PropertyDescriptor[] props = null;
		try {
			props = Introspector.getBeanInfo(bean.getClass(), Object.class)
					.getPropertyDescriptors();
		} catch (IntrospectionException e) {
		}
		if (props != null) {
			for (int i = 0; i < props.length; i++) {
				try {
					String name = object2json(props[i].getName());
					String value = object2json(props[i].getReadMethod().invoke(
							bean));
					json.append(name);
					json.append(":");
					json.append(value);
					json.append(",");
				} catch (Exception e) {
				}
			}
			json.setCharAt(json.length() - 1, '}');
		} else {
			json.append("}");
		}
		return json.toString();
	}

	public static String list2json(List<?> list) {
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (list != null && list.size() > 0) {
			for (Object obj : list) {
				json.append(object2json(obj));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}

	public static String array2json(Object[] array) {
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (array != null && array.length > 0) {
			for (Object obj : array) {
				json.append(object2json(obj));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}

	public static String map2json(Map<?, ?> map) {
		StringBuilder json = new StringBuilder();
		json.append("{");
		if (map != null && map.size() > 0) {
			for (Object key : map.keySet()) {
				json.append(object2json(key));
				json.append(":");
				json.append(object2json(map.get(key)));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, '}');
		} else {
			json.append("}");
		}
		return json.toString();
	}

	public static String set2json(Set<?> set) {
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (set != null && set.size() > 0) {
			for (Object obj : set) {
				json.append(object2json(obj));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}

	public static String string2json(String s) {
		if (s == null)
			return "";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			switch (ch) {
			case '"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			case '/':
				sb.append("\\/");
				break;
			default:
				if (ch >= '\u0000' && ch <= '\u001F') {
					String ss = Integer.toHexString(ch);
					sb.append("\\u");
					for (int k = 0; k < 4 - ss.length(); k++) {
						sb.append('0');
					}
					sb.append(ss.toUpperCase());
				} else {
					sb.append(ch);
				}
			}
		}
		return sb.toString();
	}
}
