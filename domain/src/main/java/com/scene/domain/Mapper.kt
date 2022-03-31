package com.scene.domain

interface Mapper<in I, out O> {
    fun map(input: I): O
}
