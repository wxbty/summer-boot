package ink.zfei.boot.context.properties.bind;

import ink.zfei.boot.context.properties.Binder;
import ink.zfei.boot.context.properties.source.ConfigurationProperty;
import ink.zfei.boot.context.properties.source.ConfigurationPropertySource;

public interface BindContext {

    Binder getBinder();

    /**
     * Return the current depth of the binding. Root binding starts with a depth of
     * {@code 0}. Each subsequent property binding increases the depth by {@code 1}.
     * @return the depth of the current binding
     */
    int getDepth();

    /**
     * Return an {@link Iterable} of the {@link ConfigurationPropertySource sources} being
     * used by the {@link Binder}.
     * @return the sources
     */
    Iterable<ConfigurationPropertySource> getSources();

    /**
     * Return the {@link ConfigurationProperty} actually being bound or {@code null} if
     * the property has not yet been determined.
     * @return the configuration property (may be {@code null}).
     */
    ConfigurationProperty getConfigurationProperty();
}
