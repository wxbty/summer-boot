package ink.zfei.boot.context.properties.bind;

import ink.zfei.summer.core.env.ConfigurableEnvironment;
import ink.zfei.summer.core.env.Environment;
import ink.zfei.summer.core.env.PropertySource;
import ink.zfei.summer.core.env.PropertySources;
import ink.zfei.summer.util.Assert;
import ink.zfei.summer.util.PropertyPlaceholderHelper;

public class PropertySourcesPlaceholdersResolver implements PlaceholdersResolver{

    private final Iterable<PropertySource<?>> sources;
    private final PropertyPlaceholderHelper helper;

    public PropertySourcesPlaceholdersResolver(Environment environment) {
        this(getSources(environment), null);
    }

    public PropertySourcesPlaceholdersResolver(Iterable<PropertySource<?>> sources, PropertyPlaceholderHelper helper) {
        this.sources = sources;
        this.helper = helper;
    }

    private static PropertySources getSources(Environment environment) {
        Assert.notNull(environment, "Environment must not be null");
        Assert.isInstanceOf(ConfigurableEnvironment.class, environment,
                "Environment must be a ConfigurableEnvironment");
        return ((ConfigurableEnvironment) environment).getPropertySources();
    }

    @Override
    public Object resolvePlaceholders(Object value) {
        if (value instanceof String) {
            return this.helper.replacePlaceholders((String) value, this::resolvePlaceholder);
        }
        return value;
    }

    protected String resolvePlaceholder(String placeholder) {
        if (this.sources != null) {
            for (PropertySource<?> source : this.sources) {
                Object value = source.getProperty(placeholder);
                if (value != null) {
                    return String.valueOf(value);
                }
            }
        }
        return null;
    }
}
