package ink.zfei.boot.web.servlet;

import ink.zfei.boot.autoconfigure.web.servlet.ServletRegistrationBean;
import ink.zfei.boot.autoconfigure.web.servlet.server.ServletContextInitializer;
import ink.zfei.summer.beans.factory.ListableBeanFactory;
import ink.zfei.summer.util.LinkedMultiValueMap;
import ink.zfei.summer.util.MultiValueMap;

import javax.servlet.Servlet;
import java.util.*;
import java.util.stream.Collectors;

public class ServletContextInitializerBeans extends AbstractCollection<ServletContextInitializer>{

    private final MultiValueMap<Class<?>, ServletContextInitializer> initializers;
    private final List<Class<? extends ServletContextInitializer>> initializerTypes;
    private final Set<Object> seen = new HashSet<>();
    private List<ServletContextInitializer> sortedList;

    @SafeVarargs
    public ServletContextInitializerBeans(ListableBeanFactory beanFactory,
                                          Class<? extends ServletContextInitializer>... initializerTypes) {
        this.initializers = new LinkedMultiValueMap<>();
        this.initializerTypes = (initializerTypes.length != 0) ? Arrays.asList(initializerTypes)
                : Collections.singletonList(ServletContextInitializer.class);
        addServletContextInitializerBeans(beanFactory);
//        addAdaptableBeans(beanFactory);
        List<ServletContextInitializer> sortedInitializers = this.initializers.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        this.sortedList = Collections.unmodifiableList(sortedInitializers);
    }

    private void addServletContextInitializerBeans(ListableBeanFactory beanFactory) {
        for (Class<? extends ServletContextInitializer> initializerType : this.initializerTypes) {
            for (Map.Entry<String, ? extends ServletContextInitializer> initializerBean : getOrderedBeansOfType(beanFactory,
                    initializerType)) {
                addServletContextInitializerBean(initializerBean.getKey(), initializerBean.getValue(), beanFactory);
            }
        }
    }


    private void addServletContextInitializerBean(String beanName, ServletContextInitializer initializer,
                                                  ListableBeanFactory beanFactory) {
        if (initializer instanceof ServletRegistrationBean) {
            Servlet source = ((ServletRegistrationBean<?>) initializer).getServlet();
            addServletContextInitializerBean(Servlet.class, beanName, initializer, beanFactory, source);
        }

    }

    private void addServletContextInitializerBean(Class<?> type, String beanName, ServletContextInitializer initializer,
                                                  ListableBeanFactory beanFactory, Object source) {
        this.initializers.add(type, initializer);
        if (source != null) {
            // Mark the underlying source as seen in case it wraps an existing bean
            this.seen.add(source);
        }
    }

    private <T> List<Map.Entry<String, T>> getOrderedBeansOfType(ListableBeanFactory beanFactory, Class<T> type) {
        return getOrderedBeansOfType(beanFactory, type, Collections.emptySet());
    }

    private <T> List<Map.Entry<String, T>> getOrderedBeansOfType(ListableBeanFactory beanFactory, Class<T> type,
                                                                 Set<?> excludes) {
        String[] names = beanFactory.getBeanNamesForType(type, true, false);
        Map<String, T> map = new LinkedHashMap<>();
        for (String name : names) {
            if (!excludes.contains(name)) {
                T bean = beanFactory.getBean(name, type);
                if (!excludes.contains(bean)) {
                    map.put(name, bean);
                }
            }
        }
        List<Map.Entry<String, T>> beans = new ArrayList<>(map.entrySet());
        return beans;
    }

    @Override
    public Iterator<ServletContextInitializer> iterator() {
        return this.sortedList.iterator();
    }

    @Override
    public int size() {
        return this.sortedList.size();
    }
}
