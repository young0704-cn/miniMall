package com.home.miniMall.service;

import com.home.miniMall.pojo.Admin;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Mytest {
    @Test
    public void mytest(){
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        AdminService service=context.getBean("adminServiceImpl",AdminService.class);
        Admin admin=new Admin();
        admin.setaId(1);
        admin.setaName("admin");
        admin.setaPass("000000");

        System.out.println(admin+"=================");
        Admin user=service.login(admin);

        System.out.println(user.getaName());
    }
}
