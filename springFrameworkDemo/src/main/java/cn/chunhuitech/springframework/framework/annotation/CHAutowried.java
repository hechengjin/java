package cn.chunhuitech.springframework.framework.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CHAutowried {
    String value() default "";
}
