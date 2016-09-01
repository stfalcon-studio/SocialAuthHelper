package com.stfalcon.socialauthhelper.instagram.model;

/**
 * Created by Anton Bevza on 8/18/16.
 */
public class InstagramResponse<T> {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
