package edu.zstu.dao;

import edu.zstu.domain.DetectPortInfo;

public interface DetectPortInfoMapper {
    int deleteByPrimaryKey(Integer detectPortId);

    int insert(DetectPortInfo record);

    int insertSelective(DetectPortInfo record);

    DetectPortInfo selectByPrimaryKey(Integer detectPortId);

    int updateByPrimaryKeySelective(DetectPortInfo record);

    int updateByPrimaryKey(DetectPortInfo record);
}