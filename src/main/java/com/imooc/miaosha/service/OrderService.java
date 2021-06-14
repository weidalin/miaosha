package com.imooc.miaosha.service;

import com.imooc.miaosha.dao.GoodsDao;
import com.imooc.miaosha.dao.OrderDao;
import com.imooc.miaosha.domain.MiaoshaOrder;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.OrderInfo;
import com.imooc.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderService {
    @Autowired
    OrderDao orderDao;

    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(Long id, long goodsId) {

        return orderDao.getMiaoshaOrderByUserIdGoodsId(id, goodsId);

    }

    @Transactional
    public OrderInfo createOrder(MiaoshaUser user, GoodsVo goodsVo) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date()); // 创建日期
        orderInfo.setDeliveryAddrId(0L);    // 快递id
        orderInfo.setGoodsCount(1); // 商品数量
        orderInfo.setGoodsId(goodsVo.getId());  // 商品id
        orderInfo.setGoodsName(goodsVo.getGoodsName()); // 商品名称
        orderInfo.setGoodsPrice(goodsVo.getMiaoshaPrice()); // 秒杀价格
        orderInfo.setOrderChannel(1);   // 渠道，安卓，苹果，pc
        orderInfo.setStatus(0); // 订单状态
        orderInfo.setUserId(user.getId());
        long orderId = orderDao.insert(orderInfo);

        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goodsVo.getId());
        miaoshaOrder.setOrderId(orderId);
        miaoshaOrder.setUserId(user.getId());

        orderDao.insertMiaoshaOrder(miaoshaOrder);
        return orderInfo;

    }
}
