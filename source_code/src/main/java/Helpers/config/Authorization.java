package Helpers.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorization {
    int ROLE_USER = 1, ROLE_ADMIN = 2, ROLE_SCHEDULER = 3;

    int[] roles() default {};
    boolean allowAnonymous() default false;
}
