package ink.zfei.boot.context.properties.source;

import ink.zfei.summer.core.env.PropertySource;

public class ConfigurationPropertySourcesPropertySource extends PropertySource<Iterable<ConfigurationPropertySource>> {

    ConfigurationPropertySourcesPropertySource(String name, Iterable<ConfigurationPropertySource> source) {
        super(name, source);
    }

    @Override
    public Object getProperty(String name) {
        ConfigurationProperty configurationProperty = findConfigurationProperty(name);
        return (configurationProperty != null) ? configurationProperty.getValue() : null;
    }

    private ConfigurationProperty findConfigurationProperty(String name) {
        try {
            return findConfigurationProperty(ConfigurationPropertyName.of(name, true));
        }
        catch (Exception ex) {
            return null;
        }
    }

    private ConfigurationProperty findConfigurationProperty(ConfigurationPropertyName name) {
        if (name == null) {
            return null;
        }
        for (ConfigurationPropertySource configurationPropertySource : getSource()) {
            ConfigurationProperty configurationProperty = configurationPropertySource.getConfigurationProperty(name);
            if (configurationProperty != null) {
                return configurationProperty;
            }
        }
        return null;
    }


}
