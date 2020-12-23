package com.demo.tvshows.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.demo.tvshows.R
import com.demo.tvshows.databinding.FragmentBaseBinding
import com.demo.tvshows.ui.tvshows.TvShowsActivity
import kotlinx.android.synthetic.main.fragment_base.fragmentToolbar

open class BaseFragment<DB : ViewDataBinding>(@LayoutRes val contentLayoutRes: Int) : Fragment() {

    lateinit var binding : DB
    private lateinit var baseBinding: FragmentBaseBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        baseBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_base, container, false)
        return baseBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(baseBinding){
            fragmentContent.viewStub?.layoutResource = contentLayoutRes
            fragmentContent.viewStub?.inflate()
            binding = fragmentContent.binding as DB
        }
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
