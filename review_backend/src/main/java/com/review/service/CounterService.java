package com.review.service;

import com.review.entity.Counter;
import org.springframework.stereotype.Service;

@Service
public class CounterService {
    private static Counter counter;

    public CounterService() {
        this.counter = new Counter();
    }

    public Integer getCounterValue(){
        return counter.getValue();
    }
    public void setCounterValue(Integer value){
        counter.setValue(value);
    }
}
