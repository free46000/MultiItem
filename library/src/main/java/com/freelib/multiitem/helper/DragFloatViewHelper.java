package com.freelib.multiitem.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * 拖动时浮动Item视图辅助类
 * 浮动视图采用WindowManager + ImageView展示被拖动Item视图生成的Bitmap
 * Created by free46000 on 2017/4/1.
 */
public class DragFloatViewHelper {
    private View currTouchedView;
    private WindowManager manager;
    private WindowManager.LayoutParams params;
    private int offsetX;
    private int offsetY;

    /**
     * 创建浮层view
     *
     * @param coverView 被覆盖的view，用于计算浮层位置
     * @param scale     缩放比例
     */
    public void createView(View coverView, float touchRawX, float touchRawY, float scale) {
        currTouchedView = createFloatView(coverView);

        manager = (WindowManager) currTouchedView.getContext().getSystemService(
                Context.WINDOW_SERVICE);
        params = initParams(currTouchedView);
        params.width = (int) (coverView.getWidth() * scale);//窗口的宽和高
        params.height = (int) (coverView.getHeight() * scale);
        int[] location = getLocation(coverView);
        params.x = location[0];//窗口位置的偏移量
        params.y = location[1];
        manager.addView(currTouchedView, params);

        offsetX = (int) (touchRawX - location[0]);
        offsetY = (int) (touchRawY - location[1]);
    }

    /**
     * @param coverView 被覆盖的view，用于生产浮层View
     * @return 需要跟随手势浮动的 View
     */
    protected View createFloatView(View coverView) {
        ImageView floatView = new ImageView(coverView.getContext());
        coverView.destroyDrawingCache();
        coverView.setDrawingCacheEnabled(true);
        Bitmap bitmap = coverView.getDrawingCache();
        if (bitmap != null && !bitmap.isRecycled()) {
            floatView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            floatView.setImageBitmap(bitmap);
        }
        return floatView;
    }

    /**
     * @return 需要跟随手势浮动的 View
     */
    public View getFloatView() {
        return currTouchedView;
    }


    protected int[] getLocation(View coverView) {
        int[] result = new int[2];
        coverView.getLocationOnScreen(result);

        return result;
    }


    private WindowManager.LayoutParams initParams(View floatView) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.token = floatView.getWindowToken();
        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;// 程序提示window
        layoutParams.format = PixelFormat.TRANSLUCENT;// 支持透明
        //params.format = PixelFormat.RGBA_8888;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS |
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        return layoutParams;
    }

    /**
     * 更新浮层View
     *
     * @param x X
     * @param y Y
     */
    public void updateView(int x, int y) {
        if (currTouchedView != null) {
            params.x = x - offsetX;
            params.y = y - offsetY;
            manager.updateViewLayout(currTouchedView, params);
        }
    }

    /**
     * 移除浮层view
     */
    public void removeView() {
        if (currTouchedView != null && manager != null) {
            manager.removeView(currTouchedView);
        }
    }
}
