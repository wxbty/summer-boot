package ink.zfei.web.context.support;

import ink.zfei.summer.context.support.GenericApplicationContext;
import ink.zfei.summer.lang.Nullable;
import ink.zfei.web.context.ConfigurableWebApplicationContext;

import javax.servlet.ServletContext;

public class GenericWebApplicationContext extends GenericApplicationContext implements ConfigurableWebApplicationContext {

    @Nullable
    private ServletContext servletContext;


    @Override
    public void setServletContext(ServletContext var1) {
        this.servletContext = servletContext;
    }
}
