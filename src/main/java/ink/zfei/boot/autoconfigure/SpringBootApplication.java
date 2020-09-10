package ink.zfei.boot.autoconfigure;


//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
public @interface SpringBootApplication {

    Class<?>[] scanBasePackageClasses() default {};
}

