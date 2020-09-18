package top.kyqzwj.wx.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/8/27 22:23
 */
@Component
public class WebSpringBeanUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    public WebSpringBeanUtils() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        WebSpringBeanUtils.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    public static <T> T getBean(String name, @Nullable Class<T> requiredType) {
        return applicationContext.getBean(name, requiredType);
    }

    public static String[] getBeanForType(Class<?> requiredType) {
        return applicationContext.getBeanNamesForType(requiredType);
    }
}