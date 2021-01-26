package ink.zfei.boot.context.properties.bind;

import ink.zfei.summer.beans.PropertyEditorRegistry;
import ink.zfei.summer.core.ResolvableType;
import ink.zfei.summer.core.convert.ConversionService;
import ink.zfei.summer.core.convert.TypeDescriptor;
import ink.zfei.summer.util.Assert;

import java.beans.PropertyEditor;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.function.Consumer;

public final class BindConverter {

    private final ConversionService conversionService;

    private BindConverter(ConversionService conversionService,
                          Consumer<PropertyEditorRegistry> propertyEditorInitializer) {
        Assert.notNull(conversionService, "ConversionService must not be null");
        this.conversionService = null;
    }

    public <T> T convert(Object result, Bindable<T> target) {
        return convert(result, target.getType(), target.getAnnotations());
    }

    @SuppressWarnings("unchecked")
    <T> T convert(Object value, ResolvableType type, Annotation... annotations) {
        if (value == null) {
            return null;
        }
        return (T) this.conversionService.convert(value, TypeDescriptor.forObject(value),
                new ResolvableTypeDescriptor(type, annotations));
    }

    public static BindConverter get(ConversionService conversionService,
                                    Consumer<PropertyEditorRegistry> propertyEditorInitializer) {
//        if (conversionService == ApplicationConversionService.getSharedInstance()
//                && propertyEditorInitializer == null) {
//            if (sharedInstance == null) {
//                sharedInstance = new BindConverter(conversionService, propertyEditorInitializer);
//            }
//            return sharedInstance;
//        }
        return new BindConverter(conversionService, propertyEditorInitializer);
    }

    private static class ResolvableTypeDescriptor extends TypeDescriptor {

        ResolvableTypeDescriptor(ResolvableType resolvableType, Annotation[] annotations) {
            super(resolvableType, null, annotations);
        }

    }
}
