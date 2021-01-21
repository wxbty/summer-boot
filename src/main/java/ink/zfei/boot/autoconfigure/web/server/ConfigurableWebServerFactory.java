package ink.zfei.boot.autoconfigure.web.server;

/**
 * 可配置的WebServerFactory
 * web服务通用属性配置，比如监听的ip、端口、ssl属性等
 */
public interface ConfigurableWebServerFactory extends WebServerFactory {

    /**
     * 既然提供web服务，端口肯定得可配吧
     */
    void setPort(int port);
}
