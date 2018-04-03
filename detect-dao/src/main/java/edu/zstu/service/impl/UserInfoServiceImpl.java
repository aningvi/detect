package edu.zstu.service.impl;

import edu.zstu.dao.UserInfoMapper;
import edu.zstu.domain.UserInfo;
import edu.zstu.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Aning
 * Date: 2018/4/2
 * Time: 15:50
 * CopyRight @ 2017 IIOT
 * @author Aning
 */
@Service
public class UserInfoServiceImpl implements IUserInfoService{

    @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public UserInfo getUserById(int id) {
        return userInfoMapper.selectByPrimaryKey(id);
    }
}
