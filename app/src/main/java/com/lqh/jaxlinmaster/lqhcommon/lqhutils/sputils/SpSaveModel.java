package com.lqh.jaxlinmaster.lqhcommon.lqhutils.sputils;

import java.io.Serializable;

/**
 * Created by Linqh on 2021/10/25.
 *
 * @describe:
 */
public class SpSaveModel<T> implements Serializable {
    private int saveTime;
    private T value;
    private long currentTime;

    public SpSaveModel() {
    }

    public SpSaveModel(int saveTime, T value,long currentTime) {
        this.saveTime = saveTime;
        this.value = value;
        this.currentTime=currentTime;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public int getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(int saveTime) {
        this.saveTime = saveTime;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
