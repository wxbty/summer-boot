package ink.zfei.boot.autoconfigure.web.servlet;

import ink.zfei.boot.autoconfigure.web.ServerProperties;
import ink.zfei.boot.autoconfigure.web.embedded.tomcat.TomcatServletWebServerFactory;
import ink.zfei.boot.autoconfigure.web.server.WebServerFactoryCustomizerBeanPostProcessor;
import ink.zfei.summer.core.annotation.Bean;
import ink.zfei.summer.core.annotation.Configuration;

@Configuration
public class ServletWebServerFactoryConfiguration {

    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        return factory;
    }

    @Bean
    public ServerProperties serverProperties() {
        return new ServerProperties();
    }

    @Bean
    public ServletWebServerFactoryCustomizer servletWebServerFactoryCustomizer(ServerProperties serverProperties) {
        return new ServletWebServerFactoryCustomizer(serverProperties);
    }

    /**
     * 拦截webServer工厂实例化过程，通过外部配置工厂属性
     */
    @Bean
    public WebServerFactoryCustomizerBeanPostProcessor webServerFactoryCustomizerBeanPostProcessor() {
        return new WebServerFactoryCustomizerBeanPostProcessor();
    }
}
