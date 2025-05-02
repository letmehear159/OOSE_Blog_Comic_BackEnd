package OOSE_Final_Project.Blog.customAnnotation;

import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameOrEmailValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameOrEmailRequired {

    String message() default "Tên tài khoản hoặc email phải có";

}