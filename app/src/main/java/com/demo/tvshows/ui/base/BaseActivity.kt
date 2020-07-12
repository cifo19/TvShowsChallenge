package com.demo.tvshows.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.DialogCallback
import com.afollestad.materialdialogs.MaterialDialog
import com.demo.tvshows.R
import com.demo.tvshows.util.network.errorhandler.NoConnectionException
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    abstract val fragmentContainerId: Int

    override fun androidInjector() = androidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    fun onError(
        throwable: Throwable,
        onPositiveButtonClick: (() -> Unit)? = null,
        onTryAgain: (() -> Unit)? = null
    ) {
        if (throwable is NoConnectionException) {
            onNoInternetConnection(onTryAgain)
            return
        }

        val errorMessage = throwable.message ?: getString(R.string.unhandled_exception_message)

        MaterialDialog(this).show {
            message(text = errorMessage)
            positiveButton(res = R.string.ok, click = object : DialogCallback {
                override fun invoke(materialDialog: MaterialDialog) {
                    onPositiveButtonClick?.invoke()
                }
            })
            negativeButton(res = R.string.exit, click = onErrorDialogNegativeClick)
        }
    }

    fun addFragment(fragment: Fragment, tag: String, addToBackStack: Boolean = false) {
        supportFragmentManager.beginTransaction()
            .add(fragmentContainerId, fragment, tag)
            .apply { if (addToBackStack) addToBackStack(tag) }
            .commit()
    }

    private fun onNoInternetConnection(onTryAgain: (() -> Unit)? = null) {
        MaterialDialog(this).show {
            message(res = R.string.no_connection_exception)
            positiveButton(res = R.string.try_again, click = object : DialogCallback {
                override fun invoke(materialDialog: MaterialDialog) {
                    onTryAgain?.invoke()
                }
            })
            negativeButton(res = R.string.exit, click = onErrorDialogNegativeClick)
            cancelable(false)
        }
    }

    private val onErrorDialogNegativeClick = object : DialogCallback {
        override fun invoke(materialDialog: MaterialDialog) {
            finish()
        }
    }
}
