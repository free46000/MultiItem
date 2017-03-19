package com.freelib.multiitem.demo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.BaseViewHolder;
import com.freelib.multiitem.demo.bean.ImageBean;
import com.freelib.multiitem.demo.bean.ImageTextBean;
import com.freelib.multiitem.demo.bean.TextBean;
import com.freelib.multiitem.demo.viewholder.ImageAndTextManager;
import com.freelib.multiitem.demo.viewholder.ImageViewManager;
import com.freelib.multiitem.demo.viewholder.TextViewManager;
import com.freelib.multiitem.listener.OnItemClickListener;
import com.freelib.multiitem.listener.OnItemLongClickListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.layout_recycler)
public class ItemClickActivity extends AppCompatActivity {
    @ViewById(R.id.recyclerView)
    protected RecyclerView recyclerView;

    public static void startItemClickActivity(Context context) {
        ItemClickActivity_.intent(context).start();
    }

    @AfterViews
    protected void initViews() {
        setTitle(R.string.item_click_title);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //初始化adapter
        BaseItemAdapter adapter = new BaseItemAdapter();
        //为XXBean数据源注册XXManager管理类
        adapter.register(ImageTextBean.class, new ImageAndTextManager());
        recyclerView.setAdapter(adapter);
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(new ImageTextBean(R.drawable.img2, "AAAAA" + i));
        }

        adapter.setDataItems(list);


        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseViewHolder viewHolder) {
                //通过viewHolder获取需要的数据
                toastUser(String.format("你点击了第%s位置的数据：%s", viewHolder.getItemPosition()
                        , viewHolder.getItemData()));
            }
        });
        adapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void onItemLongClick(BaseViewHolder viewHolder) {
                //通过viewHolder获取需要的数据
                toastUser(String.format("你长按了第%s位置的数据：%s", viewHolder.getItemPosition()
                        , viewHolder.getItemData()));
            }
        });

    }

    private void toastUser(String msg) {
        Toast.makeText(ItemClickActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

}
