package com.freelib.multiitem.demo.input;

import android.support.annotation.NonNull;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;

/**
 *
 * todo 是否应该继承InputHolderManager 1.思考真实实现场景怎么写起来更方便
 * todo 2.思考真实应用场景new BaseItemInput(key,new InputHolderManager())或者new BaseItemInput
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
