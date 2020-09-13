package ink.zfei.boot;

public enum WebApplicationType {

    /**
     * The application should not run as a web application and should not start an
     * embedded web server.
     */
    NONE,

    /**
     * The application should run as a servlet-based web application and should start an
     * embedded servlet web server.
     */
    SERVLET,

    /**
     * The application should run as a reactive web application and should start an
     * embedded reactive web server.
     */
    REACTIVE;

    private static final String[] SERVLET_INDICATOR_CLASSES = { "javax.servlet.Servlet",
            "org.springframework.web.context.ConfigurableWebApplicationContext" };

    private static final String WEBMVC_INDICATOR_CLASS = "org.springframework.web.servlet.DispatcherServlet";

    private static final String WEBFLUX_INDICATOR_CLASS = "org.springframework.web.reactive.DispatcherHandler";

    private static final String JERSEY_INDICATOR_CLASS = "org.glassfish.jersey.servlet.ServletContainer";

    private static final String SERVLET_APPLICATION_CONTEXT_CLASS = "org.springframework.web.context.WebApplicationContext";

    private static final String REACTIVE_APPLICATION_CONTEXT_CLASS = "org.springframework.boot.web.reactive.context.ReactiveWebApplicationContext";

    public static WebApplicationType deduceFromClasspath() {
//        if (ClassUtils.isPresent(WEBFLUX_INDICATOR_CLASS, null) && !ClassUtils.isPresent(WEBMVC_INDICATOR_CLASS, null)
//                && !ClassUtils.isPresent(JERSEY_INDICATOR_CLASS, null)) {
//            return WebApplicationType.REACTIVE;
//        }
//        for (String className : SERVLET_INDICATOR_CLASSES) {
//            if (!ClassUtils.isPresent(className, null)) {
//                return WebApplicationType.NONE;
//            }
//        }
        return WebApplicationType.SERVLET;
    }

    static WebApplicationType deduceFromApplicationContext(Class<?> applicationContextClass) {
//        if (isAssignable(SERVLET_APPLICATION_CONTEXT_CLASS, applicationContextClass)) {
//            return WebApplicationType.SERVLET;
//        }
//        if (isAssignable(REACTIVE_APPLICATION_CONTEXT_CLASS, applicationContextClass)) {
//            return WebApplicationType.REACTIVE;
//        }
        return WebApplicationType.SERVLET;
    }

//    private static boolean isAssignable(String target, Class<?> type) {
//        try {
//            return ClassUtils.resolveClassName(target, null).isAssignableFrom(type);
//        }
//        catch (Throwable ex) {
//            return false;
//        }
//    }

}
