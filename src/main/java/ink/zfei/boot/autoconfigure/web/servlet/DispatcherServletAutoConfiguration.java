package ink.zfei.boot.autoconfigure.web.servlet;

import ink.zfei.boot.mvc.DispatcherServlet;
import ink.zfei.summer.core.annotation.Bean;
import ink.zfei.summer.core.annotation.Configuration;

@Configuration
public class DispatcherServletAutoConfiguration {

    public static final String DEFAULT_DISPATCHER_SERVLET_BEAN_NAME = "dispatcherServlet";

    @Bean(DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)
    public DispatcherServletRegistrationBean dispatcherServletRegistration() {
        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        DispatcherServletRegistrationBean registration = new DispatcherServletRegistrationBean(dispatcherServlet, "/");
        registration.setName("dispatcherServlet");
        return registration;
    }
}
