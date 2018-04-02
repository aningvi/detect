package edu.zstu.web.controller.api;

import edu.zstu.domain.UserInfo;
import edu.zstu.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * User: Aning
 * Date: 2018/4/2
 * Time: 15:58
 * CopyRight @ 2017 IIOT
 * @author Aning
 */
@RestController
public class LoginController {
    @Autowired
    IUserInfoService userInfoService;

    @RequestMapping("/user/login")
    public void login(){
        UserInfo admin = userInfoService.getUserByUsername("admin");
        System.out.println(admin);

    }
}
