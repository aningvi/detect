package edu.zstu.web.controller;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created with IntelliJ IDEA.
 * @Author: Aning
 * Date: 2018/1/26
 * Time: 19:56
 * CopyRight @ 2017 IIOT
 */

@Controller
@Api("登录相关类")
public class IndexController {

    private final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value = "系统入口", notes = "系统入口")
    public String index(){
        return "index";
    }
}
