package com.learn.multidatasource.service.impl;

import com.learn.multidatasource.config.DatabaseContextHolder;
import com.learn.multidatasource.entity.ErpUser;
import com.learn.multidatasource.entity.LearnUser;
import com.learn.multidatasource.enums.DatabaseType;
import com.learn.multidatasource.mapper.ErpMapper;
import com.learn.multidatasource.mapper.LearnMapper;
import com.learn.multidatasource.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private ErpMapper erpMapper;

    @Autowired
    private LearnMapper learnMapper;

    @Override
    public ErpUser getErpUser(Integer id) {
        DatabaseContextHolder.setDatabaseType(DatabaseType.ERP);
        return erpMapper.selectByPrimaryKey(id);
    }

    @Override
    public LearnUser getLearnUser(Integer id) {
        DatabaseContextHolder.setDatabaseType(DatabaseType.LEARN);
        return learnMapper.selectByPrimaryKey(id);
    }
}
