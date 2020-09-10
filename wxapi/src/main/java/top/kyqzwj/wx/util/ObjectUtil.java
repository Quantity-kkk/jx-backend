package top.kyqzwj.wx.util;

import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanMap;
import top.kyqzwj.wx.attribute.CommonAttribute;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/8/27 22:40
 */
public class ObjectUtil {
    private static Logger logger = LoggerFactory.getLogger(ObjectUtil.class);
    public static final List<String> DEFAULT_FILTER_FIELDS = Arrays.asList("companyCode", "departmentCode", "createBy", "createTime", "updateBy", "updateTime", "isVoid", "locTimeZone", "version");

    public ObjectUtil() {
    }

    public static Class createClass(String clazzName) {
        try {
            return Class.forName(clazzName);
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }

    public static <T> T createObject(Object srcObj) {
        if (srcObj == null) {
            return null;
        } else {
            try {
                if (srcObj instanceof String) {
                    return (T)createClass((String)srcObj).newInstance();
                } else if (srcObj instanceof Class) {
                    return (T)((Class)srcObj).newInstance();
                } else {
                    throw new RuntimeException("Source object only supports String/Class type.");
                }
            } catch (Exception var2) {
                throw new RuntimeException(var2);
            }
        }
    }

    public static <T extends Serializable> T deepClone(T object) {
        return SerializationUtils.clone(object);
    }

    public static Object deserialize(byte[] objectData) {
        return SerializationUtils.deserialize(objectData);
    }

    public static <T> T deserialize(InputStream inputStream) {
        return SerializationUtils.deserialize(inputStream);
    }

    public static String getSlashClassPath(Class clazz) {
        return StringUtil.replace(StringUtil.subString(clazz.getName(), 0, clazz.getName().indexOf(clazz.getSimpleName())), ".", "/");
    }

    public static boolean isString(Object obj) {
        return obj == null ? false : obj instanceof String;
    }

    public static boolean isInteger(Object obj) {
        if (obj == null) {
            return false;
        } else {
            return Integer.class.equals(obj.getClass()) || Integer.TYPE.equals(obj.getClass());
        }
    }

    public static boolean isLong(Object obj) {
        if (obj == null) {
            return false;
        } else {
            return Long.class.equals(obj.getClass()) || Long.TYPE.equals(obj.getClass());
        }
    }

    public static boolean isFloat(Object obj) {
        if (obj == null) {
            return false;
        } else {
            return Float.class.equals(obj.getClass()) || Float.TYPE.equals(obj.getClass());
        }
    }

    public static boolean isDouble(Object obj) {
        if (obj == null) {
            return false;
        } else {
            return Double.class.equals(obj.getClass()) || Double.TYPE.equals(obj.getClass());
        }
    }

    public static boolean isBigDecimal(Object obj) {
        return obj == null ? false : obj instanceof BigDecimal;
    }

    public static boolean isBigInteger(Object obj) {
        return obj == null ? false : obj instanceof BigInteger;
    }

    public static boolean isDate(Object obj) {
        return obj == null ? false : obj instanceof Date;
    }

    public static boolean isArray(Object obj) {
        return obj == null ? false : obj.getClass().isArray();
    }

    public static boolean isList(Object obj) {
        return obj == null ? false : obj instanceof List;
    }

    public static boolean isMap(Object obj) {
        return obj == null ? false : obj instanceof Map;
    }

    public static boolean isSet(Object obj) {
        return obj == null ? false : obj instanceof Set;
    }

    public static byte[] serialize(Serializable obj) {
        return SerializationUtils.serialize(obj);
    }

    public static void serialize(Serializable obj, OutputStream outputStream) {
        SerializationUtils.serialize(obj, outputStream);
    }

    public static final String toString(Object object) {
        return JSONUtil.format(object);
    }

    public static <T> T replaceNull(T object, T defaultValue) {
        return object == null ? defaultValue : object;
    }

    public static <T> void copyPropertyExcludeFramework(T sourceBean, T tagretBean) throws InvocationTargetException, IllegalAccessException {
        Method[] methods = sourceBean.getClass().getMethods();
        Method[] var3 = methods;
        int var4 = methods.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Method method = var3[var5];
            String methodName = method.getName();
            if (methodName.startsWith("get") && methodName.length() > 3 && !Collection.class.isAssignableFrom(method.getReturnType())) {
                String attrName = StringUtil.firstCharToLowerCase(methodName.substring(3));
                if (!DEFAULT_FILTER_FIELDS.contains(attrName.toLowerCase()) && !"class".equals(attrName.toLowerCase())) {
                    try {
                        Method targetMethod = tagretBean.getClass().getMethod("set" + methodName.substring(3), method.getReturnType());
                        if (null == method.invoke(sourceBean)) {
                            logger.debug("[{}]拷贝属性过程中发现空值，直接跳过拷贝");
                        } else {
                            targetMethod.invoke(tagretBean, method.invoke(sourceBean));
                        }
                    } catch (NoSuchMethodException var10) {
                        var10.printStackTrace();
                    }
                }
            }
        }

    }

