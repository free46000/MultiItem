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
 * 姓名和性别录入Item，一个录入item对应多个提交的值{"name":"","sex":""}
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

    //本例中需要返回两组key-value所以去覆写getValueMap()
    @Override
    public Object getValue() {
        //在本方法中返回两个值的组合，作用是为判断表单的值是否被改变提供依据
        //也可以直接覆写isValueChange()方法达到定制化
        if (nameEdit == null) {
            return null;
        }
        return nameEdit.getText().toString() + sexRadio.getCheckedRadioButtonId();
    }

    @Override
    public boolean isValueValid() {
        //如果名字输入框录入的值不为空则有效；其它无效
        return nameEdit != null && !TextUtils.isEmpty(nameEdit.getText().toString());
    }

    @Override
    public Map<String, Object> getValueMap() {
        if (nameEdit == null) {
            return null;
        }

        //此处自己组装Map{name:name,sex:sex}并返回，这样可以达到一个Item返回两组值的效果
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
