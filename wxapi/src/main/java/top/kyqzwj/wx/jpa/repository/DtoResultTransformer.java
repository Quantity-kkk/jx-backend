package top.kyqzwj.wx.jpa.repository;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.property.access.internal.PropertyAccessStrategyBasicImpl;
import org.hibernate.property.access.internal.PropertyAccessStrategyChainedImpl;
import org.hibernate.property.access.internal.PropertyAccessStrategyFieldImpl;
import org.hibernate.property.access.internal.PropertyAccessStrategyMapImpl;
import org.hibernate.property.access.spi.PropertyAccessStrategy;
import org.hibernate.property.access.spi.Setter;
import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.kyqzwj.wx.util.JSONUtil;
import top.kyqzwj.wx.util.StringUtil;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/8/27 22:05
 */
public class DtoResultTransformer implements ResultTransformer {
    private static final Logger log = LoggerFactory.getLogger(DtoResultTransformer.class);
    private static final String GET = "get";
    private static final String SET = "set";
    private static final String IS = "is";
    Class dtoClass;
    private boolean isInitialized;
    private String[] aliases;
    private Setter[] setters;
    private HashSet<String> fields;

    public DtoResultTransformer(Class dtoClass) {
        this.dtoClass = dtoClass;
    }

    private void initialize(String[] aliases) {
        PropertyAccessStrategyChainedImpl propertyAccessStrategy = new PropertyAccessStrategyChainedImpl(new PropertyAccessStrategy[]{PropertyAccessStrategyBasicImpl.INSTANCE, PropertyAccessStrategyFieldImpl.INSTANCE, PropertyAccessStrategyMapImpl.INSTANCE});
        this.aliases = new String[aliases.length];
        this.setters = new Setter[aliases.length];
        this.fields = new HashSet(50);
        this.initFields();

        for(int i = 0; i < aliases.length; ++i) {
            String alias = StringUtil.camel(aliases[i]);
            if (alias != null) {
                this.aliases[i] = alias;
                if (this.fields.contains(alias)) {
                    this.setters[i] = propertyAccessStrategy.buildPropertyAccess(this.dtoClass, alias).getSetter();
                }
            }
        }

        this.isInitialized = true;
    }

    private void initFields() {
        Method[] methods = this.dtoClass.getMethods();
        Map<String, Integer> getSetMap = new HashMap(50);

        for(int i = 0; i < methods.length; ++i) {
            if (this.isGetSet(methods[i])) {
                String fieldName = this.getFiledName(methods[i]);
                if (getSetMap.containsKey(fieldName)) {
                    getSetMap.put(fieldName, 2);
                } else {
                    getSetMap.put(fieldName, 1);
                }
            }
        }

        getSetMap.forEach((k, v) -> {
            if (v == 2) {
                this.fields.add(k);
            }

        });
    }

    private boolean isGetSet(Method method) {
        if (method.getModifiers() != 1) {
            return false;
        } else {
            return method.getName().startsWith("get") || method.getName().startsWith("set") || method.getName().startsWith("is");
        }
    }

    private String getFiledName(Method method) {
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

    @Override
    public Object transformTuple(Object[] objects, String[] aliases) {
        if (log.isDebugEnabled()) {
            log.debug("transformTuple objects is:" + JSONUtil.format(objects));
            log.debug("transformTuple strings is:" + JSONUtil.format(aliases));
        }

        if (!this.isInitialized) {
            this.initialize(aliases);
        }

        Object dto = null;

        try {
            dto = this.dtoClass.newInstance();

            for(int i = 0; i < aliases.length; ++i) {
                if (this.setters[i] != null) {
                    objects[i] = this.adapterValue(objects[i], this.setters[i]);
                    this.setters[i].set(dto, objects[i], (SessionFactoryImplementor)null);
                }
            }
        } catch (InstantiationException | IllegalAccessException var5) {
            log.error("transformTuple method,setter field error!", var5);
        }

        return dto;
    }

    private Object adapterValue(Object value, Setter setter) {
        if (value == null) {
            return null;
        } else if (setter.getMethod().getGenericParameterTypes()[0].getTypeName().equals("java.lang.Long") && value instanceof BigInteger) {
            return ((BigInteger)value).longValue();
        } else {
            return setter.getMethod().getGenericParameterTypes()[0].getTypeName().equals("java.lang.Long") && value instanceof Integer ? ((Integer)value).longValue() : value;
        }
    }

    @Override
    public List transformList(List list) {
        if (log.isInfoEnabled()) {
            log.debug("transformList list is:" + JSONUtil.format(list));
        }

        return list;
    }
}
