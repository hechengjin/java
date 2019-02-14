package cn.chunhuitech.springframework.framework.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})  //表示该注解可以用于什么地方
@Retention(RetentionPolicy.RUNTIME)  //表示需要在什么级别保存该注解信息
@Documented  //将注解包含在Javadoc中
public @interface CHController {
    String value() default "";
}
