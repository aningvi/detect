package edu.zstu.dao;

import edu.zstu.domain.NormalDetectInfo;

public interface NormalDetectInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NormalDetectInfo record);

    int insertSelective(NormalDetectInfo record);

    NormalDetectInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NormalDetectInfo record);

    int updateByPrimaryKey(NormalDetectInfo record);
}