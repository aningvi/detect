package edu.zstu.dao;

import edu.zstu.domain.DeviceInfo;

public interface DeviceInfoMapper {
    int deleteByPrimaryKey(Integer deviceId);

    int insert(DeviceInfo record);

    int insertSelective(DeviceInfo record);

    DeviceInfo selectByPrimaryKey(Integer deviceId);

    int updateByPrimaryKeySelective(DeviceInfo record);

    int updateByPrimaryKey(DeviceInfo record);
}