package com.freelib.multiitem.demo.input;

import android.databinding.ViewDataBinding;
import android.text.TextUtils;

import com.freelib.multiitem.demo.BR;
import com.freelib.multiitem.demo.R;
import com.freelib.multiitem.item.DataBindItemInput;

/**
 * Created by free46000 on 2017/4/16.
 */
public class ItemInfo extends DataBindItemInput<ItemInfo> {
    private String name;
    private String info;

    public ItemInfo(String key) {
        super(key);
    }

    @Override
    protected void initInputView(ViewDataBinding dataBinding) {
        dataBinding.setVariable(BR.itemData, this);
    }

    @Override
    public Object getValue() {
        return info == null ? "" : info;
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_info_input_data_bind;
    }

    @Override
    public boolean isValueValid() {
        return !TextUtils.isEmpty(info);
    }

    public ItemInfo setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
