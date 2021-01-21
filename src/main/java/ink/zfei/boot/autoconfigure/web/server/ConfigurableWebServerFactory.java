package ink.zfei.boot.autoconfigure.web.server;

public interface ConfigurableWebServerFactory extends WebServerFactory{

    void setPort(int port);
}
