package com.freelib.multiitem.demo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.animation.AlphaInAnimation;
import com.freelib.multiitem.animation.BaseAnimation;
import com.freelib.multiitem.animation.ScaleInAnimation;
import com.freelib.multiitem.animation.SlideInBottomAnimation;
import com.freelib.multiitem.animation.SlideInLeftAnimation;
import com.freelib.multiitem.animation.SlideInRightAnimation;
import com.freelib.multiitem.demo.bean.ImageBean;
import com.freelib.multiitem.demo.bean.ImageTextBean;
import com.freelib.multiitem.demo.bean.TextBean;
import com.freelib.multiitem.demo.viewholder.ImageAndTextManager;
import com.freelib.multiitem.demo.viewholder.ImageViewManager;
import com.freelib.multiitem.demo.viewholder.TextViewManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_animation)
public class AnimationActivity extends AppCompatActivity {
    @ViewById(R.id.recyclerView)
    protected RecyclerView recyclerView;

    private BaseItemAdapter adapter;

    public static void startActivity(Context context) {
        AnimationActivity_.intent(context).start();
    }

    @AfterViews
    protected void initViews() {
        setTitle(R.string.animation_title);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //初始化adapter
        adapter = new BaseItemAdapter();
        //为XXBean数据源注册XXManager管理类
        adapter.register(ImageTextBean.class, new ImageAndTextManager());
        recyclerView.setAdapter(adapter);
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(new ImageTextBean(R.drawable.img2, "BBB" + i));
        }

        adapter.setDataItems(list);
    }

    @CheckedChange({R.id.leftCheck, R.id.rightCheck, R.id.bottomCheck, R.id.scaleCheck, R.id.alphaCheck})
    protected void checkedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (!isChecked) {
            return;
        }
        BaseAnimation baseAnimation;
        switch (compoundButton.getId()) {
            case R.id.leftCheck:
                baseAnimation = new SlideInLeftAnimation();
                break;
            case R.id.rightCheck:
                baseAnimation = new SlideInRightAnimation();
                break;
            case R.id.bottomCheck:
                baseAnimation = new SlideInBottomAnimation();
                break;
            case R.id.alphaCheck:
                baseAnimation = new AlphaInAnimation();
                break;
            default:
                baseAnimation = new ScaleInAnimation();
                break;
        }
        //开启动画，并取消动画只在第一次加载时展示
        adapter.enableAnimation(baseAnimation, false);
    }
}
