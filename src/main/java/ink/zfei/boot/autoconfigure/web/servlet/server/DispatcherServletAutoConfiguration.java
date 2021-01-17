package ink.zfei.boot.autoconfigure.web.servlet.server;

import ink.zfei.boot.autoconfigure.http.HttpProperties;
import ink.zfei.boot.autoconfigure.web.servlet.WebMvcProperties;
import ink.zfei.boot.mvc.DispatcherServlet;
import ink.zfei.summer.core.annotation.Bean;
import ink.zfei.summer.core.annotation.Configuration;

@Configuration
public class DispatcherServletAutoConfiguration {

    public static final String DEFAULT_DISPATCHER_SERVLET_BEAN_NAME = "dispatcherServlet";


    @Bean(DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)
    public DispatcherServlet dispatcherServlet(HttpProperties httpProperties, WebMvcProperties webMvcProperties) {
        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        return dispatcherServlet;
    }
}
