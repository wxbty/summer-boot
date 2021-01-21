package ink.zfei.boot.autoconfigure.web.embedded.tomcat;

import ink.zfei.boot.autoconfigure.web.server.ConfigurableWebServerFactory;
import org.apache.catalina.Valve;

/**
 * 配置tomcat特有的属性
 */
public interface ConfigurableTomcatWebServerFactory extends ConfigurableWebServerFactory {

    /**
     * tomcat各组件通过Valve连接，可自定义Valve
     */
    void addEngineValves(Valve... engineValves);
}
