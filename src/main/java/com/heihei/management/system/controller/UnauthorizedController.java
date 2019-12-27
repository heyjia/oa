package com.heihei.management.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName UnauthorizedController
 * @Description TODO
 * @Author CHENZEJIA
 * @Date 2019/12/24 15:58
 **/
@Controller
public class UnauthorizedController {
    @RequestMapping(value = "/unAuthorized")
    public String unAuthorized(){
        return "demo/unAuthorized";
    }
}
