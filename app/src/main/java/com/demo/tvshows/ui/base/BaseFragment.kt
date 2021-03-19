package com.demo.tvshows.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.demo.tvshows.R
import com.demo.tvshows.ui.tvshows.TvShowsActivity
import kotlinx.android.synthetic.main.fragment_base.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

open class BaseFragment(@LayoutRes val contentLayoutRes: Int) : Fragment() {

    private var jobs: MutableList<Job> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_base, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentContent.layoutResource = contentLayoutRes
        fragmentContent.inflate()
    }

    override fun onStop() {
        super.onStop()
        jobs.forEach { it.cancel() }
    }

    fun launchWhenStarted(block: suspend CoroutineScope.() -> Unit) {
        lifecycleScope.launchWhenStarted(block).also(jobs::add)
    }

    fun onError(
        throwable: Throwable,
        onPositiveButtonClick: (() -> Unit)? = null,
        onTryAgain: (() -> Unit)? = null
    ) {
        (requireActivity() as TvShowsActivity).onError(throwable, onPositiveButtonClick, onTryAgain)
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
