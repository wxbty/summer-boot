package ink.zfei.boot.context;

import ink.zfei.boot.mvc.DispatcherServlet;
import ink.zfei.summer.context.AnnotationConfigApplicationContext;
import ink.zfei.summer.core.annation.AnnotationConfigUtils;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class AnnotationConfigServletWebServerApplicationContext extends AnnotationConfigApplicationContext {

    private int DEFAULT_PORT = 8086;
    public static String TOMCAT_HOSTNAME = "127.0.0.1";
    public static String WEBAPP_PATH = "src/main";
    public static String WEBINF_CLASSES = "/WEB-INF/classes";
    public static String CLASS_PATH = "target/classes";
    public static String INTERNAL_PATH = "/";

    public AnnotationConfigServletWebServerApplicationContext(String basePackages, Class<?> componentClasses) throws IOException, URISyntaxException, ClassNotFoundException {
        super(basePackages,componentClasses);
        createWebServer();
    }


    public void register(Class<?> componentClasses) {
        AnnotationConfigUtils.registerAnnotationConfigProcessors(this);
        super.register(componentClasses);
    }

    private void createWebServer() {

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(DEFAULT_PORT);
        tomcat.setHostname(TOMCAT_HOSTNAME);
        tomcat.setBaseDir("."); // tomcat 信息保存在项目下

        /*
         * https://www.cnblogs.com/ChenD/p/10061008.html
         */
        StandardContext myCtx = null;
        try {
            myCtx = (StandardContext) tomcat
                    .addWebapp("/access", System.getProperty("user.dir") + File.separator + WEBAPP_PATH);
        } catch (ServletException e) {
            e.printStackTrace();
        }
        /*
         * true时：相关classes | jar 修改时，会重新加载资源，不过资源消耗很大
         * autoDeploy 与这个很相似，tomcat自带的热部署不是特别可靠，效率也不高。生产环境不建议开启。
         * 相关文档：
         * http://www.blogjava.net/wangxinsh55/archive/2011/05/31/351449.html
         */
        myCtx.setReloadable(false);
        /*String webAppMount = System.getProperty("user.dir") + File.separator + TomcatStart.CLASS_PATH;
        WebResourceRoot root = new StandardRoot(myCtx);
        root.addPreResources(
            new DirResourceSet(root, TomcatStart.WEBINF_CLASSES, webAppMount, TomcatStart.INTERNAL_PATH));*/

        // 注册servlet
        tomcat.addServlet("/access", "demoServlet", new DispatcherServlet(this));
        // servlet mapping
        myCtx.addServletMappingDecoded("/demo.do", "demoServlet");
        try {
            tomcat.start();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
//        tomcat.getServer().await();


    }
}
