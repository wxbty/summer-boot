package ink.zfei.boot.autoconfigure.web.servlet;

import ink.zfei.boot.autoconfigure.web.embedded.tomcat.TomcatServletWebServerFactory;
import ink.zfei.summer.core.annotation.Bean;
import ink.zfei.summer.core.annotation.Configuration;

/**
 * Configuration classes for servlet web servers
 * web容器配置类，springboot自带tomcat、jetty、undertow
 * summer只实现以上的tomcat，后续假如netty作为web容器
 */
@Configuration
//@Import(ServletWebServerFactoryConfiguration.EmbeddedTomcat.class)
public class ServletWebServerFactoryAutoConfiguration {

    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        return factory;
    }
}
