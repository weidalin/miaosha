package com.imooc.miaosha.redis;

public abstract class BasePrefix implements KeyPrefix {

    private int expireSecondes;

    private String prefix;

    public BasePrefix(String prefix) { // 0-永不过期
        this(0, prefix);
    }

    public BasePrefix(int expireSecondes, String prefix) {
        this.expireSecondes = expireSecondes;
        this.prefix = prefix;
    }

    @Override
    public int expireSeconds() { // 默认0代表用不过期
        return expireSecondes;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();

        return className + ":" + prefix;
    }
}
