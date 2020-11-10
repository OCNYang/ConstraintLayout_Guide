# ConstraintLayout 虚拟辅助类部件

## Guideline 参照线

如果你熟悉 UI 设计软件你应该已经使用过参照线，并对它的作用熟悉了。参照线 guideline 提供了视觉上的参照用于 Views 的对齐，而且**不会在运行的时候显示**，只要你熟悉它的使用了就会发现它对你的对齐实现非常方便。

由上面的简介我们可以知道 Guideline 有以下特点：
* 种类：Guideline 引导线有：垂直引导线、水平引导线 两种；
* 作用：这些引导线用于约束视图组件位置的对应参考;
* 不可见：Guideline 引导线是不可见的，用户在界面中看不到引导线（实质上它是一个可见性一直为 View.GONE 的 View）；
* 引导线自身位置定位方式：使用 dp 单位的尺寸值表示相对开始或结束的位置，或百分比值，基于布局的边缘，设定引导线的位置。

Guideline 有以下属性：
* 方向属性（纵向或横向）：`android:orientation=“horizontal”`; 取值 `horizontal /vertical`;
* 自身位置定位（以始端为准）：`app:layout_constraintGuide_begin=“100dp”`；取值 dp 尺寸值；距离 顶部 (水平)，左侧 ( 垂直 ) 位置；
* 自身位置定位（以尾端为准）：`app:layout_constraintGuide_end=“100dp”`；取值 dp 尺寸值；距离 底部 (水平)，右侧 ( 垂直 ) 位置；
* 百分比自身位置定位：`app:layout_constraintGuide_percent=“0.5”`；取值 0 ~ 1.0 之间的小数，距始端的百分比位置。

![demo Guideline]()

## Barrier 阻碍线

Barrier 和 Guideline 一样，都是自己不可见，只是用来定位的控件。
这个小部件不好用语言描述，这里直接通过例子介绍。

一个例子
假如我们要实现这样一种效果，控件 C 以控件 A 和 B 的最右边缘对齐。如下图：

