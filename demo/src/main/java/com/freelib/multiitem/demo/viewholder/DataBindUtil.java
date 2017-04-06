package com.freelib.multiitem.demo.viewholder;

import android.databinding.BindingAdapter;
import android.databinding.ViewDataBinding;
import android.widget.ImageView;

import com.freelib.multiitem.demo.BR;

/**
 * Created by free46000 on 2017/4/6.
 */
public class DataBindUtil {
    @BindingAdapter({"android:src"})
    public static void setImageViewResource(ImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }

    public static void bindViewHolder(ViewDataBinding dataBinding, Object data) {
        dataBinding.setVariable(BR.itemData, data);
    }

}
