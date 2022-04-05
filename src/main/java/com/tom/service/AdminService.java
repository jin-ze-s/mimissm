package com.tom.service;

import com.tom.pojo.Admin;

/**
 * @author tom
 */
public interface AdminService {
    //完成登录判断r
    Admin login(String name, String pwd);

    
}
