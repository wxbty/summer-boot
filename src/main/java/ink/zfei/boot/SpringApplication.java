package ink.zfei.boot;

import ink.zfei.boot.context.AnnotationConfigServletWebServerApplicationContext;
import ink.zfei.boot.demo.Starter;
import ink.zfei.boot.demo.TestBean;
import ink.zfei.summer.core.ApplicationContext;

import java.io.IOException;
import java.net.URISyntaxException;

public class SpringApplication {

    private String defaultPackage;
    private Class starterClass;

    public SpringApplication(Class<Starter> starterClass) {
        this.starterClass = starterClass;

    }

    public static void run(Class<Starter> starterClass, String[] args) {
        new SpringApplication(starterClass).run(starterClass);
    }


    public void run(Class<Starter> starterClass) {

        //1、创建spring容器
        defaultPackage = starterClass.getPackage().getName();

        ApplicationContext context = createApplicationContext();
        //2、启动内嵌tomcat

        Object testBean = context.getBean(TestBean.class);
        System.out.println(testBean);

        try {
            Thread.sleep(10000000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private ApplicationContext createApplicationContext() {

        try {
            return new AnnotationConfigServletWebServerApplicationContext(defaultPackage);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }


}
