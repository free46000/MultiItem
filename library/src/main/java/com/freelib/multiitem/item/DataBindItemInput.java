package com.freelib.multiitem.item;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.InputHolderManager;

/**
 * 数据绑定的录入Item
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

    /**
     * 通过ViewDataBinding初始化Input视图
     *
     * @param dataBinding
     * @see InputHolderManager#initInputView(BaseViewHolder)
     */
    protected abstract void initInputView(ViewDataBinding dataBinding);

}