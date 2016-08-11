package com.arjinmc.countdowntimer.pojo;

import com.arjinmc.countdowntimer.util.Constant;

import java.io.Serializable;

/**
 * Created by Yusuf on 2016/8/11.
 */
public class MussEvent implements Serializable {
    private int id;
    private String name;
    private int currentLeftTime = -1;
    private int limit = -1;
    private int running = Constant.STOP;


    public MussEvent() {
    }

    public MussEvent(String name, int limit) {
        this.name = name;
        this.currentLeftTime = -1;
        this.limit = limit;
        this.running = Constant.STOP;
    }

    public MussEvent(int id, String name, int currentLeftTime, int limit, int running) {
        this.id = id;
        this.name = name;
        this.currentLeftTime = currentLeftTime;
        this.limit = limit;
        this.running = running;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrentLeftTime() {
        return currentLeftTime;
    }

    public void setCurrentLeftTime(int currentLeftTime) {
        this.currentLeftTime = currentLeftTime;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int isRunning() {
        return running;
    }

    public void setRunning(int running) {
        this.running = running;
    }
}