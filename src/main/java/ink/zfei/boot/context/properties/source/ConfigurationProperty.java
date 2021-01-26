package ink.zfei.boot.context.properties.source;

import ink.zfei.summer.util.Assert;

public class ConfigurationProperty {

    private final ConfigurationPropertyName name;

    private final Object value;


    public ConfigurationProperty(ConfigurationPropertyName name, Object value) {
        Assert.notNull(name, "Name must not be null");
        Assert.notNull(value, "Value must not be null");
        this.name = name;
        this.value = value;
    }

    public Object getValue() {
        return this.value;
    }
}
