package ink.zfei.boot.autoconfigure;

import ink.zfei.summer.core.ImportSelector;
import ink.zfei.summer.core.io.support.SpringFactoriesLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

public class AutoConfigurationImportSelector implements ImportSelector {
    public String[] selectImports(Class aClass) {
        //通过classpath类加载器，加载spi配置类文件
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        List<String> autoConfigurations = SpringFactoriesLoader.loadFactoryNames(EnableAutoConfiguration.class, loader);
        if (autoConfigurations != null && autoConfigurations.size() > 0) {
            return autoConfigurations.toArray(new String[0]);
        }

        return null;

    }
}
