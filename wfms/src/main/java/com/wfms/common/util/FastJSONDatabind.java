package com.wfms.common.util;

import java.io.IOException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * * This serializer uses FastJSON *
 * [http://code.alibabatech.com/wiki/display/FastJSON] for JSON data binding.
 */
public class FastJSONDatabind {
	/*public static void register(TestGroups groups) {
		groups.media.add(JavaBuiltIn.MediaTransformer,
				new GenericSerializer<MediaContent>("json/fastjson-databind",
						MediaContent.class));
	}

	static class GenericSerializer<T> extends Serializer<T> {
		private final String name;
		private final Class<T> type;

		public GenericSerializer(String name, Class<T> clazz) {
			this.name = name;
			type = clazz;
		}

		public String getName() {
			return name;
		}

		public T deserialize(byte[] array) throws Exception {
			T result = JSON.parseObject(array, type);
			return result;
		}

		public byte[] serialize(T data) throws IOException {
			return toJSONString(data, SerializerFeature.WriteEnumUsingToString);
		}

		public static final byte[] toJSONString(Object object,
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
	}*/
}