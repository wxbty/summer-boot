package ink.zfei.boot.web.servlet.context;

import ink.zfei.boot.autoconfigure.web.server.WebServer;
import ink.zfei.boot.autoconfigure.web.servlet.server.ServletWebServerFactory;
import ink.zfei.summer.beans.factory.config.ConfigurableListableBeanFactory;
import ink.zfei.summer.context.AnnotationConfigApplicationContext;
import ink.zfei.summer.context.ApplicationContextException;
import ink.zfei.summer.context.annotation.AnnotatedBeanDefinitionReader;
import ink.zfei.summer.context.annotation.ClassPathBeanDefinitionScanner;
import ink.zfei.summer.core.annotation.AnnotationConfigUtils;
import ink.zfei.summer.util.Assert;
import ink.zfei.summer.util.ClassUtils;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class AnnotationConfigServletWebServerApplicationContext extends ServletWebServerApplicationContext {

    private final AnnotatedBeanDefinitionReader reader;
    private final ClassPathBeanDefinitionScanner scanner;

    private final Set<Class<?>> annotatedClasses = new LinkedHashSet<>();


    private int DEFAULT_PORT = 8086;
    public static String TOMCAT_HOSTNAME = "127.0.0.1";
    public static String WEBAPP_PATH = "src/main";
    public static String WEBINF_CLASSES = "/WEB-INF/classes";
    public static String CLASS_PATH = "target/classes";
    public static String INTERNAL_PATH = "/";

    public AnnotationConfigServletWebServerApplicationContext()  {
        this.reader = new AnnotatedBeanDefinitionReader(this);
        this.scanner = new ClassPathBeanDefinitionScanner(this);
    }

    public AnnotationConfigServletWebServerApplicationContext(Class<?>... annotatedClasses) {
        this();
        register(annotatedClasses);
        refresh();
    }

    public void register(Class<?>... annotatedClasses) {
        Assert.notEmpty(annotatedClasses, "At least one annotated class must be specified");
        this.annotatedClasses.addAll(Arrays.asList(annotatedClasses));
    }

    @Override
    protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        super.postProcessBeanFactory(beanFactory);
//        if (this.basePackages != null && this.basePackages.length > 0) {
//            this.scanner.scan(this.basePackages);
//        }
        if (!this.annotatedClasses.isEmpty()) {
            this.reader.register(ClassUtils.toClassArray(this.annotatedClasses));
        }
    }

}
