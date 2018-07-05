package com.task.senior.callback;

public interface OnFirebaseOperationListener {

    void onSuccess();

    void onFailed(String errorMessage);
}
