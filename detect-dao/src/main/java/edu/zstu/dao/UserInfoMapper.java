package edu.zstu.dao;

import edu.zstu.domain.UserInfo;

public interface UserInfoMapper {
    UserInfo selectByUserName(String username);
}