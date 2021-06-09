package com.imooc.miaosha.redis;

interface KeyPrefix {
    public int expireSeconds();

    public String getPrefix();
}
