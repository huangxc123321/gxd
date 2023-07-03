package com.gxa.jbgsw.common.utils;

import com.gxa.jbgsw.common.exception.BizException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CopyPropertionIngoreNull {

    // 把为空的属性就不替换了
    public static String[] getNullPropertyNames (Object source) {
        List<String> res = new ArrayList<>();
        Class<?> aClass = source.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field field: declaredFields) {
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(source);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if(value == null){
                res.add(field.getName());
            }
        }
        return res.toArray(new String[0]);
    }

}
