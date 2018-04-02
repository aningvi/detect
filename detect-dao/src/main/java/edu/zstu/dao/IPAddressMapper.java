package edu.zstu.dao;

import edu.zstu.domain.IPAddress;

public interface IPAddressMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IPAddress record);

    int insertSelective(IPAddress record);

    IPAddress selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IPAddress record);

    int updateByPrimaryKey(IPAddress record);
}