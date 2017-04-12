package com.freelib.multiitem.demo.bean;

/**
 * 主界面Bean
 * Created by free46000 on 2017/4/12.
 */
public class MainBean extends TextBean {
    private OnMainItemClickListener clickListener;

    public MainBean(String text, OnMainItemClickListener clickListener) {
        super(text);
        this.clickListener = clickListener;
    }

    public void onItemClick() {
        clickListener.onClick();
    }

    public interface OnMainItemClickListener {
        void onClick();
    }
}
