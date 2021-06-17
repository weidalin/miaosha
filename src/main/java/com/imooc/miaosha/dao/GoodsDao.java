package com.imooc.miaosha.dao;

import com.imooc.miaosha.domain.MiaoshaGoods;
import com.imooc.miaosha.domain.MiaoshaOrder;
import com.imooc.miaosha.domain.OrderInfo;
import com.imooc.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GoodsDao {

    @Select("select g.*, mg.stock_count, mg.start_date, mg.end_date, mg.miaosha_price from miaosha_goods mg left join goods g on mg.id = g.id")
    public List<GoodsVo> listGoodsVo();

    @Select("select g.*, mg.stock_count, mg.start_date, mg.end_date, mg.miaosha_price from miaosha_goods mg left join goods g on mg.id = g.id where g.id = #{goodsId}")
    GoodsVo getGoodsVoByGoodsId(@Param("goodsId") long goodsId);

//    @Update("update miaosha_goods set stock_count = stock_count - 1 where goods_id = #{id} and stock_count > 0")
//    int reduceStock(GoodsVo goodsVo);

    @Update("update miaosha_goods set stock_count = stock_count - 1 where goods_id = #{goodsId} and stock_count > 0")
    public int reduceStock(MiaoshaGoods g);





}
