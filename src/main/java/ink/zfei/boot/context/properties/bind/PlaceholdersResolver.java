package ink.zfei.boot.context.properties.bind;

public interface PlaceholdersResolver {

    PlaceholdersResolver NONE = (value) -> value;

    Object resolvePlaceholders(Object value);
}
