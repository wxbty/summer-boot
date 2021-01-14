package ink.zfei.boot;

import ink.zfei.summer.beans.BeanDefinitionRegistry;
import ink.zfei.summer.context.annotation.AnnotatedBeanDefinitionReader;
import ink.zfei.summer.util.Assert;

public class BeanDefinitionLoader {

    private final Object[] sources;
    private final AnnotatedBeanDefinitionReader annotatedReader;

    BeanDefinitionLoader(BeanDefinitionRegistry registry, Object... sources) {
        Assert.notEmpty(sources, "Sources must not be empty");
        this.sources = sources;
        this.annotatedReader = new AnnotatedBeanDefinitionReader(registry);
    }

    int load() {
        int count = 0;
        for (Object source : this.sources) {
            count += load(source);
        }
        return count;
    }

    private int load(Object source) {

        if (source instanceof Class<?>) {
            return load((Class<?>) source);
        }
        throw new IllegalArgumentException("Invalid source type " + source.getClass());
    }

    private int load(Class<?> source) {

        this.annotatedReader.register(source);
        return 1;
    }
}
