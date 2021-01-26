package ink.zfei.boot.context.config;

import ink.zfei.boot.SpringApplication;
import ink.zfei.boot.context.event.ApplicationEnvironmentPreparedEvent;
import ink.zfei.boot.env.EnvironmentPostProcessor;
import ink.zfei.summer.core.ApplicationEvent;
import ink.zfei.summer.core.ApplicationListener;
import ink.zfei.summer.core.env.ConfigurableEnvironment;
import ink.zfei.summer.core.io.support.SpringFactoriesLoader;

import java.util.List;

/**
 * 读取application.yml到environment
 */
public class ConfigFileApplicationListener implements EnvironmentPostProcessor, ApplicationListener {

    private static final String DEFAULT_SEARCH_LOCATIONS = "classpath:/,classpath:/config/,file:./,file:./config/";
    private static final String DEFAULT_NAMES = "application";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationEnvironmentPreparedEvent) {
            onApplicationEnvironmentPreparedEvent((ApplicationEnvironmentPreparedEvent) event);
        }
    }

    private void onApplicationEnvironmentPreparedEvent(ApplicationEnvironmentPreparedEvent event) {
        List<EnvironmentPostProcessor> postProcessors = loadPostProcessors();
        postProcessors.add(this);
        for (EnvironmentPostProcessor postProcessor : postProcessors) {
            postProcessor.postProcessEnvironment(event.getEnvironment(), event.getSpringApplication());
        }
    }

    List<EnvironmentPostProcessor> loadPostProcessors() {
        return SpringFactoriesLoader.loadFactories(EnvironmentPostProcessor.class, getClass().getClassLoader());
    }
}
