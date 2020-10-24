package ink.zfei.boot.autoconfigure.web.servlet;

import ink.zfei.boot.autoconfigure.web.embedded.tomcat.TomcatServletWebServerFactory;
import ink.zfei.summer.core.annotation.Bean;
import ink.zfei.summer.core.annotation.Configuration;

@Configuration
public class ServletWebServerFactoryConfiguration {

//    @Configuration
//  todo 条件注解  @ConditionalOnClass({ Servlet.class, Tomcat.class, UpgradeProtocol.class })
//   public static class EmbeddedTomcat {

        @Bean
        TomcatServletWebServerFactory tomcatServletWebServerFactory() {
            TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
            return factory;
        }

//    }
}
