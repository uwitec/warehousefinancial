package com.wfms.common.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

import com.wfms.model.system.ModuleGenInfo;
import com.wfms.model.system.RightGenInfo;

public class JacksonUtil {

	private static ObjectMapper jacksonMapper = new ObjectMapper();

	public static Set<Object> getSingletons() {
		Set<Object> s = new HashSet<Object>();

		// Register the Jackson provider for JSON

		// Make (de)serializer use a subset of JAXB and (afterwards) Jackson
		// annotations
		// See http://wiki.fasterxml.com/JacksonJAXBAnnotations for more
		// information
		ObjectMapper mapper = new ObjectMapper();
		AnnotationIntrospector primary = new JaxbAnnotationIntrospector();
		AnnotationIntrospector secondary = new JacksonAnnotationIntrospector();
		AnnotationIntrospector pair = new AnnotationIntrospector.Pair(primary,
				secondary);
		mapper.getDeserializationConfig().setAnnotationIntrospector(pair);
		mapper.getSerializationConfig().setAnnotationIntrospector(pair);

		// Set up the provider
		JacksonJaxbJsonProvider jaxbProvider = new JacksonJaxbJsonProvider();
		jaxbProvider.setMapper(mapper);
		s.add(jaxbProvider);
		return s;
	}

	public static Object formatObject(String json, Class<?> cls) {
		Object toObj = null;
		try {
			toObj = cls.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		try {
			toObj = jacksonMapper.readValue(json, cls);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return toObj;
	}

	// Bean转JSON：

	public static String formatJsonString(Object formatBean) {
		StringWriter sw = new StringWriter();
		JsonGenerator gen = null;
		try {
			gen = jacksonMapper.getJsonFactory().createJsonGenerator(sw);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			jacksonMapper.writeValue(gen, formatBean);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			gen.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String json = sw.toString();
		return json;
	}
	
	/*public static Object configureObject(){
		//jacksonMapper.configure(SerializationConfig.Feature., true).readValue("{a:1, b:2}", HashMap.class);  
	}*/
	
	public static void main(String[] args) {
		ModuleGenInfo module = new ModuleGenInfo();
		module.setModuleId(12323);
		module.setDescription("www.xunersoft.com");
		module.setHasChild("true");
		module.setRight(new RightGenInfo(12312));
		String jsonString = formatJsonString(module);
		System.out.println(jsonString);
	}
}
