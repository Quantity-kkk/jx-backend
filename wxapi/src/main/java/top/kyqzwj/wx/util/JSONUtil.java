package top.kyqzwj.wx.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/8/27 22:06
 */
public class JSONUtil {
    public JSONUtil() {
    }

    public static String format(Object obj) {
        return obj != null ? JSON.toJSONString(obj, new SerializerFeature[]{SerializerFeature.WriteMapNullValue, SerializerFeature.UseISO8601DateFormat, SerializerFeature.PrettyFormat, SerializerFeature.DisableCircularReferenceDetect}) : "";
    }

    public static Map parseMap(String jsonStr) {
        return JSON.parseObject(jsonStr, new Feature[]{Feature.AllowISO8601DateFormat});
    }

    public static List parseList(String jsonStr) {
        return JSON.parseArray(jsonStr);
    }

    public static <T> T parseBean(String jsonStr, Class<T> beanClass) {
        return JSON.parseObject(jsonStr, beanClass);
    }

    public static <T> List<T> parseBeanList(String jsonStr, Class<T> beanClass) {
        return JSON.parseArray(jsonStr, beanClass);
    }
}
