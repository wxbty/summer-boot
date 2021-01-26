package ink.zfei.boot.context.properties.bind;

import ink.zfei.boot.context.properties.Binder;
import ink.zfei.boot.context.properties.source.ConfigurationPropertyName;

public interface DataObjectBinder {

    <T> T bind(ConfigurationPropertyName name, Bindable<T> target, Binder.Context context,
               DataObjectPropertyBinder propertyBinder);

    /**
     * Return a newly created instance or {@code null} if the {@link DataObjectBinder}
     * does not support the specified {@link Bindable}.
     * @param target the bindable to create
     * @param context the bind context
     * @param <T> the source type
     * @return the created instance
     */
    <T> T create(Bindable<T> target, Binder.Context context);

}
