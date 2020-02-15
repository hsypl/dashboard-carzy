package com.crazy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xiaoxx
 */
public class JsonUtils {

	/**
     * 功能描述：把JSON数据转换成指定的java对象
     * @param jsonData JSON数据
     * @param clazz 指定的java对象
     * @return 指定的java对象
     */
    public static <T> T getJsonToBean(String jsonData, Class<T> clazz) {
        return JSON.parseObject(jsonData, clazz);
    }

    /**
     * 功能描述：把java对象转换成JSON数据
     * @param object java对象
     * @return JSON数据
     */
    public static String getBeanToJson(Object object) {
        return JSON.toJSONString(object);
    }

    /**
     * 功能描述：把JSON数据转换成指定的java对象列表
     * @param jsonData JSON数据
     * @param clazz 指定的java对象
     * @return List<T>
     */
    public static <T> List<T> getJsonToList(String jsonData, Class<T> clazz) {
        return JSON.parseArray(jsonData, clazz);
    }

    /**
     * 功能描述：把JSON数据转换成较为复杂的List<Map<String, Object>>
     * @param jsonData JSON数据
     * @return List<Map<String, Object>>
     */
    public static List<Map<String, Object>> getJsonToListMap(String jsonData) {
        return JSON.parseObject(jsonData, new TypeReference<List<Map<String, Object>>>() {
        });
    }
    
    /**
     * 将对象序列化为json 包含集合
     * @return 
     */
    public static JSONObject objectToJsonIncludes(Object obj, String[] args) {
       //属性过滤器对象
       SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
       
       //属性排斥集合,强调某些属性不需要或者一定不能被序列化
       //Set<String> excludes = filter.getExcludes();
       //属性包含集合,强调仅需要序列化某些属性.具体用哪一个,看实际情况
       Set<String> includes = filter.getIncludes();
       //排除不需序列化的属性
       for (String string : args) {
    	   includes.add(string);
       }
       //调用fastJson的方法,对象转json,
       //参数一:需要被序列化的对象
       //参数二:用于过滤属性的过滤器
       //参数三:关闭循环引用,若不加这个,页面无法展示重复的属性值
       String string = JSON.toJSONString(obj, filter,SerializerFeature.DisableCircularReferenceDetect);
       JSONObject object = JSONObject.parseObject(string);
       return object;
    }
    
    /**
     * 将对象序列化为json 排斥集合
     * @return 
     */
    public static JSONObject objectToJsonExcludes(Object obj, String[] args) {
       //属性过滤器对象
       SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
       
       //属性排斥集合,强调某些属性不需要或者一定不能被序列化
       Set<String> excludes = filter.getExcludes();
       //属性包含集合,强调仅需要序列化某些属性.具体用哪一个,看实际情况.此处我用的前者
       //Set<String> includes = filter.getIncludes();
       //排除不需序列化的属性
       for (String string : args) {
    	   excludes.add(string);
       }
       //调用fastJson的方法,对象转json,
       //参数一:需要被序列化的对象
       //参数二:用于过滤属性的过滤器
       //参数三:关闭循环引用,若不加这个,页面无法展示重复的属性值
       String string = JSON.toJSONString(obj, filter,SerializerFeature.DisableCircularReferenceDetect);
       JSONObject object = JSONObject.parseObject(string);
       return object;
    }
}
