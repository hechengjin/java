package cn.chunhuitech.springframework.framework.annotation;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CHRequestParam {
    String value() default "";
}
