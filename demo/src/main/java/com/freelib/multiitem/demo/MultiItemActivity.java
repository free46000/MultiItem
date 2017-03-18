package com.freelib.multiitem.demo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.BaseViewHolder;
import com.freelib.multiitem.demo.bean.ImageBean;
import com.freelib.multiitem.demo.bean.ImageTextBean;
import com.freelib.multiitem.demo.bean.TextBean;
import com.freelib.multiitem.demo.viewholder.ImageAndTextManager;
import com.freelib.multiitem.demo.viewholder.ImageViewManager;
import com.freelib.multiitem.demo.viewholder.TextViewManager;
import com.freelib.multiitem.listener.OnItemClickListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.layout_recycler)
public class MultiItemActivity extends AppCompatActivity {
    @ViewById(R.id.recyclerView)
    protected RecyclerView recyclerView;

    public static void startMultiItemActivity(Context context) {
        MultiItemActivity_.intent(context).start();
    }

    @AfterViews
    protected void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        BaseItemAdapter adapter = new BaseItemAdapter();
        //为TextBean数据源注册TextViewManager管理类
        adapter.register(TextBean.class, new TextViewManager());
        adapter.register(ImageTextBean.class, new ImageAndTextManager());
        adapter.register(ImageBean.class, new ImageViewManager());
        recyclerView.setAdapter(adapter);
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(new TextBean("AAA" + i));
            list.add(new ImageBean(R.drawable.ic_launcher));
            list.add(new ImageTextBean(R.drawable.ic_launcher, "BBB" + i));
        }

        adapter.setDataItems(list);


        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseViewHolder viewHolder) {
                //通过viewHolder获取需要的数据
                System.out.println(viewHolder.getItemData() + "==" + viewHolder.getItemPosition()
                        + "==" + viewHolder.getViewHolderManager());
            }
        });

    }
}
