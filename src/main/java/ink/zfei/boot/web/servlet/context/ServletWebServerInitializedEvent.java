package ink.zfei.boot.web.servlet.context;

import ink.zfei.boot.autoconfigure.web.server.WebServer;
import ink.zfei.summer.core.ApplicationEvent;

public class ServletWebServerInitializedEvent extends ApplicationEvent {
    public ServletWebServerInitializedEvent(WebServer webServer, ServletWebServerApplicationContext servletWebServerApplicationContext) {
    }
}
