package com.learn.multidatasource.service;

import com.learn.multidatasource.entity.ErpUser;
import com.learn.multidatasource.entity.LearnUser;

public interface UserService {
    ErpUser getErpUser(Integer id);

    LearnUser getLearnUser(Integer id);
}
