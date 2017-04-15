package com.freelib.multiitem.demo.input;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.demo.R;
import com.freelib.multiitem.item.BaseItemInput;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by free46000 on 2017/4/13.
 */
public class ItemNameAndSex extends BaseItemInput<ItemNameAndSex> {
    private EditText nameEdit;
    private RadioGroup sexRadio;

    public ItemNameAndSex() {
        super("");
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_name_sex;
    }

    //本例中需要返回两组key-value所以去覆写
    @Override
    public Object getValue() {
        //在getValue中返回两个值的组合，用以判断表单的值是否被改变
        //也可以直接覆写isValueChange()方法达到定制化
        if (nameEdit == null) {
            return null;
        }
        return nameEdit.getText().toString() + sexRadio.getCheckedRadioButtonId();
    }

    @Override
    public boolean isValueValid() {
        return nameEdit != null && !TextUtils.isEmpty(nameEdit.getText().toString());
    }

    @Override
    public Map<String, Object> getValueMap() {
        if (nameEdit == null) {
            return null;
        }

        Map<String, Object> valueMap = new HashMap<>(2);
        valueMap.put("name", nameEdit.getText().toString());
        int sexStrResId = sexRadio.getCheckedRadioButtonId() == R.id.man ? R.string.man : R.string.woman;
        valueMap.put("sex", nameEdit.getContext().getString(sexStrResId));

        return valueMap;
    }

    @Override
    protected void initInputView(BaseViewHolder holder) {
        nameEdit = getView(holder.itemView, R.id.editText);
        sexRadio = getView(holder.itemView, R.id.sexRadio);
    }
}
