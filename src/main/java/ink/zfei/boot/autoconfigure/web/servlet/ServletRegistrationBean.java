package ink.zfei.boot.autoconfigure.web.servlet;

import ink.zfei.summer.core.Conventions;
import ink.zfei.summer.util.Assert;
import ink.zfei.summer.util.ObjectUtils;
import ink.zfei.summer.util.StringUtils;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class ServletRegistrationBean<T extends Servlet> extends DynamicRegistrationBean<ServletRegistration.Dynamic> {

    private T servlet;
    private boolean alwaysMapUrl = true;
    private Set<String> urlMappings = new LinkedHashSet<>();
    private String name;
    private static final String[] DEFAULT_MAPPINGS = {"/*"};

    public void setName(String name) {
        Assert.hasLength(name, "Name must not be empty");
        this.name = name;
    }

    public ServletRegistrationBean(T servlet, String... urlMappings) {
        this(servlet, true, urlMappings);
    }

    public ServletRegistrationBean(T servlet, boolean alwaysMapUrl, String... urlMappings) {
        Assert.notNull(servlet, "Servlet must not be null");
        Assert.notNull(urlMappings, "UrlMappings must not be null");
        this.servlet = servlet;
        this.alwaysMapUrl = alwaysMapUrl;
        this.urlMappings.addAll(Arrays.asList(urlMappings));
    }

    @Override
    protected ServletRegistration.Dynamic addRegistration(String description, ServletContext servletContext) {
        String name = getServletName();
        return servletContext.addServlet(name, this.servlet);
    }


    public String getServletName() {
        return getOrDeduceName(this.servlet);
    }


    protected final String getOrDeduceName(Object value) {
        return (this.name != null) ? this.name : Conventions.getVariableName(value);
    }

    @Override
    protected String getDescription() {
        Assert.notNull(this.servlet, "Servlet must not be null");
        return "servlet " + getServletName();
    }

    public T getServlet() {
        return this.servlet;
    }

    public void addUrlMappings(String... urlMappings) {
        Assert.notNull(urlMappings, "UrlMappings must not be null");
        this.urlMappings.addAll(Arrays.asList(urlMappings));
    }

    /**
     * tomcat注册servlet后，对servlet进行配置，如url映射路径、是否处理文件、启动顺序
     * @param registration
     */
    @Override
    protected void configure(ServletRegistration.Dynamic registration) {
        super.configure(registration);
        String[] urlMapping = StringUtils.toStringArray(this.urlMappings);
        if (urlMapping.length == 0 && this.alwaysMapUrl) {
            urlMapping = DEFAULT_MAPPINGS;
        }
        if (!ObjectUtils.isEmpty(urlMapping)) {
            /**
             * 往tomcat注册DispatchServlet后，必须配置urlMapping
             * addMapping方法会移除默认DefaultServlet的url，从而使DispatchServlet作为默认servlet
             */
            registration.addMapping(urlMapping);
        }
    }
}

