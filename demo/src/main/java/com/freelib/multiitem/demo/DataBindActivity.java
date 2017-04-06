package com.freelib.multiitem.demo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.demo.bean.ImageBean;
import com.freelib.multiitem.demo.bean.ImageTextBean;
import com.freelib.multiitem.demo.bean.TextBean;
import com.freelib.multiitem.demo.viewholder.DataBindViewHolderManager;
import com.freelib.multiitem.demo.viewholder.DataBindUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

// TODO: 2017/4/6 DataBindViewHolderManager移到lib中，测试lib中gradle加上databind true使用时不用databind是否可以
// TODO: 2017/4/6 思考写一个BaseDataBindManager构造函数只要layoutid（这个地方注释需要写清楚些）
// TODO: 2017/4/6 DataBindUtil增加一个通过字符串展示图片的适配方法
@EActivity(R.layout.layout_recycler)
public class DataBindActivity extends AppCompatActivity {
    @ViewById(R.id.recyclerView)
    protected RecyclerView recyclerView;

    public static void startActivity(Context context) {
        DataBindActivity_.intent(context).start();
    }

    @AfterViews
    protected void initViews() {
        setTitle(R.string.data_bind_title);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //初始化adapter
        BaseItemAdapter adapter = new BaseItemAdapter();
        //为XXBean数据源注册XXManager管理类
        adapter.register(TextBean.class, new DataBindViewHolderManager<>(
                R.layout.item_text_data_bind,
                DataBindUtil::bindViewHolder));
        adapter.register(ImageTextBean.class, new DataBindViewHolderManager<>(
                R.layout.item_image_text_data_bind,
                DataBindUtil::bindViewHolder));
        recyclerView.setAdapter(adapter);
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(new TextBean("AAA" + i));
            list.add(new ImageTextBean(R.drawable.img2, "BBB" + i));
        }

        adapter.setDataItems(list);
    }
}
