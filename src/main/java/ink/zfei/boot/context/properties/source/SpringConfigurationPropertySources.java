package ink.zfei.boot.context.properties.source;

import ink.zfei.summer.core.env.PropertySource;
import ink.zfei.summer.util.Assert;

import java.util.Iterator;

public class SpringConfigurationPropertySources implements Iterable<ConfigurationPropertySource> {

    private final Iterable<PropertySource<?>> sources;

    SpringConfigurationPropertySources(Iterable<PropertySource<?>> sources) {
        Assert.notNull(sources, "Sources must not be null");
        this.sources = sources;
    }

    @Override
    public Iterator<ConfigurationPropertySource> iterator() {
        return null;
    }
}
