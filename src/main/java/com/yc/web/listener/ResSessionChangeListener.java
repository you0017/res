package com.yc.web.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 监听 session的变化情况
 */
@WebListener
public class ResSessionChangeListener implements HttpSessionAttributeListener {
    public ResSessionChangeListener(){
        System.out.println("ResSessionChangeListener的构造方法");
    }
    //session中的属性添加 => 某个键第一次 session.setAttribute("",obj)
    @Override
    public void attributeAdded(HttpSessionBindingEvent se) {
        //HttpSessionAttributeListener.super.attributeAdded(se);
        System.out.println("add");
        System.out.println(se.getName());
        System.out.println(se.getValue());
    }
    //session中的属性覆盖 => 某个键二次调用 session.setAttribute("",obj)
    @Override
    public void attributeReplaced(HttpSessionBindingEvent se) {
        //HttpSessionAttributeListener.super.attributeReplaced(se);
        System.out.println("replace");
        System.out.println(se.getName());
        System.out.println(se.getValue());
    }

    //session中的删除属性 => 某个键调用 session.removeAttribute("")
    @Override
    public void attributeRemoved(HttpSessionBindingEvent se) {
        //HttpSessionAttributeListener.super.attributeRemoved(se);
        System.out.println("remove");
        System.out.println(se.getName());
        System.out.println(se.getValue());
    }


}
