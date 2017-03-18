package com.freelib.multiitem.demo.viewholder;

import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelib.multiitem.adapter.BaseViewHolder;
import com.freelib.multiitem.adapter.BaseViewHolderManager;
import com.freelib.multiitem.demo.R;
import com.freelib.multiitem.demo.bean.ImageBean;

/**
 * @author free46000  2017/03/17
 * @version v1.0
 */
public class ImageViewManager extends BaseViewHolderManager<ImageBean> {


    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, @NonNull ImageBean data) {
        ImageView imageView = getView(holder, R.id.image);
        imageView.setImageResource(data.getImg());
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_image;
    }

}
