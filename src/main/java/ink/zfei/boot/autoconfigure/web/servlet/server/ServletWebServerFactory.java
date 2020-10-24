package ink.zfei.boot.autoconfigure.web.servlet.server;

import ink.zfei.boot.autoconfigure.web.server.WebServer;

public interface ServletWebServerFactory {


    WebServer getWebServer(ServletContextInitializer... initializers);

}
