package com.learn.multidatasource.mapper;

import com.learn.multidatasource.entity.LearnUser;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface LearnMapper extends Mapper<LearnUser> {
}
