package ink.zfei.boot.context.properties.source;

import ink.zfei.summer.core.env.ConfigurableEnvironment;
import ink.zfei.summer.core.env.Environment;
import ink.zfei.summer.core.env.MutablePropertySources;
import ink.zfei.summer.core.env.PropertySource;
import ink.zfei.summer.util.Assert;

public class ConfigurationPropertySources {

    private static final String ATTACHED_PROPERTY_SOURCE_NAME = "configurationProperties";

    public static Iterable<ConfigurationPropertySource> get(Environment environment) {
        Assert.isInstanceOf(ConfigurableEnvironment.class, environment);
        MutablePropertySources sources = ((ConfigurableEnvironment) environment).getPropertySources();
        ConfigurationPropertySourcesPropertySource attached = (ConfigurationPropertySourcesPropertySource) sources
                .get(ATTACHED_PROPERTY_SOURCE_NAME);
        if (attached == null) {
            return from(sources);
        }
        return attached.getSource();
    }

    public static Iterable<ConfigurationPropertySource> from(Iterable<PropertySource<?>> sources) {
        return new SpringConfigurationPropertySources(sources);
    }
}
