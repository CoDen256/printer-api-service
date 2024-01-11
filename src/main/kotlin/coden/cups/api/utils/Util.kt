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