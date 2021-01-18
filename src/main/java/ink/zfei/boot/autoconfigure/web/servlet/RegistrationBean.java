package ink.zfei.boot.autoconfigure.web.servlet;

import ink.zfei.boot.autoconfigure.web.servlet.server.ServletContextInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public abstract class RegistrationBean implements ServletContextInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        String description = getDescription();
        register(description, servletContext);
    }

    protected abstract String getDescription();

    protected abstract void register(String description, ServletContext servletContext);
}
