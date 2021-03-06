package ink.zfei.boot.autoconfigure.web.embedded.tomcat;

import ink.zfei.boot.autoconfigure.web.server.WebServer;
import ink.zfei.boot.autoconfigure.web.server.WebServerException;
import ink.zfei.boot.autoconfigure.web.servlet.server.AbstractServletWebServerFactory;
import ink.zfei.boot.autoconfigure.web.servlet.server.ServletContextInitializer;
import ink.zfei.boot.autoconfigure.web.servlet.server.ServletWebServerFactory;
import ink.zfei.summer.util.Assert;
import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.Valve;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.loader.WebappLoader;
import org.apache.catalina.startup.Tomcat;
import org.apache.coyote.AbstractProtocol;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class TomcatServletWebServerFactory extends AbstractServletWebServerFactory implements ConfigurableTomcatWebServerFactory {

    public static final String DEFAULT_PROTOCOL = "org.apache.coyote.http11.Http11NioProtocol";
    private File baseDirectory;
    private String protocol = DEFAULT_PROTOCOL;
    private final List<Connector> additionalTomcatConnectors = new ArrayList<>();
    private String contextPath = "";
    private static final Set<Class<?>> NO_CLASSES = Collections.emptySet();
    private List<Valve> engineValves = new ArrayList<>();

    @Override
    public WebServer getWebServer(ServletContextInitializer... initializers) {
        Tomcat tomcat = new Tomcat();
        File baseDir = (this.baseDirectory != null) ? this.baseDirectory : createTempDir("tomcat");
        tomcat.setBaseDir(baseDir.getAbsolutePath());
        Connector connector = new Connector(this.protocol);
        tomcat.getService().addConnector(connector);
        customizeConnector(connector);
        tomcat.setConnector(connector);
        tomcat.getHost().setAutoDeploy(false);
//        configureEngine(tomcat.getEngine());
        for (Connector additionalConnector : this.additionalTomcatConnectors) {
            tomcat.getService().addConnector(additionalConnector);
        }
        prepareContext(tomcat.getHost(), initializers);
        return getTomcatWebServer(tomcat);
    }

    protected void prepareContext(Host host, ServletContextInitializer[] initializers) {
        TomcatEmbeddedContext context = new TomcatEmbeddedContext();

        context.setName(getContextPath());
        context.setDisplayName(getDisplayName());
        context.setPath(getContextPath());
        File docBase = createTempDir("tomcat-docbase");
        context.setDocBase(docBase.getAbsolutePath());
        context.addLifecycleListener(new Tomcat.FixContextListener());
//        context.setParentClassLoader((this.resourceLoader != null) ? this.resourceLoader.getClassLoader()
//                : ClassUtils.getDefaultClassLoader());
        context.setUseRelativeRedirects(false);
        WebappLoader loader = new WebappLoader(context.getParentClassLoader());
        loader.setLoaderClass(TomcatEmbeddedWebappClassLoader.class.getName());
        loader.setDelegate(true);
        context.setLoader(loader);
        addDefaultServlet(context);
        host.addChild(context);
        configureContext(context, initializers);
    }

    public String getContextPath() {
        return this.contextPath;
    }

    protected final File createTempDir(String prefix) {
        try {
            File tempDir = File.createTempFile(prefix + ".", "." + getPort());
            tempDir.delete();
            tempDir.mkdir();
            tempDir.deleteOnExit();
            return tempDir;
        } catch (IOException ex) {
            throw new WebServerException(
                    "Unable to create tempDir. java.io.tmpdir is set to " + System.getProperty("java.io.tmpdir"), ex);
        }
    }


    protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
        return new TomcatWebServer(tomcat, getPort() >= 0);
    }

    private void addDefaultServlet(Context context) {
        Wrapper defaultServlet = context.createWrapper();
        defaultServlet.setName("default");
        defaultServlet.setServletClass("org.apache.catalina.servlets.DefaultServlet");
        defaultServlet.addInitParameter("debug", "0");
        defaultServlet.addInitParameter("listings", "false");
        defaultServlet.setLoadOnStartup(1);
        // Otherwise the default location of a Spring DispatcherServlet cannot be set
        defaultServlet.setOverridable(true);
        context.addChild(defaultServlet);
        context.addServletMappingDecoded("/", "default");
    }

    protected void configureContext(Context context, ServletContextInitializer[] initializers) {
        TomcatStarter starter = new TomcatStarter(initializers);
        if (context instanceof TomcatEmbeddedContext) {
            TomcatEmbeddedContext embeddedContext = (TomcatEmbeddedContext) context;
            embeddedContext.setStarter(starter);
            embeddedContext.setFailCtxIfServletStartFails(true);
        }
        //要把servlet初始器注册到tomcat的container，初始化->注册dispatchServlet
        context.addServletContainerInitializer(starter, NO_CLASSES);
    }

    public void setContextPath(String contextPath) {
        checkContextPath(contextPath);
        this.contextPath = contextPath;
    }

    private void checkContextPath(String contextPath) {
        Assert.notNull(contextPath, "ContextPath must not be null");
        if (!contextPath.isEmpty()) {
            if ("/".equals(contextPath)) {
                throw new IllegalArgumentException("Root ContextPath must be specified using an empty string");
            }
            if (!contextPath.startsWith("/") || contextPath.endsWith("/")) {
                throw new IllegalArgumentException("ContextPath must start with '/' and not end with '/'");
            }
        }
    }

    protected void customizeConnector(Connector connector) {
        int port = Math.max(getPort(), 0);
        //连接器必须设置端口
        connector.setPort(port);

    }

    @Override
    public void addEngineValves(Valve... engineValves) {
        Assert.notNull(engineValves, "Valves must not be null");
        this.engineValves.addAll(Arrays.asList(engineValves));
    }
}
