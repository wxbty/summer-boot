package ink.zfei.boot.context.properties;

import ink.zfei.summer.beans.BeanDefinitionRegistry;
import ink.zfei.summer.context.AnnotationConfigApplicationContext;
import ink.zfei.summer.core.ImportBeanDefinitionRegistrar;

public class EnableConfigurationPropertiesRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(BeanDefinitionRegistry registry, Class configClass) {

    }
}
