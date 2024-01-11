package coden.cups.api.utils

import java.io.FileInputStream
import java.util.*

class Util

fun highlight(string: String): String = "\u001B[32m$string\u001B[0m"

fun loadProperties(filename: String): Properties {
    return FileInputStream(filename).use {
        Properties().apply { load(it) }
    }
}

fun loadResourceProperties(filename: String): Properties {
    return Util::class.java.getResourceAsStream(filename).use {
        Properties().apply { load(it) }
    }
}

fun <T: Any> T.success(): Result<T>{
    return Result.success(this)
}

fun <T: Any> failure(e: Throwable = IllegalArgumentException()): Result<T>{
    return Result.failure(e)
}

inline fun <R, T> Result<T>.flatMap(transform: (T) -> Result<R>): Result<R> {
    return fold(
        {transform(it)},
        {Result.failure(it)}
    )
}