package ink.zfei.test.bean.inpack;

import ink.zfei.boot.autoconfigure.SpringBootApplication;
import ink.zfei.summer.core.annotation.Bean;
import ink.zfei.test.Popo;
import ink.zfei.test.unpack.ImportSelectDemoBean;
import ink.zfei.test.unpack.RedisProties;
import ink.zfei.boot.SpringApplication;
import ink.zfei.summer.beans.factory.NoSuchBeanDefinitionException;
import ink.zfei.summer.core.ApplicationContext;
import ink.zfei.test.unpack.TestNonBean;
import org.junit.Assert;
import org.junit.Test;

@SpringBootApplication
public class BootTest {

    @Bean
    public Popo popo() {
        return new Popo();
    }

    @Test
    public void mainTest() {
        SpringApplication.run(BootTest.class, null);
    }

    @Test
    public void annotationBean() {
        ApplicationContext context = SpringApplication.run(BootTest.class, null);
        Popo testBean = (Popo) context.getBean("popo");
        Assert.assertNotNull(testBean);
        boolean notExistBean;
        try {
            context.getBean("testNonBean");
            notExistBean = false;
        } catch (NoSuchBeanDefinitionException e) {
            notExistBean = true;
        }
        Assert.assertTrue(notExistBean);
    }

    @Test
    public void scanBasePackage() {
        ApplicationContext context = SpringApplication.run(BootTest.class, null);
        TestBean testBean = (TestBean) context.getBean("testBean");
        Assert.assertNotNull(testBean);
        boolean notExistBean;
        try {
            context.getBean("testNonBean");
            notExistBean = false;
        } catch (NoSuchBeanDefinitionException e) {
            notExistBean = true;
        }
        Assert.assertTrue(notExistBean);
    }

    @Test
    public void configurationClass() {

        //测试@Configuration 的@bean
        ApplicationContext context = SpringApplication.run(BootTest.class, null);
        RedisProties redisProties = (RedisProties) context.getBean("redisProties");
        Assert.assertNotNull(redisProties);
        //测试@Configuration 的@importSelect
        ImportSelectDemoBean testNonBean = (ImportSelectDemoBean) context.getBean("importSelectDemoBean");
        Assert.assertNotNull(testNonBean);
    }
}
