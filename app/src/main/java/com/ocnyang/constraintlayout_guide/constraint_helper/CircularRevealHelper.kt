package com.ocnyang.constraintlayout_guide.constraint_helper

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.ViewAnimationUtils
import androidx.constraintlayout.widget.ConstraintHelper
import androidx.constraintlayout.widget.ConstraintLayout
import com.ocnyang.constraintlayout_guide.R
import kotlin.math.hypot

/*******************************************************************
 *    * * * *   * * * *   *     *       @Author: OCN.Yang
 *    *     *   *         * *   *       @CreateDate: 2021/1/30 9:42 AM.
 *    *     *   *         *   * *       @Email: ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  @GitHub: https://github.com/OCNYang
 *******************************************************************/

class CircularRevealHelper @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintHelper(context, attrs, defStyleAttr) {

    var mDuration = 3000L
    override fun init(attrs: AttributeSet?) {
        super.init(attrs)
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CircularRevealHelper)
        mDuration = typedArray.getInt(R.styleable.CircularRevealHelper_duration, 3000).toLong()
        typedArray.recycle()
    }

    // updatePostLayout 会在 onLayout 之后调用，使用我们只需要在这里做动画就可以了
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
                anim.duration = mDuration
                anim.start()
            }
        }
    }

}