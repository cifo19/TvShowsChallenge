package com.scene.app.util

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<MutableList<T>>.modifyValue(modifier: MutableList<T>.() -> Unit) {
    val currentValue = value ?: mutableListOf()
    value = currentValue.apply(modifier)
}
