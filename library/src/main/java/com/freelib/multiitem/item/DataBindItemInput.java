package com.freelib.multiitem.item;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;

/**
 * Created by free46000 on 2017/4/16.
 */
public abstract class DataBindItemInput<T extends BaseItemInput> extends BaseItemInput<T> {

    {
        enableDataBind();
    }

    /**
     * @param key 录入对应key
     */
    public DataBindItemInput(String key) {
        super(key);
    }

    @Override
    protected void initInputView(BaseViewHolder holder) {
        ViewDataBinding dataBinding = DataBindingUtil.getBinding(holder.itemView);
        initInputView(dataBinding);
    }

    protected abstract void initInputView(ViewDataBinding dataBinding);

}