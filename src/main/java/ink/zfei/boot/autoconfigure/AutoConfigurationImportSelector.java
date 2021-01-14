package ink.zfei.boot.autoconfigure;

import ink.zfei.summer.core.ImportSelector;
import ink.zfei.summer.core.io.support.SpringFactoriesLoader;
import ink.zfei.summer.core.type.AnnotationMetadata;

import java.util.List;

public class AutoConfigurationImportSelector implements ImportSelector {
    public String[] selectImports(Class aClass) {
        //通过classpath类加载器，加载spi配置类文件sd
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        List<String> autoConfigurations = SpringFactoriesLoader.loadFactoryNames(EnableAutoConfiguration.class, loader);
        if (autoConfigurations != null && autoConfigurations.size() > 0) {
            return autoConfigurations.toArray(new String[0]);
        }

        return null;

    }

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        //通过classpath类加载器，加载spi配置类文件sd
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        List<String> autoConfigurations = SpringFactoriesLoader.loadFactoryNames(EnableAutoConfiguration.class, loader);
        if (autoConfigurations != null && autoConfigurations.size() > 0) {
            return autoConfigurations.toArray(new String[0]);
        }

        return null;
    }
}
