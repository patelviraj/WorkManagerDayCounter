package com.daycounter.interfaces;

public interface OnAsyncTaskEventListener<T> {
    void onSuccess(T object);

    void onFailure(Exception e);
}
