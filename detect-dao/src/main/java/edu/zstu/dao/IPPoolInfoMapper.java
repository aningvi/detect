package edu.zstu.dao;

import edu.zstu.domain.IPPoolInfo;

public interface IPPoolInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IPPoolInfo record);

    int insertSelective(IPPoolInfo record);

    IPPoolInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IPPoolInfo record);

    int updateByPrimaryKey(IPPoolInfo record);
}