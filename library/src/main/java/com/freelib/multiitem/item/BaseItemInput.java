package com.freelib.multiitem.item;

import android.support.annotation.NonNull;

import com.freelib.multiitem.adapter.holder.InputHolderManager;

/**
 * Created by free46000 on 2017/4/10.
 */
public abstract class BaseItemInput<T extends BaseItemInput> extends InputHolderManager<T> implements ItemInput {
    protected String key;

    /**
     * @param key 录入对应key
     */
    public BaseItemInput(String key) {
        this.key = key;
    }


    @NonNull
    @Override
    public String getItemTypeName() {
        return toString();
    }

    @Override
    public InputHolderManager getViewHolderManager() {
        return this;
    }

    @Override
    public String getKey() {
        return key;
    }

}
