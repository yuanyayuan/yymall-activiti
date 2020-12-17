package com.nexus.common.validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @className FlagValidatorClass
 * @description //TODO
 * @author LiYuan
 * @date 2020/11/3
**/
public class FlagValidatorClass implements ConstraintValidator<FlagValidator, Object> {

    /**
     * values 临时变量保存flag值列表
     */
    private String values;

    @Override
    public void initialize(FlagValidator flagValidator) {
        this.values = flagValidator.values();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        //分割定义的有效值
        String[] value_array = values.split(",");
        boolean isFlag = false;
        //遍历比对有效值
        for (String s : value_array) {
            //存在一致跳出循环，赋值isFlag=true
            if (s.equals(value.toString())) {
                isFlag = true;
                break;
            }
        }
        return isFlag;
    }
}
