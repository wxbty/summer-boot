package ink.zfei.boot.context.properties.source;

public interface ConfigurationPropertySource {

    ConfigurationProperty getConfigurationProperty(ConfigurationPropertyName name);
}
