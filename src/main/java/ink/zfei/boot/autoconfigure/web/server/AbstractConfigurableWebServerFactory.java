package ink.zfei.boot.autoconfigure.web.server;

/**
 * ConfigurableWebServerFactory实现类
 */
public abstract class AbstractConfigurableWebServerFactory implements ConfigurableWebServerFactory{

    private int port = 8080;

    public int getPort() {
        return this.port;
    }

    @Override
    public void setPort(int port) {
        this.port = port;
    }



}
