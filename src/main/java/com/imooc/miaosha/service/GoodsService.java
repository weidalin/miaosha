package com.imooc.miaosha.service;

import com.imooc.miaosha.dao.GoodsDao;
import com.imooc.miaosha.dao.UserDao;
import com.imooc.miaosha.domain.Goods;
import com.imooc.miaosha.domain.MiaoshaGoods;
import com.imooc.miaosha.domain.User;
import com.imooc.miaosha.exception.GlobalException;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GoodsService {
	
	@Autowired
	GoodsDao goodsDao;

	public List<GoodsVo> listGoodsVo(){
		return goodsDao.listGoodsVo();
	}


	public GoodsVo getGoodsVoByGoodsId(long goodsId) {
		return goodsDao.getGoodsVoByGoodsId(goodsId);
	}

	@Transactional
	public void reduceStock(GoodsVo goods) {
		MiaoshaGoods g = new MiaoshaGoods();
		g.setGoodsId(goods.getId());
		int reduceStock = goodsDao.reduceStock(g);
		reduceStock = 1 / reduceStock;
//		if(reduceStock == 0){
//			throw new GlobalException(CodeMsg.MIAO_SHA_OVER);
//		}
		System.out.println("reduceStock............." + reduceStock);
	}
}
