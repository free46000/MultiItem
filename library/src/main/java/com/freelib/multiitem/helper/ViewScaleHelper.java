package com.freelib.multiitem.helper;

import android.view.View;

import java.util.ArrayList;
import java.util.List;


/**
 * 视图缩放控制类
 * 适用于多Recycler类型的缩放
 * Created by free46000 on 2017/3/31.
 */
public class ViewScaleHelper {
    public static final int VIEW_WIDTH_KEY = -21681;
    public static final int VIEW_HEIGHT_KEY = -21682;

    private List<View> verticalViewList = new ArrayList<>();
    private View contentView;
    private View horizontalView;
    private boolean isInScaleMode;

    private int verticalWidth;
    private float scale = 0.5f;


    public synchronized boolean isInScaleMode() {
        return isInScaleMode;
    }

    public float getScale() {
        return scale;
    }

    /**
     * 开启或关闭缩放模式
     */
    public void toggleScaleModel() {
        if (isInScaleMode) {
            stopScaleModel();
        } else {
            startScaleModel();
        }
    }

    /**
     * 开启缩放
     */
    public synchronized void startScaleModel() {
        if (isInScaleMode) {
            return;
        }
        isInScaleMode = true;

        scaleContentView(contentView);
        scaleContentView(horizontalView);
        horizontalView.setScaleX(scale);
        horizontalView.setScaleY(scale);
        horizontalView.setPivotX(0f);
        horizontalView.setPivotY(0f);


        for (View view : verticalViewList) {
            //当父视图setScaleX的时候此时的宽度即为需要被记住的宽度，这样可以保证缩小后的宽度不再次恢复原来的大小
            verticalWidth = view.getWidth();
            scaleVerticalView(view, verticalWidth);
            view.requestLayout();
        }
    }

    /**
     * 关闭缩放
     */
    public synchronized void stopScaleModel() {
        if (!isInScaleMode) {
            return;
        }
        isInScaleMode = false;
        restoreView(contentView);
        restoreView(horizontalView);
        for (View view : verticalViewList) {
            restoreView(view);
            view.requestLayout();
        }
        horizontalView.setScaleX(1f);
        horizontalView.setScaleY(1f);

    }

    public void addVerticalView(View view) {
        verticalViewList.add(view);

        if (isInScaleMode) {
            scaleVerticalView(view, verticalWidth);
        }

    }

    /**
     * 缩放垂直视图
     * 垂直的视图宽度需要记住当前缩小后的宽度，然后设置到layout param，不然会充满屏幕宽度
     * 高度无需控制，就是需要充满屏幕
     *
     * @param width 需要被保持的宽度
     */
    private void scaleVerticalView(View view, int width) {
        recordViewWidth(view);
        view.getLayoutParams().width = width;
    }

    /**
     * 缩放内容视图
     * 内容视图高宽需要缩放1/scale倍数，后续进行setScaleX and Y的时候才能和原来的视图同等大小
     *
     * @param view
     */
    private void scaleContentView(View view) {
        recordViewWidth(view);
        recordViewHeight(view);
        view.getLayoutParams().width = (int) (view.getWidth() / scale);
        view.getLayoutParams().height = (int) (view.getHeight() / scale);
    }

    private void recordViewWidth(View view) {
        view.setTag(VIEW_WIDTH_KEY, view.getLayoutParams().width);
    }

    private void recordViewHeight(View view) {
        view.setTag(VIEW_HEIGHT_KEY, view.getLayoutParams().height);
    }

    private void restoreView(View view) {
        Object tag = null;
        if ((tag = view.getTag(VIEW_WIDTH_KEY)) != null) {
            view.getLayoutParams().width = (int) tag;
        }
        if ((tag = view.getTag(VIEW_HEIGHT_KEY)) != null) {
            view.getLayoutParams().height = (int) tag;
        }
    }

    public View getContentView() {
        return contentView;
    }

    public void setContentView(View contentView) {
        this.contentView = contentView;
    }

    public View getHorizontalView() {
        return horizontalView;
    }

    public void setHorizontalView(View horizontalView) {
        this.horizontalView = horizontalView;
    }
}
