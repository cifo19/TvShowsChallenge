package com.scene.uitest.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader

val gson: Gson = GsonBuilder().create()

inline fun <reified T> parseFile(fileName: String): T {
    val type = object : TypeToken<T>() {}.type
    return gson.fromJson(gson.getResourceReader(fileName), type)
}

fun Gson.getResourceReader(fileName: String): BufferedReader {
    val resource = javaClass.classLoader!!.getResourceAsStream(fileName)
    return BufferedReader(InputStreamReader(resource, Charsets.UTF_8))
}
