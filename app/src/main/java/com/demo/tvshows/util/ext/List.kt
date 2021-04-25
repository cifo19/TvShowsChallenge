package com.demo.tvshows.util.ext

fun <T> List<T>.modify(modifier: MutableList<T>.() -> Unit) = run {
    toMutableList().apply(modifier)
}

