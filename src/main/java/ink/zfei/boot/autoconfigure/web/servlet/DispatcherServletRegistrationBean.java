package ink.zfei.boot.autoconfigure.web.servlet;

import ink.zfei.boot.mvc.DispatcherServlet;
import ink.zfei.summer.util.Assert;

public class DispatcherServletRegistrationBean extends ServletRegistrationBean<DispatcherServlet>{

    private final String path;

    /**
     * Create a new {@link DispatcherServletRegistrationBean} instance for the given
     * servlet and path.
     * @param servlet the dispatcher servlet
     * @param path the dispatcher servlet path
     */
    public DispatcherServletRegistrationBean(DispatcherServlet servlet, String path) {
        super(servlet);
        Assert.notNull(path, "Path must not be null");
        this.path = path;
        super.addUrlMappings(getServletUrlMapping());
    }

    public String getPath() {
        return this.path;
    }

    String getServletUrlMapping() {
        if (getPath().equals("") || getPath().equals("/")) {
            return "/";
        }
        if (getPath().contains("*")) {
            return getPath();
        }
        if (getPath().endsWith("/")) {
            return getPath() + "*";
        }
        return getPath() + "/*";
    }
}
