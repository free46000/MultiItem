package com.freelib.multiitem.adapter.holder;

import android.support.annotation.NonNull;

import com.freelib.multiitem.item.ItemInput;

import java.util.Collections;
import java.util.Map;

/**
 * Created by free46000 on 2017/4/10.
 */
public abstract class InputHolderManager<T extends ItemInput> extends BaseViewHolderManager<T> {
    protected Object originalValue;
    protected BaseViewHolder viewHolder;

    public abstract String getKey();

    @NonNull
    public abstract Object getValue();

    @Override
    protected void onCreateViewHolder(@NonNull BaseViewHolder holder) {
        super.onCreateViewHolder(holder);
        initInputView(holder);
        originalValue = getValue();
        viewHolder = holder;
    }

    protected abstract void initInputView(BaseViewHolder holder);

    @Deprecated
    @Override
    public void onBindViewHolder(BaseViewHolder holder, T t) {
    }

    @Deprecated
    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, @NonNull T t, @NonNull ViewHolderParams params) {
        super.onBindViewHolder(holder, t, params);
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
