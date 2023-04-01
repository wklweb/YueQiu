package com.yueqiu.common.domain;

public enum Type {
    ERROR(500),
    SUCCESS(200),
    WARNING(301);

    private int value;

    Type(int value) {
        this.value = value;
    }

    public Object value() {
        return this.value;
    }
}
