package edu.zstu.dao;

import edu.zstu.domain.DetectTaskInfo;

public interface DetectTaskInfoMapper {
    int deleteByPrimaryKey(Integer detectTaskId);

    int insert(DetectTaskInfo record);

    int insertSelective(DetectTaskInfo record);

    DetectTaskInfo selectByPrimaryKey(Integer detectTaskId);

    int updateByPrimaryKeySelective(DetectTaskInfo record);

    int updateByPrimaryKey(DetectTaskInfo record);
}