package edu.zstu.dao;

import edu.zstu.domain.DeviceGroupInfo;

public interface DeviceGroupInfoMapper {
    int deleteByPrimaryKey(Integer deviceGroupId);

    int insert(DeviceGroupInfo record);

    int insertSelective(DeviceGroupInfo record);

    DeviceGroupInfo selectByPrimaryKey(Integer deviceGroupId);

    int updateByPrimaryKeySelective(DeviceGroupInfo record);

    int updateByPrimaryKey(DeviceGroupInfo record);
}