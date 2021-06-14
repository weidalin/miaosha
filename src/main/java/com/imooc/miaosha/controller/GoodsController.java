package com.imooc.miaosha.controller;

import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.service.GoodsService;
import com.imooc.miaosha.service.MiaoshaUserService;
import com.imooc.miaosha.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    MiaoshaUserService miaoshaUserService;

    @Autowired
    GoodsService goodsService;
    private static Logger log = LoggerFactory.getLogger(GoodsController.class);

    // QPS 2,348
    // 10 * 500

    @RequestMapping("/to_list")
    public String toLogin( Model model,
                          MiaoshaUser user){
        model.addAttribute("user", user);
        // 查询商品列表
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        return "goods_list";
    }
    @RequestMapping("/to_detail/{goodsId}")
    public String detail( Model model,
                           MiaoshaUser user,
                            @PathVariable("goodsId")long goodsId){
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goodsVo);
        model.addAttribute("user", user);
        //
        long startAt = goodsVo.getStartDate().getTime();
        long endAt = goodsVo.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int miaoshaStatus = 0;
        int remainSeconds = 0;
        if(now < startAt){  // 秒杀还没开始，倒计时
            miaoshaStatus =  0;
            remainSeconds = (int)((startAt - now) / 1000);
        }else if(now >endAt){ // 秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        }else{                // 秒杀进行时
            miaoshaStatus = 1;
            remainSeconds = 0;
        }

        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        return "goods_detail";
    }

}
