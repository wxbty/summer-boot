package ink.zfei.boot;

import ink.zfei.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import ink.zfei.summer.beans.BeanDefinitionRegistry;
import ink.zfei.summer.beans.factory.config.ConfigurableListableBeanFactory;
import ink.zfei.summer.context.ConfigurableApplicationContext;
import ink.zfei.summer.core.AbstractApplicationContext;
import ink.zfei.summer.core.ApplicationContext;
import ink.zfei.summer.core.ApplicationListener;
import ink.zfei.summer.core.env.ConfigurableEnvironment;
import ink.zfei.summer.core.env.StandardEnvironment;
import ink.zfei.summer.core.io.support.SpringFactoriesLoader;
import ink.zfei.summer.util.Assert;
import ink.zfei.summer.util.CollectionUtils;
import ink.zfei.summer.web.context.support.StandardServletEnvironment;

import java.lang.reflect.Constructor;
import java.security.AccessControlException;
import java.util.*;

public class SpringApplication {

    private String defaultPackage;
    private ConfigurableEnvironment environment;
    private WebApplicationType webApplicationType;

    private List<ApplicationListener> listeners;
    private Set<Class<?>> primarySources;
    private Set<String> sources = new LinkedHashSet<>();

    public SpringApplication(Class<?>... primarySources) {
        this.primarySources = new LinkedHashSet<>(Arrays.asList(primarySources));
        //判断应用类型
        this.webApplicationType = WebApplicationType.deduceFromClasspath();
        //通过spi 获取listener、ApplicationContextInitializer
        setListeners((Collection) getSpringFactoriesInstances(ApplicationListener.class));

    }

    private List<String> getSpringFactoriesInstances(Class<?> type) {

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        return SpringFactoriesLoader.loadFactoryNames(type, loader);

    }

    public static ApplicationContext run(Class<?> primarySource, String[] args) {
        return new SpringApplication(primarySource).run(args);
    }


    public ConfigurableApplicationContext run(String[] args) {

        //1、计时器
        //2、容器引用
        ConfigurableApplicationContext context = null;
        //3、异常报告
        //4、设置无头模式
        //5、获取springboot级别的所有listener，触发start事件
        //6、准备环境
        ConfigurableEnvironment environment = prepareEnvironment(listeners, args);
        //1、创建spring容器
        context = createApplicationContext();
        //2、启动内嵌tomcat
        prepareContext(context, environment);

        refreshContext(context);
        //        Object testBean = context.getBean(Water.class);
//        System.out.println(testBean);
        return context;
    }

    private void refreshContext(ConfigurableApplicationContext context) {
        refresh(context);
    }

    protected void refresh(ApplicationContext applicationContext) {
        Assert.isInstanceOf(AbstractApplicationContext.class, applicationContext);
        ((AbstractApplicationContext) applicationContext).refresh();
    }

    private void prepareContext(ConfigurableApplicationContext context, ConfigurableEnvironment environment) {
        context.setEnvironment(environment);
        postProcessApplicationContext(context);
        applyInitializers(context);
//        listeners.contextPrepared(context);

        // Add boot specific singleton beans
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();

        // Load the sources
        Set<Object> sources = getAllSources();
        Assert.notEmpty(sources, "Sources must not be empty");
        load(context, sources.toArray(new Object[0]));
//        listeners.contextLoaded(context);
    }

    private void load(ConfigurableApplicationContext context, Object[] sources) {
        BeanDefinitionLoader loader = createBeanDefinitionLoader(getBeanDefinitionRegistry(context), sources);
        loader.load();
    }

    private BeanDefinitionLoader createBeanDefinitionLoader(BeanDefinitionRegistry registry, Object[] sources) {
        return new BeanDefinitionLoader(registry, sources);
    }

    private BeanDefinitionRegistry getBeanDefinitionRegistry(ApplicationContext context) {
        if (context instanceof BeanDefinitionRegistry) {
            return (BeanDefinitionRegistry) context;
        }
        if (context instanceof AbstractApplicationContext) {
            return (BeanDefinitionRegistry) ((AbstractApplicationContext) context).getBeanFactory();
        }
        throw new IllegalStateException("Could not locate BeanDefinitionRegistry");
    }

    private Set<Object> getAllSources() {
        Set<Object> allSources = new LinkedHashSet<>();
        if (!CollectionUtils.isEmpty(this.primarySources)) {
            allSources.addAll(this.primarySources);
        }
        if (!CollectionUtils.isEmpty(this.sources)) {
            allSources.addAll(this.sources);
        }
        return Collections.unmodifiableSet(allSources);
    }

    private void applyInitializers(ConfigurableApplicationContext context) {
    }

    //自定义bean命名策略、resourceLoader、参数转换器
    private void postProcessApplicationContext(ConfigurableApplicationContext context) {
    }

    private ConfigurableEnvironment prepareEnvironment(List<ApplicationListener> listeners, String[] args) {

        ConfigurableEnvironment environment = getOrCreateEnvironment();
        //todo 把args 放入env中
        return environment;

    }

    private ConfigurableApplicationContext createApplicationContext() {

        return new AnnotationConfigServletWebServerApplicationContext();


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
