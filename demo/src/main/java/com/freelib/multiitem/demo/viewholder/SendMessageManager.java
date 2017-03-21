package com.freelib.multiitem.demo.viewholder;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.freelib.multiitem.demo.R;
import com.freelib.multiitem.demo.bean.MessageBean;

/**
 * @author free46000  2017/03/17
 */
public class SendMessageManager extends BaseViewHolderManager<MessageBean> {


    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, @NonNull MessageBean data) {
        TextView textView = getView(holder, R.id.text);
        textView.setText(data.getMessage());
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_chat_send;
    }

}
