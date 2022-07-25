package com.yida.scdgateway.config;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.yida.scdgateway.cache.RedisCacheManager;
import com.yida.scdgateway.dao.UserDao;
import com.yida.scdgateway.shiro.ShiroRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.LinkedHashMap;

/**
 * shiro的专用配置类
 */
@Configuration//启动时注入到IOC控制翻转容器里面
public class ShiroConfig {
    @Resource
    private UserDao userDao;
    @Value("${Credential.AlgorithmName}") //从配置参数取过来的是什么加密算法
    private String algorithmName;
    @Value("${Credential.hashIterations}")//从配置参数取过来的是什么加密次数
    private Integer hashIterations;

    /**
     * * 配置shiro的拦截规则
     * * 注意：拦截规则，是有序的，即从上到下；拦截范围，从小到大。
     * * 上面的匹配了，下面的就不会再生效！
     */
    @Bean
    ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        //没有登录的情况下，访问了需要登录认证的页面，跳转的页面
        shiroFilterFactoryBean.setLoginUrl("/login");

        //设置拦截规则
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/md5a.js", "anon");//anon表示匿名访问，不用登录
        filterChainDefinitionMap.put("/login", "anon");//anon表示匿名访问，不用登录
        filterChainDefinitionMap.put("/loginData", "anon");//anon表示匿名访问，不用登录
        filterChainDefinitionMap.put("/main/**", "authc");//authc表示登录后，才能访问
        filterChainDefinitionMap.put("/logout", "logout");//logout表示登出
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * * 安全管理器
     */
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        securityManager.setRealm(shiroRealm());//设置Realm
        return securityManager;
    }


    @Bean
    public ShiroRealm shiroRealm() {
        ShiroRealm sRealm = new ShiroRealm(); //功能：给shiro认证提供数据库查询支持
        //密文密码匹配器
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName(algorithmName);//设置加密算法（MD5,SHA-1,SHA-256）
        hashedCredentialsMatcher.setHashIterations(hashIterations);//设置加密次数
        sRealm.setCredentialsMatcher(hashedCredentialsMatcher);


        //设置缓存(提升认证效率)
        //RedisCache缓存
        sRealm.setCacheManager(new RedisCacheManager());
        sRealm.setCachingEnabled(true);//开启全局缓存
        sRealm.setAuthenticationCachingEnabled(true);//开启登录认证缓存
        sRealm.setAuthenticationCacheName("com.yida.shiroauthorizationehcache.shiro.ShiroRealm.authenticationCache");//自定义登录认证缓存名称 是key   simpleAuthorizationInfo是value缓存到缓存里面
        sRealm.setAuthorizationCachingEnabled(true);//开启授权认证缓存
        sRealm.setAuthorizationCacheName("com.yida.shiroauthorizationehcache.shiro.ShiroRealm.authorizationCache");//自定义授权认证缓存名称

        return sRealm;
    }

    /**
     * 开启shiro注解，保证能够在spring组件中使用shiro的自身注解
     * * 一般注解在controller类里
     */
    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authAttrSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authAttrSourceAdvisor.setSecurityManager(securityManager());
        return authAttrSourceAdvisor;
    }
    //专门用来解析thymeleaf模板里的Shiro方言
    @Bean(name="shiroDialect")
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
}

