package com.imooc.miaosha.redis;

public class GoodsKey extends BasePrefix {
    private GoodsKey(int expireSecondes, String prefix) {
        super(expireSecondes, prefix);
    }

    public static GoodsKey getGoodsList = new GoodsKey(60000, "goodslist");
    public static GoodsKey getGoodsDetail = new GoodsKey(60000, "goodsdetail");
    public static GoodsKey getMiaoshaGoodsStock = new GoodsKey(0, "goodsstock");
}
