package com.imooc.miaosha.service;

import com.imooc.miaosha.dao.GoodsDao;
import com.imooc.miaosha.dao.UserDao;
import com.imooc.miaosha.domain.Goods;
import com.imooc.miaosha.domain.MiaoshaGoods;
import com.imooc.miaosha.domain.User;
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

	public void reduceStock(GoodsVo goodsVo) {
		MiaoshaGoods g = new MiaoshaGoods();
		g.setId(goodsVo.getId());
		g.setStockCount(goodsVo.getStockCount() - 1);
		goodsDao.reduceStock(goodsVo);
	}
}
