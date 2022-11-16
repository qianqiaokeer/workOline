package com.zhangkehui.voaserver.utils;

import cn.afterturn.easypoi.excel.annotation.Excel;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;

public class EasyPoiUtil<T> {

    /**
     * 需要被反射的对象，使用泛型规范传入对象
     */
    public T t;

    public void hiddColumn(String columnName, Boolean target) throws Exception {
        if (t == null) {
            throw new ClassNotFoundException("TARGET OBJECT NOT FOUNT");
        }
        if (StringUtils.isEmpty(columnName)) {
            throw new NullPointerException("COLUMN NAME NOT NULL");
        }
        if (target == null) {
            target = true;
        }
        // 获取目标对象的属性值
        Field field = t.getClass().getDeclaredField(columnName);
        // 获取注解反射对象
        Excel excelAnnon = field.getAnnotation(Excel.class);
        // 获取代理
        InvocationHandler invocationHandler = Proxy.getInvocationHandler(excelAnnon);
        Field excelField = invocationHandler.getClass().getDeclaredField("memberValues");
        // 因为这个字段是 private final 修饰，所以要打开权限
        excelField.setAccessible(true);
        Map memberValues = (Map) excelField.get(invocationHandler);
        memberValues.put("isColumnHidden", target);
    }
}
