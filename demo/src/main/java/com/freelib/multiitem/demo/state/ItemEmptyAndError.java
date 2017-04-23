package com.freelib.multiitem.demo.state;

import android.databinding.ViewDataBinding;

import com.freelib.multiitem.demo.BR;
import com.freelib.multiitem.demo.R;
import com.freelib.multiitem.item.BaseItemState;

/**
 * Created by free46000 on 2017/4/23.
 */
public class ItemEmptyAndError extends BaseItemState<ItemEmptyAndError> {
    private String message;
    private String btnText = "点击重试";

    public ItemEmptyAndError(String message) {
        this.message = message;
    }

    @Override
    protected void onBindViewHolder(ViewDataBinding dataBinding, ItemEmptyAndError data) {
        dataBinding.setVariable(BR.itemData, data);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.layout_empty_error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBtnText() {
        return btnText;
    }

    public void setBtnText(String btnText) {
        this.btnText = btnText;
    }
}
