package top.kyqzwj.wx.util;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cglib.beans.BeanMap;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/8/27 22:34
 */
public class BeanUtil {
    private static Log logger = LogFactory.getLog(BeanUtil.class);
    private static final ConcurrentHashMap<String, BeanMap> BEAN_MAP_CACHE = new ConcurrentHashMap();
    public static final List<String> DEFAULT_FILTER_FIELDS = Arrays.asList("id", "createTime", "updateBy", "createBy", "updateTime", "isVoid", "version");
    private static final String GET = "get";
    private static final String SET = "set";
    private static final String IS = "is";

    public static <T> T getProperty(Object objBean, String key) {
        Object value = null;

        try {
            value = PropertyUtils.getProperty(objBean, key);
        } catch (Exception var4) {
            logger.warn("Can't get property <Key:" + key + "> from <Bean: " + objBean.getClass().getName() + ">");
        }

        return (T)value;
    }

    public static Class<?> getPropertyType(Object objBean, String key) {
        Class propertyType = null;

        try {
            propertyType = PropertyUtils.getPropertyType(objBean, key);
        } catch (Exception var4) {
            logger.warn("Can't get property Type <Key:" + key + "> from <Bean: " + objBean.getClass().getName() + ">");
        }

        return propertyType;
    }

    public static Method getReadMethod(Object objBean, String key) {
        Method method = null;

        try {
            PropertyDescriptor descriptor = PropertyUtils.getPropertyDescriptor(objBean, key);
            method = PropertyUtils.getReadMethod(descriptor);
        } catch (Exception var4) {
            if (objBean == null) {
                throw new RuntimeException(var4);
            }

            logger.warn("Can't get read method <Key:" + key + "> from <Bean: " + objBean.getClass().getName() + ">");
        }

        return method;
    }

    public static Method getWriteMethod(Object objBean, String key) {
        Method method = null;

        try {
            PropertyDescriptor descriptor = PropertyUtils.getPropertyDescriptor(objBean, key);
            method = PropertyUtils.getWriteMethod(descriptor);
        } catch (Exception var4) {
            if (objBean == null) {
                throw new RuntimeException(var4);
            }

            logger.warn("Can't get write method [Key:" + key + "] from [Bean: " + objBean.getClass().getName() + "]");
        }

        return method;
    }

    public static void setProperty(Object objBean, String key, Object value) {
        try {
            BeanUtils.setProperty(objBean, key, value);
        } catch (Exception var4) {
            logger.warn("Can't set property [Key:" + key + ", Value:" + ObjectUtil.toString(value) + "] to [Bean: " + objBean.getClass().getName() + "]");
        }

    }

    public static <T> T toBean(Object srcBeanObj, Class<T> targetBeanClass) {
        return JSONUtil.parseBean(JSONUtil.format(srcBeanObj), targetBeanClass);
    }

    public static <T> List<T> toBeanList(Object srcBeanObj, Class<T> targetBeanClass) {
        return JSONUtil.parseBeanList(JSONUtil.format(srcBeanObj), targetBeanClass);
    }

    public static Object DeepClone(Serializable srcBean) {
        return SerializationUtils.clone(srcBean);
    }

    public BeanUtil() {
    }

    public static <T> T newInstance(Class<T> clazz) {
        try {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(clazz);
            enhancer.setCallback(new BeanUtil.MethodInterceptorImpl());
            return (T)enhancer.create();
        } catch (Throwable var2) {
            logger.warn("Can't newInstance class" + clazz.getSimpleName());
            throw new Error(var2.getMessage());
        }
    }

    public static Object newInstanceWithout(Class clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException var2) {
            var2.printStackTrace();
        } catch (IllegalAccessException var3) {
            var3.printStackTrace();
        }