![Barrier 效果](https://github.com/OCNYang/ConstraintLayout_Guide/blob/master/docs/barrier_demo.png?raw=true)

**不用 Barrier 的实现方法**  
因为 A 和 B 的宽度是动态变化的，所以控件 C 不能简单的依赖于具体的 A 或 B 的右边缘，只能把 A 和 B 放到一个 ViewGroup 里面，然后控件 C 依赖于这个新的 ViewGroup 的右边缘。如下图：

![ViewGroup 实现](https://github.com/OCNYang/ConstraintLayout_Guide/blob/master/docs/barrier_demo2.png?raw=true)

这样实现虽然可以解决问题，但是却引入了一层嵌套。而使用 Barrier 则可以不用嵌套就能实现这样的效果。

**使用 Barrier 的解决方案**  

```
<!--首先，我们先定义控件 A 和 B-->
<Button
    android:id="@+id/btn_a"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:layout_constraintTop_toTopOf="parent"
    app:layout_constraintLeft_toLeftOf="parent"
    android:layout_marginTop="200dp"
    android:layout_marginLeft="30dp"
    android:text="这是控件A，我比较宽"/>
<Button
    android:id="@+id/btn_b"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:layout_constraintLeft_toLeftOf="@id/btn_a"
    app:layout_constraintTop_toBottomOf="@id/btn_a"
    android:layout_marginTop="30dp"
    android:text="这是控件B"/>
<!--其后，定义一个 Barrier-->
<android.support.constraint.Barrier
    android:id="@+id/barrier"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:barrierDirection="end"
    app:constraint_referenced_ids="btn_a,btn_b"/>
<!--最后，定义一个控件其约束依赖于 Barrier-->
<Button
    android:id="@+id/btn_c"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:layout_constraintLeft_toLeftOf="@id/barrier"
    app:layout_constraintTop_toTopOf="parent"
    android:layout_marginTop="200dp"
    android:layout_marginLeft="30dp"
    android:text="这是控件C"/>
```
   
其中，`app:constraint_referenced_ids="btn_a,btn_b"` 这句指定这个 Barrier 是用来控制 id 为 btn_a 和 btn_b 的两个控件。而 `app:barrierDirection="end"` 这句等于在这两个控件的右端设置一道 “屏障”。
最后，我们让控件 C 依赖于这个 Barrier。注意这句：`app:layout_constraintLeft_toLeftOf="@id/barrier"`

## Layer

Layer 的用法很简单，它的主要作用是给 ConstraintLayout 布局下的多个 View 添加**共同的背景**，也可用于添加**共同的动画**。(另外它是 ConstraintHelper 的一个子类，以后我们会再介绍)

例如添加共同背景：

```
<androidx.constraintlayout.widget.ConstraintLayout>
    <androidx.constraintlayout.helper.widget.Layer
        android:id="@+id/layer"
        android:layout_marginTop="50dp"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:background="@color/colorPrimary"
        app:constraint_referenced_ids="ivImage,tvName"
        app:layout_constraintLeft_toLeftOf="@id/ivImage"
        app:layout_constraintRight_toRightOf="parent"
        android:padding="10dp"
        app:layout_constraintTop_toTopOf="parent" />

    <ImageView
        android:id="@+id/ivImage"
        ...
        app:layout_constraintTop_toBottomOf="@id/layer" />

    <TextView
        android:id="@+id/tvName"
        ...
        app:layout_constraintTop_toBottomOf="@id/ivImage" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

![代码 demo]()

如果你想实现共同的动画，只需要将动画作用到 Layer 上就行了，这样它绑定的多个 View 就会有共同的动画效果。

## Flow 流布局

Flow 是 VirtualLayout 同样也是实现于 ContraintHelper，和其他子类一样，通过 `constraint_referenced_ids` 来引用 ConstraintLayout 下的子控件。可以水平或垂直定位引用的控件，类似于链。
通过 `flow_wrapMode` 可以指定具体的排列方式，有三种模式（后面还会详解）：
* **wrap none** : 简单地把 constraint_referenced_ids 里面的元素组成 chain, 即使空间不够  
![wrap none](https://github.com/OCNYang/ConstraintLayout_Guide/blob/master/docs/flow_wrap_none.png)
* **wrap chain** : 根据空间的大小和元素的大小组成一条或者多条 chain  
![wrap chain](https://github.com/OCNYang/ConstraintLayout_Guide/blob/master/docs/flow_wrap_chain.png)
* **wrap aligned** : wrap chain 类似，但是会对齐  
![wrap aligned](https://github.com/OCNYang/ConstraintLayout_Guide/blob/master/docs/flow_wrap_aligned.png)

VirtualLayout 是 ConstraintHelper 的实现，它也是普通的视图 View，所以你可以像其他 View 一样使用它。比如在它上面设置约束、给它设置 View 的一些属性（背景、padding 等）。
VirtualLayout 和其他 ViewGroup 之间的主要区别有：
* VirtualLayout 能够保持当前视图层次，不会添加嵌套
* 因为上面的特征，VirtualLayout 和它引用的控件是同一视图层，这样它自身和它引用的控件都可以被其他控件用于约束、引用等
* VirtualLayout 允许动态修改行为属性（比如 Flow，调整方向）

**flow_wrapMode = "none"**

将引用的多个控件创建一个水平/垂直的链，它是 Flow 默认的展示模式，有以下属性：  
* flow_horizontalStyle = "spread | spread_inside | packed"
* flow_verticalStyle = "spread | spread_inside | packed"
* flow_horizontalBias = "float"
* flow_verticalBias = "float"
* flow_horizontalGap = "dimension"
* flow_verticalGap = "dimension"
* flow_horizontalAlign = "start | end | center"
* flow_verticalAlign = "top | bottom | center | baseline

虽然引用的控件按照定义的方向以链的形式展示，但在其他方向维度上的布局展示方式由 `flow_horizontalAlign` 和 `flow_verticalAlign` 控制。

**flow_wrapMode = "chain"**  
创建的链和默认 wrap:none 模式相似，只是当引用的控件较多，一行/列 的空间不够展示时，后面的控件就会新添 一行/列 来展示，效果如前面简介中的图。
它具有的属性和 wrap:none 中的相同。另外还添加了一些特有的属性来为第一个链指定链的样式和链的偏差，这样就可以为 第一个链 相比于 最终创建的其他链 指定一些不同的链行为。

* flow_firstHorizontalStyle = "spread|spread_inside|packed"
* flow_firstVerticalStyle = "spread|spread_inside|packed"
* flow_firstHorizontalBias = "float"
* flow_firstVerticalBias = "float"

最后一个重要的属性是 `flow_maxElementsWrap`，它指定一行/列控件的最大数量。

**flow_wrapMode = "aligned"**  
它与 WRAP_CHAIN 相同的 XML 属性，不同之处在于引用的控件将以一组行和列而不是链来布局。因此，它没有指定链样式和偏差的属性。

## 参考

* [Android ConstraintLayout 进阶：Barrier 的使用及实例](https://www.jianshu.com/p/ed5183e44128)
* [ConstraintLayout 引导线 Guideline 约束 (简介 | 可视化操作 | 属性 | 水平引导线 | 垂直引导线 | 开始结束尺寸 | 百分比位置 | 约束组件)](https://blog.csdn.net/shulianghan/article/details/105982860)
* [Constraintlayout 2.0：你们要的更新](https://blog.csdn.net/Android725/article/details/107721625)

