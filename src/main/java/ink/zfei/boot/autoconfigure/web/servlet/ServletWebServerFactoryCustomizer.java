package ink.zfei.boot.autoconfigure.web.servlet;

import ink.zfei.boot.autoconfigure.web.ServerProperties;
import ink.zfei.boot.autoconfigure.web.server.WebServerFactoryCustomizer;
import ink.zfei.boot.autoconfigure.web.servlet.server.ConfigurableServletWebServerFactory;

public class ServletWebServerFactoryCustomizer implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    //ServerProperties读取自application.yml等配置文件
    private final ServerProperties serverProperties;

    public ServletWebServerFactoryCustomizer(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
        factory.setPort(this.serverProperties.getPort());
    }
}
