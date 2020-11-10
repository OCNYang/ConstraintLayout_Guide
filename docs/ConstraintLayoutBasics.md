# ConstraintLayout 基础讲解（官方 API 文档翻译）

## 相对定位

相对定位，在水平以及垂直轴线的方向上，一个控件的一边受到另一个控件的一边的约束。  
比如，Button B 受到 Button A 的约束：  
![relative_positioning](https://github.com/OCNYang/ConstraintLayout_Guide/blob/master/docs/relative-positioning.png?raw=true)  

```
<!--将按钮B的左边限制在按钮A的右边-->
<Button android:id="@+id/buttonA" ... />
<Button android:id="@+id/buttonB" ...
    app:layout_constraintLeft_toRightOf="@+id/buttonA" />
```

约束布局的坐标示意图：  
![](https://github.com/OCNYang/ConstraintLayout_Guide/blob/master/docs/relative-positioning-constraints.png?raw=true)  

以下是可用的约束条件列表：  

* layout_constraintLeft_toLeftOf
* layout_constraintLeft_toRightOf
* layout_constraintRight_toLeftOf
* layout_constraintRight_toRightOf
* layout_constraintTop_toTopOf
* layout_constraintTop_toBottomOf
* layout_constraintBottom_toTopOf
* layout_constraintBottom_toBottomOf
* layout_constraintBaseline_toBaselineOf
* layout_constraintStart_toEndOf
* layout_constraintStart_toStartOf
* layout_constraintEnd_toStartOf
* layout_constraintEnd_toEndOf

它们都通过 id 来和其他控件或父控件建立起约束关系。  
相对于父控件创建约束，如下：  


```
<Button android:id="@+id/buttonB" ...
    app:layout_constraintLeft_toLeftOf="parent" />
```

## margin 外边距

![Relative Positioning Margins](https://github.com/OCNYang/ConstraintLayout_Guide/blob/master/docs/relative-positioning-margin.png?raw=true)  


关于 margin 主要有以下几个属性：  
* android:layout_marginStart
* android:layout_marginEnd
* android:layout_marginLeft
* android:layout_marginTop
* android:layout_marginRight
* android:layout_marginBottom

> 注意，当如果使用了 `layout_constraintLeft_toRightOf = "@id/buttonB"` 这样的属性之后，只有 `layout_marginLeft` 这个属性才起作用，其他边，top，bottom 等等同理。
> 另外，在一个控件默认在左上角的时候，`layout_marginLeft` 或者 `layout_marginTop` 这样子的属性是没有用的，原因，没有被某一个控件约束到，如果使用了 `layout_constraintTop_toTopOf = “parent”` 这样子的属性的话，`layout_marginTop = "10dp"` 这样的属性就有用了。

当约束目标的可见性为 View.GONE 时，你还可以使用以下属性指示不同的边距值：  
* layout_goneMarginStart
* layout_goneMarginEnd
* layout_goneMarginLeft
* layout_goneMarginTop
* layout_goneMarginRight
* layout_goneMarginBottom

## “不可能的”约束（相反约束、双向拉力）

ConstraintLayout 非常有用的一个方面是对“不可能”约束的处理。  
比如：  

```
<android.support.constraint.ConstraintLayout ...>
     <Button android:id="@+id/button" ...
         android:layout_width="100dp"
         app:layout_constraintLeft_toLeftOf="parent"
         app:layout_constraintRight_toRightOf="parent/>
</>
```
当约束遇到这种情况时，因为控件不可能同时满足这两个约束（**宽度一定时**，和父布局左边对齐的同时和父布局右边也对齐），这时，约束就像相反的力量一样将控件平均分开，这样控件最终会在父布局中居中。  

![Centering Positioning](https://github.com/OCNYang/ConstraintLayout_Guide/blob/master/docs/centering-positioning.png?raw=true)

上面这个例子中相反约束造成的置中，只是在控件的宽度（或高度）一定时形成的效果。而如果把上面例子中的宽度改为 `android:layout_width="0dp` 时，这个时候，控件 button 的宽度就会因为双向约束的存在而充满父布局，相当于 `android:layout_width="match_parent"`

## Bias 偏移

上面讲到的“相反约束”时，默认的效果是控件被居中；但是此时你也可以通过使用偏移属性来调整定位，使其偏向一边。  

* layout_constraintHorizontal_bias
* layout_constraintVertical_bias

![Centering Positioning with Bias](https://github.com/OCNYang/ConstraintLayout_Guide/blob/master/docs/centering-positioning-bias.png?raw=true)

例如，将左侧的偏移值从默认的 50% 改为 30% ，这样左侧会更短，控件会更偏向左侧：  
```
<android.support.constraint.ConstraintLayout ...>
     <Button android:id="@+id/button" ...
         app:layout_constraintHorizontal_bias="0.3"
         app:layout_constraintLeft_toLeftOf="parent"
         app:layout_constraintRight_toRightOf="parent/>
     </>
```

使用 bias ,可以更好的适应屏幕大小变化的用户界面。

## 环形定位

您可以以一定的角度和距离约束一个控件中心相对于另一个控件中心。这允许你将一个控件放置在一个圆圈上。  

* layout_constraintCircle // 引用另一个小部件id
* layout_constraintCircleRadius // 到另一个小部件中心的距离
* layout_constraintCircleAngle //小部件应该处于哪个角度(角度，从0到360)

如图：  
![Circular Positioning](https://github.com/OCNYang/ConstraintLayout_Guide/blob/master/docs/circle1.png?raw=true)  
![Circular Positioning](https://github.com/OCNYang/ConstraintLayout_Guide/blob/master/docs/circle2.png?raw=true)  

```
<Button android:id="@+id/buttonA" ... />
<Button android:id="@+id/buttonB" ...
  app:layout_constraintCircle="@+id/buttonA"
  app:layout_constraintCircleRadius="100dp"
  app:layout_constraintCircleAngle="45" />
```

## 尺寸约束

### 最大值最小值尺寸

可以给 ConstraintLayout 设置最大高度宽度以及最小高度宽度，什么情况下可以这样设置呢，就是当高度宽度设置为 wrap_content 的时候可以用。  
属性共四个如下：  
* android:minWidth
* android:minHeight
* android:maxWidth
* android:maxHeight

### 控件尺寸

ConstraintLayout 下的子控件的尺寸，也就是 layout_height 和 layout_width，只能以下三种：  
1. 具体尺寸，比如 20dp，50dp 这样子
2. wrap_content
3. 0dp

> 如果使用 match_parent 属性，其实也是会有充满的效果的，但我们一般不提倡使用。但你可以使用 0dp 结合 反向约束，达到充满父布局的效果。

![](https://github.com/OCNYang/ConstraintLayout_Guide/blob/master/docs/dimension-match-constraints.png?raw=true)

### WRAP_CONTENT 尺寸

针对于 wrap_content，还有一种强制性约束，就是一般情况下 wrap_content 是无法约束它的结果尺寸的，一般情况下这样的控件的尺寸是跟着它的文字大小而进行改变。在 wrap_content 的情况下，为了限制最后它的尺寸 ConstraintLayout 提供了一下两个属性：  
* app:layout_constrainedWidth = "true | false"
* app:layout_constrainedHeight = "true | false"

### MATCH_CONSTRAINT 尺寸（0dp）

当使用 match_constraint 这个值的时候，默认就是将剩下可用的空间全部当成自己最后的尺寸，几个额外的属性是这样子的：

* layout_constraintWidth_min 和 layout_constraintHeight_min // 给尺寸设置最小的值
* layout_constraintWidth_max 和 layout_constraintHeight_max // 给尺寸设置最大的值
* layout_constraintWidth_percent 和 layout_constraintHeight_percent // 给尺寸设置一个父容器的百分比的值

当最大值最小值设置成一样时，那就相当于 wrap_content 的值这么大了

### 百分比尺寸

用百分比尺寸的话，遵循一下三个原则：
1. `layout_height` 或者 `layout_width` 要设置成 `match_constraint（0dp）`
2. 默认情况下应该这么设置：`app:layout_constraintWidth_default = "parent"` 或者 `app_layout_constriantHeight_default = “parent”`（1.1 版本中需要这么做，后面的版本就不需要这么做了）
3. 最后设置百分比尺寸，也就是设置 `layout_constraintWidth_percent` 和 `layout_constraintHeight_percent` 值，值在 0-1 之间，是小数，比如，0.2、0.8 这样子

### Ratio 比例尺寸

就是控件设置自身宽与高的一种比例，想使用比例尺寸的话，必须把 width 或者 height 一个设置为 `match_constriant（0dp）`，然后设置`layout_constraintDimensionRatio` 这个属性。  
比如：  

```
<Button 
    android:layout_width="wrap_content"
    android:layout_height="0dp"
    app:layout_constraintDimensionRatio="1:1" />
```
然后，宽和高就是一样的值了，宽多少高就是多少。
关于这个比例的值的两个说明：
1. 它是一个 float 值，代表宽和高的一个比例
2. 1:1 是格式是 width:height

当然这是一个尺寸是设置为 0dp 的情况，那么当两个尺寸都是 0dp 了呢，系统将会最大程度地满足这样一个比例值，在一个尺寸是 0dp 的基础上并且被约束了，怎么约束另一个呢，可以用 “H” 和 “ W” 这样的字眼来进行显示，当然是宽和高的首字母了，比如 “H，16：9”，“W，9：16” 这样子的，H 或者 W 代表的是前面那一个数字的尺寸，字母和尺寸之间用逗号 “,” 来进行分隔，像下面这样子用：  

```
<Button 
    android:layout_width="0dp"
    android:layout_height="0dp"
    app:layout_constraintDimensionRatio="H,16:9"
    app:layout_constraintBottom_toBottomOf="parent"
    app:layout_constraintTop_toTopOf="parent"/>  
```
这样子设置的话，高满屏，宽也被比例设置为满屏了。

## 链式约束 Chains

链式是指在单个方向上，为这个方向的几个不同控件链接成组，把组里的几个控件可以看成一个整体来进行约束。
**创建一个链**
如果一个组内不同的控件，通过双向连接链接在一起，则认为它们就是一个链。
如下图，有两个控件的最小的链：
![Chain](https://github.com/OCNYang/ConstraintLayout_Guide/blob/master/docs/chains.png?raw=true)

**链头**  
在一个链组中，链中的第一个元素我们称为链头控件（对于水平链来说，头部是最左边的部件，对于垂直链来说，头部是最上面的部件）。我们可以在链头控件上设置一定属性来控制整条链。  
![Chain Head](https://github.com/OCNYang/ConstraintLayout_Guide/blob/master/docs/chains-head.png?raw=true)

**链间隙**
如果在连接中指定了边距 margins ，则会将其考虑在内。在链扩展的情况下，将从分配的空间中扣除差额。
例如，在水平链上，如果一个元素定义右边框为10dp，下一个元素定义左边框为5dp，那么这两个元素之间的结果边框为15dp。
在计算链用于定位项的剩余空间时，要将项及其边距一起考虑。剩余空格不包含边距。

**链的样式**
当我们在上面说的**链头控件**上设置属性 `layout_constraintHorizontal_chainStyle` 或`layout_constraintVertical_chainStyle` 时，整条链的展示效果将根据指定的样式值改变(默认为CHAIN_SPREAD)。
* CHAIN_SPREAD // 元素将展开(默认样式)
* 权重链——在CHAIN_SPREAD模式中，如果某些小部件被设置为MATCH_CONSTRAINT（0dp），它们将分割可用空间
* CHAIN_SPREAD_INSIDE // 类似，但是链的端点不会散开
* CHAIN_PACKED // 链中的元素将被打包在一起。子元素的水平或垂直偏差属性将会影响包装元素的位置

![Chains Styles](https://github.com/OCNYang/ConstraintLayout_Guide/blob/master/docs/chains-styles.png?raw=true)  

**链的权重**

链的默认行为是将元素平均分布在可用空间中。如果一个或多个元素使用 0dp ，它们将使用可用的空白空间(在它们之间平均分配)。
属性 `layout_constraintHorizontal_weight` 和 `layout_constraintVertical_weight` 可以控制分配的比例。
例如，在一个使用 MATCH_CONSTRAINT（0dp） 包含两个元素的链上，第一个控件的权重为2，第二个控件的权重为1，第一个控件所占的空间将是第二个控件的两倍。



