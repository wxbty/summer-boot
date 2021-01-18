package ink.zfei.boot.autoconfigure.web.servlet;

import ink.zfei.summer.util.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.Registration;
import javax.servlet.ServletContext;

public abstract class DynamicRegistrationBean<D extends Registration.Dynamic> extends RegistrationBean {

    private static final Log logger = LogFactory.getLog(RegistrationBean.class);

    protected final void register(String description, ServletContext servletContext) {
        D registration = addRegistration(description, servletContext);
        if (registration == null) {
            logger.info(StringUtils.capitalize(description) + " was not registered (possibly already registered?)");
            return;
        }
//        configure(registration);
    }

    protected abstract D addRegistration(String description, ServletContext servletContext);

}
