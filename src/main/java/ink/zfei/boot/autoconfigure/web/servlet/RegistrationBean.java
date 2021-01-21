package ink.zfei.boot.autoconfigure.web.servlet;

import ink.zfei.boot.autoconfigure.web.servlet.server.ServletContextInitializer;

import javax.servlet.ServletContext;

public abstract class RegistrationBean implements ServletContextInitializer {
    @Override
    public void onStartup(ServletContext servletContext) {
        String description = getDescription();
        register(description, servletContext);
    }

    protected abstract String getDescription();

    protected abstract void register(String description, ServletContext servletContext);
}
