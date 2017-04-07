package com.freelib.multiitem.demo;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.holder.DataBindViewHolderManager;
import com.freelib.multiitem.demo.bean.ImageTextBean;
import com.freelib.multiitem.demo.bean.TextBean;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

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
        //绑定写法一(简单)：直接传入BR.itemData(VariableId)
        adapter.register(TextBean.class, new DataBindViewHolderManager<>(R.layout.item_text_data_bind, BR.itemData));
        //绑定写法二(自由)：传入ItemBindView接口实例，可以定制绑定业务逻辑
        adapter.register(ImageTextBean.class, new DataBindViewHolderManager<>(
                R.layout.item_image_text_data_bind, this::onBindViewHolder));
        recyclerView.setAdapter(adapter);
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(new TextBean("AAA" + i));
            list.add(new ImageTextBean("img2", "BBB" + i));
        }

        adapter.setDataItems(list);
    }

    //将数据绑定的视图中，具体代码由DataBinding库自动生成
    private void onBindViewHolder(ViewDataBinding dataBinding, Object data) {
        //还可以写一些其他的绑定业务逻辑......
        dataBinding.setVariable(BR.itemData, data);
    }
}
