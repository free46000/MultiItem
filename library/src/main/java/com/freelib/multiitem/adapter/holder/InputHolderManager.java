package com.freelib.multiitem.adapter.holder;

import android.support.annotation.NonNull;

import com.freelib.multiitem.item.ItemInput;

import java.util.Collections;
import java.util.Map;

/**
 * 录入的的ViewHolderManager
 * Created by free46000 on 2017/4/10.
 */
public abstract class InputHolderManager<T extends ItemInput> extends BaseViewHolderManager<T> {
    protected Object originalValue;
    protected BaseViewHolder viewHolder;

    /**
     * 录入的key，和{@link #getValue()}一起组装为Map  如果为null则不组装 <br>
     * 如果是复杂的录入可以直接覆写{@link #getValueMap()}自己组装key-value Map
     *
     * @return 录入key
     */
    public abstract String getKey();

    /**
     * 录入的值，和{@link #getKey()}一起组装为Map  如果为null则不组装 <br>
     * 如果是复杂的录入可以直接覆写{@link #getValueMap()}自己组装key-value Map
     *
     * @return 录入值
     */
    public abstract Object getValue();

    @Override
    protected void onCreateViewHolder(@NonNull BaseViewHolder holder) {
        super.onCreateViewHolder(holder);
        initInputView(holder);
        originalValue = getValue();
        viewHolder = holder;
    }

    /**
     * 初始化Input视图，由于Input视图不可以复用，所以直接在初始化视图时设置好相关内容即可
     *
     * @param holder BaseViewHolder
     */
    protected abstract void initInputView(BaseViewHolder holder);

    @Deprecated
    @Override
    public void onBindViewHolder(BaseViewHolder holder, T t) {
    }

    @Deprecated
    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, @NonNull T t, @NonNull ViewHolderParams params) {
        super.onBindViewHolder(holder, t, params);
    }

    /**
     * 复杂业务可以覆写本方法，自定义返回多组key-value
     *
     * @return 录入的key-value Map
     */
    public Map<String, Object> getValueMap() {
        String key = getKey();
        Object value = getValue();
        if (key == null || value == null) {
            return null;
        }
        return Collections.singletonMap(key, value);
    }

    /**
     * 是否在初始化后发生改变，如用户手动更改<br>
     * 在{@link #initInputView(BaseViewHolder)}的时候会记录当时的value，然后调用本方法时去做对比
     *
     * @return true:已改变；false:未改变
     */
    public boolean isValueChange() {
        Object value = getValue();
        return value == null ? null != originalValue : !value.equals(originalValue);
    }

    /**
     * 是否验证有效，如Item不能为空
     *
     * @return true:有效；false:无效  默认true
     */
    public boolean isValueValid() {
        return true;
    }


    @Override
    public boolean isClickable() {
        return false;
    }
}
