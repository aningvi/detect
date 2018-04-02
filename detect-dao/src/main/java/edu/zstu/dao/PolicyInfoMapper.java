package edu.zstu.dao;

import edu.zstu.domain.PolicyInfo;

public interface PolicyInfoMapper {
    int deleteByPrimaryKey(Integer policyId);

    int insert(PolicyInfo record);

    int insertSelective(PolicyInfo record);

    PolicyInfo selectByPrimaryKey(Integer policyId);

    int updateByPrimaryKeySelective(PolicyInfo record);

    int updateByPrimaryKey(PolicyInfo record);
}