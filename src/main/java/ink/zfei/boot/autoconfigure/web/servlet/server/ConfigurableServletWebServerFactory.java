package ink.zfei.boot.autoconfigure.web.servlet.server;

import ink.zfei.boot.autoconfigure.web.server.ConfigurableWebServerFactory;

/**
 * 一个具体的可配置web服务工厂= 可配置通用web服务 + 具体类型web服务（如servlet或响应式服务）
 * 配置通用的servlet服务属性（jetty、tomcat都可配的属性、如contextPath、session等）
 */
public interface ConfigurableServletWebServerFactory extends ConfigurableWebServerFactory, ServletWebServerFactory {

    //一个servlet服务jvm进程，可同时部署多个应用，配置应用名
    void setDisplayName(String displayName);

}
