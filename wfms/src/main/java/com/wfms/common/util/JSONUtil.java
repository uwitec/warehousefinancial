package com.wfms.common.util;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.JSONLibDataFormatSerializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.NameFilter;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.wfms.common.orm.Page;

public class JSONUtil {

	private static String dateFormat = "yyyy-M-d";

	/**
	 * PropertyFilter and NameFilter序列化
	 * 
	 * @param fromObject
	 * @param required
	 * @return
	 */
	public static String serializeObject(Object fromObject,
			final String[] filters, boolean filterMark,
			final Map<String, String>... nameFilterConfig) {

		SerializeWriter out = new SerializeWriter();
		JSONSerializer serializer = new JSONSerializer(out);

		if (filters != null && filters.length > 0) {
			PropertyFilter pfilter = getPropertyFilter(filters, filterMark);
			serializer.getPropertyFilters().add(pfilter);
		}
		if (nameFilterConfig != null && nameFilterConfig.length > 0) {
			NameFilter nfilter = getNameFilter(nameFilterConfig[0]);
			serializer.getNameFilters().add(nfilter);
		}
		serializer.write(fromObject);
		String text = out.toString();
		return text;
	}

	/**
	 * 返回PropertyFilter
	 * 
	 * @param requires
	 * @return
	 */
	public static PropertyFilter getPropertyFilter(final String[] requires,
			boolean... filterMark) {
		if (requires == null || requires.length == 0) {
			return null;
		}
		final boolean isFilter = (filterMark != null && filterMark.length > 0) ? filterMark[0]
				: true;
		PropertyFilter pfilter = new PropertyFilter() {
			public boolean apply(Object source, String name, Object value) {
				for (String require : requires) {
					if (require.equals(name)) {
						return !isFilter;
					}
				}
				return isFilter;
			}
		};
		return pfilter;
	}

	/**
	 * 返回NameFilter
	 * 
	 * @param nameConfig
	 * @return
	 */
	private static NameFilter getNameFilter(final Map<String, String> nameConfig) {
		if (nameConfig == null || nameConfig.size() == 0) {
			return null;
		}
		NameFilter filter = new NameFilter() {
			public String process(Object source, String name, Object value) {
				for (Map.Entry<String, String> nameFilterConfig : nameConfig
						.entrySet()) {
					if (name.equals(nameFilterConfig.getKey())) {
						return nameFilterConfig.getValue();
					}
				}
				return name;
			}

		};
		return filter;
	}

	/**
	 * 默认对象序列化方法
	 * 
	 * @param object
	 * @return
	 */
	public static String getDefaultJsonString(Object object) {
		return JSON.toJSONString(object, dateConfig(), defaultSerialFeture());
	}

	/**
	 * 格式化Page指定属性值
	 * 
	 * @param page
	 * @param required
	 * @return
	 */
	public static String serializePage(Page page, String[] required,
			boolean filterMark, Map<String, String>... nameFilterConfig) {
		int orginalLength = required != null ? required.length : 0;
		String[] destRequired = null;
		if (orginalLength != 0 && filterMark) {
			int destLength = orginalLength + 4;
			destRequired = new String[destLength];
			destRequired[destLength - 1] = "results";
			destRequired[destLength - 2] = "total";
			destRequired[destLength - 3] = "limit";
			destRequired[destLength - 4] = "start";
			System.arraycopy(required, 0, destRequired, 0, orginalLength);
			required = destRequired;
		}
		final Map<String, String> nameFilterConf = (nameFilterConfig != null && nameFilterConfig.length > 0) ? nameFilterConfig[0]
				: (Map<String, String>) null;
		String serializeString = null;
		if (nameFilterConf == null) {
			serializeString = serializeObject(page, required, filterMark);
		} else {
			serializeString = serializeObject(page, required, filterMark,
					nameFilterConf);
		}
		return serializeString;
	}

	/**
	 * 格式化Page指定属性值
	 * 
	 * @param page
	 * @param required
	 * @return
	 */
	public static String requiredPage(Page page, String[] required,
			Map<String, String>... nameFilterConfig) {
		return serializePage(page, required, false, nameFilterConfig);
	}

	/**
	 * 过滤Page指定属性
	 * 
	 * @param page
	 * @param required
	 * @return
	 */
	public static String filterPage(Page page, String[] required,
			Map<String, String>... nameFilterConfig) {
		return serializePage(page, required, true, nameFilterConfig);
	}

	/**
	 * 日期处理，也可以通过注解实现[@JSONField（format="yyyy-MM-dd"]
	 * 
	 * @param format
	 * @return
	 */
	public static SerializeConfig dateConfig(String... format) {
		SerializeConfig config = new SerializeConfig();
		ObjectSerializer dateSerial = null;
		if (format == null || format.length == 0) {
			dateSerial = new SimpleDateFormatSerializer(dateFormat);
		} else {
			dateSerial = new SimpleDateFormatSerializer(format[0]);
		}
		config.put(Date.class, new JSONLibDataFormatSerializer()); // 使用和json-lib兼容的日期输出格式
		config.put(Date.class, dateSerial);
		return config;
	}

	/**
	 * 默认处理方法
	 * 
	 * @return
	 */
	public static SerializerFeature[] defaultSerialFeture() {
		final SerializerFeature[] features = {
				SerializerFeature.WriteMapNullValue, // 输出空置字段
				SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
				SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null
				SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null
				SerializerFeature.WriteNullStringAsEmpty // 字符类型字段如果为null，输出为""，而不是null
		};
		return features;
	}

	/**
	 * 序列化并返回字节数据
	 * 
	 * @param object
	 * @param features
	 * @return
	 */
	public static final byte[] toJSONStringBytes(Object object,
			com.alibaba.fastjson.serializer.SerializerFeature... features) {
		SerializeWriter out = new SerializeWriter();
		try {
			JSONSerializer serializer = new JSONSerializer(out);
			for (com.alibaba.fastjson.serializer.SerializerFeature feature : features) {
				serializer.config(feature, true);
			}
			serializer.write(object);
			return out.toBytes("UTF-8");
		} catch (StackOverflowError e) {
			throw new JSONException("maybe circular references", e);
		} finally {
			out.close();
		}
	}

	/**
	 * 反序列化字节数组并返回对象
	 * 
	 * @param input
	 * @param clazz
	 * @param features
	 * @return
	 */
	public static final <T> T parseObject(byte[] input, Type clazz,
			Feature... features) {
		return JSON.parseObject(input, clazz, features);
	}

	public static String serializeISO8601Date(Date date) {
		SerializeWriter out = new SerializeWriter();
		JSONSerializer serializer = new JSONSerializer(out);
		serializer.config(SerializerFeature.UseISO8601DateFormat, true);
		serializer.write(date);
		String dateText = out.toString();
		return dateText;
	}

	public static Date paseISO8601Date(String isoDateString) {
		Date date = (java.util.Date) JSON.parseObject(isoDateString,
				Date.class, Feature.AllowISO8601DateFormat);
		return date;
	}

	public static ValueFilter getValueFilter(final String[] nullFields) {
		if (nullFields == null || nullFields.length == 0) {
			return null;
		}
		ValueFilter vfilter = new ValueFilter() {
			public Object process(Object source, String name, Object value) {
				for (String fieldName : nullFields) {
					if (name.equals(fieldName)) {
						return null;
					}
				}
				return value;
			}
		};
		return vfilter;
	}

}
