package edu.zstu.dao;

import edu.zstu.domain.DeviceLogInfo;

public interface DeviceLogInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceLogInfo record);

    int insertSelective(DeviceLogInfo record);

    DeviceLogInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeviceLogInfo record);

    int updateByPrimaryKey(DeviceLogInfo record);
}