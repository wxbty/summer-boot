package ink.zfei.boot.context.properties.bind;

public interface DataObjectPropertyBinder {

    Object bindProperty(String propertyName, Bindable<?> target);
}
