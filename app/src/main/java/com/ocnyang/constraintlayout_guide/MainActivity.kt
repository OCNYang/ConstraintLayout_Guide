package com.ocnyang.constraintlayout_guide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ocnyang.constraintlayout_guide.basics.ConstraintLayoutBasicsActivity

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
}