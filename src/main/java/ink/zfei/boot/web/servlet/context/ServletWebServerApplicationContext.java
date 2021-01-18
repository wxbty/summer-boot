package ink.zfei.boot.web.servlet.context;

import ink.zfei.boot.autoconfigure.web.server.WebServer;
import ink.zfei.boot.autoconfigure.web.servlet.server.ServletContextInitializer;
import ink.zfei.boot.autoconfigure.web.servlet.server.ServletWebServerFactory;
import ink.zfei.boot.web.servlet.ServletContextInitializerBeans;
import ink.zfei.summer.beans.factory.config.ConfigurableListableBeanFactory;
import ink.zfei.summer.context.ApplicationContextException;
import ink.zfei.web.context.support.GenericWebApplicationContext;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Collection;

public class ServletWebServerApplicationContext extends GenericWebApplicationContext {

    private volatile WebServer webServer;
    private ServletContext servletContext;


    private void createWebServer() {

        WebServer webServer = this.webServer;
        ServletContext servletContext = getServletContext();

        if (webServer == null && servletContext == null) {
            ServletWebServerFactory factory = getWebServerFactory();
            this.webServer = factory.getWebServer(getSelfInitializer());
        }

//        Tomcat tomcat = new Tomcat();
//        tomcat.setPort(DEFAULT_PORT);
//        tomcat.setHostname(TOMCAT_HOSTNAME);
//        tomcat.setBaseDir("."); // tomcat 信息保存在项目下
//
//        StandardContext myCtx = null;
//        try {
//            myCtx = (StandardContext) tomcat
//                    .addWebapp("/access", System.getProperty("user.dir") + File.separator + WEBAPP_PATH);
//        } catch (ServletException e) {
//            e.printStackTrace();
//        }
//        /*
//         * true时：相关classes | jar 修改时，会重新加载资源，不过资源消耗很大
//         * autoDeploy 与这个很相似，tomcat自带的热部署不是特别可靠，效率也不高。生产环境不建议开启。
//         * 相关文档：
//         * http://www.blogjava.net/wangxinsh55/archive/2011/05/31/351449.html
//         */
//        myCtx.setReloadable(false);
//        /*String webAppMount = System.getProperty("user.dir") + File.separator + TomcatStart.CLASS_PATH;
//        WebResourceRoot root = new StandardRoot(myCtx);
//        root.addPreResources(
//            new DirResourceSet(root, TomcatStart.WEBINF_CLASSES, webAppMount, TomcatStart.INTERNAL_PATH));*/
//
//        // 注册servlet
//        tomcat.addServlet("/access", "demoServlet", new DispatcherServlet(this));
//        // servlet mapping
//        myCtx.addServletMappingDecoded("/demo.do", "demoServlet");
//        try {
//            tomcat.start();
//        } catch (LifecycleException e) {
//            e.printStackTrace();
//        }
//        startDaemonAwaitThread(tomcat);


    }

    private ServletContextInitializer getSelfInitializer() {
        return this::selfInitialize;
    }

    private void selfInitialize(ServletContext servletContext) throws ServletException {
        prepareWebApplicationContext(servletContext);
        for (ServletContextInitializer beans : getServletContextInitializerBeans()) {
            beans.onStartup(servletContext);
        }
    }

    private void prepareWebApplicationContext(ServletContext servletContext) {
        setServletContext(servletContext);
    }

    public ServletContext getServletContext() {
        return this.servletContext;
    }

    private void startDaemonAwaitThread(Tomcat tomcat) {
        Thread awaitThread = new Thread("container") {

            @Override
            public void run() {
                tomcat.getServer().await();
            }

        };
        awaitThread.setContextClassLoader(getClass().getClassLoader());
        awaitThread.setDaemon(false);
        awaitThread.start();
    }

    protected ServletWebServerFactory getWebServerFactory() {
        // Use bean names so that we don't consider the hierarchy
        String[] beanNames = getBeanFactory().getBeanNamesForType(ServletWebServerFactory.class);
        if (beanNames.length == 0) {
            throw new ApplicationContextException("Unable to start ServletWebServerApplicationContext due to missing "
                    + "ServletWebServerFactory bean.");
        }
        if (beanNames.length > 1) {
            throw new ApplicationContextException("Unable to start ServletWebServerApplicationContext due to multiple "
                    + "ServletWebServerFactory beans : " + beanNames);
        }
        return this.getBean(beanNames[0], ServletWebServerFactory.class);
    }


    @Override
    protected void onRefresh() {
        super.onRefresh();
        try {
            createWebServer();
        }
        catch (Throwable ex) {
            throw new ApplicationContextException("Unable to start web server", ex);
        }
    }

    protected Collection<ServletContextInitializer> getServletContextInitializerBeans() {
        return new ServletContextInitializerBeans(getBeanFactory());
    }

    @Override
    protected void finishRefresh() {
        super.finishRefresh();
        WebServer webServer = startWebServer();
        if (webServer != null) {
            publishEvent(new ServletWebServerInitializedEvent(webServer, this));
        }
    }

    private WebServer startWebServer() {
        WebServer webServer = this.webServer;
        if (webServer != null) {
            webServer.start();
        }
        return webServer;
    }
}
