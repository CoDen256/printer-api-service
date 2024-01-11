package coden.cups.api.utils

import java.io.FileInputStream
import java.util.*

fun highlight(string: String): String = "\u001B[32m$string\u001B[0m"

fun loadProperties(filename: String): Properties {
    return FileInputStream(filename).use {
        Properties().apply { load(it) }
    }
}