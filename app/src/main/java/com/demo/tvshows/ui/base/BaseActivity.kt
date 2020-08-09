package com.demo.tvshows.ui.base

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.DialogCallback
import com.afollestad.materialdialogs.MaterialDialog
import com.demo.tvshows.R
import com.demo.tvshows.errorhandler.NoConnectionException

abstract class BaseActivity : AppCompatActivity() {

    abstract val fragmentContainerId: Int

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
