package com.freelib.multiitem.helper;

import android.app.Activity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


/**
 * todo 宽度的处理不合适，包括缩小和恢复时候宽度高度的获取
 * Created by free46000 on 2016/10/10.
 */
public class ViewScaleHelper {
    private List<View> verticalViewList = new ArrayList<>();
    private View mContentView;
    private View horizontalView;
    private boolean isInScaleMode;
    private Activity mActivity;

    private int verticalWidth;
    private int scale = 2;

    public ViewScaleHelper(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public synchronized boolean isInScaleMode() {
        return isInScaleMode;
    }

    public float getScale() {
        return scale;
    }

    public synchronized void startScaleModel() {
        if (isInScaleMode) {
            return;
        }
        isInScaleMode = true;
        int width = mContentView.getLayoutParams().width;
        int height = mContentView.getLayoutParams().height;

        mContentView.getLayoutParams().width = width * scale;
        mContentView.getLayoutParams().height = height * scale;
        horizontalView.getLayoutParams().height = height * scale;
        horizontalView.getLayoutParams().width = width * scale;
        horizontalView.setScaleX((float) 1 / scale);
        horizontalView.setScaleY((float) 1 / scale);
        horizontalView.setPivotX(0f);
        horizontalView.setPivotY(0f);


        for (View view : verticalViewList) {
            verticalWidth = view.getWidth();
            view.getLayoutParams().width = view.getWidth();
            view.requestLayout();
        }
    }

    public void toggleScaleModel() {
        if (isInScaleMode) {
            stopScaleModel();
        } else {
            startScaleModel();
        }
    }

    public synchronized void stopScaleModel() {
        if (!isInScaleMode) {
            return;
        }
        isInScaleMode = false;
        mContentView.getLayoutParams().width = -1;
        mContentView.getLayoutParams().height = -1;
        horizontalView.getLayoutParams().height = -1;
        horizontalView.getLayoutParams().width = -1;
        for (View view : verticalViewList) {
            view.getLayoutParams().width = -1;
//            view.getLayoutParams().height = -2;
            view.requestLayout();
        }
        horizontalView.setScaleX(1f);
        horizontalView.setScaleY(1f);

    }

    public void addVerticalView(View view) {
        verticalViewList.add(view);

        if (isInScaleMode) {
            view.getLayoutParams().width = verticalWidth;
        }

    }

    public View getContentView() {
        return mContentView;
    }

    public void setContentView(View contentView) {
        mContentView = contentView;
    }

    public View getHorizontalView() {
        return horizontalView;
    }

    public void setHorizontalView(View horizontalView) {
        this.horizontalView = horizontalView;
    }
}
