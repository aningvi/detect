package edu.zstu.dao;

import edu.zstu.domain.ProtocolInfo;

public interface ProtocolInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProtocolInfo record);

    int insertSelective(ProtocolInfo record);

    ProtocolInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProtocolInfo record);

    int updateByPrimaryKey(ProtocolInfo record);
}