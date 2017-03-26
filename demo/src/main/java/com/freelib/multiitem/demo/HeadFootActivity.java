package com.freelib.multiitem.demo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.holder.BaseViewHolder;
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

import static android.view.View.Y;

@EActivity(R.layout.layout_recycler)
public class HeadFootActivity extends AppCompatActivity {
    @ViewById(R.id.recyclerView)
    protected RecyclerView recyclerView;

    public static void startHeadFootActivity(Context context) {
        HeadFootActivity_.intent(context).start();
    }

    @AfterViews
    protected void initViews() {
        setTitle(R.string.head_foot_title);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //初始化adapter
        BaseItemAdapter adapter = new BaseItemAdapter();
        //为XXBean数据源注册XXManager管理类
        adapter.register(TextBean.class, new TextViewManager());
        adapter.register(ImageTextBean.class, new ImageAndTextManager());
        adapter.register(ImageBean.class, new ImageViewManager());
        TextView headView1 = new TextView(this);
        headView1.setText("通过addHeadView增加的head1");
        adapter.addHeadView(headView1);
        adapter.addHeadItem(new TextBean("通过addHeadItem增加的head2"));
        TextView footView1 = new TextView(this);
        footView1.setText("通过addFootView增加的foot1");
        adapter.addFootView(footView1);
        adapter.addFootItem(new TextBean("通过addFootItem增加的foot2"));
        recyclerView.setAdapter(adapter);
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            list.add(new TextBean("AAA" + i));
            list.add(new ImageBean(R.drawable.img1));
            list.add(new ImageTextBean(R.drawable.img2, "BBB" + i));
        }
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseViewHolder viewHolder) {
                if (viewHolder.getItemPosition() == 0) {
                    adapter.addDataItem(new TextBean("我是后加的"));
                } else {
                    adapter.removeDataItem(adapter.getDataList().size() - 1);
                }
            }
        });
        adapter.setDataItems(list);
    }
}
