package com.efasttask.util;

import java.util.concurrent.TimeUnit;

public class MeasureExecutingTimeAnchor {
    private final long startTime;
    private final String name;

    public MeasureExecutingTimeAnchor() {
        this.name = EftMiscUtils.getMethodName(1);
        this.startTime = System.currentTimeMillis();
    }

    public MeasureExecutingTimeAnchor(String name) {
        this.startTime = System.currentTimeMillis();
        this.name = name;
    }

    public long getCurrent() {
        return getCurrent(TimeUnit.MILLISECONDS);
    }

    public long getCurrent(TimeUnit timeUnit) {
        return timeUnit.convert(System.currentTimeMillis() - this.startTime, TimeUnit.MILLISECONDS);
    }

    public String getCurrentMessage() {
        return getCurrentMessage(TimeUnit.MILLISECONDS);
    }

    public String getCurrentMessage(TimeUnit timeUnit) {
        return "The action <" + this.name + ">: " + getCurrent(timeUnit);
    }

    public void showCurrentMessage() {
        this.showCurrentMessage(TimeUnit.MILLISECONDS);
    }

    public void showCurrentMessage(TimeUnit timeUnit) {
        System.out.println(this.getCurrentMessage(timeUnit));
    }
}