package ink.zfei.boot.autoconfigure.web.servlet.server;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public interface ServletContextInitializer {

    void onStartup(ServletContext servletContext) throws ServletException;

}
