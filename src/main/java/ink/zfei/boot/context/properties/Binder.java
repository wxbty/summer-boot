package ink.zfei.boot.context.properties;

import ink.zfei.boot.context.properties.bind.*;
import ink.zfei.boot.context.properties.source.ConfigurationProperty;
import ink.zfei.boot.context.properties.source.ConfigurationPropertyName;
import ink.zfei.boot.context.properties.source.ConfigurationPropertySource;
import ink.zfei.boot.context.properties.source.ConfigurationPropertySources;
import ink.zfei.summer.beans.PropertyEditorRegistry;
import ink.zfei.summer.core.convert.ConversionService;
import ink.zfei.summer.core.env.Environment;
import ink.zfei.summer.util.Assert;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Binder {

    private final Iterable<ConfigurationPropertySource> sources;

    private final PlaceholdersResolver placeholdersResolver;

    private final ConversionService conversionService;

    private final Consumer<PropertyEditorRegistry> propertyEditorInitializer;

    private final BindHandler defaultBindHandler;

    private final List<DataObjectBinder> dataObjectBinders;


    public Binder(Iterable<ConfigurationPropertySource> sources, PlaceholdersResolver placeholdersResolver,
                  ConversionService conversionService, Consumer<PropertyEditorRegistry> propertyEditorInitializer,
                  BindHandler defaultBindHandler) {
        Assert.notNull(sources, "Sources must not be null");
        this.sources = sources;
        this.placeholdersResolver = (placeholdersResolver != null) ? placeholdersResolver : PlaceholdersResolver.NONE;
        this.conversionService = conversionService;
        this.propertyEditorInitializer = propertyEditorInitializer;
        this.defaultBindHandler = defaultBindHandler;
        this.dataObjectBinders = null;
    }

    public static Binder get(Environment environment) {
        return get(environment, null);
    }

    public static Binder get(Environment environment, BindHandler defaultBindHandler) {
        Iterable<ConfigurationPropertySource> sources = ConfigurationPropertySources.get(environment);
        PropertySourcesPlaceholdersResolver placeholdersResolver = new PropertySourcesPlaceholdersResolver(environment);
        return new Binder(sources, placeholdersResolver, null, null, defaultBindHandler);
    }

    public <T> BindResult<T> bind(String name, Bindable<T> target) {
        return bind(ConfigurationPropertyName.of(name), target, null);
    }

    public <T> BindResult<T> bind(ConfigurationPropertyName name, Bindable<T> target, BindHandler handler) {
        T bound = bind(name, target, handler, false);
        return BindResult.of(bound);
    }

    private <T> T bind(ConfigurationPropertyName name, Bindable<T> target, BindHandler handler, boolean create) {
        Assert.notNull(name, "Name must not be null");
        Assert.notNull(target, "Target must not be null");
        handler = (handler != null) ? handler : this.defaultBindHandler;
        Context context = new Context();
        return bind(name, target, handler, context, false, create);
    }

    private <T> T bind(ConfigurationPropertyName name, Bindable<T> target, BindHandler handler, Context context,
                       boolean allowRecursiveBinding, boolean create) {
        context.clearConfigurationProperty();
        Bindable<T> replacementTarget = handler.onStart(name, target, context);
        if (replacementTarget == null) {
            return handleBindResult(name, target, handler, context, null, create);
        }
        target = replacementTarget;
        Object bound = bindObject(name, target, handler, context, allowRecursiveBinding);
        return handleBindResult(name, target, handler, context, bound, create);

    }

    private <T> T handleBindResult(ConfigurationPropertyName name, Bindable<T> target, BindHandler handler,
                                   Context context, Object result, boolean create) {
        if (result != null) {
            result = handler.onSuccess(name, target, context, result);
            result = context.getConverter().convert(result, target);
        }
        if (result == null && create) {
            result = create(target, context);
            result = handler.onCreate(name, target, context, result);
            result = context.getConverter().convert(result, target);
            Assert.state(result != null, () -> "Unable to create instance for " + target.getType());
        }
        handler.onFinish(name, target, context, result);
        return context.getConverter().convert(result, target);
    }

    private Object create(Bindable<?> target, Context context) {
        return null;
    }


   public final class Context implements BindContext {

        private final BindConverter converter;

        private int depth;

        private final List<ConfigurationPropertySource> source = Arrays.asList((ConfigurationPropertySource) null);

        private int sourcePushCount;

        private final Deque<Class<?>> dataObjectBindings = new ArrayDeque<>();

        private final Deque<Class<?>> constructorBindings = new ArrayDeque<>();

        private ConfigurationProperty configurationProperty;

        Context() {
            this.converter = BindConverter.get(Binder.this.conversionService, Binder.this.propertyEditorInitializer);
        }

        private void increaseDepth() {
            this.depth++;
        }

        private void decreaseDepth() {
            this.depth--;
        }

        private <T> T withSource(ConfigurationPropertySource source, Supplier<T> supplier) {
            if (source == null) {
                return supplier.get();
            }
            this.source.set(0, source);
            this.sourcePushCount++;
            try {
                return supplier.get();
            } finally {
                this.sourcePushCount--;
            }
        }

        private <T> T withDataObject(Class<?> type, Supplier<T> supplier) {
            this.dataObjectBindings.push(type);
            try {
                return withIncreasedDepth(supplier);
            } finally {
                this.dataObjectBindings.pop();
            }
        }

        private boolean isBindingDataObject(Class<?> type) {
            return this.dataObjectBindings.contains(type);
        }

        private <T> T withIncreasedDepth(Supplier<T> supplier) {
            increaseDepth();
            try {
                return supplier.get();
            } finally {
                decreaseDepth();
            }
        }

        private void setConfigurationProperty(ConfigurationProperty configurationProperty) {
            this.configurationProperty = configurationProperty;
        }

        void clearConfigurationProperty() {
            this.configurationProperty = null;
        }

        void pushConstructorBoundTypes(Class<?> value) {
            this.constructorBindings.push(value);
        }

        boolean isNestedConstructorBinding() {
            return !this.constructorBindings.isEmpty();
        }

        void popConstructorBoundTypes() {
            this.constructorBindings.pop();
        }

        PlaceholdersResolver getPlaceholdersResolver() {
            return Binder.this.placeholdersResolver;
        }

        BindConverter getConverter() {
            return this.converter;
        }

        @Override
        public Binder getBinder() {
            return Binder.this;
        }

        @Override
        public int getDepth() {
            return this.depth;
        }

        @Override
        public Iterable<ConfigurationPropertySource> getSources() {
            if (this.sourcePushCount > 0) {
                return this.source;
            }
            return Binder.this.sources;
        }

        @Override
        public ConfigurationProperty getConfigurationProperty() {
            return this.configurationProperty;
        }

    }

    private <T> Object bindObject(ConfigurationPropertyName name, Bindable<T> target, BindHandler handler,
                                  Context context, boolean allowRecursiveBinding) {
        ConfigurationProperty property = findProperty(name, context);
        if (property == null && context.depth != 0) {
            return null;
        }
//        AggregateBinder<?> aggregateBinder = getAggregateBinder(target, context);
//        if (aggregateBinder != null) {
//            return bindAggregate(name, target, handler, context, aggregateBinder);
//        }
        if (property != null) {
            return bindProperty(target, context, property);
        }
        return bindDataObject(name, target, handler, context, allowRecursiveBinding);
    }

    private <T> Object bindProperty(Bindable<T> target, Context context, ConfigurationProperty property) {
        context.setConfigurationProperty(property);
        Object result = property.getValue();
        result = this.placeholdersResolver.resolvePlaceholders(result);
        result = context.getConverter().convert(result, target);
        return result;
    }

    private ConfigurationProperty findProperty(ConfigurationPropertyName name, Context context) {
        if (name.isEmpty()) {
            return null;
        }
        for (ConfigurationPropertySource source : context.getSources()) {
            ConfigurationProperty property = source.getConfigurationProperty(name);
            if (property != null) {
                return property;
            }
        }
        return null;
    }

    private Object bindDataObject(ConfigurationPropertyName name, Bindable<?> target, BindHandler handler,
                                  Context context, boolean allowRecursiveBinding) {
//        if (isUnbindableBean(name, target, context)) {
//            return null;
//        }
        Class<?> type = target.getType().resolve(Object.class);
        if (!allowRecursiveBinding && context.isBindingDataObject(type)) {
            return null;
        }
        DataObjectPropertyBinder propertyBinder = (propertyName, propertyTarget) -> bind(name.append(propertyName),
                propertyTarget, handler, context, false, false);
        return context.withDataObject(type, () -> {
            for (DataObjectBinder dataObjectBinder : this.dataObjectBinders) {
                Object instance = dataObjectBinder.bind(name, target, context, propertyBinder);
                if (instance != null) {
                    return instance;
                }
            }
            return null;
        });
    }


}
