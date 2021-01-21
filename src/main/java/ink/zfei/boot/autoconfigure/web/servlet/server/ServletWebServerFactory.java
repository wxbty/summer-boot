package ink.zfei.boot.autoconfigure.web.servlet.server;

import ink.zfei.boot.autoconfigure.web.server.WebServer;

/**
 * webServer的工厂，提供http/servlet类型的webServer
 */
public interface ServletWebServerFactory {


    WebServer getWebServer(ServletContextInitializer... initializers);

}
