## 前言
`RecyclerView`是一个大家常用的列表控件，在列表中不免会出现多种类型的布局，这时`adapter`中多种类型的判断就会充满着`switch`的坏味道，可怕的是需求变更，增加或修改新的类型时，所有的改动都在`adapter`中进行，没有一个良好的扩展性。
`MutliItem`主要就是解决这些问题，在正常使用中做到了`Adapter`零编码，解放了复杂的`Adapter`类，本库提供了多类型和`ViewHolder`创建绑定的管理器，这样`Adapter`通过依赖倒置与列表中的多类型解耦，还提高了扩展性。在本库中不同实体类可以直接当成数据源绑定到`adapter`中，你不用去担心`item type`的计算，并且对每种类型的`ViewHolder`也做到了隔离。
本库的定位并不是大而全，但是会尽量做到简单易用。

## 效果截图
![multi_item](https://github.com/free46000/cloud/raw/master/multiitem/multi_item.png)

## 下一步要做什么
- 易于构建的maven依赖方式
- 复杂页面即相同数据源不同展现的最佳实践（详情界面，聊天界面）
- 任务面板 跨`RecyclerView`的`Item`拖动 支持双击缩小后拖动（仿协同办公软件）
- 增加`header footer line`等一些可复用视图
- DataBinding特性支持
- 录入界面的复用和封装的demo代码（录入业务较多同学可以多多关注）
- 思考动画分割线等一些功能封装

## 用法
#### 添加依赖
配置gradle：

```
//近期提供
```
或者你也可以直接克隆源码

#### 多种类型列表用法
这里由于单一和多种类型写法上没有差别，所以就不单独贴出单一类型的列表代码了。
注册多种类型`ViewHolderManager`，并为`adapter`设置多种类型数据源:
``` java
//初始化adapter
BaseItemAdapter adapter = new BaseItemAdapter();
//为TextBean数据源注册ViewHolderManager管理类
adapter.register(TextBean.class, new TextViewManager());
//为更多数据源注册ViewHolderManager管理类
adapter.register(ImageTextBean.class, new ImageAndTextManager());
adapter.register(ImageBean.class, new ImageViewManager());

//组装数据源list
List<Object> list = new ArrayList<>();
list.add(new TextBean("AAA"));
list.add(new ImageBean(R.drawable.img1));
list.add(new ImageTextBean(R.drawable.img2, "BBB" + i));

//为adapter注册数据源list
adapter.setDataItems(list);

recyclerView.setAdapter(adapter);

```
`ViewHolder`管理类的子类`TextViewManager`类，其他类相似，下面贴出本类全部代码，是不是非常清晰：
``` java
public class ImageViewManager extends BaseViewHolderManager<ImageBean> {


    @Override
    public void onBindViewHolder(BaseViewHolder holder, ImageBean data) {
        //在指定viewHolder中获取控件为id的view
        ImageView imageView = getView(holder, R.id.image);
        imageView.setImageResource(data.getImg());
    }

    @Override
    protected int getItemLayoutId() {
        //返回item布局文件id
        return R.layout.item_image;
    }

}
```
至此本库的多种类型列表用法已经完成，并没有修改或继承`RecyclerView Adapter`类，完全使用默认实现`BaseItemAdapter`即可。

#### 设置点击监听
点击监听：
``` java
adapter.setOnItemClickListener(new OnItemClickListener() {
    @Override
    public void onItemClick(BaseViewHolder viewHolder) {
        //通过viewHolder获取需要的数据
        toastUser(String.format("你点击了第%s位置的数据：%s", viewHolder.getItemPosition()
        , viewHolder.getItemData()));
    }
});
```
长按监听：
``` java
adapter.setOnItemLongClickListener(new OnItemLongClickListener() {
    @Override
    public void onItemLongClick(BaseViewHolder viewHolder) {
        //通过viewHolder获取需要的数据
        toastUser(String.format("你长按了第%s位置的数据：%s", viewHolder.getItemPosition()
                , viewHolder.getItemData()));
    }
});
```

## 详解
#### 主要流程
- 为指定的数据源注册`ViewHolderManager`提供视图创建绑定等工作
- 在列表创建的过程中通过数据源在`ItemTypeManager`找到对应的`ViewHolderManager`
- 按照需要创建与刷新视图并对视图做一些通用处理

#### ViewHolder管理
ViewHolder管理源码类为`ViewHolderManager`，使用者会首先注册数据源和本实例的对应关系，由类型管理类提供统一管理。
- 提供了参数类，会在`adapter`调用本类方法的时候传入并做出通用处理
- 本类的设计使用泛型，是为了在后续回调方法中有更直观的类型体现，这也是强类型和泛型带来的好处，给人在编写代码的时候带来确定感
- 本类为抽象类需要重写`ViewHolder`的创建与绑定方法，为了方便后续使用，写了一个简单的`BaseViewHolderManager`实现类，请读者根据业务自行决定是否需要使用更灵活的基类，这里贴出需要复写的两个方法，延续了`Adapter`中的命名规则，在使用中减少一些认知成本：
``` java
/**
 * 创建ViewHolder
 * {@link android.support.v7.widget.RecyclerView.Adapter#onCreateViewHolder}
 */
@NonNull
public abstract V onCreateViewHolder(@NonNull ViewGroup parent);

/**
 * 为ViewHolder绑定数据
 * {@link android.support.v7.widget.RecyclerView.Adapter#onBindViewHolder}
 *
 * @param t 数据源
 */
public abstract void onBindViewHolder(@NonNull V holder, @NonNull T t);
```

#### 类型管理
类型管理源码类为`ItemTypeManager`，通过数据源`className List`和`viewHolderManager List`两组集合对类型进行管理，并对`Adapter`提供注册和对应关系查找等方法的支持，这里并没有把这个地方设计灵活，如果有一些变化还是希望可以在`ViewHolderManager`做出适配，在这里贴出关键代码，希望有好的方式也可以留言交流：
``` java
if (itemClassNames.contains(className)) {
    viewHolderManagers.set(itemClassNames.indexOf(className), provider);
} else {
    itemClassNames.add(className);
    viewHolderManagers.add(provider);
}
```
