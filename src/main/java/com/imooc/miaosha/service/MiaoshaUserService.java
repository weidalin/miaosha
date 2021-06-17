package com.imooc.miaosha.service;

import com.imooc.miaosha.dao.MiaoshaUserDao;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.exception.GlobalException;
import com.imooc.miaosha.redis.MiaoshaUserKey;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.util.MD5Util;
import com.imooc.miaosha.util.UUIDUtil;
import com.imooc.miaosha.vo.LoginVo;
import com.sun.org.apache.bcel.internal.classfile.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Component
public class MiaoshaUserService {
    public static final String COOKIE_NAME_TOKEN = "token";

    @Autowired
    MiaoshaUserDao miaoshaUserDao;

    @Autowired
    RedisService redisService;


    public MiaoshaUser getById(Long id){
        // 取缓存
        MiaoshaUser miaoshaUser = redisService.get(MiaoshaUserKey.getById, ""+id, MiaoshaUser.class);
        if(miaoshaUser != null){
            return miaoshaUser;
        }
        // 取数据库
        miaoshaUser = miaoshaUserDao.getById(id);
        if(miaoshaUser != null){
            redisService.set(MiaoshaUserKey.getById, "" + id, miaoshaUser);
        }

        return miaoshaUser;
    }

    public boolean updatePassword(String token, long id, String passwd){
        // 取user
        MiaoshaUser user = getById(id);
        if(user == null){
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        // 更新数据库
        MiaoshaUser toBeUpdate = new MiaoshaUser();
        toBeUpdate.setId(id);
        toBeUpdate.setPassword(MD5Util.formPassToDBPass(passwd, user.getSalt()));
        miaoshaUserDao.update(toBeUpdate);
        // 处理缓存
        redisService.delete(MiaoshaUserKey.token, ""+ id);
        user.setPassword(toBeUpdate.getPassword());
        redisService.set(MiaoshaUserKey.getById, token, user);
        return true;
    }


//    public boolean login(HttpServletResponse response, LoginVo loginVo) {
    public String login(HttpServletResponse response, LoginVo loginVo) {
        if(loginVo == null){
//            return CodeMsg.SERVER_ERROR;
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }

        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        // 判断手机号是否存在
        MiaoshaUser user = getById(Long.parseLong(mobile));
        if(user == null){
//            return CodeMsg.MOBILE_NOT_EXIST;
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        // 验证密码
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String  calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
        if(!calcPass.equals(dbPass)){
//            return CodeMsg.PASSWORD_ERROR;
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
//        return CodeMsg.SUCCESS;
        // 生成 cookie
        String token = UUIDUtil.uuid();
        addCookie(response, token, user);
//        return true;
        return token;
    }

    public MiaoshaUser getByToken(HttpServletResponse response, String token) {
        if(StringUtils.isEmpty(token)){
            return null;
        }
        MiaoshaUser user =  redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
        // 延长有效期
        if(user != null){
            addCookie(response, token, user);
        }
        return user;

    }

    private void addCookie(HttpServletResponse response, String token, MiaoshaUser user){

        // 在redis 中保存token
        redisService.set(MiaoshaUserKey.token, token, user);
        // 生成 Cookie
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        // 设置cookie 过期时间，保持与redis 一致
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        // 设置目录
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