        return null;
    }

    public static BeanMap getBeanMap(Object object) {
        BeanMap map;
        if (BEAN_MAP_CACHE.contains(object)) {
            map = (BeanMap)BEAN_MAP_CACHE.get(object.getClass().getName());
            map.setBean(object);
            return map;
        } else {
            map = BeanMap.create(object);
            map.put(object.getClass().getName(), object);
            return map;
        }
    }

    public static Object getPropertie(Object bean, String fieldName) {
        try {
            BeanMap map = getBeanMap(bean);
            return map.get(fieldName);
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static void setPropertie(Object bean, String fieldName, Object value) {
        try {
            BeanMap map = getBeanMap(bean);
            map.put(fieldName, value);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public static <T> T beanCopy(Object source, Class<T> clazz) {
        T target = newInstance(clazz);
        beanCopy(source, target);
        return target;
    }

    public static void beanCopy(Object source, Object target, List<String> filterFields, boolean copyNull) {
        if (copyNull && (filterFields == null || filterFields.size() == 0)) {
            beanCopy(source, target);
        } else {
            BeanMap beanMap = getBeanMap(source);
            Iterator<String> iterator = beanMap.keySet().iterator();
            HashMap<String, Object> map = new HashMap();
            String key = "";
            Object value = null;

            while(iterator.hasNext()) {
                key = (String)iterator.next();
                value = beanMap.get(key);
                putToMap(map, copyNull, key, value, filterFields);
            }

            BeanMap beanMapTarget = getBeanMap(target);
            beanMapTarget.putAll(map);
        }

    }

    public static void beanCopy(Object source, Object target, boolean ignoreBaseField, List<String> ignoreField) {
        BeanMap map = getBeanMap(source);
        BeanMap mapTarget = getBeanMap(target);
        if (ignoreField == null) {
            ignoreField = new ArrayList();
        }

        Iterator var6;
        Object o;
        if (ignoreBaseField) {
            var6 = map.keySet().iterator();

            while(var6.hasNext()) {
                o = var6.next();
                if (!((List)ignoreField).contains(o)) {
                    mapTarget.put(o, map.get(o));
                }
            }
        } else {
            var6 = map.keySet().iterator();

            while(var6.hasNext()) {
                o = var6.next();
                mapTarget.put(o, map.get(o));
            }
        }

    }

    public static void beanCopy(Object source, Object target, boolean copyNull) {
        List<String> filterFields = DEFAULT_FILTER_FIELDS;
        beanCopy(source, target, filterFields, copyNull);
    }

    public static void beanCopy(Object source, Object target) {
        BeanMap map = getBeanMap(source);
        BeanMap mapTarget = getBeanMap(target);
        Iterator var4 = map.keySet().iterator();

        while(var4.hasNext()) {
            Object o = var4.next();
            if (!DEFAULT_FILTER_FIELDS.contains(o)) {
                mapTarget.put(o, map.get(o));
            }
        }

    }

    public static Map<String, Object> beanToMap(Object bean) {
        Map<String, Object> map = new HashMap();
        if (bean != null) {
            BeanMap beanMap = getBeanMap(bean);
            Iterator var3 = beanMap.keySet().iterator();

            while(var3.hasNext()) {
                Object key = var3.next();
                map.put(key.toString(), beanMap.get(key));
            }
        }

        return map;
    }

    public static <T> List<T> mapListToBeanList(List<Map> data, Class<T> dtoClass) {
        List<T> list = new ArrayList(data.size());
        if (data != null) {
            data.forEach((map) -> {
                list.add(toBean(map, dtoClass));
            });
        }

        return list;
    }

    public static <T> List<Map> beanListToMapList(List<T> data) {
        List<Map> list = new ArrayList(data.size());
        if (data != null) {
            data.forEach((bean) -> {
                list.add(beanToMap(bean));
            });
        }

        return list;
    }

    public static <T> List<T> convert(Class<T> cla, List<?> list) {
        if (list == null) {
            return null;
        } else {
            List<T> dtos = (List)list.stream().map((e) -> {
                T a = newInstance(cla);
                beanCopy(e, a, false);
                return a;
            }).collect(Collectors.toList());
            return dtos;
        }
    }

    public static HashMap<String, Class<?>> getAllField(Class<?> domainClass) {
        HashMap<String, Class<?>> fields = new HashMap();
        initFields(fields, domainClass);
        return fields;
    }

    public static HashMap<String, Class<?>> getAllField(Class<?>... domainClass) {
        HashMap<String, Class<?>> fields = new HashMap();

        for(int i = 0; i < domainClass.length; ++i) {
            initFields(fields, domainClass[i]);
        }

        return fields;
    }

    private static void initFields(HashMap<String, Class<?>> fields, Class<?> domainClass) {
        Method[] methods = domainClass.getMethods();
        Map<String, Integer> getSetMap = new HashMap(50);
        Map<String, Class<?>> typeMap = new HashMap(50);

        for(int i = 0; i < methods.length; ++i) {
            if (isGetSet(methods[i])) {
                String fieldName = getFiledName(methods[i]);
                if (!fields.containsKey(fieldName)) {
                    if (!methods[i].getReturnType().equals(Void.TYPE)) {
                        typeMap.put(fieldName, methods[i].getReturnType());
                    }

                    if (getSetMap.containsKey(fieldName)) {
                        getSetMap.put(fieldName, 2);
                    } else {
                        getSetMap.put(fieldName, 1);
                    }
                }
            }
        }

        getSetMap.forEach((k, v) -> {
            if (v == 2) {
                fields.put(k, typeMap.get(k));
            }

        });
    }

    private static boolean isGetSet(Method method) {
        if (method.getModifiers() != 1) {
            return false;
        } else {
            return method.getName().startsWith("get") || method.getName().startsWith("set") || method.getName().startsWith("is");
        }
    }

    private static String getFiledName(Method method) {
        String field = "";
        if (!method.getName().startsWith("get") && !method.getName().startsWith("set")) {
            if (method.getName().startsWith("is")) {
                field = method.getName().substring(2);
            }
        } else {
            field = method.getName().substring(3);
        }

        return StringUtil.getFirstLower(field);
    }

    private static void putToMap(HashMap<String, Object> map, boolean copyNull, String key, Object value, List<String> filterFields) {
        if (value == null) {
            if (copyNull && (filterFields == null || !filterFields.contains(key))) {
                map.put(key, (Object)null);
            }
        } else if (filterFields == null || !filterFields.contains(key)) {
            map.put(key, value);
        }

    }

    private static class MethodInterceptorImpl implements MethodInterceptor {
        private MethodInterceptorImpl() {
        }

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            return methodProxy.invokeSuper(o, objects);
        }
    }
}
