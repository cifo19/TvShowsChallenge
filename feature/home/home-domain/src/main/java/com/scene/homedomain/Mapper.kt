package com.scene.homedomain

interface Mapper<in I, out O> {
    fun map(input: I): O
}
