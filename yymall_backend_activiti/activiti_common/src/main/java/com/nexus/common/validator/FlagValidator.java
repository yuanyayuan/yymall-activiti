package com.nexus.common.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @className FlagValidator
 * @description
 * @author LiYuan
 * @date 2020/11/3
**/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = FlagValidatorClass.class)
public @interface FlagValidator {
    //flag的有效值多个使用","隔开
    String values();

    //提示内容
    String message() default "flag不存在";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
