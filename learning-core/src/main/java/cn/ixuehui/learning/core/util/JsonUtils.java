package cn.ixuehui.learning.core.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Json工具类
 * @author 王佳音
 * com.frame.core.utils.JsonUtils
 * 2017年5月29日
 */
public class JsonUtils {
	private static ObjectMapper mapper = new ObjectMapper()
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
			.setSerializationInclusion(Include.NON_NULL).setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
	
	/**
	 * 日期格式上 打注解@JsonSerialize(using=JsonDateSerializer.class)  
	 * @param obj
	 * @return
	 */
	public static String objToJson(Object obj){
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			return null;
		}
	}

	/**
	 * 日期格式上 打注解@JsonSerialize(using=JsonDateSerializer.class)  
	 * @param jsonStr
	 * @param clazz
	 * @return
	 */
	public static <T> T jsonToObj(String jsonStr,Class<T> clazz){
		try {
			return mapper.readValue(jsonStr, clazz);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static <T> T jsonToList(String jsonStr, TypeReference<T> valueTypeRef){  
		try {  
			return mapper.readValue(jsonStr, valueTypeRef);  
		} catch (Exception e) {  
			return null;
		}  
	}
	
	public static Map<?,?> jsonToMap(String jsonStr){
		try {
			return mapper.readValue(jsonStr, Map.class);
		} catch (Exception e) {
			return null;
		}
	}
	
}
