# ConstraintLayout 实用小控件

## ImageFilterButton

圆角图片，圆形图片怎么实现？自定义 View? 通过 ImageFilterButton, 一个属性就搞定；ImageFilterButton 能做的还有更多。

ImageFilterButton 的属性有以下：
* roundPercent 圆角比例，取值在 0-1，由正方形向圆形过渡；
* round 圆角值，这里是通过像素值控制圆角；
* altSrc，altSrc 和 src 属性是一样的概念，altSrc 提供的资源将会和 src 提供的资源通过 crossfade 属性形成交叉淡化效果。默认情况下，crossfade=0，altSrc 所引用的资源不可见，取值在 0-1。
* warmth 色温（调节图片的属性）：1 = neutral 自然，2 = warm 暖色，0.5 = cold 冷色；
* brightness 亮度（调节图片的属性）：0 = black 暗色，1 = original 原始，2 = twice as bright 两倍亮度；
* saturation 饱和度（调节图片的属性）：0 = grayscale 灰色，1 = original 原始，2 = hyper saturated 超饱和；
* contrast 对比（调节图片的属性）：1 = unchanged 原始，0 = gray 暗淡，2 = high contrast 高对比；

![Guideline](https://github.com/OCNYang/ConstraintLayout_Guide/blob/master/docs/ImageFilterButton1.jpeg)
![Guideline](https://github.com/OCNYang/ConstraintLayout_Guide/blob/master/docs/ImageFilterButton2.jpeg)


> 上面调节图片属性的取值都是 0、1、2，不过大家可以取其他值，效果也是不一样的。 
> 还有一个属性 overlay，官方解释：定义替换图像是在原始图像上淡入淡出还是与其交叉淡入淡出。默认为true。对于半透明对象设置为false。
> 由上方 Demo 截图可以看出，altSrc 和 crossfade 效果失效；brightness 亮度也看不出来差别效果。

## ImageFilterView

ImageFilterView 与 ImageFilterButton 的属性一模一样，只是它两继承的父类不一样，一些操作也就不一样。ImageFilterButton 继承自 AppCompatImageButton, 也就是 ImageButtion。而 ImageFilterView 继承自 ImageView。

## MockView
还记得你家项目经理给你的 UI 原型图么？想不想回敬一下项目经理，是时候了～
MockView 能简单的帮助构建 UI 界面，通过对角线形成的矩形 + 标签。

![Guideline](https://github.com/OCNYang/ConstraintLayout_Guide/blob/master/docs/MockView.jpeg)

中间黑色显示的是 MockView 的 id。通过 MockView 可以很好的构建一些 UI 思路。


