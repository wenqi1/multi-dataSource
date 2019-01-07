package com.learn.multidatasource.controller;

import com.learn.multidatasource.entity.ErpUser;
import com.learn.multidatasource.entity.LearnUser;
import com.learn.multidatasource.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("erp")
    public ErpUser getErpUser(Integer id){
        return userService.getErpUser(id);
    }

    @RequestMapping("learn")
    public LearnUser getLearnUser(Integer id){
        return userService.getLearnUser(id);
    }
}
