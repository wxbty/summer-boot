package ink.zfei.boot.autoconfigure;


import ink.zfei.summer.core.annotation.Configuration;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Configuration
public @interface SpringBootConfiguration {
}
