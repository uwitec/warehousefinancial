package com.wfms.common.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.JSONLibDataFormatSerializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.JSONSerializerMap;
import com.alibaba.fastjson.serializer.JavaBeanSerializer;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

public class JSONFeidDemo extends TestCase {
	public static class User {
		private int id;
		private String name;

		@JSONField(name = "uid")
		public int getId() {
			return id;
		}

		@JSONField(name = "uid")
		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public void test_0() throws Exception {
		User user = new User();
		user.setId(123);
		user.setName("毛头");
		String text = JSON.toJSONString(user);
		Assert.assertEquals("{\"name\":\"毛头\",\"uid\":123}", text);
		System.out.println(text);
	}

	/**
	 * 兼容json-lib
	 */
	private static final JSONSerializerMap mapping;
	static {
		mapping = new JSONSerializerMap();
		mapping.put(Date.class, new JSONLibDataFormatSerializer());
		// 使用和json-lib兼容的日期输出格式
	}
	private static final SerializerFeature[] features = {
			SerializerFeature.WriteMapNullValue,// 输出空置字段
			SerializerFeature.WriteNullListAsEmpty,
			// list字段如果为null，输出为[]，而不是null
			SerializerFeature.WriteNullNumberAsZero,
			// 数值字段如果为null，输出为0，而不是null
			SerializerFeature.WriteNullBooleanAsFalse,
			// Boolean字段如果为null，输出为false，而不是null
			SerializerFeature.WriteNullStringAsEmpty
	// 字符类型字段如果为null，输出为""，而不是null
	};

	// 序列化为和JSON-LIB兼容的字符串
	public static String toCompatibleJSONString(Object object) {
		return JSON.toJSONString(object, mapping, features);
	}

	/**
	 * 定制序列化日期
	 */
	// SerializeConfig mapping;
	private static JSONSerializerMap mapping1 = new JSONSerializerMap();
	static {
		mapping.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd"));
	}

	public void testDate() throws Exception {
		Date date = new Date();
		String text = JSON.toJSONString(date, mapping1);
		Assert.assertEquals(JSON
				.toJSONString(new SimpleDateFormat("yyyy-MM-dd").format(date)),
				text);
	}

	/**
	 * 循环引用
	 */
	public static class Department {
		private int id;
		private String name;
		// 使用transient private transient Department parent;
		private Department parent;
		private List<Department> children = new ArrayList<Department>();

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		// 使用@JSONField @JSONField(serialize=false)
		public Department getParent() {
			return parent;
		}

		public void setParent(Department parent) {
			this.parent = parent;
		}

		public List<Department> getChildren() {
			return children;
		}

		public void setChildren(List<Department> children) {
			this.children = children;
		}
	}

	/**
	 * 字段排序
	 */
	public class V1 {
		private int f2;

		private int f1;
		private int f4;
		private int f3;
		private int f5;

		public int getF2() {
			return f2;
		}

		public void setF2(int f2) {
			this.f2 = f2;
		}

		public int getF1() {
			return f1;
		}

		public void setF1(int f1) {
			this.f1 = f1;
		}

		public int getF4() {
			return f4;
		}

		public void setF4(int f4) {
			this.f4 = f4;
		}

		public int getF3() {
			return f3;
		}

		public void setF3(int f3) {
			this.f3 = f3;
		}

		public int getF5() {
			return f5;
		}

		public void setF5(int f5) {
			this.f5 = f5;
		}
	}

	public void test_1() throws Exception {
		V1 entity = new V1();
		String text = JSON.toJSONString(entity, SerializerFeature.SortField);
		System.out.println(text);
		// 按字段顺序输出 // {"f1":0,"f2":0,"f3":0,"f4":0,"f5":0}
		Assert.assertEquals("{\"f1\":0,\"f2\":0,\"f3\":0,\"f4\":0,\"f5\":0}",
				text);
		JSONObject object = JSON.parseObject(text);
		text = JSON.toJSONString(object, SerializerFeature.SortField);
		Assert.assertEquals("{\"f1\":0,\"f2\":0,\"f3\":0,\"f4\":0,\"f5\":0}",
				text);
	}

	/**
	 * tab 转义
	 */
	public void testZy() {
		JSONObject json = new JSONObject();
		json.put("hello\t", "World\t!");
		Assert.assertEquals("{\"hello\t\":\"World\t!\"}",
				JSON.toJSONString(json));
		Assert.assertEquals("{\"hello\\t\":\"World\\t!\"}",
				JSON.toJSONString(json, SerializerFeature.WriteTabAsSpecial));
		Assert.assertEquals("{'hello\\t':'World\\t!'}", JSON.toJSONString(json,
				SerializerFeature.WriteTabAsSpecial,
				SerializerFeature.UseSingleQuotes));
	}

	/**
	 * 自定义序列化
	 */
	public static class User1 {
		private int id;
		private String name;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public void testCustomerSerial() {
		User user = new User();
		user.setId(123);
		user.setName("毛头");
		JSONSerializerMap mapping = new JSONSerializerMap();
		mapping.put(
				User.class,
				new JavaBeanSerializer(User.class, Collections.singletonMap(
						"id", "uid")));
		JSONSerializer serializer = new JSONSerializer(mapping);
		serializer.write(user);
		String jsonString = serializer.toString();
		Assert.assertEquals("{\"uid\":123}", jsonString);
	}
	
	
	/**
	 * 主要接口
	 */
//	public static final Object parse(String text); 
//	// 把JSON文本parse为JSONObject或者JSONArray 
//	public static final JSONObject parseObject(String text)；
//	// 把JSON文本parse成JSONObject
//	public static final <T> T parseObject(String text, Class<T> clazz); 
//	// 把JSON文本parse为JavaBean
//	public static final JSONArray parseArray(String text);
//	// 把JSON文本parse成JSONArray
//	public static final <T> List<T> parseArray(String text, Class<T> clazz); 
//	//把JSON文本parse成JavaBean集合
//	public static final String toJSONString(Object object);
//	// 将JavaBean序列化为JSON文本 
//	public static final String toJSONString(Object object, boolean prettyFormat); 
//	// 将JavaBean序列化为带格式的JSON文本 
//	public static final Object toJSON(Object javaObject); 
	//将JavaBean转换为JSONObject或者JSONArray。

}