package edu.zstu.dao;

import edu.zstu.domain.SpecialDetectInfo;

public interface SpecialDetectInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SpecialDetectInfo record);

    int insertSelective(SpecialDetectInfo record);

    SpecialDetectInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SpecialDetectInfo record);

    int updateByPrimaryKey(SpecialDetectInfo record);
}