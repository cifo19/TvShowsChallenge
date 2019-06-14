package com.demo.tvshows.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.demo.tvshows.R
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_base.childContentLayout

abstract class BaseActivity : AppCompatActivity() {

    @LayoutRes
    abstract fun getContentView(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        childContentLayout.apply {
            layoutResource = getContentView()
            inflate()
        }
    }
}
