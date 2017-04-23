package com.freelib.multiitem.demo.input;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.demo.R;
import com.freelib.multiitem.item.BaseItemInput;

/**
 * ItemEdit
 * Created by free46000 on 2017/4/12.
 */
public class ItemEdit extends BaseItemInput<ItemEdit> {
    private EditText editText;
    private String name;
    private String defValue = "";
    private String hint;


    /**
     * @param key 录入对应key
     */
    public ItemEdit(String key) {
        super(key);
    }

    /**
     * 设置展示列名
     *
     * @param name 展示列名
     */
    public ItemEdit setName(String name) {
        this.name = name;
        return this;
    }

    public ItemEdit setHint(String hint) {
        this.hint = hint;
        return this;
    }

    /**
     * 设置默认值
     *
     * @param defValue 默认值
     */
    public ItemEdit setDefValue(String defValue) {
        this.defValue = defValue;
        return this;
    }

    @Override
    public String getValue() {
        //返回录入的值，和{@link #getKey()}一起组装为Map  如果为null则不组装
        return editText == null ? defValue : editText.getText().toString();
    }

    @Override
    public boolean isValueValid() {
        //是否验证有效，如Item不能为空，如用户手动更改，true:有效；false:无效
        return !TextUtils.isEmpty(getValue());
    }

    @Override
    protected void initInputView(BaseViewHolder holder) {
        //初始化Input视图，由于Input视图不可以复用，所以直接在初始化视图时设置好相关内容即可
        TextView nameText = getView(holder.itemView, R.id.text);
        nameText.setText(name);

        editText = getView(holder.itemView, R.id.editText);
        editText.setHint(hint);
        editText.setText(defValue);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_edit;
    }

}
