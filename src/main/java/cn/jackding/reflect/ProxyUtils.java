package cn.jackding.reflect;

import cn.jackding.date.LocalDateUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.time.LocalDate;

/**
 * @Author Jack
 * @Date 2019/12/9 16:21
 * @Version 1.0.0
 */
public class ProxyUtils implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
        return method.invoke(proxy,args);
    }

    public static void main(String[] args) throws Exception {
        ProxyUtils proxy=new ProxyUtils();
        Object o=proxy.invoke(new LocalDateUtil(),LocalDateUtil.class.getMethod("getAllYearDay", LocalDate.class), new LocalDate[]{LocalDate.now()});
        System.out.println(o);
    }

}
