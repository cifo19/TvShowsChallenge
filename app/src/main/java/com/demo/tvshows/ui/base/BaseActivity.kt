package com.demo.tvshows.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.DialogCallback
import com.afollestad.materialdialogs.MaterialDialog
import com.demo.tvshows.R
import com.demo.tvshows.util.network.errorhandler.HandledServiceException
import com.demo.tvshows.util.network.errorhandler.NoConnectionException
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

    fun onError(
        throwable: Throwable,
        onPositiveButtonClick: (() -> Unit)? = null,
        onTryAgain: (() -> Unit)? = null
    ) {
        if (throwable is NoConnectionException) {
            onNoInternetConnection(onTryAgain)
            return
        }

        val errorMessage = when (throwable) {
            is HandledServiceException -> throwable.message
            else -> getString(R.string.unhandled_exception_message)
        }

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
