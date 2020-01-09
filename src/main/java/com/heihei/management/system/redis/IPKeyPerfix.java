package com.heihei.management.system.redis;

public class IPKeyPerfix extends BaseKeyPerfix {

    private IPKeyPerfix(String perfix) {
        super(perfix);
    }

    private IPKeyPerfix(int expireSeconds, String perfix) {
        super(expireSeconds, perfix);
    }

    public static IPKeyPerfix ipKeyPerfix = new IPKeyPerfix(300,"name-"); //默认300秒
}
