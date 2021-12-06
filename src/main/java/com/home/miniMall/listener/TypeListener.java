package com.home.miniMall.listener;

import com.home.miniMall.service.ProductTypeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
//监听器
public class TypeListener implements ServletContextListener {
    /*
    contextInitialized方法
        该会监听全局作用域的创建，一旦全局作用域创建意味着该服务器启动

   contextDestroyed方法
        该会监听全局作用域的销毁，一旦全局作用域销毁意味着该服务器关闭
    */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("监听到服务器启动");
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        ProductTypeService productTypeService = context.getBean("productTypeServiceImpl", ProductTypeService.class);
        servletContextEvent.getServletContext().setAttribute("ptlist", productTypeService.getAll());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("监听到服务器关闭");
    }
}
