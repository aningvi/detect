package edu.zstu.dao;

import edu.zstu.domain.IPAddrLib;

public interface IPAddrLibMapper {
    int insert(IPAddrLib record);

    int insertSelective(IPAddrLib record);
}