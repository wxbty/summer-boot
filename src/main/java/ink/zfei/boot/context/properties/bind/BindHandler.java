package ink.zfei.boot.context.properties.bind;

import ink.zfei.boot.context.properties.source.ConfigurationPropertyName;

public interface BindHandler {

    default <T> Bindable<T> onStart(ConfigurationPropertyName name, Bindable<T> target, BindContext context) {
        return target;
    }

    default Object onSuccess(ConfigurationPropertyName name, Bindable<?> target, BindContext context, Object result) {
        return result;
    }

    default Object onCreate(ConfigurationPropertyName name, Bindable<?> target, BindContext context, Object result) {
        return result;
    }

    default Object onFailure(ConfigurationPropertyName name, Bindable<?> target, BindContext context, Exception error)
            throws Exception {
        throw error;
    }

    /**
     * Called when binding finishes with either bound or unbound result. This method will
     * not be called when binding failed, even if a handler returns a result from
     * {@link #onFailure}.
     * @param name the name of the element being bound
     * @param target the item being bound
     * @param context the bind context
     * @param result the bound result (may be {@code null})
     */
    default void onFinish(ConfigurationPropertyName name, Bindable<?> target, BindContext context, Object result) {
    }
}
