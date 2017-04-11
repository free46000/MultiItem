package com.freelib.multiitem.demo.input;

import android.support.annotation.NonNull;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;

import java.util.Collections;
import java.util.Map;

import static android.R.attr.key;

/**
 * Created by free46000 on 2017/4/10.
 */
public abstract class InputHolderManager<T extends ItemInput> extends BaseViewHolderManager<T> {
    protected BaseViewHolder holder;
    protected T itemData;
    protected Object originalValue;

    protected abstract void onBindViewHolder();

    public abstract String getKey();

    @NonNull
    public abstract Object getValue();

    @Override
    protected void onCreateViewHolder(@NonNull BaseViewHolder holder) {
        super.onCreateViewHolder(holder);
        originalValue = getValue();
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, @NonNull T itemData) {
        this.holder = holder;
        this.itemData = itemData;
        onBindViewHolder();
    }


    @NonNull
    public Map<String, Object> getValueMap() {
        return Collections.singletonMap(getKey(), getValue());
    }

    public boolean isValueChange() {
        return getOriginalValue() != null && !(getOriginalValue().equals(getValue()));
    }

    protected Object getOriginalValue() {
        return originalValue;
    }

    @Override
    public boolean isClickable() {
        return false;
    }
}
