package com.review.entity;

public class Counter {
    private Integer value;

    public Counter(Integer value) {
        this.value = value;
    }
    public Counter() {
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
