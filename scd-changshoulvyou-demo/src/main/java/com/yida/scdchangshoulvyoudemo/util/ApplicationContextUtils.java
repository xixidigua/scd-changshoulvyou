package com.yida.scdchangshoulvyoudemo.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取应用程序上下文对象
 *    目的是用该应用程序上下文对象获取spring的Ioc容器中的单例对象。
 */
@Component //注入到spring的Ioc容器中
public class ApplicationContextUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    //根据bean名称，返回spring的Ioc容器里的单例对象
    public static Object getBean(String beanName) {
        Object bean = applicationContext.getBean(beanName);
        return bean;
    }
}
