package com.home.miniMall.service;

import com.home.miniMall.mapper.AdminMapper;
import com.home.miniMall.pojo.Admin;
import com.home.miniMall.pojo.AdminExample;
import com.home.miniMall.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    private AdminMapper mapper;

    @Override
    public Admin login(Admin admin) {

        if (admin.getaName()!=null|admin.getaName()!=null){
            AdminExample example=new AdminExample();
        /*
Criteria            example.createCriteria()    表示每一个Criteria对象由AND连接的,是逻辑与的关系。
oredCriteria        example.or()                表示每一个Criteria对象由由OR连接的,是逻辑或关系。

example.createCriteria().andAIdEqualTo(admin.getaId()).andANameEqualTo(admin.getaName());   例如，表示根据aId、aName，进行 and&与 查询
example.or().andAIdEqualTo(admin.getaId()).andANameEqualTo(admin.getaName());   例如，表示根据aId、aName，进行 or|或 查询
        */
            example.createCriteria().andANameEqualTo(admin.getaName()).andAPassEqualTo(MD5Util.getMD5(admin.getaPass()));

            //根据aName、aPass查询数据库是否存在符合的该用户     此处aPass是MD5密文
            List<Admin> admins=mapper.selectByExample(example);
            if (admins.size()==1){
                return admin;
            }
        }

        return null;
    }
}
