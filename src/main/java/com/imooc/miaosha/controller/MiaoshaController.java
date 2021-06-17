package com.imooc.miaosha.controller;

import com.imooc.miaosha.domain.MiaoshaOrder;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.OrderInfo;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.GoodsService;
import com.imooc.miaosha.service.MiaoshaService;
import com.imooc.miaosha.service.MiaoshaUserService;
import com.imooc.miaosha.service.OrderService;
import com.imooc.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {
    @Autowired
    MiaoshaUserService miaoshaUserService;


    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @Autowired
    GoodsService goodsService;
    private static Logger log = LoggerFactory.getLogger(MiaoshaController.class);

    // 5000 * 10
    // QPS 5,992.33
    @RequestMapping(value="/do_miaosha", method= RequestMethod.POST)
    @ResponseBody
    public Result<OrderInfo> do_miaosha(Model model, MiaoshaUser user,
                                        @RequestParam("goodsId")long goodsId){
        model.addAttribute("user", user);
        if(user == null){
            return Result.error(CodeMsg.SERVER_ERROR);
        }
        // 判断库存
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goodsVo.getStockCount();
        if(stock <= 0){
            return Result.error(CodeMsg.MIAO_SHA_OVER);
//            model.addAttribute("errmsg", CodeMsg.MIAO_SHA_OVER.getMsg());
//            return "miaosha_fail";
        }
        // 判断是否已经秒杀过该商品
        MiaoshaOrder order;
        order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order != null){

            return Result.error(CodeMsg.REPEATE_MIAOSHA);
//            model.addAttribute("errmsg", CodeMsg.REPEATE_MIAOSHA.getMsg());
//            System.err.println("这个用户已经有该商品的秒杀订单了...");
//            return "miaosha_fail";
        }
        // 减库存，下订单，写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(user, goodsVo);
//        model.addAttribute("orderInfo", orderInfo);
//        model.addAttribute("goods", goodsVo);
//        return "order_detail";
        return Result.success(orderInfo);



    }


//    @RequestMapping("/do_miaosha")
//    public String do_miaosha(Model model, MiaoshaUser user,
//                             @Param("goodsId")long goodsId){
//        System.err.println("user.toString(): " + user.toString());
//        model.addAttribute("user", user);
//        if(user == null){
//            return "login";
//        }
//        // 判断库存
//        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
//        int stock = goodsVo.getStockCount();
//        if(stock <= 0){
//            model.addAttribute("errmsg", CodeMsg.MIAO_SHA_OVER.getMsg());
//            return "miaosha_fail";
//        }
//        // 判断是否已经秒杀过该商品
//        MiaoshaOrder order;
//        order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
//        if(order != null){
//            model.addAttribute("errmsg", CodeMsg.REPEATE_MIAOSHA.getMsg());
//            System.err.println("这个用户已经有该商品的秒杀订单了...");
//            return "miaosha_fail";
//        }
//        // 减库存，下订单，写入秒杀订单
//        OrderInfo orderInfo = miaoshaService.miaosha(user, goodsVo);
//        model.addAttribute("orderInfo", orderInfo);
//        model.addAttribute("goods", goodsVo);
//        return "order_detail";
//
//    }



}
