package com.tom.service.impl;

import com.tom.mapper.AdminMapper;
import com.tom.pojo.Admin;
import com.tom.pojo.AdminExample;
import com.tom.service.AdminService;
import com.tom.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tom
 */
@Service
public class AdminServiceImpl implements AdminService {

    //在业务逻辑层中一定有数据访问层对象
    @Autowired
    AdminMapper adminMapper;

    @Override
    public Admin login(String name, String pwd) {
        //根据传入的用户信息到DB中查询相应用户
        AdminExample adminExample = new AdminExample();
        adminExample.createCriteria().andANameEqualTo(name);
        List<Admin> admins = adminMapper.selectByExample(adminExample);
        if (admins.size() > 0) {
            String pass = admins.get(0).getaPass();
            String pass2 = MD5Util.getMD5(pwd);
            if (pass.equals(pass2)){
                return admins.get(0);
            }
        }
        return null;
    }
}
