package com.learn.multidatasource.mapper;

import com.learn.multidatasource.entity.ErpUser;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ErpMapper extends Mapper<ErpUser>{

}
