package ink.zfei.boot.autoconfigure.web.server;

/**
 * 标记创建webServer的工厂
 * webServer不一定是http服务器（servlet），也可能是其他类型服务器，比如netty
 * 和ServletWebServerFactory进行区分
 */
public interface WebServerFactory {
}
