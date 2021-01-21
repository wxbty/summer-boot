package ink.zfei.boot.autoconfigure.web.server;

import ink.zfei.boot.autoconfigure.web.servlet.server.ConfigurableServletWebServerFactory;
import ink.zfei.summer.beans.BeanPostProcessor;
import ink.zfei.summer.beans.factory.BeanFactory;
import ink.zfei.summer.beans.factory.BeanFactoryAware;
import ink.zfei.summer.beans.factory.ListableBeanFactory;
import ink.zfei.summer.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class WebServerFactoryCustomizerBeanPostProcessor implements BeanPostProcessor, BeanFactoryAware {

    private ListableBeanFactory beanFactory;
    private List<WebServerFactoryCustomizer<?>> customizers;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        Assert.isInstanceOf(ListableBeanFactory.class, beanFactory,
                "WebServerCustomizerBeanPostProcessor can only be used with a ListableBeanFactory");
        this.beanFactory = (ListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if (bean instanceof WebServerFactory) {
            postProcessBeforeInitialization((WebServerFactory) bean);
        }
        return bean;
    }

    @SuppressWarnings("unchecked")
    private void postProcessBeforeInitialization(WebServerFactory webServerFactory) {
        getCustomizers().forEach(customizer->customizer.customize((ConfigurableServletWebServerFactory) webServerFactory));
    }

    private Collection<WebServerFactoryCustomizer<?>> getCustomizers() {
        if (this.customizers == null) {
            // Look up does not include the parent context
            this.customizers = new ArrayList<>(getWebServerFactoryCustomizerBeans());
            this.customizers = Collections.unmodifiableList(this.customizers);
        }
        return this.customizers;
    }

    /**
     * Customizer读取自Spring容器
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Collection<WebServerFactoryCustomizer<?>> getWebServerFactoryCustomizerBeans() {
        return (Collection) this.beanFactory.getBeansOfType(WebServerFactoryCustomizer.class, false, false).values();
    }
}
