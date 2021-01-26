package ink.zfei.boot;

import ink.zfei.summer.core.env.ConfigurableEnvironment;

public interface SpringApplicationRunListener {

    default void starting() {
    }

    default void environmentPrepared(ConfigurableEnvironment environment) {
    }
}
