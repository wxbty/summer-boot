package ink.zfei.boot.autoconfigure.web.servlet.server;

import ink.zfei.boot.autoconfigure.web.server.AbstractConfigurableWebServerFactory;

/**
 * 通用（端口等设置）+具体（servlet服务属性设置）具体实现
 * 通用的已经有具体实现AbstractConfigurableWebServerFactory，继承它即可
 */
public abstract class AbstractServletWebServerFactory extends AbstractConfigurableWebServerFactory
        implements ConfigurableServletWebServerFactory {

    //应用名
    private String displayName;

    @Override
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
