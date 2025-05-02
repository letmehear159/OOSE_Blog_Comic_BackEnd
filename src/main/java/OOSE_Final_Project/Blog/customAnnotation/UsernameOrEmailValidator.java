package OOSE_Final_Project.Blog.customAnnotation;

import OOSE_Final_Project.Blog.dto.req.LoginReq;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameOrEmailValidator implements ConstraintValidator<UsernameOrEmailRequired, LoginReq> {

    @Override
    public boolean isValid(LoginReq value, ConstraintValidatorContext context) {
        if (value == null)
            return false;
        return value.getIdentifier() != null && !value.getIdentifier()
                                                      .isEmpty();
    }
}
