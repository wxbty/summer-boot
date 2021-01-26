package ink.zfei.boot;

import ink.zfei.summer.core.env.ConfigurableEnvironment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SpringApplicationRunListeners {

    private final List<SpringApplicationRunListener> listeners;

    public SpringApplicationRunListeners(Collection<? extends SpringApplicationRunListener> listeners) {
        this.listeners = new ArrayList<>(listeners);
    }

    void starting() {
        for (SpringApplicationRunListener listener : this.listeners) {
            listener.starting();
        }
    }

    void environmentPrepared(ConfigurableEnvironment environment) {
        for (SpringApplicationRunListener listener : this.listeners) {
            listener.environmentPrepared(environment);
        }
    }
}
