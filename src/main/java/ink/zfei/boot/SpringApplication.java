package ink.zfei.boot;

import ink.zfei.boot.context.AnnotationConfigServletWebServerApplicationContext;
import ink.zfei.summer.core.ApplicationContext;
import ink.zfei.summer.core.ApplicationListener;
import ink.zfei.summer.core.env.ConfigurableEnvironment;
import ink.zfei.summer.core.env.StandardEnvironment;
import ink.zfei.summer.core.io.support.SpringFactoriesLoader;
import ink.zfei.summer.web.context.support.StandardServletEnvironment;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class SpringApplication {

    private String defaultPackage;
    private Class<?> starterClass;
    private ConfigurableEnvironment environment;
    private WebApplicationType webApplicationType;

    private List<ApplicationListener> listeners;

    public SpringApplication(Class<?> starterClass) {
        this.starterClass = starterClass;
        //判断应用类型
        this.webApplicationType =WebApplicationType.deduceFromClasspath();
        //通过spi 获取listener、ApplicationContextInitializer
        setListeners((Collection) getSpringFactoriesInstances(ApplicationListener.class));

    }

    private List<String> getSpringFactoriesInstances(Class<?> type) {

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        return SpringFactoriesLoader.loadFactoryNames(type, loader);

    }

    public static ApplicationContext run(Class<?> starterClass, String[] args) {
       return new SpringApplication(starterClass).run(args);
    }


    public ApplicationContext run(String[] args) {

        //1、计时器
        //2、容器引用
        ApplicationContext context = null;
        //3、异常报告
        //4、设置无头模式
        //5、获取springboot级别的所有listener，触发start事件
        //6、准备环境
        ConfigurableEnvironment environment = prepareEnvironment(listeners, args);
        //1、创建spring容器
        defaultPackage = starterClass.getPackage().getName();

        context = createApplicationContext();
        //2、启动内嵌tomcat

//        Object testBean = context.getBean(Water.class);
//        System.out.println(testBean);
        return context;
    }

    private ConfigurableEnvironment prepareEnvironment(List<ApplicationListener> listeners, String[] args) {

        ConfigurableEnvironment environment = getOrCreateEnvironment();
        //todo 把args 放入env中
        return environment;

    }

    private ApplicationContext createApplicationContext() {

        try {
            AnnotationConfigServletWebServerApplicationContext context = new AnnotationConfigServletWebServerApplicationContext(defaultPackage, starterClass);
            return context;
        } catch (IOException | URISyntaxException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }


    public void setListeners(Collection<? extends ApplicationListener> listeners) {
        this.listeners = new ArrayList<>(listeners);
    }


    private <T> List<T> createSpringFactoriesInstances(Class<T> type, Class<?>[] parameterTypes,
                                                       ClassLoader classLoader, Object[] args, Set<String> names) {
        List<T> instances = new ArrayList<>(names.size());
        for (String name : names) {
            try {
                Class<?> instanceClass = Class.forName(name);
                Constructor<?> constructor = instanceClass.getDeclaredConstructor(parameterTypes);
                constructor.setAccessible(true);
                T instance = (T) constructor.newInstance(args);
                instances.add(instance);
            } catch (Throwable ex) {
                throw new IllegalArgumentException("Cannot instantiate " + type + " : " + name, ex);
            }
        }
        return instances;
    }


    private ConfigurableEnvironment getOrCreateEnvironment() {
        if (this.environment != null) {
            return this.environment;
        }
        switch (this.webApplicationType) {
            case SERVLET:
                return new StandardServletEnvironment();
            default:
                return new StandardEnvironment();
        }
    }

}
