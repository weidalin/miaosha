package com.imooc.miaosha.controller;

import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.MiaoshaUserService;
import com.imooc.miaosha.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@Controller
@RequestMapping("/goods")
public class GoodController {
    @Autowired
    MiaoshaUserService miaoshaUserService;


    private static Logger log = LoggerFactory.getLogger(GoodController.class);

    @RequestMapping("/to_list")
    public String toLogin( Model model,
//                           HttpServletResponse response,
//                          @CookieValue(value=MiaoshaUserService.COOKIE_NAME_TOKEN, required = false)  String cookieToken,
//                          @RequestParam(value=MiaoshaUserService.COOKIE_NAME_TOKEN, required = false) String paramToken,
                          MiaoshaUser user){
//        if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)){
//            return "login";
//        }
//        String token = StringUtils.isEmpty(paramToken) ? cookieToken :  paramToken;
//        MiaoshaUser user = miaoshaUserService.getByToken(response, token);
        model.addAttribute("user", user);
        return "goods_list";
    }

}
