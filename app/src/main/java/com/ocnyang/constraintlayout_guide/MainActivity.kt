package com.ocnyang.constraintlayout_guide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ocnyang.constraintlayout_guide.assist_view.ConstraintLayoutAssistViewActivity
import com.ocnyang.constraintlayout_guide.basics.ConstraintLayoutBasicsActivity
import com.ocnyang.constraintlayout_guide.constraint_helper.ConstraintHelperActivity
import com.ocnyang.constraintlayout_guide.utils_widget.ConstraintLayoutUtilsWidgetActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * 相对定位
     */
    fun onClickBtn1(view: View) {
        startActivity(Intent(this, ConstraintLayoutBasicsActivity::class.java))
    }
    /**
     * 辅助类部件
     */
    fun onClickBtn2(view: View) {
        startActivity(Intent(this, ConstraintLayoutAssistViewActivity::class.java))
    }

    /**
     * utils 小控件
     */
    fun onClickBtn3(view: View) {
        startActivity(Intent(this, ConstraintLayoutUtilsWidgetActivity::class.java))
    }
    /**
     * utils 小控件
     */
    fun onClickBtn4(view: View) {
        startActivity(Intent(this, ConstraintHelperActivity::class.java))
    }
}