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
 * Created by free46000 on 2017/4/1.
 */
public class DragFloatViewHelper {
    private View currTouchedView;
    private WindowManager wManager;
    private WindowManager.LayoutParams mParams;
    private int offsetX, offsetY;

    /**
     * 创建浮层view
     *
     * @param coverView 被覆盖的view，用于计算浮层位置
     * @param scale     缩放比例
     */
    public void createView(View coverView, float touchRawX, float touchRawY, float scale) {
        currTouchedView = createFloatView(coverView);

        wManager = (WindowManager) currTouchedView.getContext().getSystemService(
                Context.WINDOW_SERVICE);
        mParams = initParams(currTouchedView);
        mParams.width = (int) (coverView.getWidth() * scale);//窗口的宽和高
        mParams.height = (int) (coverView.getHeight() * scale);
        int[] location = getLocation(coverView);
        mParams.x = location[0];//窗口位置的偏移量
        mParams.y = location[1];
        wManager.addView(currTouchedView, mParams);

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
        WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
        mParams.token = floatView.getWindowToken();
        mParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;// 程序提示window
        mParams.format = PixelFormat.TRANSLUCENT;// 支持透明
        //mParams.format = PixelFormat.RGBA_8888;
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        mParams.gravity = Gravity.LEFT | Gravity.TOP;
        return mParams;
    }

    /**
     * 更新浮层View
     *
     * @param x X
     * @param y Y
     */
    public void updateView(int x, int y) {
        if (currTouchedView != null) {
            mParams.x = x - offsetX;
            mParams.y = y - offsetY;
            wManager.updateViewLayout(currTouchedView, mParams);
        }
    }

    /**
     * 移除浮层view
     */
    public void removeView() {
        if (currTouchedView != null && wManager != null) {
            wManager.removeView(currTouchedView);
        }
    }
}
