package ink.zfei.web.context;

import ink.zfei.summer.context.ConfigurableApplicationContext;
import ink.zfei.summer.lang.Nullable;

import javax.servlet.ServletContext;

/**
 * 可配置的web容器（写入配置）
 */
public interface ConfigurableWebApplicationContext extends WebApplicationContext, ConfigurableApplicationContext {

    void setServletContext(@Nullable ServletContext var1);

}
