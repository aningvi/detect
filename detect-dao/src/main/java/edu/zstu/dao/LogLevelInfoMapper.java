package edu.zstu.dao;

import edu.zstu.domain.LogLevelInfo;

public interface LogLevelInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LogLevelInfo record);

    int insertSelective(LogLevelInfo record);

    LogLevelInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LogLevelInfo record);

    int updateByPrimaryKey(LogLevelInfo record);
}