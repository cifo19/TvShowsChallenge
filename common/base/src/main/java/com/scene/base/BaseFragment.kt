package com.scene.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_base.fragmentContent
import kotlinx.android.synthetic.main.fragment_base.fragmentToolbar

open class BaseFragment(@LayoutRes val contentLayoutRes: Int) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_base, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentContent.layoutResource = contentLayoutRes
        fragmentContent.inflate()
    }

    fun onError(
        throwable: Throwable,
        onPositiveButtonClick: (() -> Unit)? = null,
        onTryAgain: (() -> Unit)? = null
    ) {
        (requireActivity() as BaseActivity).onError(throwable, onPositiveButtonClick, onTryAgain)
    }

    fun setToolbarTitle(title: String) {
        fragmentToolbar.title = title
        fragmentToolbar.isVisible = true
    }

    fun addFragment(fragment: Fragment, tag: String, addToBackStack: Boolean = true) {
        val activity = requireActivity() as BaseActivity
        activity.addFragment(fragment, tag, addToBackStack)
    }
}
