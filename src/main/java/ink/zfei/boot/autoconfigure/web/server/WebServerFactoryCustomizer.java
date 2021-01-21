package ink.zfei.boot.autoconfigure.web.server;

import ink.zfei.boot.autoconfigure.web.servlet.server.ConfigurableServletWebServerFactory;

@FunctionalInterface
public interface WebServerFactoryCustomizer<T extends WebServerFactory> {

    /**
     * 对webServer的工厂进行定制，如设置端口、协议等外部配置
     * Customizer来自spring容器，可以代替tomcat的web.xml
     * @param factory the web server factory to customize
     */
    void customize(ConfigurableServletWebServerFactory factory);

}
