package com.freelib.multiitem.demo.input;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.demo.R;
import com.freelib.multiitem.item.BaseItemInput;

import static android.R.id.edit;

/**
 * ItemEdit
 * Created by free46000 on 2017/4/12.
 */
public class ItemEdit extends BaseItemInput<ItemEdit> {
    private EditText editText;
    public String name;
    public String value;
    private String hint;


    /**
     * @param key 录入对应key
     */
    public ItemEdit(String key) {
        super(key);
    }


    @NonNull
    @Override
    public String getValue() {
        return editText.getText().toString();
    }

    @Override
    protected void initInputView(BaseViewHolder holder) {
        TextView nameText = getView(holder.itemView, R.id.text);
        nameText.setText(name);

        editText = getView(holder.itemView, R.id.editText);
        editText.setHint(hint);
        editText.setText(value);
    }


    @Override
    protected int getItemLayoutId() {
        return R.layout.item_edit;
    }

}
