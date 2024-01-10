package coden.cups.api

import java.io.InputStream

interface PrinterService {
    fun getPrinters(): List<Printer>
    fun getPrinter(name: String): Printer
    fun createJob(printerName: String, data: InputStream, params: PrintParams=PrintParams.Builder().build()): PrintJob
}

data class Printer(
    val name: String,
    val url: String?,
    val description: String?,
    val location: String?,
    val isDefault: Boolean = false,
)

data class PrintParams(
    val copies: Int
) {
    data class Builder(
        var copies: Int = 0
    ) {
        fun copies(newCopies: Int?) = apply { this.copies = newCopies ?: this.copies }
        fun build() = PrintParams(copies)
    }
}

data class PrintJob(
    val id: Int
)