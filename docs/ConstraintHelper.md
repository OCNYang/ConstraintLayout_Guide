# ConstraintHelper

其实通过上面的教程，大家应该很熟悉 ConstraintHelper 了，至少对它的使用形式很熟悉了。通过 `app:constraint_referenced_ids` 来指定要反映特定效果的控件集合，用逗号分隔的 ID 来指定。

官方已提供实现的类有以下几个，基本上前面都已经介绍过了：

![ConstraintHelper 实现类](https://github.com/OCNYang/ConstraintLayout_Guide/blob/master/docs/ConstraintHelpers.png) 

ConstraintLayout2.0 除了提供以上几个已经实现的 ConstraintHelper 外，现在也支持自定义 ConstraintHelper 的方式。

## 自定义 ConstraintHelper

**为什么需要自定义：**
* 保持 View 的层级不变，不会像 ViewGroup 会增加 view 的层级；
* 封装一些特定的行为，方便使用
* 同一个 View 可以被多个 Helper 引用，可以很方便组合出一些复杂的效果

**如何自定义**  
* Helper 继承了 View，所以它本身也是 View；
* Helper 持有绑定的 View 的引用，所以可以获取到 View（getViews） 然后操作 view；
* 提供了 onLayout 前后的方法回调

Helper 主要的方法如下，基本上看名字就知道干嘛的，可以根据自己的需要来实现对应方法，不必全部：


```
public abstract class ConstraintHelper extends View {
    // 初始化方法，可以通过 attrs 获取属性的内容
    protected void init(AttributeSet attrs)
    public void onDraw(Canvas canvas)
    public void validateParams()
    // 执行于 onMeasure 开始的时候
    public void updatePreLayout(ConstraintLayout container)
    // onLayout 被调用的时候执行
    public void updatePostLayout(ConstraintLayout container)
    // onMeasure 每次调用都会执行
    public void updatePostMeasure(ConstraintLayout container)
    // 每当有变化时都会调用
    public void updatePostConstraints(ConstraintLayout constainer)
}
```

## 动手自定义一个

我们来实现一个简单的 Helper，当图片展示的时候，我们使它有揭露动画的效果：

![实现效果](https://github.com/OCNYang/ConstraintLayout_Guide/blob/master/docs/ConstraintHelperDemo.jpeg)

CircularReveal 的效果我们使用 ViewAnimationUtils 提供的 `createCircularReveal` 方法：


```
public static Animator createCircularReveal(View view,
            int centerX,  int centerY, float startRadius, float endRadius)
```

下面来看自定义 Helper 的具体代码，很简单：


```
class CircularRevealHelper @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintHelper(context, attrs, defStyleAttr) {
    override fun updatePostLayout(container: ConstraintLayout) {
        super.updatePostLayout(container)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val views = getViews(container)
            for (view in views) {
                val anim = ViewAnimationUtils.createCircularReveal(
                    view, view.width / 2,
                    view.height / 2, 0f,
                    // Math 的方法
                    hypot((view.height / 2).toDouble(), (view.width / 2).toDouble()).toFloat()
                )
                anim.duration = 3000
                anim.start()
            }
        }
    }
}
```

我们也可以通过自定义属性，来控制动画执行的时间：


```
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <declare-styleable name="CircularRevealHelper">
        <attr name="duration" format="integer" />
    </declare-styleable>
</resources>
```

在 Helper 获取属性：

```
// 同时修改上面 updatePostLayout 方法，将 mDuration 赋值给 anim.duration
var mDuration = 3000L
override fun init(attrs: AttributeSet?) {
    super.init(attrs)
    val typedArray =
        context.obtainStyledAttributes(attrs, R.styleable.CircularRevealHelper)
    mDuration = typedArray.getInt(R.styleable.CircularRevealHelper_duration, 3000).toLong()
    typedArray.recycle()
}
```

最后，直接在布局中使用就行了：


```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".constraint_helper.ConstraintHelperActivity">

    <ImageView
        android:id="@+id/iv1"
        android:layout_width="0dp"
        android:layout_height="0dp"
        android:scaleType="centerCrop"
        android:src="@drawable/ocnyang"
        app:layout_constraintBottom_toTopOf="@+id/iv2"
        app:layout_constraintHeight_percent="0.2"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <ImageView
        android:id="@+id/iv2"
        android:layout_width="0dp"
        android:layout_height="0dp"
        android:scaleType="centerCrop"
        android:src="@drawable/ocnyang"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintHeight_percent="0.5"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/iv1" />

    <com.ocnyang.constraintlayout_guide.constraint_helper.CircularRevealHelper
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:constraint_referenced_ids="iv1,iv2"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

## 参考

* [ConstraintLayout2.0 新特性詳解及實戰](https://codertw.com/%E7%A8%8B%E5%BC%8F%E8%AA%9E%E8%A8%80/751158/)  
* [【Android】ConstraintHelper の使い方](https://qiita.com/emusute1212/items/7b34ec4562309d183458)  
* [ConstraintHelpers 指南](https://www.polidea.com/blog/android-constraintlayout-the-guide-to-constrainthelpers/)  


