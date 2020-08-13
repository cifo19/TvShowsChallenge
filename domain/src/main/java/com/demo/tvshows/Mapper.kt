package com.demo.tvshows

interface Mapper<in I, out O> {
    fun map(input: I): O
}
