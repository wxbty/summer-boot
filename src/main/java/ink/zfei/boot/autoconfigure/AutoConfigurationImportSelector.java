package ink.zfei.boot.autoconfigure;

import ink.zfei.summer.core.ImportSelector;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

public class AutoConfigurationImportSelector implements ImportSelector {
    public String[] selectImports(Class aClass) {
        //通过classpath类加载器，加载spi配置类文件
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            Enumeration<URL> urls = loader.getResources("META-INF/spring.factories");
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                InputStream is = url.openStream();
                Properties properties = new Properties();
                properties.load(is);
                String res = properties.getProperty("ink.zfei.boot.autoconfigureEnableAutoConfiguration");
                return new String[]{res};
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
