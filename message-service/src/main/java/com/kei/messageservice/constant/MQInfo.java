package com.kei.messageservice.constant;

public enum MQInfo {
    RESERVE("reserve.queue", "reserve.exchange", "reserve.key"),
    ;

    public final String name;
    public final String exchange;
    public final String key;

    private MQInfo(String name, String exchange, String key) {
        this.name = name;
        this.exchange = exchange;
        this.key = key;
    }
}
