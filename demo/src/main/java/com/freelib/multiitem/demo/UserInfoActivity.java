package com.freelib.multiitem.demo;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.icu.text.UnicodeSet;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.holder.DataBindViewHolderManager;
import com.freelib.multiitem.demo.bean.ImageBean;
import com.freelib.multiitem.demo.bean.ImageTextBean;
import com.freelib.multiitem.demo.bean.ItemInfo;
import com.freelib.multiitem.demo.bean.TextBean;
import com.freelib.multiitem.demo.bean.UserBean;
import com.freelib.multiitem.demo.viewholder.ImageAndTextManager;
import com.freelib.multiitem.demo.viewholder.ImageViewManager;
import com.freelib.multiitem.demo.viewholder.TextViewManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.layout_recycler)
public class UserInfoActivity extends AppCompatActivity {
    @ViewById(R.id.recyclerView)
    protected RecyclerView recyclerView;

    public static void startActivity(Context context) {
        UserInfoActivity_.intent(context).start();
    }

    @AfterViews
    protected void initViews() {
        setTitle(R.string.user_info_title);

        UserBean userBean = getUserBean();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //初始化adapter
        BaseItemAdapter adapter = new BaseItemAdapter();
        //为UserBean数据源注册数据绑定View Holder Manager管理类
        adapter.register(ItemInfo.class, new DataBindViewHolderManager<>(
                R.layout.item_image_text_data_bind, BR.itemData));
        recyclerView.setAdapter(adapter);

        List<ItemInfo> list = new ArrayList<>(5);
        list.add(new ItemInfo("名字", userBean.getName()));
        list.add(new ItemInfo("性别", userBean.getSex()));
        list.add(new ItemInfo("年龄", userBean.getAge()));
        list.add(new ItemInfo("城市", userBean.getAddr()));
        list.add(new ItemInfo("介绍", userBean.getInfo()));
        adapter.setDataItems(list);
    }


    private UserBean getUserBean() {
        UserBean userBean = new UserBean();
        userBean.setName("张三");
        userBean.setSex("男");
        userBean.setInfo("简单介绍");
        userBean.setAddr("中国北京");
        userBean.setAge("26岁");
        return userBean;
    }

}
