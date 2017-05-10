package com.freelib.multiitem.demo.viewholder;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

/**
 * 数据绑定工具类
 * Created by free46000 on 2017/4/6.
 */
public class DataBindUtil {

    /**
     * 通过android:imageUrl可以在xml布局中直接为ImageView设置url地址，这样方便业务中使用第三方库加载网络图片
     *
     * @param imageView xml中ImageView实例
     * @param imgUrl    网络图片地址
     */
    @BindingAdapter({"android:imageUrl"})
    public static void setImageViewResource(ImageView imageView, String imgUrl) {
        Context context = imageView.getContext();
        //此处通过imgUrl字符串获取资源ID,具体使用根据业务需要
        int resId = context.getResources().getIdentifier(imgUrl, "drawable", context.getPackageName());
        imageView.setImageResource(resId);
    }
}
