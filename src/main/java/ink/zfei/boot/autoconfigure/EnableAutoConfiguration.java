package ink.zfei.boot.autoconfigure;


import ink.zfei.summer.annation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({AutoConfigurationImportSelector.class})
public @interface EnableAutoConfiguration {

    Class<?>[] exclude() default {};

    String[] excludeName() default {};
}
