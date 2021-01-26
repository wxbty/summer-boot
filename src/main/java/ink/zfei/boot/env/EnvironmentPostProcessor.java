package ink.zfei.boot.env;

import ink.zfei.boot.SpringApplication;
import ink.zfei.summer.core.env.ConfigurableEnvironment;

@FunctionalInterface
public interface EnvironmentPostProcessor {

    /**
     * Post-process the given {@code environment}.
     * @param environment the environment to post-process
     * @param application the application to which the environment belongs
     */
    void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application);

}