    private static <T> T createInstance(Class<T> objClass) {
        try {
            return objClass.newInstance();
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }

    public static <T> T mapToBean(Map map, Class<T> targetClass, boolean isSkipFrameField, List<String> skipFields) throws IllegalAccessException, InstantiationException {
        Method[] methods = targetClass.getMethods();
        T result = targetClass.newInstance();
        Method[] var6 = methods;
        int var7 = methods.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            Method method = var6[var8];
            String methodName = method.getName();
            if (methodName.startsWith("set") && methodName.length() > 3 && !Collection.class.isAssignableFrom(method.getReturnType())) {
                String attrName = StringUtil.firstCharToLowerCase(methodName.substring(3));
                if ((!isSkipFrameField || !DEFAULT_FILTER_FIELDS.contains(attrName.toLowerCase())) && !"class".equals(attrName.toLowerCase()) && (null == skipFields || !skipFields.contains(attrName))) {
                    try {
                        Object nodeData = map.get(attrName);
                        if (null == nodeData) {
                            logger.debug("[{}]拷贝属性过程中发现空值，直接跳过拷贝");
                        } else {
                            method.invoke(result, nodeData);
                        }
                    } catch (Exception var13) {
                        var13.printStackTrace();
                    }
                }
            }
        }

        return result;
    }

    public static Map getBeanMap(Object bean, Collection<String> filterFields) {
        Map<String, Object> map = new HashMap(16);
        if (bean != null) {
            BeanMap beanMap = BeanUtil.getBeanMap(bean);
            Iterator var4 = beanMap.keySet().iterator();

            while(true) {
                String keyName;
                do {
                    do {
                        if (!var4.hasNext()) {
                            return map;
                        }

                        Object key = var4.next();
                        keyName = (String)key;
                    } while(DEFAULT_FILTER_FIELDS.contains(keyName));
                } while(filterFields != null && filterFields.contains(keyName));

                map.put(keyName, beanMap.get(keyName));
            }
        } else {
            return map;
        }
    }

    public static void clearFrameWorkFields(Object object, Boolean skipId, Boolean ingoreCase, String... fields) {
        try {
            List<String> resultField = new ArrayList();
            Iterator var5 = CommonAttribute.FRAMEWORK_FIELDS.iterator();

            while(var5.hasNext()) {
                String tempField = (String)var5.next();
                resultField.add(ingoreCase ? tempField.toLowerCase() : tempField);
            }

            String[] var16 = fields;
            int var18 = fields.length;

            for(int var7 = 0; var7 < var18; ++var7) {
                String tempField = var16[var7];
                resultField.add(ingoreCase ? tempField.toLowerCase() : tempField);
            }

            Class targetClass = object.getClass();
            Method[] methods = targetClass.getMethods();
            Method[] var20 = methods;
            int var21 = methods.length;

            for(int var9 = 0; var9 < var21; ++var9) {
                Method method = var20[var9];
                String methodName = method.getName();
                if (methodName.startsWith("set") && method.getParameterCount() == 1 && methodName.length() > 3) {
                    String field = StringUtil.firstCharToLowerCase(methodName.substring(3));
                    if ((!skipId || !field.toLowerCase().equals("id")) && (resultField.contains(field) || ingoreCase && resultField.contains(field.toLowerCase()))) {
                        try {
                            method.invoke(object);
                        } catch (Exception var14) {
                            logger.error("移除框架属性发生异常！字段：{}", methodName, var14);
                        }
                    }
                }
            }
        } catch (Exception var15) {
            logger.error("移除框架属性发生异常！", var15);
        }

    }
}
