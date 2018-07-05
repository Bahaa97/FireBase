package com.task.senior.callback;

import com.task.senior.Model.Item;

import java.util.List;

public interface OnFirebaseDataListener {

    void onSuccess(List<Item> items);

    void onError(String errorMsg);

}
