package edu.zstu.service;

import edu.zstu.domain.UserInfo;

/**
 * Created with IntelliJ IDEA.
 * User: Aning
 * Date: 2018/4/2
 * Time: 15:50
 * CopyRight @ 2017 IIOT
 * @author Aning
 */
public interface IUserInfoService {
    UserInfo getUserByUsername(String username);
}
