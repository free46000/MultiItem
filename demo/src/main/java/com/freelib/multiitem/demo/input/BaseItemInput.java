package com.freelib.multiitem.demo.input;

import android.support.annotation.NonNull;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;

/**
 *
 * todo 是否增加验证Rule，怎样增加更灵活
 * todo 测试输入法与输入框之间覆盖问题
 * Created by free46000 on 2017/4/10.
 */
public abstract class BaseItemInput extends InputHolderManager implements ItemInput {
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
