package com.heihei.management.system.redis;

public interface KeyPerfix {
    int getExpireSeconds();
    String getPerfix();
}
